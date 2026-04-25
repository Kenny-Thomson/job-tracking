package ru.vk.education.job.service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.vk.education.job.domain.Vacancy;
import java.util.Map;

@Component
public class JobSuggestionTask implements Runnable {
    private JobTrackerService service;
    public JobSuggestionTask(JobTrackerService service){
        this.service = service;
    }
    @Scheduled(fixedDelay = 60000)
    @Override
    public void run(){
        Map<String, Vacancy> resultForUsers = service.getBestVacancyForAllUsers();
        resultForUsers.forEach((k,v)-> System.out.println(k + ", лучшее предложение - " + v));
    }
}
