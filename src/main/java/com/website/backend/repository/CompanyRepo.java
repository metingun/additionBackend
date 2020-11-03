package com.website.backend.repository;

import com.website.backend.model.CategoriesModel;
import com.website.backend.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<CompanyModel, Long> {
    CompanyModel findByCompanyName(String categoryName);

    CompanyModel findById(long id);

    List<CompanyModel> findAllByMenuType(int menuType);
}
