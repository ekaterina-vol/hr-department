package com.github.ekaterina_vol.hr_department.app.cli.commands;

import com.github.ekaterina_vol.hr_department.app.cli.commands.employee.CreateEmployeeCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employee.DeleteByIdEmployeeCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employee.FindAllEmployeeCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employee.FindByIdEmployeeCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employee.UpdateEmployeeCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employment.CreateEmploymentCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employment.DeleteByIdEmploymentCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employment.FindAllEmploymentCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employment.FindByIdEmploymentCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.employment.UpdateEmploymentCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.history.CreateEmployeeHistoryCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.history.DeleteByIdEmployeeHistoryCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.history.FindAllEmployeeHistoryCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.history.FindByIdEmployeeHistoryCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.history.UpdateEmployeeHistoryCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.post.CreatePostCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.post.DeleteByIdPostCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.post.FindAllPostCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.post.FindByIdPostCommand;
import com.github.ekaterina_vol.hr_department.app.cli.commands.post.UpdatePostCommand;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeHistoryService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmployeeService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.EmploymentService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import com.github.ekaterina_vol.hr_department.infrastructure.services.ServiceProvider;
import com.github.ekaterina_vol.hr_department.infrastructure.services.domain.DomainEmployeeServiceImpl;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommandFactory {
    private List<Command> commands;
    private HelpCommand helpCommand = new HelpCommand("help", "");

    public CommandFactory(ServiceProvider serviceProvider) {
        EmployeeService employeeService = serviceProvider.getDomainEmployeeService();
        EmployeeHistoryService employeeHistoryService = serviceProvider.getDomainEmployeeHistoryService();
        EmploymentService employmentService = serviceProvider.getDomainEmploymentService();
        PostService postService = serviceProvider.getDomainPostService();

        this.commands = new ArrayList<>();

        this.commands.add(new CreateEmployeeCommand(employeeService));
        this.commands.add(new DeleteByIdEmployeeCommand(employeeService));
        this.commands.add(new FindAllEmployeeCommand(employeeService));
        this.commands.add(new FindByIdEmployeeCommand(employeeService));
        this.commands.add(new UpdateEmployeeCommand(employeeService));

        this.commands.add(new CreateEmploymentCommand(employmentService));
        this.commands.add(new DeleteByIdEmploymentCommand(employmentService));
        this.commands.add(new FindAllEmploymentCommand(employmentService));
        this.commands.add(new FindByIdEmploymentCommand(employmentService));
        this.commands.add(new UpdateEmploymentCommand(employmentService));

        this.commands.add(new CreateEmployeeHistoryCommand(employeeHistoryService));
        this.commands.add(new DeleteByIdEmployeeHistoryCommand(employeeHistoryService));
        this.commands.add(new FindAllEmployeeHistoryCommand(employeeHistoryService));
        this.commands.add(new FindByIdEmployeeHistoryCommand(employeeHistoryService));
        this.commands.add(new UpdateEmployeeHistoryCommand(employeeHistoryService));

        this.commands.add(new CreatePostCommand(postService));
        this.commands.add(new DeleteByIdPostCommand(postService));
        this.commands.add(new FindAllPostCommand(postService));
        this.commands.add(new FindByIdPostCommand(postService));
        this.commands.add(new UpdatePostCommand(postService));

        this.commands.add(this.helpCommand);

        this.updateHelpMsg();
    }


    public Command recognize(String commandName) {
        for (Command command : commands) {
            if (command.getName().equals(commandName)) {
                return command;
            }
        }

        return null;
    }

    private void updateHelpMsg() {
        StringBuilder sb = new StringBuilder();

        sb.append("List of commands:\n");

        for (Command command : commands) {
            sb.append("\t");
            sb.append(command.getHelp());
            sb.append("\n");
        }

        sb.deleteCharAt(sb.length() - 1);
        helpCommand.setMsg(sb.toString());
    }
}
