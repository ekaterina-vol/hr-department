package com.github.ekaterina_vol.hr_department.app.cli.commands.post;

import com.github.ekaterina_vol.hr_department.app.cli.commands.Command;
import com.github.ekaterina_vol.hr_department.domain.entities.Post;
import com.github.ekaterina_vol.hr_department.infrastructure.services.PostService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FindAllPostCommand implements Command {
    public static final String NAME = "post-findAll";

    private PostService service;


    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Post> posts = service.findAll();
        posts.forEach(System.out::println);
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
