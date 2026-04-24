package ru.vk.education.job.service;

import ru.vk.education.job.domain.JobMatch;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.domain.Vacancy;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Collectors;


public class JobTrackerService {
    private final Set<User> users = new TreeSet<>();
    private final Set<Vacancy> vacancies = new TreeSet<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void addUser(User user) {
        lock.writeLock().lock();
        try {
            users.add(user);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void printUsers() {
        users.forEach(System.out::println);
    }

    public void addVacancy(Vacancy vacancy) {
        lock.writeLock().lock();
        try {
            vacancies.add(vacancy);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void printVacancies() {
        vacancies.forEach(System.out::println);
    }

    public User findUserByName(String nameUser) {
        return users.stream().
                filter(user -> nameUser.equals(user.name())).
                findFirst().
                orElse(null);
    }

    public void suggestVacancies(String nameUser) {
        User user = findUserByName(nameUser);
        if (user == null)
            return;
        JobMatch.findVacancies(user, vacancies)
                .stream()
                .filter(JobMatch::isRelevantResult)
                .limit(2)
                .forEach(System.out::println);
    }

    public void printStat(Map<String, String> params) {
        if (params.containsKey("exp")) {
            printVacanciesByExp(Integer.parseInt(params.get("exp")));
        }
        if (params.containsKey("match")) {
            printUsersByMatchCount(Integer.parseInt(params.get("match")));
        }
        if (params.containsKey("top-skills")) {
            printTopSkills(Long.parseLong(params.get("top-skills")));
        }
    }

    private void printVacanciesByExp(int exp) {
        vacancies.stream()
                .filter(v -> v.hasEnoughExperience(exp))
                .sorted(Comparator.comparing(Vacancy::title))
                .forEach(System.out::println);
    }

    private void printUsersByMatchCount(int matchCount) {
        users.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        u -> JobMatch.findVacancies(u, vacancies).stream()
                                .filter(JobMatch::isRelevantResult)
                                .collect(Collectors.counting())
                ))
                .entrySet().stream()
                .filter(e -> e.getValue() >= matchCount)
                .map(Map.Entry::getKey)
                .sorted()
                .forEach(System.out::println);
    }

    private void printTopSkills(long limit) {
        users.stream()
                .flatMap(u -> u.skills().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting())
                )
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .sorted()
                .forEach(System.out::println);
    }

    public Map<String, Vacancy> getBestVacancyForAllUsers() {
        lock.readLock().lock();
        try {
            Map<String, Vacancy> bestVacancyForUsers = new TreeMap<>();
            for (User user : users) {
                List<JobMatch> matches = JobMatch.findVacancies(user, vacancies);
                if (!matches.isEmpty() && matches.get(0).isRelevantResult())
                    bestVacancyForUsers.put(user.name(), matches.get(0).vacancy());
            }
            return bestVacancyForUsers;
        } finally {
            lock.readLock().unlock();
        }
    }
}
