package com.github.ekaterina_vol.hr_department.app.cli.commands.employee;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindAllEmployeeCommand implements Command {
    public static final String NAME = "employee-findAll";

    private EmployeeService service;

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Employee> employees = service.findAll();
        employees.forEach(System.out::println);
    }

    @Override
    public String getHelp() {
        return NAME;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
