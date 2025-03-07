package com.arturk.storage.entity.repository;

import com.arturk.storage.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> getAllByManufacturerId(Long manufacturerId, Pageable pageable);
}
