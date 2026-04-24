package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.service.JobTrackerService;

public class SuggestCommand implements Command {
    private final JobTrackerService service;
    private final String nameUser;

    public SuggestCommand(JobTrackerService service, String nameUser){
        this.service = service;
        this.nameUser = nameUser;
    }
    @Override
    public void execute() {
        service.suggestVacancies(nameUser);
    }
    @Override
    public String name(){
        return "suggest";
    }
}
