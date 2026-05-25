package me.isoham.lmsbackend.mapper;

import org.springframework.stereotype.Component;

import me.isoham.lmsbackend.dto.BookRequest;
import me.isoham.lmsbackend.dto.BookResponse;
import me.isoham.lmsbackend.entity.Book;

@Component
public class BookMapper {

	public BookResponse toResponse(Book book) {
		// , book.getIsbn()
		return new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity(),
				book.getAvailableQuantity());
	}

	public Book toEntity(BookRequest request) {
		return new Book(request.getId(), request.getTitle(), request.getAuthor(), null, request.getQuantity(),
				request.getQuantity(), true);
	}
}
