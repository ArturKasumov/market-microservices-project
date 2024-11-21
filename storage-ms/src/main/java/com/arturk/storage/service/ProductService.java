package com.arturk.storage.service;

import com.arturk.storage.convertor.ProductConvertor;
import com.arturk.storage.dto.ProductDto;
import com.arturk.storage.entity.ProductEntity;
import com.arturk.storage.entity.repository.ManufacturerRepository;
import com.arturk.storage.entity.repository.ProductRepository;
import com.arturk.storage.exception.ManufacturerNotFoundException;
import com.arturk.storage.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ProductConvertor productConvertor;

    public ProductDto createProduct(ProductDto product) {
        log.debug("Creating product started");
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setCategory(product.getCategory());
        productEntity.setManufacturer(
                manufacturerRepository.findById(product.getManufacturerId())
                        .orElseThrow(ManufacturerNotFoundException::new)
        );
        productEntity = productRepository.save(productEntity);
        log.debug("Product was created with id: {}", productEntity.getId());
        return productConvertor.toProductDto(productEntity);
    }

    public ProductDto getProductById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        return productConvertor.toProductDto(productEntity);
    }

    public ProductDto updateProduct(ProductDto product) {
        ProductEntity productEntity = productRepository.findById(product.getId())
                .orElseThrow(ProductNotFoundException::new);
        log.debug("Updating product with id: {} started", productEntity.getId());
        if (product.getName() != null) {
            productEntity.setName(product.getName());
        }
        if (product.getQuantity() != null) {
            productEntity.setQuantity(product.getQuantity());
        }
        if (product.getCategory() != null) {
            productEntity.setCategory(product.getCategory());
        }
        if (product.getManufacturerId() != null) {
            productEntity.setManufacturer(
                    manufacturerRepository.findById(product.getManufacturerId())
                            .orElseThrow(ManufacturerNotFoundException::new)
            );
        }
        log.debug("Updating product with id: {} finished", productEntity.getId());
        productEntity = productRepository.save(productEntity);
        return productConvertor.toProductDto(productEntity);
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }
}
