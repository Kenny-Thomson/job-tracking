package ru.vk.education.job.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vk.education.job.domain.Vacancy;
import ru.vk.education.job.service.JobTrackerService;

import java.util.List;

@RestController
@RequestMapping("/api/suggest")
public class SuggestController {
    private final JobTrackerService service;

    public SuggestController(JobTrackerService service) {
        this.service = service;
    }

    @GetMapping ("/{name}")
    public List<Vacancy> suggest(@PathVariable String name){
       return service.getSuggestVacancies(name);
    }
}
