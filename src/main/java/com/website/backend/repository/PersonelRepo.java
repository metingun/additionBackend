package com.website.backend.repository;

import com.website.backend.model.PersonelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonelRepo extends JpaRepository<PersonelModel, Long> {
    PersonelModel findByName(String categoryName);

    PersonelModel findById(long id);
}
