package com.arturk.storage.service;

import com.arturk.storage.entity.ImageEntity;
import com.arturk.storage.entity.repository.ImageRepository;
import com.arturk.storage.exception.SavingImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final S3BucketService s3BucketService;

    public void saveImageEntity(Long productId, MultipartFile productImage) {
        if (productId == null || productImage == null || productImage.isEmpty()) {
            throw new IllegalArgumentException("Product ID and image must not be null or empty during saving product image");
        }

        try {
            String imageUrl = s3BucketService.uploadImage(
                    productImage.getOriginalFilename() + UUID.randomUUID(),
                    productImage.getBytes(),
                    productImage.getContentType()
            );

            ImageEntity imageEntity = new ImageEntity(productId, imageUrl);
            imageRepository.save(imageEntity);
        } catch (IOException e) {
            log.error("Error during saving product image", e);
            throw new SavingImageException();
        }
    }
}
