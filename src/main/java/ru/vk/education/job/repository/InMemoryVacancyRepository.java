package ru.vk.education.job.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.vk.education.job.domain.Vacancy;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class InMemoryVacancyRepository implements VacancyRepository {
    private final Set<Vacancy> vacancies = new TreeSet<>();

    public void save(Vacancy vacancy){
        vacancies.add(vacancy);
    }

    public Set<Vacancy> findAll(){
        return Collections.unmodifiableSet(vacancies);
    }
}
