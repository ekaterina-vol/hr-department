package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeHistoryRepository;

import java.util.List;
import java.util.Optional;

public class EmployeeHistoryRepositoryImpl implements EmployeeHistoryRepository {
    @Override
    public Optional<EmployeeHistory> create(EmployeeHistory entity) {
        return Optional.empty();
    }

    @Override
    public Optional<EmployeeHistory> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<EmployeeHistory> findAll() {
        return null;
    }

    @Override
    public Optional<EmployeeHistory> update(EmployeeHistory entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
