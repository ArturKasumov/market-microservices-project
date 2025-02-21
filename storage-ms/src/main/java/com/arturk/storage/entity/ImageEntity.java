package com.arturk.storage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IMAGE")
@Getter
@Setter
@NoArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_seq_generator")
    @SequenceGenerator(name = "image_seq_generator", sequenceName = "IMAGE_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "URL")
    private String url;

    public ImageEntity(Long productId, String url) {
        this.productId = productId;
        this.url = url;
    }
}
