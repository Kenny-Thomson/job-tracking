package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.cli.CommandParser;
import ru.vk.education.job.service.JobTrackerService;

public class StatCommand implements Command {
    private final JobTrackerService service;

    public StatCommand(JobTrackerService service) {
        this.service = service;
    }

    @Override
    public void execute(String args) {
        CommandParser parsed = CommandParser.parsingStatCommand(args);
        service.printStat(parsed.flags());
    }

    @Override
    public String name() {
        return "stat";
    }
}
