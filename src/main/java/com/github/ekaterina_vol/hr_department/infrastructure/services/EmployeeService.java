package com.github.ekaterina_vol.hr_department.infrastructure.services;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    Employee create(final Employee entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validateEmployeeId(entity.getEmployeeId());
        validateFirstName(entity.getFirstName());
        validateLastName(entity.getLastName());
        validateBirthDate(entity.getBirthDate());
        validateSex(entity.getSex());
        validateCreatedDate(entity.getCreatedAt());

        Optional<Employee> curEmployee = employeeRepository.create(entity);
        if (!curEmployee.isPresent()) {
            throw new IllegalArgumentException("Работник с таким id уже существует");
        }

        return curEmployee.get();
    }
    Employee findById(final Long id) {
        validateEmployeeId(id);

        Optional<Employee> curEmployee = employeeRepository.findById(id);

        if (!curEmployee.isPresent()) {
            throw new IllegalArgumentException("Работника с таким id не существует");
        }

        return curEmployee.get();

    }
    List<Employee> findAll() {
        List<Employee> employees = employeeRepository.findAll();

         if (employees.isEmpty()) {
             throw new IllegalArgumentException("Таблица пустая");
         }

         return employees;
    }
    Employee update(final Employee entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validateEmployeeId(entity.getEmployeeId());
        validateFirstName(entity.getFirstName());
        validateLastName(entity.getLastName());
        validateBirthDate(entity.getBirthDate());
        validateSex(entity.getSex());
        validateCreatedDate(entity.getCreatedAt());

        Optional<Employee> curEmployee = employeeRepository.create(entity);

        if (!curEmployee.isPresent()) {
            throw new IllegalArgumentException("Работника с таким id не существует");
        }

        return curEmployee.get();

    }
    boolean deleteById(final Long id) {
        validateEmployeeId(id);

        Optional<Employee> curEmployee = employeeRepository.findById(id);
        if (!curEmployee.isPresent()) {
            throw new IllegalArgumentException("Работника с таким id не существует");
        }
        return employeeRepository.deleteById(id);
    }

    private void validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("ID работника должен быть положительным числом");
        }
    }

    private void validateFirstName(String firstName) {
        if (firstName == null || !firstName.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует имя");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || !lastName.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует фамилия");
        }
    }

    private void validateBirthDate(LocalDateTime birthDate) {
        if (birthDate != null && birthDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Отсутствует дата рождения");
        }
    }

    private void validateSex(Employee.Sex sex) {
        if (sex == null) {
            throw new IllegalArgumentException("Отсутствует пол");
        }
    }

    private void validateCreatedDate(LocalDateTime createdDate) {
        if (createdDate != null && createdDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Отсутствует дата создания");
        }
    }

}
