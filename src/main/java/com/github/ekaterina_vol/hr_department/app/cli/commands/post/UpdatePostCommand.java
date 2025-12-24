package com.github.ekaterina_vol.hr_department.app.cli.commands.post;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdatePostCommand implements Command {
    public static final String NAME = "post-update";
    public static final String HELP_MESSAGE = NAME + "(postId, title, department)";

    private PostService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long postId = Long.parseLong(args[0]);
        final String title = args[1];
        final String department = args[2];

        service.update(Post.builder()
                .postId(postId)
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
