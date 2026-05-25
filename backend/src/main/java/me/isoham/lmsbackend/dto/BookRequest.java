package me.isoham.lmsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for creating or updating a book")
public class BookRequest {

	@Schema(description = "Book ID (Not for creating)", example = "1")
	private Long id;

	@NotBlank(message = "Title is required")
	@Schema(description = "Book title", example = "Clean Code")
	private String title;

	@NotBlank(message = "Author is required")
	@Schema(description = "Book author", example = "Robert C. Martin")
	private String author;

	// private Long isbn;

	@NotNull(message = "Quantity is required")
	@Min(value = 0, message = "Quantity cannot be negative")
	@Schema(description = "Total quantity of books", example = "10")
	private Integer quantity;
}