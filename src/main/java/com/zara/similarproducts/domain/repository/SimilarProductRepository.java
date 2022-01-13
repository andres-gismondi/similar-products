package com.zara.similarproducts.domain.repository;

import com.zara.similarproducts.application.exception.ProductRepositoryException;
import com.zara.similarproducts.domain.domain.ProductDetail;

import java.util.List;

public interface SimilarProductRepository {

    ProductDetail getProductDetail(String id) throws ProductRepositoryException;

    List<ProductDetail> getSimilarProduct(String id) throws ProductRepositoryException;

}
