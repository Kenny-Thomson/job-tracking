package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.domain.Vacancy;
import ru.vk.education.job.service.JobTrackerService;

public class AddVacancyCommand implements Command {
    private final JobTrackerService service;
    private final Vacancy vacancy;

    public AddVacancyCommand(JobTrackerService service, Vacancy vacancy) {
        this.service = service;
        this.vacancy = vacancy;
    }

    @Override
    public void execute() {
        service.addVacancy(vacancy);
    }
    @Override
    public String name(){
        return "job";
    }
}
