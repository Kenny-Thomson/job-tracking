package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.service.JobTrackerService;

public class PrintUsersCommand implements Command {
    private final JobTrackerService service;

    public PrintUsersCommand(JobTrackerService service) {
        this.service = service;
    }

    @Override
    public void execute(String args) {
        service.printUsers();
    }
    @Override
    public String name(){
        return "user-list";
    }
}
