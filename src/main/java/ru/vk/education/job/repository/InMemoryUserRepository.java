package ru.vk.education.job.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.domain.User;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Set<User> users = new TreeSet<>();

    public void save(User user) {
        users.add(user);
    }

    public Set<User> findAll() {
        return Collections.unmodifiableSet(users);
    }

    public User findByName(String name) {
        return users.stream().
                filter(user -> name.equals(user.getName())).
                findFirst().
                orElse(null);
    }
}
