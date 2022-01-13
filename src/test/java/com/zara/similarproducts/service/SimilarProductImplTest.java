package com.zara.similarproducts.service;

import com.zara.similarproducts.application.exception.ProductRepositoryException;
import com.zara.similarproducts.application.exception.ProductServiceException;
import com.zara.similarproducts.application.response.ProductDetailResponse;
import com.zara.similarproducts.domain.domain.ProductDetail;
import com.zara.similarproducts.domain.mapper.SimilarProductMapper;
import com.zara.similarproducts.domain.repository.SimilarProductRepository;
import com.zara.similarproducts.domain.service.SimilarProductService;
import com.zara.similarproducts.domain.service.impl.SimilarProductImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.List;

public class SimilarProductImplTest {

    private SimilarProductRepository repository;
    private SimilarProductService service;

    @BeforeEach
    void setUp() {
        this.repository = Mockito.mock(SimilarProductRepository.class);
        SimilarProductMapper mapper = new SimilarProductMapper();

        this.service = new SimilarProductImpl(this.repository, mapper);
    }

    @Test
    public void success() throws ProductRepositoryException, ProductServiceException {
        Mockito.when(this.repository.getSimilarProduct(Mockito.anyString())).thenReturn(List.of("1","2","3"));
        Mockito.when(this.repository.getProductDetail("1")).thenReturn(buildProductId1());
        Mockito.when(this.repository.getProductDetail("2")).thenReturn(buildProductId2());
        Mockito.when(this.repository.getProductDetail("3")).thenReturn(buildProductId3());

        List<ProductDetailResponse> response = this.service.getSimilarProducts("1");

        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void should_return_two_elements_when_one_throw_exception() throws ProductRepositoryException, ProductServiceException {
        Mockito.when(this.repository.getSimilarProduct(Mockito.anyString())).thenReturn(List.of("1","2","3"));
        Mockito.when(this.repository.getProductDetail("1")).thenReturn(buildProductId1());
        Mockito.when(this.repository.getProductDetail("2")).thenThrow(new ProductRepositoryException("Error"));
        Mockito.when(this.repository.getProductDetail("3")).thenReturn(buildProductId3());

        List<ProductDetailResponse> response = this.service.getSimilarProducts("1");

        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void should_return_empty_list_when_all_throw_exception() throws ProductRepositoryException, ProductServiceException {
        Mockito.when(this.repository.getSimilarProduct(Mockito.anyString())).thenReturn(List.of("1","2","3"));
        Mockito.when(this.repository.getProductDetail(Mockito.anyString())).thenThrow(new ProductRepositoryException("Error"));

        List<ProductDetailResponse> response = this.service.getSimilarProducts("1");

        Assertions.assertEquals(0, response.size());
    }

    @Test
    public void should_return_exception_when_similar_product_return_error() throws ProductRepositoryException {
        Mockito.when(this.repository.getSimilarProduct(Mockito.anyString())).thenThrow(new ProductRepositoryException("Error"));
        Assertions.assertThrows(ProductServiceException.class, () -> this.service.getSimilarProducts("1"));
    }

    @Test
    public void should_return_exception_when_rest_client_failed() throws ProductRepositoryException {
        Mockito.when(this.repository.getSimilarProduct(Mockito.anyString())).thenThrow(new RestClientException("Error"));
        Assertions.assertThrows(ProductServiceException.class, () -> this.service.getSimilarProducts("1"));
    }

    private ProductDetail buildProductId1() {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId("1");
        productDetail.setAvailability(Boolean.TRUE);
        productDetail.setName("Dress");
        productDetail.setPrice(BigDecimal.valueOf(12.34));
        return productDetail;
    }
    private ProductDetail buildProductId2() {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId("2");
        productDetail.setAvailability(Boolean.FALSE);
        productDetail.setName("Blazer");
        productDetail.setPrice(BigDecimal.valueOf(29.99));
        return productDetail;
    }
    private ProductDetail buildProductId3() {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId("3");
        productDetail.setAvailability(Boolean.TRUE);
        productDetail.setName("Boots");
        productDetail.setPrice(BigDecimal.valueOf(120.15));
        return productDetail;
    }
}
