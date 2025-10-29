package com.github.ekaterina_vol.hr_department.infrastructure.services.domain;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeHistoryService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class DomainEmployeeServiceImpl implements EmployeeService {
    private final EmployeeService employeeService;
    private final EmploymentService domainEmploymentService;
    private final EmployeeHistoryService domainEmployeeHistoryService;

    @Override
    public Employee create(Employee entity) {
        return employeeService.create(entity);
    }

    @Override
    public Employee findById(Long id) {
        return employeeService.findById(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @Override
    public Employee update(Employee entity) {
        return employeeService.update(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        List<Employment> employments = domainEmploymentService.findByEmployeeId(id);
        if (!employments.isEmpty()) {
            for (Employment e : employments) {
                domainEmploymentService.deleteById(e.getEmploymentId());
            }
        }
        List<EmployeeHistory> employeeHistories = domainEmployeeHistoryService.findByEmployeeId(id);
        if (!employeeHistories.isEmpty()) {
            for (EmployeeHistory eh : employeeHistories) {
                domainEmploymentService.deleteById(eh.getHistoryId());
            }
        }
        return employeeService.deleteById(id);
    }

    @Override
    public Employee updateLastName(Long employeeId, String newLastName) {
        domainEmployeeHistoryService.create(EmployeeHistory.builder()
                .employeeId(employeeId)
                .lastName(newLastName)
                .changeDate(LocalDateTime.now())
                .build()
        );
        return employeeService.updateLastName(employeeId, newLastName);
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employeeService.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Employee> findByCreatedDate(LocalDateTime createdDate) {
        return employeeService.findByCreatedDate(createdDate);
    }
}
