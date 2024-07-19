package use_case;

import entity.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import repositories.TodoListRepository;
import use_case.AddTaskUseCase.AddTaskOutputBoundary;
import use_case.AddTaskUseCase.AddTaskRequestModel;
import use_case.AddTaskUseCase.AddTaskUseCase;
import use_case.CompleteTaskUseCase.CompleteTaskOutputBoundary;
import use_case.CompleteTaskUseCase.CompleteTaskRequestModel;
import use_case.CompleteTaskUseCase.CompleteTaskUseCase;
import use_case.FilterTasksUseCase.FilterTasksOutputBoundary;
import use_case.FilterTasksUseCase.FilterTasksRequestModel;
import use_case.FilterTasksUseCase.FilterTasksResponseModel;
import use_case.FilterTasksUseCase.FilterTasksUseCase;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FilterTasksUseCaseTest {
    private TodoListRepository todoListRepository;
    private FilterTasksOutputBoundary filterTasksOutputBoundary;
    private FilterTasksUseCase filterTasksUseCase;
    private AddTaskUseCase addTaskUseCase;
    private CompleteTaskUseCase completeTaskUseCase;

    @BeforeEach
    public void setUp() {
        todoListRepository = mock(TodoListRepository.class);
        filterTasksOutputBoundary = mock(FilterTasksOutputBoundary.class);
        filterTasksUseCase = new FilterTasksUseCase(todoListRepository, filterTasksOutputBoundary);
        addTaskUseCase = new AddTaskUseCase(todoListRepository, mock(AddTaskOutputBoundary.class));
        completeTaskUseCase = new CompleteTaskUseCase(todoListRepository, mock(CompleteTaskOutputBoundary.class));
    }

    @Test
    public void testFilterCompletedTasks() {
        // Create tasks using AddTaskUseCase
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");

        TodoList todoList = new TodoList();
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        // Mark the second task as completed using CompleteTaskUseCase
        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(todoList.getTasks().get(1).getId());
        completeTaskUseCase.execute(completeRequestModel);

        // Mock the repository to return the updated to-do list
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        // Create a request model to hide completed tasks
        FilterTasksRequestModel requestModel = new FilterTasksRequestModel(true);

        // Execute the use case
        filterTasksUseCase.execute(requestModel);

        // Verify interactions with the repository
        verify(todoListRepository, times(4)).loadTodoList();

        // Capture the response model passed to the output boundary
        ArgumentCaptor<FilterTasksResponseModel> argumentCaptor = ArgumentCaptor.forClass(FilterTasksResponseModel.class);
        verify(filterTasksOutputBoundary, times(1)).present(argumentCaptor.capture());

        // Verify the filtered tasks in the response model
        FilterTasksResponseModel capturedResponseModel = argumentCaptor.getValue();
        List<TaskData> tasks = capturedResponseModel.getTasks();
        assertEquals(1, tasks.size());

        TaskData taskData = tasks.get(0);
        assertEquals(requestModel1.getTitle(), taskData.getTitle());
        assertEquals(requestModel1.getDescription(), taskData.getDescription());
        assertEquals(requestModel1.getStartDate(), taskData.getStartDate());
        assertEquals(requestModel1.getDeadline(), taskData.getDeadline());
        assertEquals(requestModel1.getCourse(), taskData.getCourse());
        assertEquals(false, taskData.isCompleted());
    }

    @Test
    public void testShowAllTasks() {
        // Create tasks using AddTaskUseCase
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");

        TodoList todoList = new TodoList();
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);

        // Mark the second task as completed using CompleteTaskUseCase
        CompleteTaskRequestModel completeRequestModel = new CompleteTaskRequestModel(todoList.getTasks().get(1).getId());
        completeTaskUseCase.execute(completeRequestModel);

        // Mock the repository to return the updated to-do list
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        // Create a request model to show all tasks
        FilterTasksRequestModel requestModel = new FilterTasksRequestModel(false);

        // Execute the use case
        filterTasksUseCase.execute(requestModel);

        // Verify interactions with the repository
        verify(todoListRepository, times(4)).loadTodoList();

        // Capture the response model passed to the output boundary
        ArgumentCaptor<FilterTasksResponseModel> argumentCaptor = ArgumentCaptor.forClass(FilterTasksResponseModel.class);
        verify(filterTasksOutputBoundary, times(1)).present(argumentCaptor.capture());

        // Verify all tasks are present in the response model
        FilterTasksResponseModel capturedResponseModel = argumentCaptor.getValue();
        List<TaskData> tasks = capturedResponseModel.getTasks();
        assertEquals(2, tasks.size());

        TaskData taskData1 = tasks.get(0);
        TaskData taskData2 = tasks.get(1);

        // Verify the first task's details
        assertEquals(requestModel1.getTitle(), taskData1.getTitle());
        assertEquals(requestModel1.getDescription(), taskData1.getDescription());
        assertEquals(requestModel1.getStartDate(), taskData1.getStartDate());
        assertEquals(requestModel1.getDeadline(), taskData1.getDeadline());
        assertEquals(requestModel1.getCourse(), taskData1.getCourse());
        assertEquals(false, taskData1.isCompleted());

        // Verify the second task's details
        assertEquals(requestModel2.getTitle(), taskData2.getTitle());
        assertEquals(requestModel2.getDescription(), taskData2.getDescription());
        assertEquals(requestModel2.getStartDate(), taskData2.getStartDate());
        assertEquals(requestModel2.getDeadline(), taskData2.getDeadline());
        assertEquals(requestModel2.getCourse(), taskData2.getCourse());
        assertEquals(true, taskData2.isCompleted());
    }
}
