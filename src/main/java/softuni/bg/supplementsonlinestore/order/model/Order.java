package softuni.bg.supplementsonlinestore.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import softuni.bg.supplementsonlinestore.product.model.Product;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal totalPrice;

    private LocalDate orderDate;


    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany
    private List<Product> product;

    @OneToOne
    private Transaction transaction;


}
