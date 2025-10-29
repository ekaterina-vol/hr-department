package com.github.ekaterina_vol.hr_department.infrastructure.services;

import com.github.ekaterina_vol.hr_department.domain.entities.Post;

import java.util.List;
import java.util.Optional;

public interface PostService extends Service<Post, Long> {
    List<Post> findByDepartment(String department);

    List<Post> findByTitle(String title);
}
