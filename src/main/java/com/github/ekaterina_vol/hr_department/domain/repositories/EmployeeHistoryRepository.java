package com.github.ekaterina_vol.hr_department.domain.repositories;

import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeHistoryRepository extends Repository<EmployeeHistory, Long> {
    List<EmployeeHistory> findByEmployeeId(Long employeeId);

    List<EmployeeHistory> findByChangeDate(LocalDateTime changeDate);
}
