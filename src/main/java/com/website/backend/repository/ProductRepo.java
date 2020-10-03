package com.website.backend.repository;

import com.website.backend.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductModel, Long> {
    ProductModel findByProductName(String productName);

    ProductModel findByProductNo(int productNo);

    ProductModel findById(long productId);

    List<ProductModel> findAllByUnitPriceForOutIsGreaterThan(double unitPriceForOut);
}
