package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.storage.FileService;

public class HistoryCommand implements Command {
    private final FileService fileService;

    public HistoryCommand(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void execute() {
        fileService.printCommandsFromFile();
    }
    @Override
    public String name(){
        return "history";
    }
}
