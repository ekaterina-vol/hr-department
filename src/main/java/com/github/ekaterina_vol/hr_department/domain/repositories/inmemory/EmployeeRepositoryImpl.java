package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final Map<Long, Employee> employeeStorage = new HashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);
    @Override
    public Optional<Employee> create(Employee entity) {
        if (employeeStorage.containsKey(entity.getEmployeeId())) {
            return Optional.empty();
        }

        Long nextId = currentId.getAndIncrement();

        Employee employeeToSave = Employee.builder()
                .employeeId(nextId)
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .sex(entity.getSex())
                .createdAt(LocalDateTime.now())
                .build();

        employeeStorage.put(employeeToSave.getEmployeeId(), employeeToSave);
        return Optional.of(employeeToSave);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable((employeeStorage.get(id)));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employeeStorage.values());
    }

    @Override
    public Optional<Employee> update(Employee entity) {
        if (!employeeStorage.containsKey(entity.getEmployeeId())) {
            return Optional.empty();
        }

        Long id = entity.getEmployeeId();
        Employee curEmployee = employeeStorage.get(id);

        Employee updatedEmployee = Employee.builder()
                .employeeId(entity.getEmployeeId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .sex(entity.getSex())
                .createdAt(curEmployee.getCreatedAt())
                .build();

        employeeStorage.put(updatedEmployee.getEmployeeId(), updatedEmployee);
        return Optional.of(updatedEmployee);
    }

    @Override
    public boolean deleteById(Long id) {
        return employeeStorage.remove(id) != null;
    }

    @Override
    public Optional<Employee> updateLastName(Long employeeId, String newLastName) {

        return findById(employeeId).map(employee -> {
            Employee updated = Employee.builder()
                    .employeeId(employee.getEmployeeId())
                    .firstName(employee.getFirstName())
                    .lastName(newLastName)
                    .birthDate(employee.getBirthDate())
                    .sex(employee.getSex())
                    .createdAt(employee.getCreatedAt())
                    .build();
            employeeStorage.put(employeeId, updated);
            return updated;
        });
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employeeStorage.values().stream()
                .filter(employee ->
                        firstName.equals(employee.getFirstName()) &&
                                lastName.equals(employee.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> findByCreatedData(LocalDateTime createdData) {
        return employeeStorage.values().stream()
                .filter(employee ->
                        createdData.equals(employee.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
