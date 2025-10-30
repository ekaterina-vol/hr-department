package com.github.ekaterina_vol.hr_department.app.cli.commands;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class HelpCommand implements Command{
    private final String name;
    @Setter
    private String msg;

    @Override
    public void execute(String[] args) {
        System.out.println(this.msg);
    }

    @Override
    public String getHelp() {
        return this.name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
