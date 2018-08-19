package com.severgroup.repository;

import com.severgroup.to.AvgRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvgRepository extends CrudRepository<AvgRecord, Long> {
    List<AvgRecord> findAll();

    List<AvgRecord> findByUserName(String name);
}
