package me.isoham.lmsbackend.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import me.isoham.lmsbackend.repository.TransactionRepository;

@Component
public class TransactionOverdueScheduler {

	private final TransactionRepository transactionRepository;

	public TransactionOverdueScheduler(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	// every 5 minutes
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void markOverdueTransactions() {
		int updated = transactionRepository.markOverdueTransactions();

		System.out.println("Updated overdue transactions: " + updated);
	}
}