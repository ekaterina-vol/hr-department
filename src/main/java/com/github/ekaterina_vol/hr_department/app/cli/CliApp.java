package com.github.ekaterina_vol.hr_department.app.cli;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.app.cli.commands.CommandFactory;
import com.github.ekaterina_vol.hr_department.infrastructure.services.ServiceProvider;

import java.util.Arrays;
import java.util.Scanner;

public class CliApp {
    private final CommandFactory commandFactory;
    public CliApp(ServiceProvider serviceProvider) {
        this.commandFactory = new CommandFactory(serviceProvider);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Command cmd = null;
            try {
                System.out.print("> ");
                final String input = scanner.nextLine();
                String[] args = input.split(" +");
                String commandName = args[0];
                if (commandName.equals("quit")) {
                    break;
                }
                cmd = commandFactory.recognize(commandName);
                if (cmd == null) {
                    System.out.printf("Command not found: %s\n", commandName);
                    continue;
                }
                String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
                cmd.execute(commandArgs);
            } catch (Exception e) {
                assert cmd != null;
                System.out.printf("Error: %s\n%s\n", e.getMessage(), cmd.getHelp());
            }
        }
    }
}
