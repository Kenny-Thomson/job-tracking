package ru.vk.education.job.domain;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class User implements Comparable<User> {
    private String name;
    private Set<String> skills;
    private int exp;

    public User(String name, Set<String> skills, int exp) {
        this.name = name;
        this.skills = skills;
        this.exp = exp;
    }

    public boolean hasSkill(String skill) {
        return skills.contains(skill);
    }

    public boolean hasEnoughExperience(int exp) {
        return this.exp >= exp;
    }

    public String name() {
        return name;
    }

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
*/

    @Override
    public String toString() {
        return name + " "
                + skills.stream().collect(Collectors.joining(",")) + " "
                + exp;
    }

    @Override
    public int compareTo(User u){
        return this.name.compareTo(u.name);
    }
}
