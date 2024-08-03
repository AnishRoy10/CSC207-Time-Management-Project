package use_case.TodoListUseCases.CompleteTaskUseCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data_access.LocalDateSerializer;
import data_access.LocalDateTimeSerializer;
import entity.*;
import org.junit.jupiter.api.*;
import repositories.LeaderboardRepository;
import repositories.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompleteTaskFilePersistenceTest {
    private UserRepository userRepository;
    private LeaderboardRepository leaderboardRepository;
    private CompleteTaskOutputBoundary completeTaskOutputBoundary;
    private CompleteTaskUseCase completeTaskUseCase;
    private User user;
    private Task task;
    private Gson gson;
    private File userCacheFile;
    private File leaderboardCacheFile;

    @BeforeEach
    public void setUp() throws IOException {
        userRepository = mock(UserRepository.class);
        leaderboardRepository = mock(LeaderboardRepository.class);
        completeTaskOutputBoundary = mock(CompleteTaskOutputBoundary.class);
        completeTaskUseCase = new CompleteTaskUseCase(userRepository, completeTaskOutputBoundary, leaderboardRepository);

        user = new User("testUser", "password", new User[0], new Course[0]);
        task = new Task(UUID.randomUUID(), "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Test Course");
        user.getTodoList().addTask(task);

        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .setPrettyPrinting()
                .create();

        // Create temporary files for user and leaderboard cache
        userCacheFile = Files.createTempFile("userCache", ".json").toFile();
        leaderboardCacheFile = Files.createTempFile("leaderboardCache", ".json").toFile();

        // Mock user repository behavior
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        doAnswer(invocation -> {
            try (Writer writer = new FileWriter(userCacheFile)) {
                gson.toJson(user, writer);
            }
            return null;
        }).when(userRepository).WriteToCache(any(User.class));

        // Initialize leaderboard data
        Map<String, Leaderboard> leaderboards = new HashMap<>();
        leaderboards.put("daily", new DailyLeaderboard("Daily Leaderboard", LocalDate.now()));
        leaderboards.put("monthly", new MonthlyLeaderboard("Monthly Leaderboard", LocalDate.now().withDayOfMonth(1)));
        leaderboards.put("allTime", new AllTimeLeaderboard("All-Time Leaderboard"));

        // Mock leaderboard repository behavior
        when(leaderboardRepository.readFromCache()).thenReturn(leaderboards);
        doAnswer(invocation -> {
            try (Writer writer = new FileWriter(leaderboardCacheFile)) {
                gson.toJson(leaderboards, writer);
            }
            return null;
        }).when(leaderboardRepository).writeToCache(anyMap());
    }

    @AfterEach
    public void tearDown() {
        if (userCacheFile != null && userCacheFile.exists()) {
            userCacheFile.delete();
        }
        if (leaderboardCacheFile != null && leaderboardCacheFile.exists()) {
            leaderboardCacheFile.delete();
        }
    }

    @Test
    public void testTaskPointsAwardedPersistence() throws IOException {
        CompleteTaskRequestModel requestModel = new CompleteTaskRequestModel(task.getId(), "testUser");

        assertEquals(task.isPointsAwarded(), false);

        // Complete the task
        completeTaskUseCase.execute(requestModel);
        assertTrue(task.isCompleted());
        assertTrue(task.isPointsAwarded());

        // Save to file
        userRepository.WriteToCache(user);

        // Read from file
        User savedUser;
        try (Reader reader = new FileReader(userCacheFile)) {
            savedUser = gson.fromJson(reader, User.class);
        }

        Optional<Task> savedTaskOptional = savedUser.getTodoList().getTasks().stream()
                .filter(t -> t.getId().equals(task.getId()))
                .findFirst();

        assertTrue(savedTaskOptional.isPresent());
        Task savedTask = savedTaskOptional.get();
        assertTrue(savedTask.isCompleted());
        assertTrue(savedTask.isPointsAwarded());
    }
}
