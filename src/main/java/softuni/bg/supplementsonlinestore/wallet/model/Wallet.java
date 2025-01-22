package softuni.bg.supplementsonlinestore.wallet.model;

import jakarta.persistence.*;
import softuni.bg.supplementsonlinestore.user.model.User;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToOne
    private User user;


}
