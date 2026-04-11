package ru.vk.education.job.service;

import ru.vk.education.job.cli.CommandParser;
import ru.vk.education.job.domain.JobMatch;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.domain.Vacancy;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class JobTrackerService {
    private Set<User> users = new TreeSet<>();
    private Set<Vacancy> vacancies = new TreeSet<>();


    public void addUser(User user) {
        users.add(user);
    }

    public void printUsers() {
        users.forEach(System.out::println);
    }

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    public void printVacancy() {
        vacancies.forEach(System.out::println);
    }

    public void match(String nameUser) {
        User correctUser = users.stream().
                filter(user -> nameUser.equals(user.name())).
                findFirst().
                orElse(null);
        if (correctUser != null) {
            List<JobMatch> matches = JobMatch.findVacancies(correctUser, vacancies);
            matches.stream().filter(JobMatch::isRelevantResult).limit(2).forEach(System.out::println);
        }
    }

    public void executeStatCommand(Map<String, String> commandWithValue) {
        if (commandWithValue.containsKey("exp")) {
            vacancies.stream()
                    .filter(v -> v.hasEnoughExperience(Integer.parseInt(commandWithValue.get("exp"))))
                    .sorted(Comparator.comparing(Vacancy::title))
                    .forEach(System.out::println);
        }
        if (commandWithValue.containsKey("match")) {
            Map<User, Long> usersWithMatches1 = users.stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            u -> JobMatch.findVacancies(u, vacancies).stream()
                                    .filter(JobMatch::isRelevantResult)
                                    .collect(Collectors.counting())

                    ));
            usersWithMatches1.entrySet().stream()
                    .filter(e -> e.getValue() >= Integer.parseInt(commandWithValue.get("match")))
                    .map(Map.Entry::getKey)
                    .sorted()
                    .forEach(System.out::println);
        }
        if (commandWithValue.containsKey("top-skills")) {
            Map<String, Long> topSkills = users.stream()
                    .flatMap(u -> u.skills().stream())
                    .collect(Collectors.groupingBy(Function.identity(),
                            Collectors.counting()));
            topSkills.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(Long.parseLong(commandWithValue.get("top-skills")))
                    .map(Map.Entry::getKey)
                    .sorted()
                    .forEach(System.out::println);
        }
    }

    public Map<String,Vacancy> getBestVacancyForAllUsers(){
       Map<String, Vacancy> bestVacancyForUsers = new HashMap<>();
       for (User user : users){
            List<JobMatch> matches = JobMatch.findVacancies(user,vacancies);
            if (!matches.isEmpty() && matches.get(0).isRelevantResult())
                bestVacancyForUsers.put(user.name(),matches.get(0).vacancy());
       }
       return bestVacancyForUsers;
    }

}
