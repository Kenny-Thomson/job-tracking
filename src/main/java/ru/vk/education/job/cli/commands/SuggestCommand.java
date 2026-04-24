package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.service.JobTrackerService;

public class SuggestCommand implements Command {
    private final JobTrackerService service;

    public SuggestCommand(JobTrackerService service){
        this.service = service;
    }
    @Override
    public void execute(String args) {
        service.suggestVacancies(args);
    }
    @Override
    public String name(){
        return "suggest";
    }
}
