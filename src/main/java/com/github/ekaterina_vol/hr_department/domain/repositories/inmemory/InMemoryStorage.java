package com.github.ekaterina_vol.hr_department.domain.repositories.inmemory;

import com.github.ekaterina_vol.hr_department.domain.repositories.EmployeeHistoryRepository;
import com.github.ekaterina_vol.hr_department.domain.repositories.EmploymentRepository;
import com.github.ekaterina_vol.hr_department.domain.repositories.Storage;
import lombok.Getter;

@Getter
public class InMemoryStorage implements Storage {
    private static InMemoryStorage INSTANCE;

    private final EmployeeRepositoryImpl employeeRepository;
    private final EmployeeHistoryRepositoryImpl employeeHistoryRepository;
    private final EmploymentRepositoryImpl employmentRepository;
    private final PostRepositoryImpl postRepository;

    private InMemoryStorage() {
        this.employeeRepository = new EmployeeRepositoryImpl();
        this.employeeHistoryRepository = new EmployeeHistoryRepositoryImpl();
        this.employmentRepository = new EmploymentRepositoryImpl();
        this.postRepository = new PostRepositoryImpl();
    }

    public static InMemoryStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryStorage();
        }
        return INSTANCE;
    }
}
