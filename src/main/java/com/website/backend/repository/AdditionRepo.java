package com.website.backend.repository;

import com.website.backend.model.AdditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionRepo extends JpaRepository<AdditionModel, Long> {
    AdditionModel findByTableNameAndActivity(String tableName, int activity);

    AdditionModel findByTableName(String tableName);

    AdditionModel findById(long id);

    List<AdditionModel> findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivity(long startDate, long finishDate, int activity);

    List<AdditionModel> findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndCashPaymentGreaterThan(long startDate, long finishDate, int activity, double cashPayment);

    List<AdditionModel> findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndCreditCardPaymentGreaterThan(long startDate, long finishDate, int activity, double cashPayment);

    List<AdditionModel> findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndTableNameContains(long startDate, long finishDate, int activity, String tableName);

    List<AdditionModel> findAllByAdditionFinishDateLongGreaterThanEqualAndAdditionFinishDateLongLessThanEqualAndActivityAndTableNameNotContains(long startDate, long finishDate, int activity, String tableName);

}
