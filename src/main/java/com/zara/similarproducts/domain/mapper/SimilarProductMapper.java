package com.zara.similarproducts.domain.mapper;

import com.zara.similarproducts.application.response.ProductDetailResponse;
import com.zara.similarproducts.domain.domain.ProductDetail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SimilarProductMapper {

    public List<ProductDetailResponse> mapToSimilarProductResponse(List<ProductDetail> details) {
        return details.stream().map(this::mapToProductDetailResponse).collect(Collectors.toList());
    }

    private ProductDetailResponse mapToProductDetailResponse(ProductDetail product) {
        ProductDetailResponse detailResponse = new ProductDetailResponse();
        detailResponse.setId(product.getId());
        detailResponse.setName(product.getName());
        detailResponse.setPrice(product.getPrice());
        detailResponse.setAvailability(product.getAvailability());
        return detailResponse;
    }

}
