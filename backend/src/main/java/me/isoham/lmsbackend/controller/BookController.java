package me.isoham.lmsbackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.isoham.lmsbackend.dto.BookRequest;
import me.isoham.lmsbackend.dto.BookResponse;
import me.isoham.lmsbackend.dto.UpdateBookRequest;
import me.isoham.lmsbackend.service.BookService;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "Operations related to library books")
public class BookController {

	private final BookService service;

	public BookController(BookService service) {
		this.service = service;
	}

	@Operation(summary = "Get all books", description = "Returns all books")
	@ApiResponse(responseCode = "200", description = "Books fetched successfully")
	@GetMapping
	public List<BookResponse> getBooks() {
		return service.getAllBooks();
	}

	@Operation(summary = "Get book by id", description = "Returns a single book using its id")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Book found"),
			@ApiResponse(responseCode = "400", description = "Book not found") })
	@GetMapping("/{id}")
	public BookResponse getBookById(@Parameter(description = "Book id", example = "1") @PathVariable("id") Long id) {
		return service.getBookById(id);
	}

	@Operation(summary = "Create a new book", description = "Creates a new book entry")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Book created successfully"),
			@ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema())),
			@ApiResponse(responseCode = "409", description = "Book already exists") })
	@PostMapping
	public BookResponse addBook(@Valid @RequestBody BookRequest book) {
		return service.saveBook(book);
	}

//	@PutMapping
//	public ResponseEntity<BookResponse> updateBook(@RequestBody BookRequest book) { // make another dto for updating //
//																					// books
//		if (book.getId() == null)
//			return ResponseEntity.badRequest().build();
//		return ResponseEntity.ok(service.saveBook(book));
//	}

	@Operation(summary = "Increase book quantity", description = "Adds quantity to both total and available quantity")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Quantity updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request"),
			@ApiResponse(responseCode = "409", description = "Book unavailable") })
	@PatchMapping("/add")
	public BookResponse addBookQuantity(@Valid @RequestBody UpdateBookRequest request) {
		return service.addBookQuantity(request.getId(), request.getQuantity());
	}

	@Operation(summary = "Decrease book quantity", description = "Removes quantity from both total and available quantity")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Quantity updated"),
			@ApiResponse(responseCode = "400", description = "Invalid request"),
			@ApiResponse(responseCode = "409", description = "Book unavailable") })
	@PatchMapping("/remove")
	public BookResponse removeBookQuantity(@Valid @RequestBody UpdateBookRequest request) {
		return service.removeBookQuantity(request.getId(), request.getQuantity());
	}

	// make a createa and update dto
	// put and patch mapping
	// full replacement and partial resp.

	// and convert random response code of does not exist into 404 using custom
	// exceptions

	@Operation(summary = "Delete a book", description = "Soft deletes a book by setting active = false")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Book deleted"),
			@ApiResponse(responseCode = "400", description = "Book not found") })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@Parameter(description = "Book id", example = "1") @PathVariable("id") Long id) {
		service.deleteBook(id);
	}
}