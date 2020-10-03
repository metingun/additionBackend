package com.website.backend.repository;

import com.website.backend.model.DiscountTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountTypeRepo extends JpaRepository<DiscountTypeModel, Long> {
    DiscountTypeModel findByDiscountName(String discountName);

    DiscountTypeModel findById(long id);
}
