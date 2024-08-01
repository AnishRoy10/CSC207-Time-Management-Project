package use_case.TodoListUseCases.CompleteTaskUseCase;

import entity.DailyLeaderboard;
import entity.MonthlyLeaderboard;
import entity.AllTimeLeaderboard;
import entity.Leaderboard;
import entity.Task;
import entity.User;
import entity.Course;
import interface_adapter.presenter.TodoListPresenter;
import interface_adapter.viewmodel.TodoListViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.LeaderboardRepository;
import repositories.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CompleteTaskIntegrationTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LeaderboardRepository leaderboardRepository;
    private DailyLeaderboard dailyLeaderboard;
    private MonthlyLeaderboard monthlyLeaderboard;
    private AllTimeLeaderboard allTimeLeaderboard;
    private CompleteTaskUseCase completeTaskUseCase;
    private TodoListPresenter presenter;
    private TodoListViewModel viewModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewModel = new TodoListViewModel();
        presenter = new TodoListPresenter(viewModel);
        completeTaskUseCase = new CompleteTaskUseCase(userRepository, presenter, leaderboardRepository);

        dailyLeaderboard = new DailyLeaderboard("Daily Leaderboard", LocalDate.now());
        monthlyLeaderboard = new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now().withDayOfMonth(1));
        allTimeLeaderboard = new AllTimeLeaderboard("All-Time Leaderboard");
    }

    @Test
    void testCompleteTask() throws IOException {
        // Given
        UUID taskId = UUID.randomUUID();
        Task task = new Task("Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        task.setId(taskId);

        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        user.addTask(task);

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(leaderboardRepository.readFromCache()).thenReturn(Map.of(
                "daily", dailyLeaderboard,
                "monthly", monthlyLeaderboard,
                "allTime", allTimeLeaderboard
        ));

        // When
        CompleteTaskRequestModel requestModel = new CompleteTaskRequestModel(taskId, "testUser");
        completeTaskUseCase.execute(requestModel);

        // Then
        verify(userRepository).WriteToCache(user);
        verify(leaderboardRepository).writeToCache(any());
        assertTrue(user.getTodoList().getTasks().stream().anyMatch(t -> t.getId().equals(taskId) && t.isCompleted()));

        Map<String, Leaderboard> updatedLeaderboards = leaderboardRepository.readFromCache();
        Leaderboard daily = updatedLeaderboards.get("daily");
        Leaderboard monthly = updatedLeaderboards.get("monthly");
        Leaderboard allTime = updatedLeaderboards.get("allTime");

        System.out.println("Leaderboard daily scores: " + daily.getScores());
        System.out.println("Leaderboard monthly scores: " + monthly.getScores());
        System.out.println("Leaderboard all-time scores: " + allTime.getScores());

        // Assert leaderboard scores
        assertEquals(500, daily.getScores().get("testUser"));
        assertEquals(500, monthly.getScores().get("testUser"));
        assertEquals(500, allTime.getScores().get("testUser"));
    }
}
