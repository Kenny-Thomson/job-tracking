package ru.vk.education.job.web;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.domain.Vacancy;
import ru.vk.education.job.service.JobTrackerService;

import java.util.Set;

@RestController
@RequestMapping("/api/vacancies")
public class JobController {
    private final JobTrackerService service;

    public JobController(JobTrackerService service) {
        this.service = service;
    }

    @GetMapping
    public Set<Vacancy> getVacancies(){
        return service.getVacancies();
    }

    @PostMapping
    public void addVacancy(@RequestBody Vacancy vacancy){
        service.addVacancy(vacancy);
    }
}
