package com.github.ekaterina_vol.hr_department.infrastructure.services.domain;

import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeHistoryService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class DomainEmployeeHistoryServiceImpl implements EmployeeHistoryService {
    private final EmployeeHistoryService employeeHistoryService;
    private final EmployeeService employeeService;
    @Override
    public EmployeeHistory create(EmployeeHistory entity) {
        employeeService.findById(entity.getEmployeeId());
        return employeeHistoryService.create(entity);
    }

    @Override
    public EmployeeHistory findById(Long id) {
        return employeeHistoryService.findById(id);
    }

    @Override
    public List<EmployeeHistory> findAll() {
        return employeeHistoryService.findAll();
    }

    @Override
    public EmployeeHistory update(EmployeeHistory entity) {
        employeeService.findById(entity.getEmployeeId());
        return employeeHistoryService.update(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return employeeHistoryService.deleteById(id);
    }

    @Override
    public List<EmployeeHistory> findByEmployeeId(Long employeeId) {
        return employeeHistoryService.findByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeHistory> findByChangeDate(LocalDateTime changeDate) {
        return employeeHistoryService.findByChangeDate(changeDate);
    }
}
