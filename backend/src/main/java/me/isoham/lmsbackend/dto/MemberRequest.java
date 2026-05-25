package me.isoham.lmsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for creating or updating a member")
public class MemberRequest {

	@Schema(description = "Member ID (Not for creating)", example = "1")
	private Long id;

	@NotBlank(message = "Name is required")
	@Schema(description = "Member name", example = "Soham Dokhale")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	@Schema(description = "Member email", example = "soham@example.com")
	private String email;

	@NotBlank(message = "Phone is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
	@Schema(description = "Member phone number", example = "9876543210")
	private String phone;
}