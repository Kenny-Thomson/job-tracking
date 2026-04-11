package ru.vk.education.job;

import ru.vk.education.job.cli.ConsoleUi;
import ru.vk.education.job.service.JobSuggestionTask;
import ru.vk.education.job.service.JobTrackerService;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        JobTrackerService service = new JobTrackerService();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(
                new JobSuggestionTask(service),
                1,
                60,
                TimeUnit.SECONDS);

        ConsoleUi consoleUi = new ConsoleUi(service);
        consoleUi.runConsole();

        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                executor.awaitTermination(5, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }


    }
}