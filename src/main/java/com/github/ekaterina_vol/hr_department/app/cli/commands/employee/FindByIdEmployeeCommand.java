package com.github.ekaterina_vol.hr_department.app.cli.commands.employee;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindByIdEmployeeCommand implements Command {
    public static final String NAME = "employee-findById";
    public static final String HELP_MESSAGE = NAME + "(id)";
    private EmployeeService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long id = Long.parseLong(args[0]);

        System.out.println(service.findById(id));
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
