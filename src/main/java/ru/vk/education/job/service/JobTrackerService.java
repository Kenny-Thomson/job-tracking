package ru.vk.education.job.service;

import ru.vk.education.job.domain.JobMatch;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.domain.Vacancy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class JobTrackerService {
    private Set<User> users = new TreeSet<>();
    private Set<Vacancy> vacancies = new HashSet<>();

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
}
