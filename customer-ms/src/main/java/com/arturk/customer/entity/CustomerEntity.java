package com.arturk.customer.entity;

import com.arturk.customer.exception.MoneyNotAvailableException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Column(name = "AMOUNT_MONEY")
    private Double amountOfMoney;

    public void credit(double amount) {
        this.setAmountOfMoney(this.getAmountOfMoney() + amount);
    }

    public void debit(double amount) {
        if (this.getAmountOfMoney() < amount) {
            throw new MoneyNotAvailableException();
        }
        this.setAmountOfMoney(this.getAmountOfMoney() - amount);
    }
}
