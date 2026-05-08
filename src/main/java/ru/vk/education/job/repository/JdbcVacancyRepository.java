package ru.vk.education.job.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.domain.Vacancy;

import java.util.Set;

@Repository
@Primary
public class JdbcVacancyRepository implements VacancyRepository{
    private final JdbcTemplate jdbc;
    private final VacancyRowMapper mapper = new VacancyRowMapper();
    public JdbcVacancyRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void save(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies(title, company, tags, exp) VALUES (?, ?, ?, ?)";
        jdbc.update(sql,
                vacancy.getTitle(),
                vacancy.getCompany(),
                String.join(",", vacancy.getTags()),
                vacancy.getExp());
    }

    @Override
    public Set<Vacancy> findAll() {
        String sql = "SELECT * FROM vacancies ORDER BY title";
        return Set.copyOf(jdbc.query(sql,mapper));
    }
}
