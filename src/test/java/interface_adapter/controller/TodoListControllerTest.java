package interface_adapter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskInputBoundary;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskInputBoundary;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskRequestModel;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksInputBoundary;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksRequestModel;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListInputBoundary;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListRequestModel;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskInputBoundary;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskRequestModel;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksInputBoundary;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksRequestModel;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/**
 * Test suite for the TodoListController class.
 */
public class TodoListControllerTest {
    private TodoListController controller;
    private AddTaskInputBoundary addTaskUseCase;
    private RemoveTaskInputBoundary removeTaskUseCase;
    private CompleteTaskInputBoundary completeTaskUseCase;
    private SortTasksInputBoundary sortTasksUseCase;
    private FilterTasksInputBoundary filterTasksUseCase;
    private LoadTodoListInputBoundary loadTodoListUseCase;

    @BeforeEach
    public void setUp() {
        addTaskUseCase = mock(AddTaskInputBoundary.class);
        removeTaskUseCase = mock(RemoveTaskInputBoundary.class);
        completeTaskUseCase = mock(CompleteTaskInputBoundary.class);
        sortTasksUseCase = mock(SortTasksInputBoundary.class);
        filterTasksUseCase = mock(FilterTasksInputBoundary.class);
        loadTodoListUseCase = mock(LoadTodoListInputBoundary.class);

        controller = new TodoListController(addTaskUseCase, removeTaskUseCase, completeTaskUseCase,
                sortTasksUseCase, filterTasksUseCase, loadTodoListUseCase);
    }

    @Test
    public void testAddTask() {
        // Arrange
        String title = "Title";
        String description = "Description";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        String course = "Course";
        String username = "user1";
        AddTaskRequestModel expectedRequest = new AddTaskRequestModel(title, description, startDate, deadline, course, username);

        // Act
        controller.addTask(title, description, startDate, deadline, course, username);

        // Assert
        verify(addTaskUseCase).execute(argThat(request ->
                request.getTitle().equals(expectedRequest.getTitle()) &&
                        request.getDescription().equals(expectedRequest.getDescription()) &&
                        request.getStartDate().equals(expectedRequest.getStartDate()) &&
                        request.getDeadline().equals(expectedRequest.getDeadline()) &&
                        request.getCourse().equals(expectedRequest.getCourse()) &&
                        request.getUsername().equals(expectedRequest.getUsername())
        ));
    }

    @Test
    public void testRemoveTask() {
        // Arrange
        int taskId = 1;
        String username = "user1";
        RemoveTaskRequestModel expectedRequest = new RemoveTaskRequestModel(taskId, username);

        // Act
        controller.removeTask(taskId, username);

        // Assert
        verify(removeTaskUseCase).execute(argThat(request ->
                request.getTaskId() == expectedRequest.getTaskId() &&
                        request.getUsername().equals(expectedRequest.getUsername())
        ));
    }

    @Test
    public void testCompleteTask() {
        // Arrange
        int taskId = 1;
        String username = "user1";
        CompleteTaskRequestModel expectedRequest = new CompleteTaskRequestModel(taskId, username);

        // Act
        controller.toggleTaskCompletion(taskId, username);

        // Assert
        verify(completeTaskUseCase).execute(argThat(request ->
                request.getTaskId() == expectedRequest.getTaskId() &&
                        request.getUsername().equals(expectedRequest.getUsername())
        ));
    }

    @Test
    public void testSortTasks() {
        // Arrange
        String criterion = "deadline";
        boolean ascending = true;
        String username = "user1";
        SortTasksRequestModel expectedRequest = new SortTasksRequestModel(criterion, ascending, username);

        // Act
        controller.sortTasks(criterion, ascending, username);

        // Assert
        verify(sortTasksUseCase).execute(argThat(request ->
                request.getCriteria().equals(expectedRequest.getCriteria()) &&
                        request.isAscending() == expectedRequest.isAscending() &&
                        request.getUsername().equals(expectedRequest.getUsername())
        ));
    }

    @Test
    public void testFilterTasks() {
        // Arrange
        boolean showCompleted = true;
        String username = "user1";
        FilterTasksRequestModel expectedRequest = new FilterTasksRequestModel(showCompleted, username);

        // Act
        controller.filterTasks(showCompleted, username);

        // Assert
        verify(filterTasksUseCase).execute(argThat(request ->
                request.isHideCompleted() == expectedRequest.isHideCompleted() &&
                        request.getUsername().equals(expectedRequest.getUsername())
        ));
    }

    @Test
    public void testLoadTodoList() {
        // Arrange
        String username = "user1";
        LoadTodoListRequestModel expectedRequest = new LoadTodoListRequestModel(username);

        // Act
        controller.loadTodoList(username);

        // Assert
        verify(loadTodoListUseCase).execute(argThat(request ->
                request.getUsername().equals(expectedRequest.getUsername())
        ));
    }

    @Test
    public void testAddAndRemoveTasksComprehensive() {
        // Arrange
        String title1 = "Title1";
        String description1 = "Description1";
        LocalDateTime startDate1 = LocalDateTime.now();
        LocalDateTime deadline1 = LocalDateTime.now().plusDays(1);
        String course1 = "Course1";
        String username = "user1";

        String title2 = "Title2";
        String description2 = "Description2";
        LocalDateTime startDate2 = LocalDateTime.now();
        LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
        String course2 = "Course2";

        AddTaskRequestModel requestModel1 = new AddTaskRequestModel(title1, description1, startDate1, deadline1, course1, username);
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel(title2, description2, startDate2, deadline2, course2, username);

        int taskIdToRemove = 1;
        RemoveTaskRequestModel removeRequestModel = new RemoveTaskRequestModel(taskIdToRemove, username);

        // Act
        controller.addTask(title1, description1, startDate1, deadline1, course1, username);
        controller.addTask(title2, description2, startDate2, deadline2, course2, username);
        controller.removeTask(taskIdToRemove, username);

        // Assert
        verify(addTaskUseCase, times(1)).execute(argThat(request ->
                request.getTitle().equals(requestModel1.getTitle()) &&
                        request.getDescription().equals(requestModel1.getDescription()) &&
                        request.getStartDate().equals(requestModel1.getStartDate()) &&
                        request.getDeadline().equals(requestModel1.getDeadline()) &&
                        request.getCourse().equals(requestModel1.getCourse()) &&
                        request.getUsername().equals(requestModel1.getUsername())
        ));
        verify(addTaskUseCase, times(1)).execute(argThat(request ->
                request.getTitle().equals(requestModel2.getTitle()) &&
                        request.getDescription().equals(requestModel2.getDescription()) &&
                        request.getStartDate().equals(requestModel2.getStartDate()) &&
                        request.getDeadline().equals(requestModel2.getDeadline()) &&
                        request.getCourse().equals(requestModel2.getCourse()) &&
                        request.getUsername().equals(requestModel2.getUsername())
        ));
        verify(removeTaskUseCase, times(1)).execute(argThat(request ->
                request.getTaskId() == removeRequestModel.getTaskId() &&
                        request.getUsername().equals(removeRequestModel.getUsername())
        ));
    }

    @Test
    public void testAddTaskMultipleUsers() {
        // Arrange
        String titleUser1 = "User1 Task";
        String descriptionUser1 = "Description1";
        LocalDateTime startDateUser1 = LocalDateTime.now();
        LocalDateTime deadlineUser1 = LocalDateTime.now().plusDays(1);
        String courseUser1 = "Course1";
        String username1 = "user1";
        AddTaskRequestModel requestModelUser1 = new AddTaskRequestModel(titleUser1, descriptionUser1, startDateUser1, deadlineUser1, courseUser1, username1);

        String titleUser2 = "User2 Task";
        String descriptionUser2 = "Description2";
        LocalDateTime startDateUser2 = LocalDateTime.now();
        LocalDateTime deadlineUser2 = LocalDateTime.now().plusDays(2);
        String courseUser2 = "Course2";
        String username2 = "user2";
        AddTaskRequestModel requestModelUser2 = new AddTaskRequestModel(titleUser2, descriptionUser2, startDateUser2, deadlineUser2, courseUser2, username2);

        // Act
        controller.addTask(titleUser1, descriptionUser1, startDateUser1, deadlineUser1, courseUser1, username1);
        controller.addTask(titleUser2, descriptionUser2, startDateUser2, deadlineUser2, courseUser2, username2);

        // Assert
        verify(addTaskUseCase, times(1)).execute(argThat(request ->
                request.getTitle().equals(requestModelUser1.getTitle()) &&
                        request.getDescription().equals(requestModelUser1.getDescription()) &&
                        request.getStartDate().equals(requestModelUser1.getStartDate()) &&
                        request.getDeadline().equals(requestModelUser1.getDeadline()) &&
                        request.getCourse().equals(requestModelUser1.getCourse()) &&
                        request.getUsername().equals(requestModelUser1.getUsername())
        ));
        verify(addTaskUseCase, times(1)).execute(argThat(request ->
                request.getTitle().equals(requestModelUser2.getTitle()) &&
                        request.getDescription().equals(requestModelUser2.getDescription()) &&
                        request.getStartDate().equals(requestModelUser2.getStartDate()) &&
                        request.getDeadline().equals(requestModelUser2.getDeadline()) &&
                        request.getCourse().equals(requestModelUser2.getCourse()) &&
                        request.getUsername().equals(requestModelUser2.getUsername())
        ));
    }

    @Test
    public void testCompleteTaskMultipleTasks() {
        // Arrange
        String title = "Task";
        String description = "Description";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        String course = "Course";
        String username = "user1";

        AddTaskRequestModel addRequestModel = new AddTaskRequestModel(title, description, startDate, deadline, course, username);
        controller.addTask(title, description, startDate, deadline, course, username);

        // Act
        controller.toggleTaskCompletion(1, username);
        controller.toggleTaskCompletion(2, username);

        // Assert
        verify(completeTaskUseCase, times(2)).execute(argThat(request ->
                request.getTaskId() == 1 && request.getUsername().equals(username) ||
                        request.getTaskId() == 2 && request.getUsername().equals(username)
        ));
    }

    @Test
    public void testSortTasksMultipleCriteria() {
        // Arrange
        String username = "user1";
        SortTasksRequestModel requestModel1 = new SortTasksRequestModel("deadline", true, username);
        SortTasksRequestModel requestModel2 = new SortTasksRequestModel("title", false, username);

        // Act
        controller.sortTasks("deadline", true, username);
        controller.sortTasks("title", false, username);

        // Assert
        verify(sortTasksUseCase, times(1)).execute(argThat(request ->
                request.getCriteria().equals(requestModel1.getCriteria()) &&
                        request.isAscending() == requestModel1.isAscending() &&
                        request.getUsername().equals(requestModel1.getUsername())
        ));
        verify(sortTasksUseCase, times(1)).execute(argThat(request ->
                request.getCriteria().equals(requestModel2.getCriteria()) &&
                        request.isAscending() == requestModel2.isAscending() &&
                        request.getUsername().equals(requestModel2.getUsername())
        ));
    }

    @Test
    public void testFilterTasksDifferentUsers() {
        // Arrange
        String username1 = "user1";
        String username2 = "user2";
        FilterTasksRequestModel requestModel1 = new FilterTasksRequestModel(true, username1);
        FilterTasksRequestModel requestModel2 = new FilterTasksRequestModel(false, username2);

        // Act
        controller.filterTasks(true, username1);
        controller.filterTasks(false, username2);

        // Assert
        verify(filterTasksUseCase, times(1)).execute(argThat(request ->
                request.isHideCompleted() == requestModel1.isHideCompleted() &&
                        request.getUsername().equals(requestModel1.getUsername())
        ));
        verify(filterTasksUseCase, times(1)).execute(argThat(request ->
                request.isHideCompleted() == requestModel2.isHideCompleted() &&
                        request.getUsername().equals(requestModel2.getUsername())
        ));
    }
}