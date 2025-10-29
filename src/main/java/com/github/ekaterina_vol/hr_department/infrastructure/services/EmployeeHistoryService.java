package com.github.ekaterina_vol.hr_department.infrastructure.services;

import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeHistoryService extends Service<EmployeeHistory, Long> {
    List<EmployeeHistory> findByEmployeeId(Long employeeId);
    List<EmployeeHistory> findByChangeDate(LocalDateTime changeDate);
}
