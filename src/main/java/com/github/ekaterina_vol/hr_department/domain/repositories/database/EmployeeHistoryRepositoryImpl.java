package com.github.ekaterina_vol.hr_department.domain.repositories.database;

import com.github.ekaterina_vol.hr_department.db.PostgreSQLManager;
import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeHistoryRepository;
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

public class EmployeeHistoryRepositoryImpl implements EmployeeHistoryRepository {
    @Getter(lazy = true)
    private static final EmployeeHistoryRepositoryImpl instance = new EmployeeHistoryRepositoryImpl();

    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByEmployeeIdStatement;
    private PreparedStatement findByChangeDateStatement;

    public EmployeeHistoryRepositoryImpl() {
        Connection connection = PostgreSQLManager.getInstance().getConnection();
        try {
            this.createStatement = connection.prepareStatement(
                    "INSERT INTO employee_history (change_date, employee_id, last_name) " +
                            "VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM employee_history " +
                            "WHERE history_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM employee_history"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM employee_history " +
                            "WHERE history_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE employee_history " +
                            "SET change_date = ?, employee_id = ?, last_name = ? " +
                            "WHERE history_id = ?"
            );

            this.findByEmployeeIdStatement = connection.prepareStatement(
                    "SELECT * FROM employee_history " +
                            "WHERE employee_id = ?"
            );

            this.findByChangeDateStatement = connection.prepareStatement(
                    "SELECT * FROM employee_history " +
                            "WHERE change_date = ?"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<EmployeeHistory> create(EmployeeHistory entity) {
        try {
            createStatement.setDate(1, Date.valueOf(entity.getChangeDate().toLocalDate()));
            createStatement.setLong(2, entity.getEmployeeId());
            createStatement.setString(3, entity.getLastName());

            int affectedRows = createStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return Optional.of(EmployeeHistory.builder()
                            .historyId(generatedId)
                            .changeDate(entity.getChangeDate())
                            .employeeId(entity.getEmployeeId())
                            .lastName(entity.getLastName())
                            .build());
                }
            }

            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create employee history", e);
        }
    }

    @Override
    public Optional<EmployeeHistory> findById(Long id) {
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    EmployeeHistory employeeHistory = extractEmployeeHistory(result);
                    return Optional.of(employeeHistory);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employee history " + id, e);
        }
    }

    @Override
    public List<EmployeeHistory> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractEmployeeHistoryList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all employee histories", e);
        }
    }

    @Override
    public Optional<EmployeeHistory> update(EmployeeHistory entity) {
        try {
            updateStatement.setDate(1, Date.valueOf(entity.getChangeDate().toLocalDate()));
            updateStatement.setLong(2, entity.getEmployeeId());
            updateStatement.setString(3, entity.getLastName());
            updateStatement.setLong(4, entity.getHistoryId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update employee history " + entity.getHistoryId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employee history by id " + id, e);
        }
    }

    @Override
    public List<EmployeeHistory> findByEmployeeId(Long employeeId) {
        try {
            findByEmployeeIdStatement.setLong(1, employeeId);
            try (ResultSet result = findByEmployeeIdStatement.executeQuery()) {
                return extractEmployeeHistoryList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employee history for employee " + employeeId, e);
        }
    }

    @Override
    public List<EmployeeHistory> findByChangeDate(LocalDateTime changeDate) {
        try {
            findByChangeDateStatement.setDate(1, Date.valueOf(changeDate.toLocalDate()));
            try (ResultSet result = findByChangeDateStatement.executeQuery()) {
                return extractEmployeeHistoryList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employee history for change date " + changeDate, e);
        }
    }

    private List<EmployeeHistory> extractEmployeeHistoryList(ResultSet result) throws SQLException {
        List<EmployeeHistory> employeeHistories = new ArrayList<>();
        while (result.next()) {
            employeeHistories.add(extractEmployeeHistory(result));
        }
        return employeeHistories;
    }

    private EmployeeHistory extractEmployeeHistory(ResultSet result) throws SQLException {
        LocalDateTime changeDate = result.getDate("change_date").toLocalDate().atStartOfDay();

        return EmployeeHistory.builder()
                .historyId(result.getLong("history_id"))
                .changeDate(changeDate)
                .employeeId(result.getLong("employee_id"))
                .lastName(result.getString("last_name"))
                .build();
    }
}
