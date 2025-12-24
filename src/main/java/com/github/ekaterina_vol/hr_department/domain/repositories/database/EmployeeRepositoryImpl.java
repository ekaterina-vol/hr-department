package com.github.ekaterina_vol.hr_department.domain.repositories.database;

import com.github.ekaterina_vol.hr_department.db.PostgreSQLManager;
import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeRepository;
import lombok.Getter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Getter(lazy = true)
    private static final EmployeeRepositoryImpl instance = new EmployeeRepositoryImpl();

    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement updateLastNameStatement;
    private PreparedStatement findByFirstNameAndLastNameStatement;
    private PreparedStatement findByCreatedDateStatement;

    public EmployeeRepositoryImpl() {
        Connection connection = PostgreSQLManager.getInstance().getConnection();
        try {
            this.createStatement = connection.prepareStatement(
                    "INSERT INTO employee (first_name, last_name, birth_date, gender, created_at) " +
                            "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM employee " +
                            "WHERE employee_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM employee"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM employee " +
                            "WHERE employee_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE employee " +
                            "SET first_name = ?, last_name = ?, birth_date = ?, gender = ?, created_at = ? " +
                            "WHERE employee_id = ?"
            );

            this.updateLastNameStatement = connection.prepareStatement(
                    "UPDATE employee " +
                            "SET last_name = ? " +
                            "WHERE employee_id = ?"
            );

            this.findByFirstNameAndLastNameStatement = connection.prepareStatement(
                    "SELECT * FROM employee " +
                            "WHERE first_name = ? AND last_name = ?"
            );

            this.findByCreatedDateStatement = connection.prepareStatement(
                    "SELECT * FROM employee " +
                            "WHERE DATE(created_at) = ?"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<Employee> create(Employee entity) {
        try {
            createStatement.setString(1, entity.getFirstName());
            createStatement.setString(2, entity.getLastName());
            createStatement.setDate(3, Date.valueOf(entity.getBirthDate().toLocalDate()));
            createStatement.setString(4, entity.getSex().toString());
            createStatement.setDate(5, Date.valueOf(entity.getCreatedAt().toLocalDate()));

            int affectedRows = createStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return Optional.of(Employee.builder()
                            .employeeId(generatedId)
                            .firstName(entity.getFirstName())
                            .lastName(entity.getLastName())
                            .birthDate(entity.getBirthDate())
                            .sex(entity.getSex())
                            .createdAt(entity.getCreatedAt())
                            .build());
                }
            }

            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create employee", e);
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    Employee employee = extractEmployee(result);
                    return Optional.of(employee);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employee " + id, e);
        }
    }

    @Override
    public List<Employee> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractEmployeeList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all employees", e);
        }
    }

    @Override
    public Optional<Employee> update(Employee entity) {
        try {
            updateStatement.setString(1, entity.getFirstName());
            updateStatement.setString(2, entity.getLastName());
            updateStatement.setDate(3, Date.valueOf(entity.getBirthDate().toLocalDate()));
            updateStatement.setString(4, entity.getSex().toString());
            updateStatement.setDate(5, Date.valueOf(entity.getCreatedAt().toLocalDate()));
            updateStatement.setLong(6, entity.getEmployeeId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update employee " + entity.getEmployeeId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employee by id " + id, e);
        }
    }

    @Override
    public Optional<Employee> updateLastName(Long employeeId, String newLastName) {
        try {
            updateLastNameStatement.setString(1, newLastName);
            updateLastNameStatement.setLong(2, employeeId);

            int updatedRows = updateLastNameStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }

            // Возвращаем обновленного сотрудника
            return findById(employeeId);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update last name for employee " + employeeId, e);
        }
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        try {
            findByFirstNameAndLastNameStatement.setString(1, firstName);
            findByFirstNameAndLastNameStatement.setString(2, lastName);
            try (ResultSet result = findByFirstNameAndLastNameStatement.executeQuery()) {
                return extractEmployeeList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employees with name " + firstName + " " + lastName, e);
        }
    }

    @Override
    public List<Employee> findByCreatedDate(LocalDateTime createdDate) {
        try {
            // Используем только дату для сравнения, игнорируя время
            findByCreatedDateStatement.setDate(1, Date.valueOf(createdDate.toLocalDate()));
            try (ResultSet result = findByCreatedDateStatement.executeQuery()) {
                return extractEmployeeList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employees created at " + createdDate, e);
        }
    }

    private List<Employee> extractEmployeeList(ResultSet result) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (result.next()) {
            employees.add(extractEmployee(result));
        }
        return employees;
    }

    private Employee extractEmployee(ResultSet result) throws SQLException {
        LocalDateTime birthDate = result.getDate("birth_date").toLocalDate().atStartOfDay();
        LocalDateTime createdAt = result.getDate("created_at").toLocalDate().atStartOfDay();

        Employee.Sex sex = Employee.Sex.valueOf(result.getString("gender"));

        return Employee.builder()
                .employeeId(result.getLong("employee_id"))
                .firstName(result.getString("first_name"))
                .lastName(result.getString("last_name"))
                .birthDate(birthDate)
                .sex(sex)
                .createdAt(createdAt)
                .build();
    }
}
