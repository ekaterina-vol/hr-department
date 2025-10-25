package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmploymentRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class EmploymentRepositoryImpl implements EmploymentRepository {
    private final Map<Long, Employment> employmentStorage = new HashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);
    @Override
    public Optional<Employment> create(Employment entity) {
        if (employmentStorage.containsKey(entity.getEmploymentId())) {
            return Optional.empty();
        }

        Long nextId = currentId.getAndIncrement();

        Employment employmentToSave = Employment.builder()
                .employmentId(nextId)
                .employeeId(entity.getEmployeeId())
                .postId(entity.getPostId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();

        employmentStorage.put(employmentToSave.getEmployeeId(), employmentToSave);
        return Optional.of(employmentToSave);
    }

    @Override
    public Optional<Employment> findById(Long id) {
        return Optional.ofNullable(employmentStorage.get(id));
    }

    @Override
    public List<Employment> findAll() {
        return new ArrayList<>(employmentStorage.values());
    }

    @Override
    public Optional<Employment> update(Employment entity) {
        if (!employmentStorage.containsKey(entity.getEmploymentId())) {
            return Optional.empty();
        }

        Long id = entity.getEmploymentId();
        Employment curEmployment = employmentStorage.get(id);

        Employment updatedEmployment = Employment.builder()
                .employmentId(entity.getEmploymentId())
                .employeeId(entity.getEmployeeId())
                .postId(entity.getPostId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();

        employmentStorage.put(updatedEmployment.getEmployeeId(), updatedEmployment);
        return Optional.of(updatedEmployment);
    }

    @Override
    public boolean deleteById(Long id) {
        return employmentStorage.remove(id) != null;
    }

    @Override
    public List<Employment> findByEmployeeId(Long employeeId) {
        return employmentStorage.values().stream()
                .filter(employment ->
                        employeeId.equals(employment.getEmployeeId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employment> findByPostId(Long postId) {
        return employmentStorage.values().stream()
                .filter(employment ->
                        postId.equals(employment.getPostId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employment> findByStartDate(LocalDateTime startDate) {
        return employmentStorage.values().stream()
                .filter(employment ->
                        startDate.equals(employment.getStartDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employment> findByEndDate(LocalDateTime endDate) {
        return employmentStorage.values().stream()
                .filter(employment ->
                        endDate.equals(employment.getEndDate()))
                .collect(Collectors.toList());
    }
}
