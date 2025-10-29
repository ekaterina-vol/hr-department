package com.github.ekaterina_vol.hr_department.domain.repositories;

import com.github.ekaterina_vol.hr_department.domain.entities.Post;

import java.util.List;

public interface PostRepository extends Repository<Post, Long> {
    List<Post> findByDepartment(String department);

    List<Post> findByTitle(String title);
}
