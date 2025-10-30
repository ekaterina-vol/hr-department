package com.github.ekaterina_vol.hr_department.app.cli.commands.employment;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CreateEmploymentCommand implements Command {
    public static final String NAME = "employment-create";
    public static final String HELP_MESSAGE = NAME + "(employeeId, postId)";

    private EmploymentService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long employeeId = Long.parseLong(args[0]);
        final Long postId = Long.parseLong(args[1]);

        service.create(Employment.builder()
                .employeeId(employeeId)
                .postId(postId)
                .startDate(LocalDateTime.now())
                .endDate(null)
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
