package ru.vk.education.job;

import ru.vk.education.job.cli.ConsoleUi;
import ru.vk.education.job.service.JobTrackerService;

public class Main {
    public static void main(String[] args) {
        JobTrackerService service = new JobTrackerService();
        ConsoleUi consoleUi = new ConsoleUi(service);
        consoleUi.runConsole();
    }
}