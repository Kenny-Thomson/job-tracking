package ru.vk.education.job.cli;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandHandler implements Handler{
    private final Map<String, Command> commands;

    public CommandHandler(List<Command> commands){
        this.commands = commands.stream().collect(Collectors.toMap(
                Command::name,
                Function.identity()
        ));
    }

    @Override
    public void handle(String inputCmd) {
        Command command = commands.get(inputCmd);
        if (command == null){
            return;
        }
        command.execute();
    }
}
