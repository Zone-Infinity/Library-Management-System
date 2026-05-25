package me.isoham.lmsbackend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.isoham.lmsbackend.dto.TransactionResponse;
import me.isoham.lmsbackend.entity.Book;
import me.isoham.lmsbackend.entity.Member;
import me.isoham.lmsbackend.entity.Transaction;
import me.isoham.lmsbackend.entity.TransactionStatus;
import me.isoham.lmsbackend.repository.BookRepository;
import me.isoham.lmsbackend.repository.MemberRepository;
import me.isoham.lmsbackend.repository.TransactionRepository;

@Service
@Transactional
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final MemberRepository memberRepository;
	private final BookRepository bookRepository;

	// private final EntityManager entityManager;

	public TransactionService(TransactionRepository transRepository, MemberRepository memberRespository,
			BookRepository bookRepository) {
		this.transactionRepository = transRepository;
		this.memberRepository = memberRespository;
		this.bookRepository = bookRepository;
		// this.entityManager = entityManager;
	}

//	// Fetch all transactions ordered by:
//	// 1. ISSUED first
//	// 2. RETURNED later
//	// 3. Latest issue date first
//	public List<TransactionResponse> getTransactions() {
//		List<Transaction> transactions = transactionRepository.findAllByOrderByStatusAscIssueDateDesc();
//
//		// Fetch all required books in a single query
//		Map<Long, String> books = bookRepository
//				.findAllById(transactions.stream().map(Transaction::getBookId).collect(Collectors.toSet())).stream()
//				.collect(Collectors.toMap(Book::getId, Book::getTitle));
//
//		// Fetch all required members in a single query
//		Map<Long, String> members = memberRepository
//				.findAllById(transactions.stream().map(Transaction::getMemberId).collect(Collectors.toSet())).stream()
//				.collect(Collectors.toMap(Member::getId, Member::getName));
//
//		// Convert entity list into response DTO list
//		return transactions.stream().map(it -> {
//			Long bookId = it.getBookId();
//			Long memberId = it.getMemberId();
//
//			return new TransactionResponse(it.getId(), bookId, books.get(bookId), memberId, members.get(memberId),
//					it.getIssueDate(), it.getDueDate(), it.getReturnDate(), it.getStatus());
//		}).toList();
//	}

	// SECOND APPROACH
//	public List<TransactionResponse> getTransactions() {
//		String queryString = "SELECT t.id, t.book_id, b.title, t.member_id, m.name, t.issue_date, t.due_date, t.return_date, t.status, t.overdue "
//				+ "FROM transactions t " + "JOIN members m ON t.member_id = m.id " + "JOIN books b ON t.book_id = b.id "
//				+ "ORDER BY t.status ASC, t.issue_date DESC";
//
//		Query query = entityManager.createNativeQuery(queryString);
//		List<Object[]> rows = query.getResultList();
//		return rows.stream().map(row -> new TransactionResponse(((Number) row[0]).longValue(), // id
//				((Number) row[1]).longValue(), // bookId
//				(String) row[2], // title
//				((Number) row[3]).longValue(), // memberId
//				(String) row[4], // memberName
//				((LocalDate) row[5]), ((LocalDate) row[6]), row[7] != null ? ((LocalDate) row[7]) : null,
//				TransactionStatus.valueOf((String) row[8]))).toList();
//	}

	public List<TransactionResponse> getTransactions() {
		return transactionRepository.findAllTransactionResponses();
	}

//	// Fetch all transactions for a specific member.
//	// (like the other #getTransactins)
//	public List<TransactionResponse> getTransactions(Long id) {
//		List<Transaction> transactions = transactionRepository.findByMemberIdOrderByStatusAscIssueDateDesc(id);
//
//		// Fetch all books referenced by these transactions
//		Map<Long, String> books = bookRepository
//				.findAllById(transactions.stream().map(Transaction::getBookId).collect(Collectors.toSet())).stream()
//				.collect(Collectors.toMap(Book::getId, Book::getTitle));
//
//		// Fetch all members referenced by these transactions
//		Map<Long, String> members = memberRepository
//				.findAllById(transactions.stream().map(Transaction::getMemberId).collect(Collectors.toSet())).stream()
//				.collect(Collectors.toMap(Member::getId, Member::getName));
//
//		// Convert transactions into DTO responses
//		return transactions.stream().map(it -> {
//			Long bookId = it.getBookId();
//			Long memberId = it.getMemberId();
//
//			return new TransactionResponse(it.getId(), bookId, books.get(bookId), memberId, members.get(memberId),
//					it.getIssueDate(), it.getDueDate(), it.getReturnDate(), it.getStatus());
//		}).toList();
//	}

	public List<TransactionResponse> getTransactions(Long memberId) {
		return transactionRepository.findAllTransactionResponsesByMemberId(memberId);
	}

	// Issue a book to a member.
	//
	// Steps:
	// 1. Validate member exists
	// 2. Validate book exists
	// 3. Prevent duplicate active issue
	// 4. Ensure copies are available
	// 5. Decrement available quantity
	// 6. Save transaction
	public TransactionResponse issueBook(Long memberId, Long bookId) {

		// Validate member existence
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("Member not found with id " + memberId));

		// Validate book existence
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new IllegalArgumentException("Book not found with id " + bookId));

		// Prevent issuing the same book multiple times to the same member
		boolean alreadyIssued = transactionRepository.existsByBookIdAndMemberIdAndStatus(bookId, memberId,
				TransactionStatus.ISSUED);

		if (alreadyIssued) {
			throw new IllegalStateException("Book already issued to member");
		}

		// Ensure at least one copy is available
		if (book.getAvailableQuantity() <= 0) {
			throw new IllegalStateException("No available copies");
		}

		// decrement available quantity
		//
		// Returns:
		// 1 -> updated successfully
		// 0 -> update failed
		int updated = bookRepository.decrementAvailableQuantity(bookId, 1);

		if (updated == 0) {
			throw new IllegalStateException("Book unavailable");
		}

		// Create new transaction
		Transaction transaction = new Transaction();

		transaction.setBookId(book.getId());
		transaction.setMemberId(member.getId());

		// Save transaction
		transaction = transactionRepository.save(transaction);

		// Build response DTO
		TransactionResponse response = new TransactionResponse(transaction.getId(), bookId, book.getTitle(), memberId,
				member.getName(), transaction.getIssueDate(), transaction.getDueDate(), transaction.getReturnDate(),
				transaction.getStatus());

		return response;
	}

	// Return a previously issued book.
	//
	// Steps:
	// 1. Validate member exists
	// 2. Validate book exists
	// 3. Validate active transaction exists
	// 4. Mark transaction as returned
	// 5. Increment available quantity atomically
	public TransactionResponse returnBook(Long memberId, Long bookId) {

		// Validate member existence
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		// Validate book existence
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));

		// Prevent available quantity from exceeding total quantity
		if (book.getQuantity() < book.getAvailableQuantity() + 1) {
			throw new IllegalStateException("No more books can be returned, total quantity = available quantity");
		}

		// Find active issued transaction
		Transaction transaction = transactionRepository
				.findByBookIdAndMemberIdAndStatus(bookId, memberId, TransactionStatus.ISSUED)
				.orElseThrow(() -> new IllegalArgumentException("No active transaction found"));

		// Mark transaction as returned
		transaction.setReturnDate(LocalDate.now());
		transaction.setStatus(TransactionStatus.RETURNED);

		// increment available quantity
		int updated = bookRepository.incrementAvailableQuantity(bookId, 1);

		if (updated == 0) {
			throw new IllegalStateException("Book unavailable (no book found)");
		}

		// Save updated transaction
		transactionRepository.save(transaction);

		// Build response DTO
		TransactionResponse response = new TransactionResponse(transaction.getId(), bookId, book.getTitle(), memberId,
				member.getName(), transaction.getIssueDate(), transaction.getDueDate(), transaction.getReturnDate(),
				transaction.getStatus());

		return response;
	}
}