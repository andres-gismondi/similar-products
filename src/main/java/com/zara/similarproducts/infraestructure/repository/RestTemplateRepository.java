package com.zara.similarproducts.infraestructure.repository;

import com.zara.similarproducts.domain.domain.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class RestTemplateRepository {

    private static final String PRODUCT_SIMILAR_ID = "/product/%s/similarids";
    private static final String PRODUCT_PRODUCT_ID = "/product/%s";

    private RestTemplate restTemplate;

    @Autowired
    public RestTemplateRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<String>> getProductIds(String productId) {
        String finalUrl = String.format(PRODUCT_SIMILAR_ID, productId);
        return this.restTemplate.exchange(finalUrl, HttpMethod.GET,null ,new ParameterizedTypeReference<>(){});
    }

    public ResponseEntity<ProductDetail> getProductId(String productId) {
        String finalUrl = String.format(PRODUCT_PRODUCT_ID, productId);
        return this.restTemplate.exchange(finalUrl, HttpMethod.GET, null, ProductDetail.class);
    }

}
