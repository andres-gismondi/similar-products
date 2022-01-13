package com.zara.similarproducts.repository;

import com.zara.similarproducts.application.exception.ProductRepositoryException;
import com.zara.similarproducts.domain.domain.ProductDetail;
import com.zara.similarproducts.domain.repository.SimilarProductRepository;
import com.zara.similarproducts.infraestructure.repository.ProductRepository;
import com.zara.similarproducts.infraestructure.repository.RestTemplateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public class SimilarProductRepositoryTest {

    private RestTemplateRepository restTemplateRepository;

    private SimilarProductRepository repository;

    @BeforeEach
    void setUp() {
        this.restTemplateRepository = Mockito.mock(RestTemplateRepository.class);
        this.repository = new ProductRepository(this.restTemplateRepository);
    }

    @Test
    public void success_get_product_detail() throws ProductRepositoryException {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId("1");
        productDetail.setPrice(BigDecimal.valueOf(9.99));
        productDetail.setName("Shorts");
        productDetail.setAvailability(Boolean.TRUE);
        ResponseEntity<ProductDetail> responseEntity = new ResponseEntity<>(productDetail,HttpStatus.OK);
        Mockito.when(this.restTemplateRepository.getProductId("1")).thenReturn(responseEntity);

        ProductDetail response = this.repository.getProductDetail("1");

        Assertions.assertEquals("1", response.getId());
        Assertions.assertEquals("Shorts", response.getName());
        Assertions.assertEquals(BigDecimal.valueOf(9.99), response.getPrice());
    }

    @Test
    public void success_get_similar_product() throws ProductRepositoryException {
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(List.of("1","2"), HttpStatus.OK);
        Mockito.when(this.restTemplateRepository.getProductIds("1")).thenReturn(responseEntity);

        List<String> response = this.repository.getSimilarProduct("1");
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void should_return_exception_when_rest_return_error_code() {
        ResponseEntity<List<String>> responseEntity = new ResponseEntity<>(List.of("1","2"), HttpStatus.NOT_FOUND);
        Mockito.when(this.restTemplateRepository.getProductIds("1")).thenReturn(responseEntity);

        Assertions.assertThrows(ProductRepositoryException.class,
                () -> this.repository.getSimilarProduct("1"), "Product not found");
    }

    @Test
    public void should_return_exception_when_id_is_empty() {
        Assertions.assertThrows(ProductRepositoryException.class,
                () -> this.repository.getProductDetail(""), "ProductId can not be null or empty");
    }

    @Test
    public void should_return_exception_when_id_is_null() {
        Assertions.assertThrows(ProductRepositoryException.class,
                () -> this.repository.getProductDetail(null), "Status code is not correct");
    }
}
