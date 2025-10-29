package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.domain.repositories.PostRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PostRepositoryImpl implements PostRepository {
    private final Map<Long, Post> postStorage = new HashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public Optional<Post> create(Post entity) {
        Long nextId = currentId.getAndIncrement();

        if (findByDepartmentAndTitle(entity.getDepartment(), entity.getTitle()).isPresent()) {
            return Optional.empty();
        }

        Post postToSave = Post.builder()
                .postId(nextId)
                .title(entity.getTitle())
                .department(entity.getDepartment())
                .build();

        postStorage.put(postToSave.getPostId(), postToSave);
        return Optional.of(postToSave);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(postStorage.get(id));
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(postStorage.values());
    }

    @Override
    public Optional<Post> update(Post entity) {
        if (!postStorage.containsKey(entity.getPostId())) {
            return Optional.empty();
        }

        Long id = entity.getPostId();
        Post curPost = postStorage.get(id);
        if (entity.getTitle().equals(curPost.getTitle()) &&
                entity.getDepartment().equals(curPost.getDepartment())) {
            return Optional.of(curPost);
        }

        Optional<Post> uniquePost = findByDepartmentAndTitle(entity.getDepartment(), entity.getTitle());
        if (uniquePost.isPresent()
                && !curPost.getDepartment().equals(uniquePost.get().getDepartment())
                && !curPost.getTitle().equals(uniquePost.get().getTitle())) {
            return uniquePost;
        }

        Post updatedPost = Post.builder()
                .postId(entity.getPostId())
                .title(entity.getTitle())
                .department(entity.getDepartment())
                .build();

        postStorage.put(updatedPost.getPostId(), updatedPost);
        return Optional.of(updatedPost);
    }

    @Override
    public boolean deleteById(Long id) {
        return postStorage.remove(id) != null;
    }

    @Override
    public List<Post> findByDepartment(String department) {
        return postStorage.values().stream()
                .filter(post -> department.equals(post.getDepartment()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findByTitle(String title) {
        return postStorage.values().stream()
                .filter(post -> title.equals(post.getTitle()))
                .collect(Collectors.toList());
    }

    private Optional<Post> findByDepartmentAndTitle(String department, String title) {
        return postStorage.values().stream()
                .filter(post -> post.getDepartment().equals(department)
                        && post.getTitle().equals(title))
                .findFirst();
    }
}