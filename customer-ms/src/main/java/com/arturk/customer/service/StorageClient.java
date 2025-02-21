package com.arturk.customer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "storage-client", url = "${storage-ms.product.url}")
public interface StorageClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}/price")
    ResponseEntity<Double> getProductPrice(@PathVariable Long productId);
}
