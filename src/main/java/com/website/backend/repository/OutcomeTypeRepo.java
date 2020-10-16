package com.website.backend.repository;

import com.website.backend.model.OutcomeTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeTypeRepo extends JpaRepository<OutcomeTypeModel, Long> {
    OutcomeTypeModel findByOutcomeType(String outcomeType);

    OutcomeTypeModel findById(long id);
}
