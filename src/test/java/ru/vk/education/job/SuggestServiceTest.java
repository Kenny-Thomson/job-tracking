package ru.vk.education.job;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vk.education.job.domain.User;
import ru.vk.education.job.domain.Vacancy;
import ru.vk.education.job.repository.UserRepository;
import ru.vk.education.job.repository.VacancyRepository;
import ru.vk.education.job.service.JobTrackerService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuggestServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VacancyRepository vacancyRepository;

    @InjectMocks
    private JobTrackerService suggestService;

    @Test
    @DisplayName("suggestTest — обычный сценарий: один пользователь и несколько вакансий")
    void suggestTest() {
        User user = new User("ivan", Set.of("java", "spring"), 3);
        Vacancy v1 = new Vacancy("Java Developer", "TechCorp", Set.of("java"), 2);
        Vacancy v2 = new Vacancy("Go Developer", "GoCorp", Set.of("go"), 1);

        when(userRepository.findByName("ivan")).thenReturn(user);
        when(vacancyRepository.findAll()).thenReturn(Set.of(v1, v2));

        List<Vacancy> result = suggestService.getSuggestVacancies("ivan");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Java Developer", result.get(0).getTitle());
        verify(userRepository).findByName("ivan");
        verify(vacancyRepository).findAll();
    }

    @Test
    @DisplayName("emptyVacanciesTest — сценарий, когда в системе нет вакансий")
    void emptyVacanciesTest() {
        User user = new User("maria", Set.of("java"), 2);

        when(userRepository.findByName("maria")).thenReturn(user);
        when(vacancyRepository.findAll()).thenReturn(Collections.emptySet());

        List<Vacancy> result = suggestService.getSuggestVacancies("maria");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findByName("maria");
        verify(vacancyRepository).findAll();
    }

    @Test
    @DisplayName("singleVacancyTest — сценарий, когда в системе только одна вакансия")
    void singleVacancyTest() {
        User user = new User("petr", Set.of("java"), 2);
        Vacancy vacancy = new Vacancy("Java Junior", "JavaCorp", Set.of("java"), 1);

        when(userRepository.findByName("petr")).thenReturn(user);
        when(vacancyRepository.findAll()).thenReturn(Set.of(vacancy));

        List<Vacancy> result = suggestService.getSuggestVacancies("petr");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Java Junior", result.get(0).getTitle());
    }

    @Test
    @DisplayName("userNotFoundTest — в системе нет юзера, для которого ищется предложение")
    void userNotFoundTest() {
        // arrange
        when(userRepository.findByName("unknown_user")).thenReturn(null);

        List<Vacancy> result = suggestService.getSuggestVacancies("unknown_user");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findByName("unknown_user");
        verifyNoInteractions(vacancyRepository);
    }
}
