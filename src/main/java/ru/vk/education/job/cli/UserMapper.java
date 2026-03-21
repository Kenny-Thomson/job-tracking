package ru.vk.education.job.cli;

import ru.vk.education.job.domain.User;

import java.util.Arrays;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toUser(CommandParser resultParsing) {
        return new User(resultParsing.positional(),
                Arrays.stream(resultParsing.flags().get("skills").split(",")).collect(Collectors.toSet()),
                Integer.parseInt(resultParsing.flags().get("exp")));

    }
}
