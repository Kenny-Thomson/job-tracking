package ru.vk.education.job.storage;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    private final Path fileName = Paths.get("commands_history.txt");


    public void saveCommandInFile(String line){
        try {
            Files.writeString(fileName,line + "\n",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {

        }
    }
    public void printCommandsFromFile(){
        try(Stream<String> lines = Files.lines(fileName)) {
           lines.forEach(System.out::println);
        } catch (IOException e) {

        }
    }

    public List<String> loadCommands(){
        try(Stream<String> lines = Files.lines(fileName)) {
             return lines.filter(line -> line.startsWith("user ") || line.startsWith("job "))
                     .collect(Collectors.toList());

        } catch (IOException e) {
            return List.of();
        }
    }
}
