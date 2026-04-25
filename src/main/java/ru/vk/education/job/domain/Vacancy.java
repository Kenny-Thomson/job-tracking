package ru.vk.education.job.domain;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Vacancy implements Comparable<Vacancy> {
    private String title;
    private String company;
    private Set<String> tags;
    private int exp;

    public Vacancy(String title, String company, Set<String> tags, int exp) {
        this.title = title;
        this.company = company;
        this.tags = tags;
        this.exp = exp;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public Set<String> getTags() {
        return tags;
    }

    public int getExp() {
        return exp;
    }

    public double findMatches(User user) {
        long result = tags.stream().
                filter(tag -> user.hasSkill(tag)).
                collect(Collectors.counting());
        return result / (user.hasEnoughExperience(exp) ? 1. : 2.);
    }

    public boolean hasEnoughExperience(int exp) {
        return this.exp >= exp;
    }

    @Override
    public String toString() {
        return title + " at " + company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(title, vacancy.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public int compareTo(Vacancy v) {
        return this.title.compareTo(v.title);
    }

}
