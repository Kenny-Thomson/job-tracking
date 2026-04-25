package ru.vk.education.job.web;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.domain.Vacancy;
import ru.vk.education.job.service.JobTrackerService;

import java.util.List;

@RestController
@RequestMapping("/api/stat")
public class StatController {
    private final JobTrackerService service;

    public StatController(JobTrackerService service) {
        this.service = service;
    }
    @GetMapping("/exp/{exp}")
    public List<Vacancy> getStatByVacanciesExp(@PathVariable int exp){
        return service.getVacanciesByExp(exp);
    }
    @GetMapping("/match/{matchCount}")
    public List<User> getStatUsersMatchCount(@PathVariable int matchCount){
        return service.getUsersByMatchCount(matchCount);
    }
    @GetMapping("/top-skills/{limit}")
    public List<String> getStatByTopSkills(@PathVariable long limit){
        return service.getTopSkills(limit);
    }
}
