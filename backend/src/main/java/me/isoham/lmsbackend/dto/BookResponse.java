package me.isoham.lmsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Book response payload")
public class BookResponse {

	@Schema(description = "Book ID", example = "1")
	private Long id;

	@Schema(description = "Book title", example = "Clean Code")
	private String title;

	@Schema(description = "Book author", example = "Robert C. Martin")
	private String author;

	// private Long isbn;

	@Schema(description = "Total quantity of books", example = "10")
	private Integer quantity;

	@Schema(description = "Available quantity of books", example = "7")
	private Integer availableQuantity;
}