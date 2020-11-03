package com.website.backend.service;

import com.website.backend.model.ProductModel;
import com.website.backend.repository.CategoriesRepo;
import com.website.backend.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoriesRepo categoriesRepo;

    public ProductService(ProductRepo productRepo,CategoriesRepo categoriesRepo) {
        this.productRepo = productRepo;
        this.categoriesRepo = categoriesRepo;
    }

    public ProductModel save(ProductModel productModel) {
        if (productRepo.findByProductName(productModel.getProductName())!=null || productRepo.findByProductNo(productModel.getProductNo())!=null){
            return null;
        }
        productModel.setCategoryId(categoriesRepo.findByCategoryName(productModel.getCategoryName()).getId());
        productRepo.save(productModel);
        return productModel;
    }

    public String deleteProduct(long productId) {
        productRepo.delete(productRepo.findById(productId));
        return "200";
    }

    public List<ProductModel> getAll(int menuType) {
        if (menuType==0){
            return productRepo.findAll();
        }
        return productRepo.findAllByUnitPriceForOutIsGreaterThan(0);
    }

    public List<ProductModel> getAllProducts() {
        return productRepo.findAll();
    }

    public String saveAll(List<ProductModel> productModels) {
        productRepo.saveAll(productModels);
        return "Successful";
    }
}
