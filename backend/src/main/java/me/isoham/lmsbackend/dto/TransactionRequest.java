package me.isoham.lmsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for issuing or returning a book")
public class TransactionRequest {

	@NotNull(message = "Member ID is required")
	@Schema(description = "Member ID", example = "1")
	private Long memberId;

	@NotNull(message = "Book ID is required")
	@Schema(description = "Book ID", example = "2")
	private Long bookId;
}