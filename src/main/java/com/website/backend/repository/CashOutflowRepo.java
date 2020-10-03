package com.website.backend.repository;

import com.website.backend.model.CashOutflowModel;
import com.website.backend.model.PersonelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashOutflowRepo extends JpaRepository<CashOutflowModel, Long> {
    CashOutflowModel findByPersonelName(String personelName);

    CashOutflowModel findById(long id);

    List<CashOutflowModel> findAllByDateLongGreaterThanEqualAndDateLongLessThanEqual(long startDate,long finishDate);
}
