package com.github.ekaterina_vol.hr_department.domain.repositories.database;

import com.github.ekaterina_vol.hr_department.db.PostgreSQLManager;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.domain.repositories.PostRepository;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepository {
    @Getter(lazy = true)
    private static final PostRepositoryImpl instance = new PostRepositoryImpl();

    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByDepartmentStatement;
    private PreparedStatement findByTitleStatement;

    public PostRepositoryImpl() {
        Connection connection = PostgreSQLManager.getInstance().getConnection();
        try {
            this.createStatement = connection.prepareStatement(
                    "INSERT INTO post (title, department) " +
                            "VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM post " +
                            "WHERE post_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM post"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM post " +
                            "WHERE post_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE post " +
                            "SET title = ?, department = ? " +
                            "WHERE post_id = ?"
            );

            this.findByDepartmentStatement = connection.prepareStatement(
                    "SELECT * FROM post " +
                            "WHERE department = ?"
            );

            this.findByTitleStatement = connection.prepareStatement(
                    "SELECT * FROM post " +
                            "WHERE title = ?"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<Post> create(Post entity) {
        try {
            createStatement.setString(1, entity.getTitle());
            createStatement.setString(2, entity.getDepartment());

            int affectedRows = createStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return Optional.of(Post.builder()
                            .postId(generatedId)
                            .title(entity.getTitle())
                            .department(entity.getDepartment())
                            .build());
                }
            }

            // Если ID нет, возвращаем оригинальный объект без ID
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create post", e);
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    Post post = extractPost(result);
                    return Optional.of(post);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get post " + id, e);
        }
    }

    @Override
    public List<Post> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractPostList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all posts", e);
        }
    }

    @Override
    public Optional<Post> update(Post entity) {
        try {
            updateStatement.setString(1, entity.getTitle());
            updateStatement.setString(2, entity.getDepartment());
            updateStatement.setLong(3, entity.getPostId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update post " + entity.getPostId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete post by id " + id, e);
        }
    }

    @Override
    public List<Post> findByDepartment(String department) {
        try {
            findByDepartmentStatement.setString(1, department);
            try (ResultSet result = findByDepartmentStatement.executeQuery()) {
                return extractPostList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get posts with department " + department, e);
        }
    }

    @Override
    public List<Post> findByTitle(String title) {
        try {
            findByTitleStatement.setString(1, title);
            try (ResultSet result = findByTitleStatement.executeQuery()) {
                return extractPostList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get posts with title " + title, e);
        }
    }

    private List<Post> extractPostList(ResultSet result) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (result.next()) {
            posts.add(extractPost(result));
        }
        return posts;
    }

    private Post extractPost(ResultSet result) throws SQLException {
        return Post.builder()
                .postId(result.getLong("post_id"))
                .title(result.getString("title"))
                .department(result.getString("department"))
                .build();
    }
}
