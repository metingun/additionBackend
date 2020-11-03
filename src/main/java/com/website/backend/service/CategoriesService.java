package com.website.backend.service;

import com.website.backend.model.CategoriesModel;
import com.website.backend.repository.CategoriesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {

    private final CategoriesRepo categoriesRepo;

    public CategoriesService(CategoriesRepo categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
    }

    public CategoriesModel save(CategoriesModel categoriesModel) {
        CategoriesModel categoriesModel1=categoriesRepo.findByCategoryName(categoriesModel.getCategoryName());
        if (categoriesModel1 != null) {
            return null;
        }
        categoriesRepo.save(categoriesModel);
        return categoriesModel;
    }

    public String delete(long id) {
        categoriesRepo.delete(categoriesRepo.findByCategoryId(id));
        return "200";
    }

    public List<CategoriesModel> getAll() {
        return categoriesRepo.findAll();
    }

    public String saveAll(List<CategoriesModel> categoriesModels) {
        categoriesRepo.saveAll(categoriesModels);
        return "Successful";
    }
}
