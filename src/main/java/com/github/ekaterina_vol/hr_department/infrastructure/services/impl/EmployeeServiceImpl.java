package com.github.ekaterina_vol.hr_department.infrastructure.services.impl;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeRepository;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(final Employee entity) {
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
            throw new IllegalArgumentException("Работник с id=" + entity.getEmployeeId() + " уже существует");
        }
        return curEmployee.get();
    }

    public Employee findById(final Long id) {
        validateEmployeeId(id);

        Optional<Employee> curEmployee = employeeRepository.findById(id);

        if (!curEmployee.isPresent()) {
            throw new IllegalArgumentException("Работника с id= " + id + " не существует");
        }

        return curEmployee.get();

    }

    public List<Employee> findAll() {
        List<Employee> employees = employeeRepository.findAll();

        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }

        return employees;
    }

    public Employee update(final Employee entity) {
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
            throw new IllegalArgumentException("Работника с id=" + entity.getEmployeeId() + " не существует");
        }

        return curEmployee.get();

    }

    public boolean deleteById(final Long id) {
        validateEmployeeId(id);

        Optional<Employee> curEmployee = employeeRepository.findById(id);
        if (!curEmployee.isPresent()) {
            throw new IllegalArgumentException("Работника с id=" + id + " не существует");
        }
        return employeeRepository.deleteById(id);
    }


    //добавить бизнес методы!!!
    @Override
    public Employee updateLastName(Long employeeId, String newLastName) {
        validateEmployeeId(employeeId);
        validateLastName(newLastName);

        Optional<Employee> curEmployee = employeeRepository.updateLastName(employeeId, newLastName);
        if (!curEmployee.isPresent()) {
            throw new IllegalArgumentException("Работника с id=" + employeeId + " не существует");
        }
        return curEmployee.get();
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        validateFirstName(firstName);
        validateLastName(lastName);

        List<Employee> employees = employeeRepository.findByFirstNameAndLastName(firstName, lastName);
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Отсутствуют работники с фамилией и именем " + firstName + " " + lastName);
        }
        return employees;
    }

    @Override
    public List<Employee> findByCreatedDate(LocalDateTime createdDate) {
        validateCreatedDate(createdDate);

        List<Employee> employees = employeeRepository.findByCreatedDate(createdDate);
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Отсутствуют записи созданные " + createdDate);
        }
        return employees;
    }


    private void validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("Id работника должен быть положительным числом");
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
