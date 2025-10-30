package com.github.ekaterina_vol.hr_department.app.cli.commands.history;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.EmployeeHistory;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeHistoryService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindAllEmployeeHistoryCommand implements Command {
    public static final String NAME = "employeeHistory-findAll";

    private EmployeeHistoryService service;

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<EmployeeHistory> employeeHistories = service.findAll();
        employeeHistories.forEach(System.out::println);
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
