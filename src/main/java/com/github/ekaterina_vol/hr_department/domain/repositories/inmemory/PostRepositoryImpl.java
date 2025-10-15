package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.domain.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepository {
    @Override
    public Optional<Post> create(Post entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Optional<Post> update(Post entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
