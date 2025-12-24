package com.github.ekaterina_vol.hr_department.infrastructure.services;

import com.github.ekaterina_vol.hr_department.domain.entities.Employment;

import java.time.LocalDateTime;
import java.util.List;

public interface EmploymentService extends Service<Employment, Long> {
    List<Employment> findByEmployeeId(Long employeeId);

    List<Employment> findByPostId(Long postId);

    List<Employment> findByStartDate(LocalDateTime startDate);

    List<Employment> findByEndDate(LocalDateTime endDate);
}
