package ru.vk.education.job.service;
import ru.vk.education.job.domain.Vacancy;
import java.util.Map;

public class JobSuggestionTask implements Runnable {
    private JobTrackerService service;
    public JobSuggestionTask(JobTrackerService service){
        this.service = service;
    }
    public void run(){
        Map<String, Vacancy> resultForUsers = service.getBestVacancyForAllUsers();
        resultForUsers.forEach((k,v)-> System.out.println(k + ", лучшее предложение - " + v));
    }
}
