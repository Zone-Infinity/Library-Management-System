package me.isoham.lmsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsResponse {
	private long totalBooks;
	private long totalMembers;
	private long issuedBooks;
	private long availableBooks;
	private long overdueBooks;
}
