package ru.vk.education.job.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JobMatch {
    private Vacancy vacancy;
    private double result;

    public JobMatch(Vacancy vacancy, double result) {
        this.vacancy = vacancy;
        this.result = result;
    }

    public static List<JobMatch> findVacancies(User user, Set<Vacancy> vacancies) {
        List<JobMatch> matches = new ArrayList<>();
        for (Vacancy v : vacancies) {
            double result = v.findMatches(user);
            matches.add(new JobMatch(v, result));
        }
        matches.sort((v1, v2) -> (Double.compare(v2.result, v1.result)));
        return matches;
    }

    public boolean isRelevantResult() {
        return result > 0;
    }

    @Override
    public String toString() {
        return vacancy.toString();
    }
}
