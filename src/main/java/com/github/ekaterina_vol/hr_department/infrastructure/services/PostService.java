package com.github.ekaterina_vol.hr_department.infrastructure.services;

import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.domain.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Optional<Post> createPost(Post post) {
     validatePostId(post.getPostId());
     validateTitle(post.getTitle());
     validateDepartment(post.getDepartment());

     Optional<Post> curPost = postRepository.create(post);

     if (!curPost.isPresent()) {
         throw new IllegalArgumentException("Такой отдел и должность уже существуют");
     }
     return curPost;
    }
    public Optional<Post> findById(final Long id) {
        validatePostId(id);
        return postRepository.findById(id);
    }
    public List<Post> findAll() {
        return postRepository.findAll();
    }
    public Optional<Post> update(final Post post) {
        validatePostId(post.getPostId());
        validateTitle(post.getTitle());
        validateDepartment(post.getDepartment());

        Optional<Post> curPost = postRepository.update(post);

        if (!curPost.isPresent()) {
            throw new IllegalArgumentException("Такой отдел и должность уже существуют или id нет в хранилище");
        }

        return curPost;
    }

    public boolean deleteById(final Long id) {
        validatePostId(id);
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
