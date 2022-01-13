package com.zara.similarproducts.infraestructure.repository;

import com.zara.similarproducts.application.exception.ProductRepositoryException;
import com.zara.similarproducts.domain.domain.ProductDetail;
import com.zara.similarproducts.domain.repository.SimilarProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepository implements SimilarProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    private RestTemplateRepository repository;

    @Autowired
    public ProductRepository(RestTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductDetail getProductDetail(String id) throws ProductRepositoryException {
        this.validateId(id);

        ResponseEntity<ProductDetail> response = this.repository.getProductId(id);
        this.validateResponse(response.getStatusCodeValue());

        logger.info("Client product detail response: [{}]", response.getBody().toString());
        return response.getBody();
    }

    @Override
    public List<String> getSimilarProduct(String id) throws ProductRepositoryException {
        this.validateId(id);

        ResponseEntity<List<String>> response = this.repository.getProductIds(id);
        this.validateResponse(response.getStatusCodeValue());

        logger.info("Client similar products response: [{}]", response.getBody());
        return response.getBody();
    }

    private void validateId(String id) throws ProductRepositoryException {
        if (StringUtils.isBlank(id)) {
            throw new ProductRepositoryException("ProductId can not be null or empty");
        }
    }

    private void validateResponse(int statusCode) throws ProductRepositoryException {
        if (statusCode < 200 || statusCode >= 300) {
            throw new ProductRepositoryException("Status code is not correct");
        }
    }
}
