package com.github.ekaterina_vol.hr_department.app.cli.commands.history;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeHistoryService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CreateEmployeeHistoryCommand implements Command {
    public static final String NAME = "employeeHistory-create";
    public static final String HELP_MESSAGE = NAME + "(employeeId, newLastName)";

    private EmployeeHistoryService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long employeeId = Long.parseLong(args[0]);
        final String newLastName = args[1];

        service.create(EmployeeHistory.builder()
                .changeDate(LocalDateTime.now())
                .employeeId(employeeId)
                .lastName(newLastName)
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
