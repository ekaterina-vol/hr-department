package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeHistoryRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class EmployeeHistoryRepositoryImpl implements EmployeeHistoryRepository {
    private final Map<Long, EmployeeHistory> employeeHistoryStorage = new HashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public Optional<EmployeeHistory> create(EmployeeHistory entity) {
        if (employeeHistoryStorage.containsKey(entity.getHistoryId())) {
            return Optional.empty();
        }

        Long nextId = currentId.getAndIncrement();

        EmployeeHistory employeeHistoryToSave = EmployeeHistory.builder()
                .historyId(nextId)
                .changeDate(entity.getChangeDate())
                .employeeId(entity.getEmployeeId())
                .lastName(entity.getLastName())
                .build();

        employeeHistoryStorage.put(employeeHistoryToSave.getEmployeeId(), employeeHistoryToSave);
        return Optional.of(employeeHistoryToSave);
    }

    @Override
    public Optional<EmployeeHistory> findById(Long id) {
        return Optional.ofNullable((employeeHistoryStorage.get(id)));
    }

    @Override
    public List<EmployeeHistory> findAll() {
        return new ArrayList<>(employeeHistoryStorage.values());
    }

    @Override
    public Optional<EmployeeHistory> update(EmployeeHistory entity) {
        if (!employeeHistoryStorage.containsKey(entity.getHistoryId())) {
            return Optional.empty();
        }

        Long id = entity.getHistoryId();
        EmployeeHistory curEmployeeHistory = employeeHistoryStorage.get(id);

        EmployeeHistory updatedEmployeeHistory = EmployeeHistory.builder()
                .historyId(entity.getHistoryId())
                .changeDate(entity.getChangeDate())
                .employeeId(entity.getEmployeeId())
                .lastName(entity.getLastName())
                .build();

        employeeHistoryStorage.put(updatedEmployeeHistory.getHistoryId(), updatedEmployeeHistory);
        return Optional.of(updatedEmployeeHistory);
    }

    @Override
    public boolean deleteById(Long id) {
        return employeeHistoryStorage.remove(id) != null;
    }


    @Override
    public List<EmployeeHistory> findByEmployeeId(Long employeeId) {
        return employeeHistoryStorage.values().stream()
                .filter(employeeHistory ->
                        employeeId.equals(employeeHistory.getEmployeeId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeHistory> findByChangeDate(LocalDateTime changeDate) {
        return employeeHistoryStorage.values().stream()
                .filter(employeeHistory ->
                        changeDate.equals(employeeHistory.getChangeDate()))
                .collect(Collectors.toList());
    }
}
