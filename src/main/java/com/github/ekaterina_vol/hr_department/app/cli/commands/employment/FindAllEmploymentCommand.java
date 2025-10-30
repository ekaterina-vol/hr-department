package com.github.ekaterina_vol.hr_department.app.cli.commands.employment;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Employment;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindAllEmploymentCommand implements Command {
    public static final String NAME = "employment-findAll";

    private EmploymentService service;

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Employment> employments= service.findAll();
        employments.forEach(System.out::println);
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
