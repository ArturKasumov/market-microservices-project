package com.arturk.storage.service;

import com.arturk.storage.convertor.ProductConvertor;
import com.arturk.storage.dto.ProductDto;
import com.arturk.storage.entity.ProductEntity;
import com.arturk.storage.entity.repository.ManufacturerRepository;
import com.arturk.storage.entity.repository.ProductRepository;
import com.arturk.storage.exception.ManufacturerNotFoundException;
import com.arturk.storage.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final ProductConvertor productConvertor;
    private final ImageService imageService;

    public ProductDto createProduct(ProductDto product) {
        log.debug("Creating product started");
        ProductEntity productEntity = productConvertor.toProductEntity(product);
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

    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        log.debug("Updating product with id: {} started", productId);
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        productConvertor.updateProductEntity(productDto, productEntity);

        if (productDto.getManufacturerId() != null) {
            productEntity.setManufacturer(
                    manufacturerRepository.findById(productDto.getManufacturerId())
                            .orElseThrow(ManufacturerNotFoundException::new)
            );
        } else {
            productEntity.setManufacturer(null);
        }

        productEntity = productRepository.save(productEntity);
        log.debug("Updating product with id: {} finished", productId);
        return productConvertor.toProductDto(productEntity);
    }

    public ProductDto patchProduct(Long productId, ProductDto productDto) {
        log.debug("Patching product with id: {} started", productId);
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        productConvertor.patchProductEntity(productDto, productEntity);

        if (productDto.getManufacturerId() != null) {
            productEntity.setManufacturer(
                    manufacturerRepository.findById(productDto.getManufacturerId())
                            .orElseThrow(ManufacturerNotFoundException::new)
            );
        }

        productEntity = productRepository.save(productEntity);
        log.debug("Patching product with id: {} finished", productId);
        return productConvertor.toProductDto(productEntity);
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<ProductDto> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .stream()
                .map(productConvertor::toProductDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByManufacturer(Long manufacturerId, Pageable pageable) {
        return productRepository.getAllByManufacturerId(manufacturerId, pageable)
                .stream()
                .map(productConvertor::toProductDto)
                .collect(Collectors.toList());
    }

    public Double getProductPrice(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        return productEntity.getPrice();
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class
    )
    public void uploadImagesForProduct(Long productId, MultipartFile[] productImages) {
        if (!productExists(productId)) {
            throw new ProductNotFoundException();
        }
        for (MultipartFile productImage : productImages) {
            imageService.saveImageEntity(productId, productImage);
        }
    }

    private boolean productExists(Long productId) {
        return productRepository.existsById(productId);
    }
}
