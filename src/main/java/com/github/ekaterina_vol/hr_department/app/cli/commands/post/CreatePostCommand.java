package com.github.ekaterina_vol.hr_department.app.cli.commands.post;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreatePostCommand implements Command {
    public static final String NAME = "post-create";
    public static final String HELP_MESSAGE = NAME + "(title, department)";

    private PostService service;
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final String title = args[0];
        final String department = args[1];

        service.create(Post.builder()
                .title(title)
                .department(department)
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
