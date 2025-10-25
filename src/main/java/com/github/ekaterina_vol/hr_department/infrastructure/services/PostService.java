package com.github.ekaterina_vol.hr_department.infrastructure.services;

import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.domain.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Получена пустая сущность");
        }

        validatePostId(entity.getPostId());
        validateTitle(entity.getTitle());
        validateDepartment(entity.getDepartment());

        Optional<Post> curPost = postRepository.create(entity);
        if (!curPost.isPresent()) {
            throw new IllegalArgumentException("Такой отдел и должность уже существуют");
        }
        return curPost.get();
    }

    public Post findById(final Long id) {
        validatePostId(id);

        Optional<Post> curPost = postRepository.findById(id);
        if (!curPost.isPresent()) {
            throw new IllegalArgumentException("Должности с таким id не существует");
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
            throw new IllegalArgumentException("Такой отдел и должность уже существуют");
        }

        return curPost.get();
    }

    public boolean deleteById(final Long id) {
        validatePostId(id);

        Optional<Post> curPost = postRepository.findById(id);
        if (!curPost.isPresent()) {
            throw new IllegalArgumentException("Должности с таким id не существует");
        }
        return postRepository.deleteById(id);
    }

    public List<Post> findByDepartment(String department) {
        validateDepartment(department);
        return postRepository.findByDepartment(department);
    }

    public Optional<Post> findByTitle(String title) {
        validateTitle(title);
        return postRepository.findByTitle(title);
    }

    private void validatePostId(Long postId) {
        if (postId == null || postId <= 0) {
            throw new IllegalArgumentException("ID  должности должен быть положительным числом");
        }
    }

    private void validateTitle(String title) {
        if (title == null || !title.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует название должности");
        }
    }

    private void validateDepartment(String department) {
        if (department == null || !department.isEmpty()) {
            throw new IllegalArgumentException("Отсутствует название отдела");
        }
    }

}
