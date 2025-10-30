package com.github.ekaterina_vol.hr_department.app.cli.commands;

public interface Command {
    void execute(String[] args);

    String getHelp();

    String getName();
}
