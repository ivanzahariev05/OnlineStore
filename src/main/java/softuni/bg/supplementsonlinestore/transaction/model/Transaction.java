package softuni.bg.supplementsonlinestore.transaction.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import softuni.bg.supplementsonlinestore.user.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

}

