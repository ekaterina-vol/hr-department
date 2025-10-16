package com.github.ekaterina_vol.hr_department.domain.repositories;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends Repository<Employee, Long> {
    Optional<Employee> updateLastName(Long employeeId, String newLastName);
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
    List<Employee> findByCreatedData(LocalDateTime createdData);
}
