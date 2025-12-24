package com.github.ekaterina_vol.hr_department.app.cli.commands.history;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeHistoryService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UpdateEmployeeHistoryCommand implements Command {
    public static final String NAME = "employeeHistory-update";
    public static final String HELP_MESSAGE = NAME + "(employeeHistoryId, changeDate, employeeId, lastName)";

    private EmployeeHistoryService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long employeeHistoryId = Long.parseLong(args[0]);
        final LocalDateTime changeDate = LocalDateTime.parse(args[1]);
        final Long employeeId = Long.parseLong(args[2]);
        final String lastName = args[3];

        service.update(EmployeeHistory.builder()
                .historyId(employeeHistoryId)
                .changeDate(changeDate)
                .employeeId(employeeId)
                .lastName(lastName)
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
