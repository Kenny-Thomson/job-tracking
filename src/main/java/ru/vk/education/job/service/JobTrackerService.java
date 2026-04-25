package ru.vk.education.job.service;

import org.springframework.stereotype.Service;
import ru.vk.education.job.domain.JobMatch;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.domain.Vacancy;
import ru.vk.education.job.repository.UserRepository;
import ru.vk.education.job.repository.VacancyRepository;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JobTrackerService {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;

    public JobTrackerService(UserRepository userRepository,
                             VacancyRepository vacancyRepository) {
        this.userRepository = userRepository;
        this.vacancyRepository = vacancyRepository;
    }

    public Set<User> getUsers() {
        return userRepository.findAll();
    }

    public Set<Vacancy> getVacancies() {
        return vacancyRepository.findAll();
    }

    public void addUser(User user) {
        lock.writeLock().lock();
        try {
            userRepository.save(user);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void printUsers() {
        userRepository.findAll().forEach(System.out::println);
    }

    public void addVacancy(Vacancy vacancy) {
        lock.writeLock().lock();
        try {
            vacancyRepository.save(vacancy);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void printVacancies() {
        vacancyRepository.findAll().forEach(System.out::println);
    }

    public User findUserByName(String nameUser) {
        return userRepository.findByName(nameUser);
    }

    public List<Vacancy> getSuggestVacancies(String nameUser) {
        User user = findUserByName(nameUser);
        if (user == null)
            return List.of();
        return JobMatch.findVacancies(user, vacancyRepository.findAll())
                .stream()
                .filter(JobMatch::isRelevantResult)
                .limit(2)
                .map(JobMatch::vacancy)
                .toList();
    }

    public void suggestVacancies(String nameUser) {
        getSuggestVacancies(nameUser).forEach(System.out::println);
    }

    public Map<String, List<?>> getStat(Map<String, String> params) {
        Map<String, List<?>> result = new HashMap<>();
        if (params.containsKey("exp")) {
            result.put("exp", getVacanciesByExp(Integer.parseInt(params.get("exp"))));
        }
        if (params.containsKey("match")) {
            result.put("match", getUsersByMatchCount(Integer.parseInt(params.get("match"))));
        }
        if (params.containsKey("top-skills")) {
            result.put("top-skills", getTopSkills(Long.parseLong(params.get("top-skills"))));
        }
        return result;
    }

    public void printStat(Map<String, String> params) {
        Map<String, List<?>> result = getStat(params);
        result.forEach((k, v) -> v.forEach(System.out::println));
    }

    public List<Vacancy> getVacanciesByExp(int exp) {
        return vacancyRepository.findAll().stream()
                .filter(v -> v.hasEnoughExperience(exp))
                .sorted(Comparator.comparing(Vacancy::getTitle))
                .toList();
    }

    public List<User> getUsersByMatchCount(int matchCount) {
        return userRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        u -> JobMatch.findVacancies(u, vacancyRepository.findAll()).stream()
                                .filter(JobMatch::isRelevantResult)
                                .collect(Collectors.counting())
                ))
                .entrySet().stream()
                .filter(e -> e.getValue() >= matchCount)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }

    public List<String> getTopSkills(long limit) {
        return userRepository.findAll().stream()
                .flatMap(u -> u.getSkills().stream())
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
                .toList();
    }

    public Map<String, Vacancy> getBestVacancyForAllUsers() {
        lock.readLock().lock();
        try {
            Map<String, Vacancy> bestVacancyForUsers = new TreeMap<>();
            for (User user : userRepository.findAll()) {
                List<JobMatch> matches = JobMatch.findVacancies(user, vacancyRepository.findAll());
                if (!matches.isEmpty() && matches.get(0).isRelevantResult())
                    bestVacancyForUsers.put(user.getName(), matches.get(0).vacancy());
            }
            return bestVacancyForUsers;
        } finally {
            lock.readLock().unlock();
        }
    }
}
