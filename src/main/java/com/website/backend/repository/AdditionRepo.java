package com.website.backend.repository;

import com.website.backend.model.AdditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdditionRepo extends JpaRepository<AdditionModel, Long> {
    AdditionModel findByTableNameAndActivity(String tableName,int activity);

    AdditionModel findById(long id);

    List<AdditionModel> findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivity(long startDate, long finishDate,int activity);
}
