package me.isoham.lmsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Member response payload")
public class MemberResponse {

	@Schema(description = "Member ID", example = "1")
	private Long id;

	@Schema(description = "Member name", example = "Soham Dokhale")
	private String name;

	@Schema(description = "Member email", example = "soham@example.com")
	private String email;

	@Schema(description = "Member phone number", example = "9876543210")
	private String phone;
}