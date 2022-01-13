package com.zara.similarproducts.application.controller;

import com.zara.similarproducts.application.response.ProductDetailResponse;
import com.zara.similarproducts.domain.service.SimilarProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class SimilarProductController {

    private static final Logger logger = LoggerFactory.getLogger(SimilarProductController.class);

    @Autowired
    private SimilarProductService similarProductService;

    @GetMapping( "/{productId}/similar")
    public ResponseEntity<List<ProductDetailResponse>> getSimilarProducts(@PathVariable String id) {
        logger.info("Executing endpointto get similar product by id: [{}]", id);
        List<ProductDetailResponse> response = this.similarProductService.getSimilarProducts(id);
        return ResponseEntity.ok(response);
    }

}
