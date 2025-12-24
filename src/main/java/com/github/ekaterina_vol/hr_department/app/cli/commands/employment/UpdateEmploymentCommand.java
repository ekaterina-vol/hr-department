package com.github.ekaterina_vol.hr_department.app.cli.commands.employment;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UpdateEmploymentCommand implements Command {
    public static final String NAME = "employment-update";
    public static final String HELP_MESSAGE = NAME + "(employmentId, employeeId, postId, startDate, endDate)";

    private EmploymentService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 5) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long employmentId = Long.parseLong(args[0]);
        final Long employeeId = Long.parseLong(args[1]);
        final Long postId = Long.parseLong(args[2]);
        final LocalDateTime startDate = LocalDateTime.parse(args[3]);
        final LocalDateTime endDate = LocalDateTime.parse(args[4]);

        service.update(Employment.builder()
                .employmentId(employmentId)
                .employeeId(employeeId)
                .postId(postId)
                .startDate(startDate)
                .endDate(endDate)
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
