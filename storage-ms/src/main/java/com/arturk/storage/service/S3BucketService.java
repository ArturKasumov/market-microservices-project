package com.arturk.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class S3BucketService {

    @Value("${spring.cloud.aws.s3.bucket-name.images}")
    private String imagesBucket;
    @Value("${spring.cloud.aws.s3.bucket-name.reports}")
    private String reportBucket;
    @Value("${spring.cloud.aws.s3.region}")
    private String region;

    private final S3Client s3Client;

    public String uploadImage(String fileName, byte[] fileData, String contentType) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(imagesBucket)
                .key(fileName)
                .contentType(contentType)
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(fileData));

        return getFileUrl(fileName);
    }

    public void uploadFile(String fileName, Path filePath) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(reportBucket)
                .key(fileName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(filePath));
    }

    private String getFileUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", imagesBucket, region, fileName);
    }
}
