package ru.vk.education.job.cli;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private String positional;
    private Map<String, String> flags = new HashMap<>();

    public CommandParser(String positional, Map<String, String> flags) {
        this.positional = positional;
        this.flags = flags;
    }

    public String positional() {
        return positional;
    }

    public Map<String, String> flags() {
        return flags;
    }

    public static CommandParser parsing(String line) {
        String[] splitLine = line.split("\s+");
        String positional = "";
        Map<String, String> flags = new HashMap<>();
        for (String str : splitLine) {
            if (str.startsWith("--") && str.contains("=")) {
                String[] split = str.split("=", 2);
                flags.put(split[0].substring(2), split[1]);
            }else {
                positional = str;
            }
        }
        return new CommandParser(positional, flags);
    }
    public static CommandParser parsingStatCommand(String line) {
        String positional = "";
        Map<String, String> flags = new HashMap<>();
        String[] splitLine = line.split("\s+");
        flags.put(splitLine[0].substring(2), splitLine[1]);
        return new CommandParser(positional, flags);
    }
}
