package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.service.JobTrackerService;

import java.util.Map;

public class StatCommand implements Command {
    private final JobTrackerService service;
    private final Map<String, String> params;

    public StatCommand(JobTrackerService service, Map<String, String> params) {
        this.service = service;
        this.params = params;
    }

    @Override
    public void execute() {
        service.printStat(params);
    }

    @Override
    public String name() {
        return "stat";
    }
}
