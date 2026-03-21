package ru.vk.education.job.cli;

import ru.vk.education.job.service.JobTrackerService;

import java.util.Scanner;

public class ConsoleUi {
    private JobTrackerService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUi(JobTrackerService service) {
        this.service = service;
    }

    public void runConsole() {
        while (true) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] splitLine = line.trim().split("\s+", 2);

            switch (splitLine[0]) {
                case "user" -> {
                    try {
                        CommandParser resultParsing = CommandParser.parsing(splitLine[1]);
                        service.addUser(UserMapper.toUser(resultParsing));
                    }catch(Exception e){

                    }
                }
                case "user-list" -> service.printUsers();
                case "job" -> {
                    try {
                        CommandParser resultParsing = CommandParser.parsing(splitLine[1]);
                        service.addVacancy(VacancyMapper.toVacancy(resultParsing));
                    }catch (Exception e){

                    }
                }
                case "job-list" -> service.printVacancy();
                case "suggest" -> {
                    try {
                        service.match(splitLine[1]);
                    }catch (Exception e){

                    }
                }
                case "exit" -> System.exit(0);
            }
        }
    }

}
