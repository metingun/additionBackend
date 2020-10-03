package com.website.backend.repository;

import com.website.backend.model.TablesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablesRepo extends JpaRepository<TablesModel, Long> {
    TablesModel findByTableName(String tableName);

    List<TablesModel> findAllByMenuType(int menuType);

    TablesModel findById(long id);

    List<TablesModel> findAllByTableType(String tableType);
}
