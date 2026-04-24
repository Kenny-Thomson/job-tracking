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
        String[] parts = inputCmd.split("\\s+",2);
        String commandName = parts[0];
        String args = parts.length>1 ? parts[1] : "";
        Command command = commands.get(commandName);
        if (command == null){
            return;
        }
        command.execute(args);
    }
}
