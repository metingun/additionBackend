package com.website.backend.service;

import com.website.backend.model.DiscountTypeModel;
import com.website.backend.repository.DiscountTypeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountTypeService {

    private final DiscountTypeRepo discountTypeRepo;

    public DiscountTypeService(DiscountTypeRepo discountTypeRepo) {
        this.discountTypeRepo = discountTypeRepo;
    }

    public DiscountTypeModel save(DiscountTypeModel discountTypeModel) {
        if (discountTypeRepo.findByDiscountName(discountTypeModel.getDiscountName()) != null) {
            return null;
        }
        discountTypeRepo.save(discountTypeModel);
        return discountTypeModel;
    }

    public String delete(long id) {
        discountTypeRepo.delete(discountTypeRepo.findById(id));
        return "200";
    }

    public List<DiscountTypeModel> getAll() {
        return discountTypeRepo.findAll();
    }
}
