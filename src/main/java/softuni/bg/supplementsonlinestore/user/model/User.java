package softuni.bg.supplementsonlinestore.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import softuni.bg.supplementsonlinestore.order.model.Order;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;
import softuni.bg.supplementsonlinestore.wallet.model.Wallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private java.lang.String username;

    @Column(unique = true, nullable = false)
    private java.lang.String email;

    @Column(nullable = false)
    private java.lang.String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    private java.lang.String imageUrl;

    private java.lang.String firstName;

    private java.lang.String lastName;

    private boolean isActive;

    private int ordersCount;

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Transaction> transactions;

    @OneToOne
    private Wallet wallet;


}

