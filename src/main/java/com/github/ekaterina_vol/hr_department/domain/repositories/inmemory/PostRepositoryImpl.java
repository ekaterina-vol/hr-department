package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.domain.repositories.PostRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class PostRepositoryImpl implements PostRepository {
    private final Map<Long, Post> postStorage = new HashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public Optional<Post> create(Post entity) {
        Long nextId = currentId.getAndIncrement();

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
        if (entity == null || entity.getPostId() == null || !postStorage.containsKey(entity.getPostId())) {
            return Optional.empty();
        }

        Long id = entity.getPostId();
        Post curPost = postStorage.get(id);
        if (entity.getTitle().equals(curPost.getTitle()) &&
        entity.getDepartment().equals(curPost.getDepartment())) {
            return Optional.of(curPost);
        }

        postStorage.put(entity.getPostId(), entity);
        return Optional.of(entity);
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
    public Optional<Post> findByTitle(String title) {
        return postStorage.values().stream()
                .filter(post -> title.equals(post.getTitle()))
                .findFirst();
    }
}