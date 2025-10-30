package com.github.ekaterina_vol.hr_department.infrastructure.services;

import com.github.ekaterina_vol.hr_department.domain.repositories.Storage;
import com.github.ekaterina_vol.hr_department.infrastructure.services.domain.DomainEmployeeHistoryServiceImpl;
import com.github.ekaterina_vol.hr_department.infrastructure.services.domain.DomainEmployeeServiceImpl;
import com.github.ekaterina_vol.hr_department.infrastructure.services.domain.DomainEmploymentServiceImpl;
import com.github.ekaterina_vol.hr_department.infrastructure.services.domain.DomainPostServiceImpl;
import com.github.ekaterina_vol.hr_department.infrastructure.services.impl.EmployeeHistoryServiceImpl;
import com.github.ekaterina_vol.hr_department.infrastructure.services.impl.EmployeeServiceImpl;
import com.github.ekaterina_vol.hr_department.infrastructure.services.impl.EmploymentServiceImpl;
import com.github.ekaterina_vol.hr_department.infrastructure.services.impl.PostServiceImpl;
import lombok.Getter;

@Getter
public class ServiceProvider {
    private final EmployeeService domainEmployeeService;
    private final EmployeeHistoryService domainEmployeeHistoryService;
    private final EmploymentService domainEmploymentService;
    private final PostService domainPostService;

    public ServiceProvider(Storage storage) {
        EmploymentService employmentService = new EmploymentServiceImpl(storage.getEmploymentRepository());
        EmployeeHistoryService employeeHistoryService = new EmployeeHistoryServiceImpl(storage.getEmployeeHistoryRepository());
        EmployeeService employeeService = new EmployeeServiceImpl(storage.getEmployeeRepository());
        PostService postService = new PostServiceImpl(storage.getPostRepository());

        this.domainEmployeeHistoryService = new DomainEmployeeHistoryServiceImpl(employeeHistoryService, employeeService);
        this.domainEmploymentService = new DomainEmploymentServiceImpl(employmentService, employeeService,postService);
        this.domainPostService = new DomainPostServiceImpl(postService, domainEmploymentService);
        this.domainEmployeeService = new DomainEmployeeServiceImpl(employeeService, domainEmploymentService, domainEmployeeHistoryService);
    }
}
