package com.github.ekaterina_vol.hr_department.app.cli.commands.employment;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteByIdEmploymentCommand implements Command {
    public static final String NAME = "employment-deleteById";
    public static final String HELP_MESSAGE = NAME + "(id)";
    private EmploymentService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long id = Long.parseLong(args[0]);

        service.deleteById(id);
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
