package ru.vk.education.job.repository;

import ru.vk.education.job.domain.User;

import java.util.Set;

public interface UserRepository {
    void save(User user);
    Set<User> findAll();
    User findByName(String name);
}
