package me.isoham.lmsbackend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions", indexes = { @Index(name = "idx_book_id", columnList = "bookId"),
		@Index(name = "idx_member_id", columnList = "memberId") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@ManyToOne
//	@JoinColumn(name = "book_id")
//	private Book book;
	private Long bookId;

//	@ManyToOne
//	@JoinColumn(name = "member_id")
//	private Member member;
	private Long memberId;

	private LocalDate issueDate;
	private LocalDate dueDate;
	private LocalDate returnDate;

	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean overdue;

	@PrePersist
	public void prePersist() {

		if (issueDate == null) {
			issueDate = LocalDate.now();
		}

		if (dueDate == null) {
			dueDate = issueDate.plusDays(14);
		}

		if (status == null) {
			status = TransactionStatus.ISSUED;
		}
	}
}

// can use dynamic insert, and columnDefination = DATE DEFAULT CURRENT_DATE
