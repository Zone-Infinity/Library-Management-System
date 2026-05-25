package me.isoham.lmsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for updating book quantity")
public class UpdateBookRequest {

	@NotNull(message = "Book ID is required")
	@Schema(description = "Book ID", example = "1")
	private Long id;

	@NotNull(message = "Quantity is required")
	@Positive(message = "Quantity should be positive")
	@Schema(description = "Quantity to add or remove", example = "5")
	private Integer quantity;
}