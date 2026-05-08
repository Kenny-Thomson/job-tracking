package ru.vk.education.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
public class JobTrackerApp {
    public static void main(String[] args){
        SpringApplication.run(JobTrackerApp.class,args);
    }

}
