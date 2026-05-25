package ru.vk.education.job;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.domain.Vacancy;
import ru.vk.education.job.repository.UserRepository;
import ru.vk.education.job.repository.VacancyRepository;
import ru.vk.education.job.service.JobTrackerService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class SuggestServiceIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("job_tracking_test")
            .withUsername("test_user")
            .withPassword("test_pass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private JobTrackerService suggestService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Test
    @DisplayName("Интеграционный тест: обычный сценарий с несколькими юзерами и несколькими вакансиями в БД")
    void suggest_normalScenarioWithMultipleUsersAndVacancies_returnsCorrectRecommendations() {
        userRepository.save(new User("alex", Set.of("java", "sql"), 3));
        userRepository.save(new User("olga", Set.of("go", "docker"), 1)); // Другой пользователь

        vacancyRepository.save(new Vacancy("Java Developer", "TechCorp", Set.of("java"), 2));
        vacancyRepository.save(new Vacancy("Database Expert", "DataCorp", Set.of("sql"), 4));
        vacancyRepository.save(new Vacancy("Go Developer", "GoCorp", Set.of("go"), 2));

        List<Vacancy> result = suggestService.getSuggestVacancies("alex");

        assertNotNull(result, "Результат не должен быть null");
        assertFalse(result.isEmpty(), "Для пользователя alex должны быть найдены вакансии");

        assertTrue(result.size() <= 2);

        result.forEach(v -> assertFalse(v.getTitle().equals("Go Developer"),
                "Вакансия Go Developer не должна предлагаться пользователю без соответствующего навыка"));
    }
}