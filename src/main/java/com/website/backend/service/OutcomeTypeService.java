package com.website.backend.service;

import com.website.backend.model.OutcomeTypeModel;
import com.website.backend.repository.OutcomeTypeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutcomeTypeService {

    private final OutcomeTypeRepo outcomeTypeRepo;

    public OutcomeTypeService(OutcomeTypeRepo outcomeTypeRepo) {
        this.outcomeTypeRepo = outcomeTypeRepo;
    }

    public OutcomeTypeModel save(OutcomeTypeModel outcomeTypeModel) {
        if (outcomeTypeRepo.findByOutcomeType(outcomeTypeModel.getOutcomeType()) != null) {
            return null;
        }
        outcomeTypeRepo.save(outcomeTypeModel);
        return outcomeTypeModel;
    }

    public String delete(long id) {
        outcomeTypeRepo.delete(outcomeTypeRepo.findById(id));
        return "200";
    }

    public List<OutcomeTypeModel> getAll() {
        return outcomeTypeRepo.findAll();
    }

    public String saveAll(List<OutcomeTypeModel> outcomeTypeModels) {
        outcomeTypeRepo.saveAll(outcomeTypeModels);
        return "Successful";
    }
}
