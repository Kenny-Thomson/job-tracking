package ru.vk.education.job;

import ru.vk.education.job.cli.CommandHandler;
import ru.vk.education.job.cli.ConsoleUI;
import ru.vk.education.job.cli.Handler;
import ru.vk.education.job.cli.LoggedCommandHandler;
import ru.vk.education.job.cli.commands.*;
import ru.vk.education.job.service.JobSuggestionTask;
import ru.vk.education.job.service.JobTrackerService;
import ru.vk.education.job.storage.FileService;

import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        JobTrackerService service = new JobTrackerService();
        FileService fileService = new FileService();

        Handler commandHandler = getHandler(service, fileService);
        fileService.loadCommands().forEach(commandHandler::handle);

        Handler handler = new LoggedCommandHandler(
                commandHandler,
                fileService
        );

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(
                new JobSuggestionTask(service),
                1,
                60,
                TimeUnit.SECONDS);

        ConsoleUI consoleUI = new ConsoleUI(handler);
        consoleUI.runConsole();

        shutdownAndAwaitTermination(executor);
    }

    private static Handler getHandler(JobTrackerService service, FileService fileService) {
        return new CommandHandler(List.of(
                new AddUserCommand(service),
                new AddVacancyCommand(service),
                new PrintUsersCommand(service),
                new PrintVacanciesCommand(service),
                new SuggestCommand(service),
                new StatCommand(service),
                new HistoryCommand(fileService)
        ));
    }

    private static void shutdownAndAwaitTermination(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    //System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}