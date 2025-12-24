package com.github.ekaterina_vol.hr_department.infrastructure.services;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService extends Service<Employee, Long> {
    Employee updateLastName(Long employeeId, String newLastName);

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findByCreatedDate(LocalDateTime createdDate);
}
