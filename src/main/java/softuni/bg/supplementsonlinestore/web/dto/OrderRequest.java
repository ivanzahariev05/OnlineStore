package softuni.bg.supplementsonlinestore.web.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderRequest {

    @Min(1)
    private BigDecimal totalPrice;

    @NotNull
    private LocalDate orderDate;

    private Transaction transaction;


}
