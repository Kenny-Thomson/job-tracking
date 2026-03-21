package ru.vk.education.job.cli;

import ru.vk.education.job.domain.Vacancy;

import java.util.Arrays;
import java.util.stream.Collectors;


public class VacancyMapper {
    public static Vacancy toVacancy(CommandParser resultParsing) {
        return new Vacancy(resultParsing.positional(),
                resultParsing.flags().get("company"),
                Arrays.stream(resultParsing.flags().get("tags").split(",")).collect(Collectors.toSet()),
                Integer.parseInt(resultParsing.flags().get("exp")));
    }
}
