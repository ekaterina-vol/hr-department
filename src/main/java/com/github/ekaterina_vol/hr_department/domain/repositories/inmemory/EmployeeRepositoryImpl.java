package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public Optional<Employee> create(Employee entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public Optional<Employee> update(Employee entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
