package com.severgroup.service;

import com.severgroup.repository.AvgRepository;
import com.severgroup.to.AvgRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AvgService {

    @Autowired
    private AvgRepository repository;

    //getAll
    public Iterable<AvgRecord> findAll() {
        return repository.findAll();
    }

    //findByName
    public List<AvgRecord> findByName(String name) {
        return repository.findByUserName(name);
    }

    //saveAll
    public void saveAll(List<AvgRecord> records) {
        repository.saveAll(records);
    }
}

