package ru.vk.education.job.repository;

import org.springframework.jdbc.core.RowMapper;
import ru.vk.education.job.domain.Vacancy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class VacancyRowMapper implements RowMapper<Vacancy> {
    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {
        String title = rs.getString("title");
        String company = rs.getString("company");
        Set<String> tags = Arrays.stream(rs.getString("tags").split(","))
                .collect(Collectors.toSet());
        int exp = rs.getInt("exp");
        return new Vacancy(title,company,tags,exp);
    }
}
