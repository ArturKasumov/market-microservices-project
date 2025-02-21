package com.arturk.storage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Audited
@Table(name = "MANUFACTURER")
@EntityListeners(AuditingEntityListener.class)
public class ManufacturerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manufacturer_seq_generator")
    @SequenceGenerator(name = "manufacturer_seq_generator", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();
}
