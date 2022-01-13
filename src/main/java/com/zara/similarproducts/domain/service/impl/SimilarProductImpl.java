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
import java.util.Objects;
import java.util.stream.Collectors;

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
            List<String> ids = this.repository.getSimilarProduct(id);
            List<ProductDetail> products = this.getProducts(ids);
            return this.mapper.mapToSimilarProductResponse(products);
        } catch (Exception e) {
            logger.error("Error getting similar products", e);
            throw new ProductServiceException("Product not found", e);
        }
    }

    private List<ProductDetail> getProducts(List<String> ids) {
        return ids.stream()
                .map(this::getProductDetail)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ProductDetail getProductDetail(String id) {
        try {
            return this.repository.getProductDetail(id);
        } catch (Exception e) {
            logger.warn("Product detail not found");
        }
        return null;
    }

}
