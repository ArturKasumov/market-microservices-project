package com.arturk.storage.entity;

import com.arturk.storage.enums.ProductCategoryEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private ProductCategoryEnum category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MANUFACTURER_ID", referencedColumnName = "ID")
    private ManufacturerEntity manufacturer;

}
