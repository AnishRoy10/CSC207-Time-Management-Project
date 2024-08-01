//package use_case.TodoListUseCases.CompleteTaskUseCase;
//
//import entity.Task;
//import entity.TodoList;
//import entity.Course;
//import entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import repositories.LeaderboardRepository;
//import repositories.UserRepository;
//import use_case.TaskData;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//public class CompleteTaskUseCaseTest2 {
//    private UserRepository userRepository;
//    private CompleteTaskOutputBoundary completeTaskOutputBoundary;
//    private LeaderboardRepository leaderboardRepository;
//    private CompleteTaskUseCase completeTaskUseCase;
//    private User user;
//    private Task task;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        userRepository = mock(UserRepository.class);
//        completeTaskOutputBoundary = mock(CompleteTaskOutputBoundary.class);
//        leaderboardRepository = mock(LeaderboardRepository.class);
//        completeTaskUseCase = new CompleteTaskUseCase(userRepository, completeTaskOutputBoundary, leaderboardRepository);
//
//        user = new User("testUser", "password", new User[0], new Course[0]);
//        task = new Task(UUID.randomUUID(), "Test Task", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Test Course");
//        user.getTodoList().addTask(task);
//
//        when(userRepository.findByUsername("testUser")).thenReturn(user);
//    }
//
//    @Test
//    public void testToggleTaskCompletion() {
//        CompleteTaskRequestModel requestModel = new CompleteTaskRequestModel(task.getId(), "testUser");
//
//        assert(!task.isPointsAwarded());
//        // Toggle task to completed
//        completeTaskUseCase.execute(requestModel);
//        assertTrue(task.isCompleted());
//        assertTrue(task.isPointsAwarded());
//
//        // Toggle task back to incomplete
//        completeTaskUseCase.execute(requestModel);
//        assertTrue(!task.isCompleted());
//        assertTrue(task.isPointsAwarded()); // Ensure pointsAwarded remains true
//
//        // Toggle task to completed again
//        completeTaskUseCase.execute(requestModel);
//        assertTrue(task.isCompleted());
//        assertTrue(task.isPointsAwarded());
//    }
//}
