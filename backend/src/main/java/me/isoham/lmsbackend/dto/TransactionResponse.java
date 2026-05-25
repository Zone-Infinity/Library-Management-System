package me.isoham.lmsbackend.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.isoham.lmsbackend.entity.TransactionStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Transaction response payload")
public class TransactionResponse {

	@Schema(description = "Transaction ID", example = "1")
	private Long id;

	@Schema(description = "Book ID", example = "2")
	private Long bookId;

	@Schema(description = "Book title", example = "Clean Code")
	private String bookTitle;

	@Schema(description = "Member ID", example = "1")
	private Long memberId;

	@Schema(description = "Member name", example = "Soham Dokhale")
	private String memberName;

	@Schema(description = "Book issue date", example = "2026-05-22")
	private LocalDate issueDate;

	@Schema(description = "Book due date", example = "2026-06-05")
	private LocalDate dueDate;

	@Schema(description = "Book return date", example = "2026-05-28")
	private LocalDate returnDate;

	@Schema(description = "Transaction status", example = "ISSUED")
	private TransactionStatus status;
}