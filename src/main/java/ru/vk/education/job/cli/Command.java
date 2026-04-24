package ru.vk.education.job.cli;

public interface Command {
    void execute(String args);
    String name();
}
