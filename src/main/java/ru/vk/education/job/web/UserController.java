package ru.vk.education.job.web;

import org.springframework.web.bind.annotation.*;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.service.JobTrackerService;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final JobTrackerService service;

    public UserController(JobTrackerService service) {
        this.service = service;
    }

    @GetMapping
    public Set<User> getUsers(){
        return service.getUsers();
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        service.addUser(user);
    }
}
