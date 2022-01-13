package com.zara.similarproducts.repository;

import com.zara.similarproducts.application.exception.ProductRepositoryException;
import com.zara.similarproducts.domain.repository.SimilarProductRepository;
import com.zara.similarproducts.infraestructure.repository.ProductRepository;
import com.zara.similarproducts.infraestructure.repository.RestTemplateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SimilarProductRepositoryTest {

    private RestTemplateRepository restTemplateRepository;

    private SimilarProductRepository repository;

    @BeforeEach
    void setUp() {
        this.restTemplateRepository = Mockito.mock(RestTemplateRepository.class);
        this.repository = new ProductRepository(this.restTemplateRepository);
    }

    @Test
    public void success_get_product_detail() {

    }

    @Test
    public void success_get_similar_product() {

    }

    @Test
    public void should_return_exception_when_rest_return_error_code() {

    }

    @Test
    public void should_return_exception_when_id_is_empty() {
        Assertions.assertThrows(ProductRepositoryException.class, () -> this.repository.getProductDetail(""));
    }

    @Test
    public void should_return_exception_when_id_is_null() {
        Assertions.assertThrows(ProductRepositoryException.class, () -> this.repository.getProductDetail(null));
    }
}
