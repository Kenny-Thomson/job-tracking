package ru.vk.education.job.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.vk.education.job.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        String name =  rs.getString("name");
        Set<String> skills = Arrays.stream(rs.getString("skills").split(","))
                .collect(Collectors.toSet());
        int exp = rs.getInt("exp");
        return new User(name,skills,exp);
    }
}
