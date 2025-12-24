package com.github.ekaterina_vol.hr_department.infrastructure.services.impl;

import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.domain.repositories.PostRepository;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public Post create(Post entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validateTitle(entity.getTitle());
        validateDepartment(entity.getDepartment());

        Optional<Post> curPost = postRepository.create(entity);
        if (!curPost.isPresent()) {
            throw new IllegalArgumentException("Отдел '" + entity.getDepartment() +
                    "' и должность '" + entity.getTitle() + "' уже существуют");
        }
        return curPost.get();
    }

    public Post findById(final Long id) {
        validatePostId(id);

        Optional<Post> curPost = postRepository.findById(id);
        if (!curPost.isPresent()) {
            throw new IllegalArgumentException("Должности с id=" + id + " не существует");
        }
        return curPost.get();
    }

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return posts;
    }

    public Post update(final Post entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validatePostId(entity.getPostId());
        validateTitle(entity.getTitle());
        validateDepartment(entity.getDepartment());

        Optional<Post> curPost = postRepository.update(entity);

        if (!curPost.isPresent()) {
            throw new IllegalArgumentException("Должности с таким id не существует");
        }

        if (!curPost.get().equals(entity)) {
            throw new IllegalArgumentException("Отдел '" + entity.getDepartment() +
                    "' и должность '" + entity.getTitle() + "' уже существуют");
        }

        return curPost.get();
    }

    public boolean deleteById(final Long id) {
        validatePostId(id);

        Optional<Post> curPost = postRepository.findById(id);
        if (!curPost.isPresent()) {
            throw new IllegalArgumentException("Должности с id=" + id + " не существует");
        }
        return postRepository.deleteById(id);
    }

    public List<Post> findByDepartment(String department) {
        validateDepartment(department);

        List<Post> posts = postRepository.findByDepartment(department);
        if (posts.isEmpty()) {
            throw new IllegalArgumentException("Отдела '" + department + "' не существует");
        }
        return posts;
    }

    public List<Post> findByTitle(String title) {
        validateTitle(title);

        List<Post> posts = postRepository.findByTitle(title);
        if (posts.isEmpty()) {
            throw new IllegalArgumentException("Должности '" + title + "' не существует");
        }
        return posts;
    }

    private void validatePostId(Long postId) {
        if (postId == null || postId <= 0) {
            throw new IllegalArgumentException("Id  должности должен быть положительным числом");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует название должности");
        }
    }

    private void validateDepartment(String department) {
        if (department == null || department.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует название отдела");
        }
    }

}
