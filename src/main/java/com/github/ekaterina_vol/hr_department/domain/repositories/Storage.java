package com.github.ekaterina_vol.hr_department.domain.repositories;

public interface Storage {
    EmployeeRepository getEmployeeRepository();

    EmployeeHistoryRepository getEmployeeHistoryRepository();

    EmploymentRepository getEmploymentRepository();

    PostRepository getPostRepository();
}
