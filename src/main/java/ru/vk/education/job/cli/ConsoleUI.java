package ru.vk.education.job.cli;
import java.util.Scanner;

public class ConsoleUI {
    private final Handler handler;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Handler handler) {
        this.handler = handler;
    }

    public void runConsole() {
        while (true) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.equalsIgnoreCase("exit")) {
                break;
            }
            handler.handle(line);
        }
    }

}
