package me.isoham.lmsbackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import me.isoham.lmsbackend.dto.MemberRequest;
import me.isoham.lmsbackend.dto.MemberResponse;
import me.isoham.lmsbackend.dto.TransactionResponse;
import me.isoham.lmsbackend.service.MemberService;
import me.isoham.lmsbackend.service.TransactionService;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Member", description = "Operations related to member management")
public class MemberController {

	private final MemberService service;
	private final TransactionService transactionService;

	public MemberController(MemberService service, TransactionService transactionService) {
		this.service = service;
		this.transactionService = transactionService;
	}

	@Operation(summary = "Get all members", description = "Returns all members")
	@ApiResponse(responseCode = "200", description = "Members fetched successfully")
	@GetMapping
	public List<MemberResponse> getMembers() {
		return service.getAllMembers();
	}

	@Operation(summary = "Get member transaction history", description = "Returns all transactions for a member ordered by status and issue date")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Transactions fetched successfully"),
			@ApiResponse(responseCode = "400", description = "Member not found") })
	@GetMapping("{id}/transactions")
	public List<TransactionResponse> getMemberTransaction(
			@Parameter(description = "Member id", example = "1") @PathVariable("id") Long id) {
		return transactionService.getTransactions(id);
	}

	@Operation(summary = "Create a new member", description = "Creates a new library member")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Member created successfully"),
			@ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(schema = @Schema())),
			@ApiResponse(responseCode = "409", description = "Member already exists") })
	@PostMapping
	public MemberResponse addMember(@Valid @RequestBody MemberRequest member) {
		return service.saveMember(member);
	}

	@Operation(summary = "Update member", description = "Updates an existing member")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Member updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid member id") })
	@PutMapping
	public MemberResponse updateMember(@Valid @RequestBody MemberRequest member) {

		if (member.getId() == null)
			throw new IllegalArgumentException("member id should be given");

		return service.saveMember(member);
	}

	@Operation(summary = "Delete member", description = "Soft deletes a member by setting active = false")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Member deleted"),
			@ApiResponse(responseCode = "400", description = "Member not found") })
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMember(@Parameter(description = "Member id", example = "1") @PathVariable("id") Long id) {
		service.deleteMember(id);
	}
}