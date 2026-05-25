package me.isoham.lmsbackend.dto;

import lombok.Data;

@Data
public class ErrorResponse {
	private Boolean isError;
	private String message;
}
