package ru.vk.education.job.cli.commands;

import ru.vk.education.job.cli.Command;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.service.JobTrackerService;

public class AddUserCommand implements Command {
    private final JobTrackerService service;
    private final User user;
    public AddUserCommand(JobTrackerService service, User user){
        this.service = service;
        this.user = user;
    }
    @Override
    public void execute() {
        service.addUser(user);
    }

/*    private String getName(){
        return args[0];
    }

    private Set<String> getSkills(){
        return Arrays.stream(args)
                .filter(s-> s.startsWith("--skills"))
                .findFirst()
                .map(s-> s.replace("--skills=",""))
                .map(s->s.split(","))
                .map(s->Arrays.stream(s).collect(Collectors.toSet()))
                .orElse(Set.of());
    }

    private int getExp(){
        return Arrays.stream(args)
                .filter(s->s.startsWith("--exp"))
                .findFirst()
                .map(s->s.replace("--exp=",""))
                .map(Integer::parseInt)
                .orElse(-1);
    }*/
    @Override
    public String name(){
        return "user";
    }
}
