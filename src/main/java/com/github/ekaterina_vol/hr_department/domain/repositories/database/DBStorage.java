package com.github.ekaterina_vol.hr_department.domain.repositories.database;

import com.github.ekaterina_vol.hr_department.domain.repositories.Storage;
import com.github.ekaterina_vol.hr_department.domain.repositories.inmemory.EmployeeHistoryRepositoryImpl;
import com.github.ekaterina_vol.hr_department.domain.repositories.inmemory.EmployeeRepositoryImpl;
import com.github.ekaterina_vol.hr_department.domain.repositories.inmemory.EmploymentRepositoryImpl;
import com.github.ekaterina_vol.hr_department.domain.repositories.inmemory.InMemoryStorage;
import com.github.ekaterina_vol.hr_department.domain.repositories.inmemory.PostRepositoryImpl;
import lombok.Getter;

@Getter
public class DBStorage implements Storage {
    private static DBStorage INSTANCE;

    private final EmployeeRepositoryImpl employeeRepository;
    private final EmployeeHistoryRepositoryImpl employeeHistoryRepository;
    private final EmploymentRepositoryImpl employmentRepository;
    private final PostRepositoryImpl postRepository;

    private DBStorage() {
        this.employeeRepository = new EmployeeRepositoryImpl();
        this.employeeHistoryRepository = new EmployeeHistoryRepositoryImpl();
        this.employmentRepository = new EmploymentRepositoryImpl();
        this.postRepository = new PostRepositoryImpl();
    }

    public static DBStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DBStorage();
        }
        return INSTANCE;
    }
}
