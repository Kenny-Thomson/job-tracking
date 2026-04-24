package ru.vk.education.job.cli;

import ru.vk.education.job.storage.FileService;

public class LoggedCommandHandler implements Handler{
    private final Handler handler;
    private final FileService fileService;

    public LoggedCommandHandler(Handler handler, FileService fileService) {
        this.handler = handler;
        this.fileService = fileService;
    }

    @Override
    public void handle(String inputCmd) {
        handler.handle(inputCmd);
        fileService.saveCommandInFile(inputCmd);
    }
}
