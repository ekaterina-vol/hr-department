package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmploymentRepository;

import java.util.List;
import java.util.Optional;

public class EmploymentRepositoryImpl implements EmploymentRepository {
    @Override
    public Optional<Employment> create(Employment entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Employment> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Employment> findAll() {
        return null;
    }

    @Override
    public Optional<Employment> update(Employment entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
