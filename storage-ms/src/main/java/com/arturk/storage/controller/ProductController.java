package com.arturk.storage.controller;

import com.arturk.storage.dto.ProductDto;
import com.arturk.storage.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v4/product")
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) {
        ProductDto productDto = productService.createProduct(product);
        return ResponseEntity.ok(productDto);
    }

    @RequestMapping(
            value = "/{productId}",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }

    @RequestMapping(
            value = "/{productId}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,
                                                    @RequestBody ProductDto product) {
        ProductDto productDto = productService.updateProduct(productId, product);
        return ResponseEntity.ok(productDto);
    }

    @RequestMapping(
            value = "/{productId}",
            method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        return ResponseEntity.ok(productDtoList);
    }

    @RequestMapping(
            value = "/manufacturer/{manufacturerId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ProductDto>> getAllProductsByManufacturer(@PathVariable Long manufacturerId) {
        List<ProductDto> productDtoList = productService.getAllProductsByManufacturer(manufacturerId);
        return ResponseEntity.ok(productDtoList);
    }

    @RequestMapping(
            value = "/{productId}/price",
            method = RequestMethod.GET
    )
    public ResponseEntity<Double> getProductPrice(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductPrice(productId));
    }

    @RequestMapping(
            value = "/{productId}/images",
            method = RequestMethod.POST
    )
    public ResponseEntity<Void> uploadImagesForProduct(
            @PathVariable Long productId,
            @RequestBody MultipartFile[] productImages) {
        productService.uploadImagesForProduct(productId, productImages);
        return ResponseEntity.ok().build();
    }
}
