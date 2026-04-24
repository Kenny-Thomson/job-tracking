package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.cli.CommandParser;
import ru.vk.education.job.cli.UserMapper;
import ru.vk.education.job.service.JobTrackerService;

public class AddUserCommand implements Command {
    private final JobTrackerService service;

    public AddUserCommand(JobTrackerService service) {
        this.service = service;
    }

    @Override
    public void execute(String args) {
        CommandParser parsed = CommandParser.parsing(args);
        service.addUser(UserMapper.toUser(parsed));
    }

    @Override
    public String name() {
        return "user";
    }
}
