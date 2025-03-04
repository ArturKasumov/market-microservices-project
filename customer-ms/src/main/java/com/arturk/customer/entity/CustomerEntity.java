package com.arturk.customer.entity;

import com.arturk.customer.exception.MoneyNotAvailableException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_generator")
    @SequenceGenerator(name = "customer_seq_generator", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AMOUNT_MONEY",
            precision = 10, scale = 2)
    private BigDecimal amountOfMoney;

    public void credit(double amount) {
        this.setAmountOfMoney(this.getAmountOfMoney().add(BigDecimal.valueOf(amount)));
    }

    public void debit(double amount) {
        if (this.getAmountOfMoney().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new MoneyNotAvailableException();
        }
        this.setAmountOfMoney(this.getAmountOfMoney().subtract(BigDecimal.valueOf(amount)));
    }
}
