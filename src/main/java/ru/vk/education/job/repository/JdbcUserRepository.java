package ru.vk.education.job.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.domain.User;

import java.util.List;
import java.util.Set;

@Repository
@Primary
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<User> mapper = new UserRowMapper();

    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (name, skills, exp) VALUES (?, ?, ?)";
        jdbc.update(sql,
                user.getName(),
                String.join(",", user.getSkills()),
                user.getExp());
    }

    @Override
    public Set<User> findAll() {
        String sql = "SELECT * FROM users ORDER BY name";
        return Set.copyOf(jdbc.query(sql, mapper));
    }

    @Override
    public User findByName(String name) {
        String sql = "SELECT * FROM users AS u WHERE u.name = ?";
        List<User> result = jdbc.query(sql, mapper, name);
        return result.isEmpty() ? null : result.get(0);
    }
}
