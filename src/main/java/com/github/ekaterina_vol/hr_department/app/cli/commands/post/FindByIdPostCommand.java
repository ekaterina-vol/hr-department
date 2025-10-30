package com.github.ekaterina_vol.hr_department.app.cli.commands.post;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import javax.print.attribute.standard.MediaSize;

@AllArgsConstructor
public class FindByIdPostCommand implements Command {
    public static final String NAME = "post-findById";
    public static final String HELP_MESSAGE = NAME + "(id)";
    private PostService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long id = Long.parseLong(args[0]);

        System.out.println(service.findById(id));
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
