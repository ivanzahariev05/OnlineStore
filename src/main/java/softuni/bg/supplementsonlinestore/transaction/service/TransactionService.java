package softuni.bg.supplementsonlinestore.transaction.service;

import org.springframework.stereotype.Service;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionStatus;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionType;
import softuni.bg.supplementsonlinestore.transaction.repository.TransactionRepository;
import softuni.bg.supplementsonlinestore.user.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction createWithdrawalTransaction(BigDecimal amount, TransactionStatus transactionStatus, LocalDateTime dateTime, User sender, String failureReason) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.WITHDRAWAL)
                .status(transactionStatus)
                .transactionDate(dateTime)
                .sender(sender.getUsername())
                .owner(sender)
                .failureReason(failureReason)
                .build();

        return transactionRepository.save(transaction);
    }

    public Transaction createDepositTransaction(BigDecimal amount, TransactionStatus status, LocalDateTime dateTime, String sender, User owner, String failureReason) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(TransactionType.DEPOSIT)
                .status(status)
                .transactionDate(dateTime)
                .sender(sender)
                .owner(owner)
                .failureReason(failureReason)
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> findByUser(UUID id, String username) {
        return transactionRepository.findByUser(id, username
        );
    }
}
