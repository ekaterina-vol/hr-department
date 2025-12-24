package com.github.ekaterina_vol.hr_department.app.cli.commands.employee;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Employee;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CreateEmployeeCommand implements Command {
    public static final String NAME = "employee-create";
    public static final String HELP_MESSAGE = NAME + "(firstName, lastName, birthDate, sex)";

    private EmployeeService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final String firstName = args[0];
        final String lastName = args[1];
        final LocalDateTime birthDate = LocalDateTime.parse(args[2]);
        final Employee.Sex sex = Employee.Sex.valueOf(args[3]);

        service.create(Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .sex(sex)
                .createdAt(LocalDateTime.now())
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
