package com.github.ekaterina_vol.hr_department.domain.repositories.database;

import com.github.ekaterina_vol.hr_department.db.PostgreSQLManager;
import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmploymentRepository;
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

public class EmploymentRepositoryImpl implements EmploymentRepository {
    @Getter(lazy = true)
    private static final EmploymentRepositoryImpl instance = new EmploymentRepositoryImpl();

    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByEmployeeIdStatement;
    private PreparedStatement findByPostIdStatement;
    private PreparedStatement findByStartDateStatement;
    private PreparedStatement findByEndDateStatement;

    public EmploymentRepositoryImpl() {
        Connection connection = PostgreSQLManager.getInstance().getConnection();
        try {
            this.createStatement = connection.prepareStatement(
                    "INSERT INTO employment (employee_id, post_id, start_date, end_date) " +
                            "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM employment " +
                            "WHERE employment_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM employment"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM employment " +
                            "WHERE employment_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE employment " +
                            "SET employee_id = ?, post_id = ?, start_date = ?, end_date = ? " +
                            "WHERE employment_id = ?"
            );

            this.findByEmployeeIdStatement = connection.prepareStatement(
                    "SELECT * FROM employment " +
                            "WHERE employee_id = ?"
            );

            this.findByPostIdStatement = connection.prepareStatement(
                    "SELECT * FROM employment " +
                            "WHERE post_id = ?"
            );

            this.findByStartDateStatement = connection.prepareStatement(
                    "SELECT * FROM employment " +
                            "WHERE start_date = ?"
            );

            this.findByEndDateStatement = connection.prepareStatement(
                    "SELECT * FROM employment " +
                            "WHERE end_date = ?"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<Employment> create(Employment entity) {
        try {
            createStatement.setLong(1, entity.getEmployeeId());
            createStatement.setLong(2, entity.getPostId());
            createStatement.setDate(3, Date.valueOf(entity.getStartDate().toLocalDate()));

            if (entity.getEndDate() != null) {
                createStatement.setDate(4, Date.valueOf(entity.getEndDate().toLocalDate()));
            } else {
                createStatement.setNull(4, java.sql.Types.DATE);
            }

            int affectedRows = createStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return Optional.of(Employment.builder()
                            .employmentId(generatedId)
                            .employeeId(entity.getEmployeeId())
                            .postId(entity.getPostId())
                            .startDate(entity.getStartDate())
                            .endDate(entity.getEndDate())
                            .build());
                }
            }

            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create employment", e);
        }
    }

    @Override
    public Optional<Employment> findById(Long id) {
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    Employment employment = extractEmployment(result);
                    return Optional.of(employment);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employment " + id, e);
        }
    }

    @Override
    public List<Employment> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractEmploymentList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all employments", e);
        }
    }

    @Override
    public Optional<Employment> update(Employment entity) {
        try {
            updateStatement.setLong(1, entity.getEmployeeId());
            updateStatement.setLong(2, entity.getPostId());
            updateStatement.setDate(3, Date.valueOf(entity.getStartDate().toLocalDate()));

            if (entity.getEndDate() != null) {
                updateStatement.setDate(4, Date.valueOf(entity.getEndDate().toLocalDate()));
            } else {
                updateStatement.setNull(4, java.sql.Types.DATE);
            }

            updateStatement.setLong(5, entity.getEmploymentId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update employment " + entity.getEmploymentId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employment by id " + id, e);
        }
    }

    @Override
    public List<Employment> findByEmployeeId(Long employeeId) {
        try {
            findByEmployeeIdStatement.setLong(1, employeeId);
            try (ResultSet result = findByEmployeeIdStatement.executeQuery()) {
                return extractEmploymentList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employments for employee " + employeeId, e);
        }
    }

    @Override
    public List<Employment> findByPostId(Long postId) {
        try {
            findByPostIdStatement.setLong(1, postId);
            try (ResultSet result = findByPostIdStatement.executeQuery()) {
                return extractEmploymentList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employments for post " + postId, e);
        }
    }

    @Override
    public List<Employment> findByStartDate(LocalDateTime startDate) {
        try {
            findByStartDateStatement.setDate(1, Date.valueOf(startDate.toLocalDate()));
            try (ResultSet result = findByStartDateStatement.executeQuery()) {
                return extractEmploymentList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employments starting at " + startDate, e);
        }
    }

    @Override
    public List<Employment> findByEndDate(LocalDateTime endDate) {
        try {
            findByEndDateStatement.setDate(1, Date.valueOf(endDate.toLocalDate()));
            try (ResultSet result = findByEndDateStatement.executeQuery()) {
                return extractEmploymentList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get employments ending at " + endDate, e);
        }
    }

    private List<Employment> extractEmploymentList(ResultSet result) throws SQLException {
        List<Employment> employments = new ArrayList<>();
        while (result.next()) {
            employments.add(extractEmployment(result));
        }
        return employments;
    }

    private Employment extractEmployment(ResultSet result) throws SQLException {
        LocalDateTime startDate = result.getDate("start_date").toLocalDate().atStartOfDay();

        Date endDateSql = result.getDate("end_date");
        LocalDateTime endDate = null;
        if (endDateSql != null && !result.wasNull()) {
            endDate = endDateSql.toLocalDate().atStartOfDay();
        }

        return Employment.builder()
                .employmentId(result.getLong("employment_id"))
                .employeeId(result.getLong("employee_id"))
                .postId(result.getLong("post_id"))
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
