package me.isoham.lmsbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.isoham.lmsbackend.dto.BookRequest;
import me.isoham.lmsbackend.dto.BookResponse;
import me.isoham.lmsbackend.entity.Book;
import me.isoham.lmsbackend.mapper.BookMapper;
import me.isoham.lmsbackend.repository.BookRepository;

@Service
@Transactional
public class BookService {
	private final BookRepository bookRepository;
	private final BookMapper mapper;

	public BookService(BookRepository bookRepository, BookMapper mapper) {
		this.bookRepository = bookRepository;
		this.mapper = mapper;
	}

	// Fetch all active (not deleted) books from database
	// and convert them into response DTOs.
	public List<BookResponse> getAllBooks() {
		return bookRepository.findByActiveTrue().stream().map(mapper::toResponse).toList();
	}

	// Fetch a single book by id.
	//
	// Throws:
	// IllegalArgumentException -> if book does not exist
	public BookResponse getBookById(Long id) {
		return mapper.toResponse(bookRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new IllegalArgumentException("Book not found with id " + id)));
	}

	// Create a new book.
	//
	// Prevents duplicate books with same: title and author
	//
	// NOTE:
	// If books are updated and quantity changes,
	// availableQuantity does not auto-sync.
	// That's why there is #addBookQuantity and #removeBookQuantity
	// Throws:
	// IllegalStateException -> if duplicate book exists
	public BookResponse saveBook(BookRequest book) {

		if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
			throw new IllegalStateException("This book already exists!");
		}

		return mapper.toResponse(bookRepository.save(mapper.toEntity(book)));
	}

	// Soft delete a book by setting:
	// active = false
	//
	// Throws:
	// IllegalArgumentException -> if book does not exist
	public void deleteBook(Long id) {

		Book book = bookRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new IllegalArgumentException("Book not found"));

		book.setActive(false);

		bookRepository.save(book);
	}

	// Increase both:
	// - quantity
	// - availableQuantity
	//
	// Throws:
	// IllegalStateException -> if update fails
	public BookResponse addBookQuantity(Long id, int quantity) {

		int updated = bookRepository.incrementQuantity(id, quantity);

		if (updated == 0) {
			throw new IllegalStateException("Book unavailable");
		}

		return mapper.toResponse(bookRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new IllegalArgumentException("Book not found with id " + id)));
	}

	// Decrease both:
	// - quantity
	// - availableQuantity

	// Prevents quantity from becoming invalid.
	//
	// Throws:
	// IllegalStateException -> if update fails
	public BookResponse removeBookQuantity(Long id, int quantity) {

		int updated = bookRepository.decrementQuantity(id, quantity);

		if (updated == 0) {
			throw new IllegalStateException("Book unavailable");
		}

		return mapper.toResponse(bookRepository.findByIdAndActiveTrue(id)
				.orElseThrow(() -> new IllegalArgumentException("Book not found with id " + id)));
	}
}