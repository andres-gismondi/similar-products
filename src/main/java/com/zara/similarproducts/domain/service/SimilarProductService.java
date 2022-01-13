package com.zara.similarproducts.domain.service;

import com.zara.similarproducts.application.exception.ProductServiceException;
import com.zara.similarproducts.application.response.ProductDetailResponse;

import java.util.List;

public interface SimilarProductService {

    List<ProductDetailResponse> getSimilarProducts(String id) throws ProductServiceException;

}
