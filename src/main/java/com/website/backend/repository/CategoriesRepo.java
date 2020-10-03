package com.website.backend.repository;

import com.website.backend.model.CategoriesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepo extends JpaRepository<CategoriesModel, Long> {
    CategoriesModel findByCategoryName(String categoryName);

    CategoriesModel findByCategoryId(long categoryId);

    CategoriesModel findById(long id);

}
