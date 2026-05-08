package ru.vk.education.job.repository;

import ru.vk.education.job.domain.Vacancy;

import java.util.Set;

public interface VacancyRepository {
    void save(Vacancy vacancy);
    Set<Vacancy> findAll();
}
