package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.cli.CommandParser;
import ru.vk.education.job.cli.VacancyMapper;
import ru.vk.education.job.service.JobTrackerService;

public class AddVacancyCommand implements Command {
    private final JobTrackerService service;

    public AddVacancyCommand(JobTrackerService service) {
        this.service = service;
    }

    @Override
    public void execute(String args) {
        CommandParser parsed = CommandParser.parsing(args);
        service.addVacancy(VacancyMapper.toVacancy(parsed));
    }

    @Override
    public String name() {
        return "job";
    }
}
