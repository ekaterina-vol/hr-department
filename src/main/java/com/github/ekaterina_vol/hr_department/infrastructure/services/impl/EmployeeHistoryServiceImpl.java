package com.github.ekaterina_vol.hr_department.infrastructure.services.impl;

import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeHistoryRepository;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeHistoryService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {
    private final EmployeeHistoryRepository employeeHistoryRepository;

    @Override
    public EmployeeHistory create(EmployeeHistory entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validateHistoryId(entity.getHistoryId());
        validateChangeDate(entity.getChangeDate());
        validateEmployeeId(entity.getEmployeeId());
        validateLastName(entity.getLastName());

        Optional<EmployeeHistory> curEmployeeHistory = employeeHistoryRepository.create(entity);
        if (!curEmployeeHistory.isPresent()) {
            throw new IllegalArgumentException("Запись истории с id=" + entity.getHistoryId() + " уже существует");
        }
        return curEmployeeHistory.get();
    }

    @Override
    public EmployeeHistory findById(Long id) {
        validateHistoryId(id);
        Optional<EmployeeHistory> curEmployeeHistory = employeeHistoryRepository.findById(id);
        if (!curEmployeeHistory.isPresent()) {
            throw new IllegalArgumentException("Записи истории с id=" + id + " не существует");
        }
        return curEmployeeHistory.get();
    }

    @Override
    public List<EmployeeHistory> findAll() {
        List<EmployeeHistory> employeeHistories = employeeHistoryRepository.findAll();
        if (employeeHistories.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return employeeHistories;
    }

    @Override
    public EmployeeHistory update(EmployeeHistory entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validateHistoryId(entity.getHistoryId());
        validateChangeDate(entity.getChangeDate());
        validateEmployeeId(entity.getEmployeeId());
        validateLastName(entity.getLastName());

        Optional<EmployeeHistory> curEmployeeHistory = employeeHistoryRepository.create(entity);
        if (!curEmployeeHistory.isPresent()) {
            throw new IllegalArgumentException("Записи истории с id=" + entity.getHistoryId() + " не существует");
        }
        return curEmployeeHistory.get();
    }

    @Override
    public boolean deleteById(Long id) {
        validateHistoryId(id);
        Optional<EmployeeHistory> curEmployeeHistory = employeeHistoryRepository.findById(id);
        if (!curEmployeeHistory.isPresent()) {
            throw new IllegalArgumentException("Записи истории с id=" + id + " не существует");
        }
        return employeeHistoryRepository.deleteById(id);
    }

    @Override
    public List<EmployeeHistory> findByEmployeeId(Long employeeId) {
        validateEmployeeId(employeeId);

        List<EmployeeHistory> employeeHistories = employeeHistoryRepository.findByEmployeeId(employeeId);
        if (employeeHistories.isEmpty()) {
            throw new IllegalArgumentException("Работника с id=" + employeeId + " не существует");
        }
        return employeeHistories;
    }

    @Override
    public List<EmployeeHistory> findByChangeDate(LocalDateTime changeDate) {
        validateChangeDate(changeDate);

        List<EmployeeHistory> employeeHistories = employeeHistoryRepository.findByChangeDate(changeDate);
        if (employeeHistories.isEmpty()) {
            throw new IllegalArgumentException("Отсутствуют записи в дату " + changeDate);
        }
        return employeeHistories;
    }

    private void validateHistoryId(Long historyId) {
        if (historyId == null || historyId <= 0) {
            throw new IllegalArgumentException("Id записи должен быть положительным числом");
        }
    }

    private void validateChangeDate(LocalDateTime changeDate) {
        if (changeDate != null && changeDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Отсутствует дата изменения");
        }
    }

    private void validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("Id работника должен быть положительным числом");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует фамилия");
        }
    }
}
