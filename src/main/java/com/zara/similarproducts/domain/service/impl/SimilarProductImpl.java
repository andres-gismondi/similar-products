package com.zara.similarproducts.domain.service.impl;

import com.zara.similarproducts.application.exception.ProductServiceException;
import com.zara.similarproducts.application.response.ProductDetailResponse;
import com.zara.similarproducts.domain.domain.ProductDetail;
import com.zara.similarproducts.domain.mapper.SimilarProductMapper;
import com.zara.similarproducts.domain.repository.SimilarProductRepository;
import com.zara.similarproducts.domain.service.SimilarProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimilarProductImpl implements SimilarProductService {

    private static final Logger logger = LoggerFactory.getLogger(SimilarProductImpl.class);

    private SimilarProductRepository repository;
    private SimilarProductMapper mapper;

    @Autowired
    public SimilarProductImpl(SimilarProductRepository repository, SimilarProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDetailResponse> getSimilarProducts(String id) throws ProductServiceException {
        logger.info("Executing service with id: [{}]", id);
        try {
            List<ProductDetail> products = this.repository.getSimilarProduct(id);
            return this.mapper.mapToSimilarProductResponse(products);
        } catch (Exception e) {
            logger.error("Error getting similar products", e);
            throw new ProductServiceException("Error getting similar products", e);
        }
    }

}
