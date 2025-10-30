package com.github.ekaterina_vol.hr_department.infrastructure.services.impl;

import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmploymentRepository;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {
    private final EmploymentRepository employmentRepository;

    public Employment create(final Employment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validateEmployeeId(entity.getEmployeeId());
        validatePostId(entity.getPostId());
        validateStartDate(entity.getStartDate());
        validateEndDate(entity.getEndDate());

        Optional<Employment> curEmployment = employmentRepository.create(entity);
        if (!curEmployment.isPresent()) {
            throw new IllegalArgumentException("Запись трудоустройства с id=" + entity.getEmploymentId() + " уже существует");
        }
        return curEmployment.get();
    }

    public Employment findById(final Long id) {
        validateEmploymentId(id);
        Optional<Employment> curEmployment = employmentRepository.findById(id);
        if (!curEmployment.isPresent()) {
            throw new IllegalArgumentException("Записи трудоустройства с id=" + id + " не существует");
        }
        return curEmployment.get();
    }

    public List<Employment> findAll() {
        List<Employment> employments = employmentRepository.findAll();
        if (employments.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return employments;
    }

    public Employment update(final Employment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validateEmploymentId(entity.getEmploymentId());
        validateEmployeeId(entity.getEmployeeId());
        validatePostId(entity.getPostId());
        validateStartDate(entity.getStartDate());
        validateEndDate(entity.getEndDate());

        Optional<Employment> curEmployment = employmentRepository.create(entity);
        if (!curEmployment.isPresent()) {
            throw new IllegalArgumentException("Записи трудоустройства с id=" + entity.getEmploymentId() + " не существует");
        }
        return curEmployment.get();
    }

    public boolean deleteById(final Long id) {
        validateEmploymentId(id);

        Optional<Employment> curEmployment = employmentRepository.findById(id);
        if (!curEmployment.isPresent()) {
            throw new IllegalArgumentException("Записи трудоустройства с id=" + id + " не существует");
        }
        return employmentRepository.deleteById(id);
    }

    public List<Employment> findByEmployeeId(Long employeeId) {
        validateEmployeeId(employeeId);

        List<Employment> employments = employmentRepository.findByEmployeeId(employeeId);
        if (employments.isEmpty()) {
            throw new IllegalArgumentException("Работника с id=" + employeeId + " не существует");
        }
        return employments;
    }

    public List<Employment> findByPostId(Long postId) {
        validatePostId(postId);

        List<Employment> employments = employmentRepository.findByPostId(postId);
        if (employments.isEmpty()) {
            throw new IllegalArgumentException("Должности с id=" + postId + " не существует");
        }
        return employments;
    }

    public List<Employment> findByStartDate(LocalDateTime startDate) {
        validateStartDate(startDate);

        List<Employment> employments = employmentRepository.findByStartDate(startDate);
        if (employments.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует дата трудоустройства " + startDate);
        }
        return employments;
    }

    public List<Employment> findByEndDate(LocalDateTime endDate) {
        validateEndDate(endDate);

        List<Employment> employments = employmentRepository.findByEndDate(endDate);
        if (employments.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует дата увольнения " + endDate);
        }
        return employments;
    }

    private void validateEmploymentId(Long employmentId) {
        if (employmentId == null || employmentId <= 0) {
            throw new IllegalArgumentException("Id записи трудоустройства должен быть положительным числом");
        }
    }

    private void validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("Id работника должен быть положительным числом");
        }
    }

    private void validatePostId(Long postId) {
        if (postId == null || postId <= 0) {
            throw new IllegalArgumentException("Id должности должен быть положительным числом");
        }
    }

    private void validateStartDate(LocalDateTime startDate) {
        if (startDate != null && startDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Отсутствует дата трудоустройства");
        }
    }

    private void validateEndDate(LocalDateTime endDate) {
        if (endDate != null && endDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Отсутствует дата увольнения");
        }
    }
}
