package com.github.ekaterina_vol.hr_department.app.cli.commands.employee;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UpdateEmployeeCommand implements Command {
    public static final String NAME = "employee-update";
    public static final String HELP_MESSAGE = NAME + "(employeeId, firstName, lastName, birthDate, sex, createdDate)";

    private EmployeeService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 6) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long employeeId = Long.parseLong(args[0]);
        final String firstName = args[1];
        final String lastName = args[2];
        final LocalDateTime birthDate = LocalDateTime.parse(args[3]);
        final Employee.Sex sex = Employee.Sex.valueOf(args[4]);
        final LocalDateTime createdDate = LocalDateTime.parse(args[5]);

        service.create(Employee.builder()
                .employeeId(employeeId)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .sex(sex)
                .createdAt(createdDate)
                .build()
        );
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
