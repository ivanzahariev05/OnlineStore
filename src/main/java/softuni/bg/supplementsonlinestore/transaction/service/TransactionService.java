package softuni.bg.supplementsonlinestore.transaction.service;

import org.springframework.stereotype.Service;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionStatus;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionType;
import softuni.bg.supplementsonlinestore.transaction.repository.TransactionRepository;
import softuni.bg.supplementsonlinestore.user.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction createNewTransaction(BigDecimal amount, TransactionType transactionType, TransactionStatus transactionStatus, LocalDateTime dateTime, User sender, User receiver) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(transactionType)
                .status(transactionStatus)
                .transactionDate(dateTime)
                .sender(sender)
                .receiver(receiver)
                .build();

        return transactionRepository.save(transaction);
    }


}
