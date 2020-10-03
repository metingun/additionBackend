package com.website.backend.service;

import com.website.backend.model.PersonelModel;
import com.website.backend.repository.PersonelRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonelService {

    private final PersonelRepo personelRepo;

    public PersonelService(PersonelRepo personelRepo) {
        this.personelRepo = personelRepo;
    }

    public PersonelModel save(PersonelModel personelModel) {
        if (personelRepo.findByName(personelModel.getName()) != null) {
            return null;
        }
        personelRepo.save(personelModel);
        return personelModel;
    }

    public String delete(long id) {
        personelRepo.delete(personelRepo.findById(id));
        return "200";
    }

    public List<PersonelModel> getAll() {
        return personelRepo.findAll();
    }
}
