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
        AddTaskRequestModel expectedRequest = new AddTaskRequestModel(title, description, startDate, deadline, course);

        // Act
        controller.addTask(title, description, startDate, deadline, course);

        // Assert
        verify(addTaskUseCase).execute(argThat(request ->
                request.getTitle().equals(expectedRequest.getTitle()) &&
                        request.getDescription().equals(expectedRequest.getDescription()) &&
                        request.getStartDate().equals(expectedRequest.getStartDate()) &&
                        request.getDeadline().equals(expectedRequest.getDeadline()) &&
                        request.getCourse().equals(expectedRequest.getCourse())
        ));
    }

    @Test
    public void testRemoveTask() {
        // Arrange
        int taskId = 1;
        RemoveTaskRequestModel expectedRequest = new RemoveTaskRequestModel(taskId);

        // Act
        controller.removeTask(taskId);

        // Assert
        verify(removeTaskUseCase).execute(argThat(request ->
                request.getTaskId() == expectedRequest.getTaskId()
        ));
    }

    @Test
    public void testCompleteTask() {
        // Arrange
        int taskId = 1;
        CompleteTaskRequestModel expectedRequest = new CompleteTaskRequestModel(taskId);

        // Act
        controller.toggleTaskCompletion(taskId);

        // Assert
        verify(completeTaskUseCase).execute(argThat(request ->
                request.getTaskId() == expectedRequest.getTaskId()
        ));
    }

    @Test
    public void testSortTasks() {
        // Arrange
        String criterion = "deadline";
        boolean ascending = true;
        SortTasksRequestModel expectedRequest = new SortTasksRequestModel(criterion, ascending);

        // Act
        controller.sortTasks(criterion, ascending);

        // Assert
        verify(sortTasksUseCase).execute(argThat(request ->
                request.getCriteria().equals(expectedRequest.getCriteria()) &&
                        request.isAscending() == expectedRequest.isAscending()
        ));
    }

    @Test
    public void testFilterTasks() {
        // Arrange
        boolean showCompleted = true;
        FilterTasksRequestModel expectedRequest = new FilterTasksRequestModel(showCompleted);

        // Act
        controller.filterTasks(showCompleted);

        // Assert
        verify(filterTasksUseCase).execute(argThat(request ->
                request.isHideCompleted() == expectedRequest.isHideCompleted()
        ));
    }

    @Test
    public void testLoadTodoList() {
        // Arrange
        LoadTodoListRequestModel expectedRequest = new LoadTodoListRequestModel();

        // Act
        controller.loadTodoList();

        // Assert
        verify(loadTodoListUseCase).execute(argThat(request ->
                true // since LoadTodoListRequestModel has no fields, we just return true here
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

        String title2 = "Title2";
        String description2 = "Description2";
        LocalDateTime startDate2 = LocalDateTime.now();
        LocalDateTime deadline2 = LocalDateTime.now().plusDays(2);
        String course2 = "Course2";

        AddTaskRequestModel requestModel1 = new AddTaskRequestModel(title1, description1, startDate1, deadline1, course1);
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel(title2, description2, startDate2, deadline2, course2);

        int taskIdToRemove = 1;
        RemoveTaskRequestModel removeRequestModel = new RemoveTaskRequestModel(taskIdToRemove);

        // Act
        controller.addTask(title1, description1, startDate1, deadline1, course1);
        controller.addTask(title2, description2, startDate2, deadline2, course2);
        controller.removeTask(taskIdToRemove);

        // Assert
        verify(addTaskUseCase, times(1)).execute(argThat(request ->
                request.getTitle().equals(requestModel1.getTitle()) &&
                        request.getDescription().equals(requestModel1.getDescription()) &&
                        request.getStartDate().equals(requestModel1.getStartDate()) &&
                        request.getDeadline().equals(requestModel1.getDeadline()) &&
                        request.getCourse().equals(requestModel1.getCourse())
        ));
        verify(addTaskUseCase, times(1)).execute(argThat(request ->
                request.getTitle().equals(requestModel2.getTitle()) &&
                        request.getDescription().equals(requestModel2.getDescription()) &&
                        request.getStartDate().equals(requestModel2.getStartDate()) &&
                        request.getDeadline().equals(requestModel2.getDeadline()) &&
                        request.getCourse().equals(requestModel2.getCourse())
        ));
        verify(removeTaskUseCase, times(1)).execute(argThat(request ->
                request.getTaskId() == removeRequestModel.getTaskId()
        ));
    }
}
