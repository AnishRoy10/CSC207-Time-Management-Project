//package use_case.TodoListUseCases;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import data_access.FileCacheLeaderboardDataAccessObject;
//import entity.Course;
//import entity.Task;
//import entity.User;
//import data_access.FileCacheUserDataAccessObject;
//import repositories.LeaderboardRepository;
//import interface_adapter.presenter.TodoListPresenter;
//import interface_adapter.viewmodel.TodoListViewModel;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import repositories.UserRepository;
//import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
//import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
//import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksRequestModel;
//import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksUseCase;
//import use_case.TodoListUseCases.SortTasksUseCase.SortTasksRequestModel;
//import use_case.TodoListUseCases.SortTasksUseCase.SortTasksUseCase;
//import use_case.TaskData;
//import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskRequestModel;
//import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskUseCase;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TodoListUseCasesTest {
//    private FileCacheUserDataAccessObject fileCacheUserDAO;
//    private FileCacheLeaderboardDataAccessObject fileCacheLeaderboardDAO;
//    private final String testFilePath = "test_userCache.json";
//    private final String testLeaderboardFilePath = "test_leaderboardCache.json";
//    private UserRepository userRepository;
//    private LeaderboardRepository leaderboardRepository;
//
//    @BeforeEach
//    void setUp() throws IOException {
//        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
//        fileCacheLeaderboardDAO = new FileCacheLeaderboardDataAccessObject(testLeaderboardFilePath);
//        userRepository = fileCacheUserDAO;
//        leaderboardRepository = fileCacheLeaderboardDAO;
//
//        // Initialize leaderboard data in the file
//        fileCacheLeaderboardDAO.writeToCache(fileCacheLeaderboardDAO.readFromCache());
//    }
//
//    @AfterEach
//    void tearDown() {
//        // Clean up by deleting the test file after each test
//        File testFile = new File(testFilePath);
//        if (testFile.exists()) {
//            testFile.delete();
//        }
//        File testLeaderboardFile = new File(testLeaderboardFilePath);
//        if (testLeaderboardFile.exists()) {
//            testLeaderboardFile.delete();
//        }
//    }
//
//    @Test
//    void testFilterTasksUseCase() {
//        User user = new User("filterUser", "password", new User[]{}, new Course[]{});
//        assertDoesNotThrow(() -> {
//            fileCacheUserDAO.WriteToCache(user);
//            TodoListViewModel viewModel = new TodoListViewModel();
//            TodoListPresenter presenter = new TodoListPresenter(viewModel);
//            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);
//            FilterTasksUseCase filterTasksUseCase = new FilterTasksUseCase(userRepository, presenter);
//
//            LocalDateTime startDate = LocalDateTime.now();
//            LocalDateTime deadline = LocalDateTime.now().plusDays(1);
//            AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline, "Course 1", "filterUser");
//            AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline, "Course 2", "filterUser");
//
//            addTaskUseCase.execute(requestModel1);
//            addTaskUseCase.execute(requestModel2);
//
//            TaskData taskData1 = viewModel.getTasks().get(0);
//            TaskData taskData2 = viewModel.getTasks().get(1);
//
//            FilterTasksRequestModel filterRequestModel = new FilterTasksRequestModel(true, "filterUser");
//            filterTasksUseCase.execute(filterRequestModel);
//
//            System.out.println("Filtered tasks count: " + viewModel.getTasks().size());
//            viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle()));
//        });
//    }
//
//    @Test
//    void testSortTasksUseCase() {
//        User user = new User("sortUser", "password", new User[]{}, new Course[]{});
//        assertDoesNotThrow(() -> {
//            fileCacheUserDAO.WriteToCache(user);
//            TodoListViewModel viewModel = new TodoListViewModel();
//            TodoListPresenter presenter = new TodoListPresenter(viewModel);
//            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);
//            SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, presenter);
//
//            LocalDateTime startDate = LocalDateTime.now();
//            LocalDateTime deadline1 = LocalDateTime.now().plusDays(1);
//            LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
//            AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline1, "Course 1", "sortUser");
//            AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline2, "Course 2", "sortUser");
//
//            addTaskUseCase.execute(requestModel1);
//            addTaskUseCase.execute(requestModel2);
//
//            SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("deadline", true, "sortUser");
//            sortTasksUseCase.execute(sortRequestModel);
//
//            System.out.println("Sorted tasks count: " + viewModel.getTasks().size());
//            viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle() + " Deadline: " + task.getDeadline()));
//        });
//    }
//
//    @Test
//    void testFilterAndSortTasksUseCase() {
//        User user = new User("complexUser", "password", new User[]{}, new Course[]{});
//        assertDoesNotThrow(() -> {
//            fileCacheUserDAO.WriteToCache(user);
//            TodoListViewModel viewModel = new TodoListViewModel();
//            TodoListPresenter presenter = new TodoListPresenter(viewModel);
//            AddTaskUseCase addTaskUseCase = new AddTaskUseCase(userRepository, presenter);
//            CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase(userRepository, presenter, leaderboardRepository);
//            FilterTasksUseCase filterTasksUseCase = new FilterTasksUseCase(userRepository, presenter);
//            SortTasksUseCase sortTasksUseCase = new SortTasksUseCase(userRepository, presenter);
//
//            LocalDateTime startDate = LocalDateTime.now();
//            LocalDateTime deadline1 = LocalDateTime.now().plusDays(1);
//            LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
//            AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Task 1", "Description 1", startDate, deadline1, "Course 1", "complexUser");
//            AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Task 2", "Description 2", startDate, deadline2, "Course 2", "complexUser");
//
//            addTaskUseCase.execute(requestModel1);
//            addTaskUseCase.execute(requestModel2);
//
//            // Print task IDs to verify
//            System.out.println("Tasks in ViewModel after adding:");
//            for (TaskData task : viewModel.getTasks()) {
//                System.out.println("Task ID: " + task.getId() + ", Title: " + task.getTitle());
//            }
//
//            // Ensure tasks are correctly added and IDs are available
//            assertEquals(2, viewModel.getTasks().size());
//
//            TaskData taskData1 = viewModel.getTasks().get(0);
//
//            CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(taskData1.getId(), "complexUser");
//            completeTaskUseCase.execute(completeRequestModel);
//
//            // Filter tasks
//            FilterTasksRequestModel filterRequestModel = new FilterTasksRequestModel(true, "complexUser");
//            filterTasksUseCase.execute(filterRequestModel);
//
//            // Sort tasks
//            SortTasksRequestModel sortRequestModel = new SortTasksRequestModel("deadline", true, "complexUser");
//            sortTasksUseCase.execute(sortRequestModel);
//
//            System.out.println("Filtered and Sorted tasks count: " + viewModel.getTasks().size());
//            viewModel.getTasks().forEach(task -> System.out.println("Task Title: " + task.getTitle() + " Deadline: " + task.getDeadline()));
//        });
//    }
//}
