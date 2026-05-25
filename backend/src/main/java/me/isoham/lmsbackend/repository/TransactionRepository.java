package me.isoham.lmsbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.isoham.lmsbackend.dto.TransactionResponse;
import me.isoham.lmsbackend.entity.Transaction;
import me.isoham.lmsbackend.entity.TransactionStatus;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	// does not work for dto, use interface projection and then map to
	// TransactionResponse
//	@Query(value = """
//			    SELECT
//			        t.id AS id,
//			        b.id AS bookId,
//			        b.title AS bookTitle,
//			        m.id AS memberId,
//			        m.name AS memberName,
//			        t.issue_date AS issueDate,
//			        t.due_date AS dueDate,
//			        t.return_date AS returnDate,
//			        t.status AS status
//			    FROM transactions t
//			    JOIN books b ON t.book_id = b.id
//			    JOIN members m ON t.member_id = m.id
//			    ORDER BY t.status ASC, t.issue_date DESC
//			""", nativeQuery = true)
//	List<TransactionResponse> findAllTransactionResponses();

	@Query("""
			    SELECT new me.isoham.lmsbackend.dto.TransactionResponse(
			        t.id,
			        b.id,
			        b.title,
			        m.id,
			        m.name,
			        t.issueDate,
			        t.dueDate,
			        t.returnDate,
			        t.status
			    )
			    FROM Transaction t
			    JOIN Book b ON t.bookId = b.id
			    JOIN Member m ON t.memberId = m.id
			    ORDER BY t.status ASC, t.issueDate DESC
			""")
	List<TransactionResponse> findAllTransactionResponses();

	@Query("""
			    SELECT new me.isoham.lmsbackend.dto.TransactionResponse(
			        t.id,
			        b.id,
			        b.title,
			        m.id,
			        m.name,
			        t.issueDate,
			        t.dueDate,
			        t.returnDate,
			        t.status
			    )
			    FROM Transaction t
			    JOIN Book b ON t.bookId = b.id
			    JOIN Member m ON t.memberId = m.id
			    WHERE m.id = :memberId
			    ORDER BY t.status ASC, t.issueDate DESC
			""")
	List<TransactionResponse> findAllTransactionResponsesByMemberId(@Param("memberId") Long memberId);

	List<Transaction> findAllByOrderByStatusAscIssueDateDesc();

	boolean existsByBookId(Long bookId);

	boolean existsByMemberId(Long bookId);

	Optional<Transaction> findByBookIdAndMemberIdAndStatus(Long bookId, Long memberId, TransactionStatus status);

	List<Transaction> findByMemberIdOrderByStatusAscIssueDateDesc(Long memberId);

	boolean existsByBookIdAndMemberIdAndStatus(Long bookId, Long memberId, TransactionStatus status);

	@Modifying
	@Query("""
			UPDATE Transaction t
			SET t.overdue = true
			WHERE t.status = 'ISSUED'
			AND t.dueDate < CURRENT_DATE
			AND t.overdue = false
			""")
	int markOverdueTransactions();
}
