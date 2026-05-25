package me.isoham.lmsbackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.isoham.lmsbackend.dto.TransactionRequest;
import me.isoham.lmsbackend.dto.TransactionResponse;
import me.isoham.lmsbackend.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction", description = "Operations related to book issue and return")
public class TransactionController {

	private final TransactionService service;

	public TransactionController(TransactionService service) {
		this.service = service;
	}

	@Operation(summary = "Issue a book", description = "Issues a book to a member")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Book issued successfully"),
			@ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema())),
			@ApiResponse(responseCode = "409", description = "Book unavailable or already issued") })
	@PostMapping("/issue")
	public TransactionResponse issueBook(@Valid @RequestBody TransactionRequest request) {
		return service.issueBook(request.getMemberId(), request.getBookId());
	}

	@Operation(summary = "Return a book", description = "Returns a previously issued book")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Book returned successfully"),
			@ApiResponse(responseCode = "400", description = "Active transaction not found"),
			@ApiResponse(responseCode = "409", description = "Return operation invalid") })
	@PostMapping("/return")
	public TransactionResponse returnBook(@Valid @RequestBody TransactionRequest request) {
		return service.returnBook(request.getMemberId(), request.getBookId());
	}

	@Operation(summary = "Get all transactions", description = "Returns all transactions ordered by status and issue date")
	@ApiResponse(responseCode = "200", description = "Transactions fetched successfully")
	@GetMapping
	public List<TransactionResponse> getTransactions() {
		return service.getTransactions();
	}
}