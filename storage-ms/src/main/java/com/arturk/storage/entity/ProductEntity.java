package com.arturk.storage.entity;

import com.arturk.storage.enums.ProductCategoryEnum;
import com.arturk.storage.exception.NotEnoughAvailableProductException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Audited
@Table(name = "PRODUCT")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
    @SequenceGenerator(name = "product_seq_generator", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MANUFACTURER_ID", referencedColumnName = "ID")
    private ManufacturerEntity manufacturer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    @NotAudited
    private List<ImageEntity> images = new ArrayList<>();

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        if(this.quantity < quantity) {
            throw new NotEnoughAvailableProductException();
        }
        this.quantity -= quantity;
    }

}
