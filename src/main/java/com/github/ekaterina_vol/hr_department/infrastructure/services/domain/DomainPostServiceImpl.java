package com.github.ekaterina_vol.hr_department.infrastructure.services.domain;

import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DomainPostServiceImpl implements PostService {
    private final PostService postService;
    private final EmploymentService domainEmploymentService;

    @Override
    public Post create(Post entity) {
        return postService.create(entity);
    }

    @Override
    public Post findById(Long id) {
        return postService.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postService.findAll();
    }

    @Override
    public Post update(Post entity) {
        return postService.update(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        List<Employment> employments = domainEmploymentService.findByPostId(id);
        if (!employments.isEmpty()) {
            for (Employment e : employments) {
                domainEmploymentService.deleteById(e.getEmploymentId());
            }
        }
        return postService.deleteById(id);
    }

    @Override
    public List<Post> findByDepartment(String department) {
        return postService.findByDepartment(department);
    }

    @Override
    public List<Post> findByTitle(String title) {
        return postService.findByTitle(title);
    }
}
