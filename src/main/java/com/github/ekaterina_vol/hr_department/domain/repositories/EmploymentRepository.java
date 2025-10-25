package com.github.ekaterina_vol.hr_department.domain.repositories;

import com.github.ekaterina_vol.hr_department.domain.entities.Employment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmploymentRepository extends Repository<Employment, Long> {
    List<Employment> findByEmployeeId(Long employeeId);
    List<Employment> findByPostId(Long postId);
    List<Employment> findByStartDate(LocalDateTime startDate);
    List<Employment> findByEndDate(LocalDateTime endDate);
}
