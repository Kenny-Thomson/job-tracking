package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.service.JobTrackerService;

public class PrintVacanciesCommand implements Command {
    private final JobTrackerService service;

    public PrintVacanciesCommand(JobTrackerService service) {
        this.service = service;
    }

    @Override
    public void execute(String args) {
        service.printVacancies();
    }
    @Override
    public String name(){
        return "job-list";
    }
}
