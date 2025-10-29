package com.github.ekaterina_vol.hr_department.infrastructure.services.domain;

import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class DomainEmploymentServiceImpl implements EmploymentService {
    private final EmploymentService employmentService;
    private final EmployeeService domainEmployeeService;
    private final PostService domainPostService;

    @Override
    public Employment create(Employment entity) {
        domainEmployeeService.findById(entity.getEmployeeId());
        domainPostService.findById(entity.getPostId());
        return employmentService.create(entity);
    }

    @Override
    public Employment findById(Long id) {
        return employmentService.findById(id);
    }

    @Override
    public List<Employment> findAll() {
        return employmentService.findAll();
    }

    @Override
    public Employment update(Employment entity) {
        domainEmployeeService.findById(entity.getEmployeeId());
        domainPostService.findById(entity.getPostId());
        return employmentService.update(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return employmentService.deleteById(id);
    }

    @Override
    public List<Employment> findByEmployeeId(Long employeeId) {
        return employmentService.findByEmployeeId(employeeId);
    }

    @Override
    public List<Employment> findByPostId(Long postId) {
        return employmentService.findByPostId(postId);
    }

    @Override
    public List<Employment> findByStartDate(LocalDateTime startDate) {
        return employmentService.findByStartDate(startDate);
    }

    @Override
    public List<Employment> findByEndDate(LocalDateTime endDate) {
        return employmentService.findByEndDate(endDate);
    }
}
