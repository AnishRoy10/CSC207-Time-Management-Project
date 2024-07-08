package use_case;

import entity.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import repositories.TodoListRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SortTasksUseCaseTest {
    private TodoListRepository todoListRepository;
    private SortTasksOutputBoundary sortTasksOutputBoundary;
    private SortTasksUseCase sortTasksUseCase;
    private AddTaskUseCase addTaskUseCase;

    @BeforeEach
    public void setUp() {
        todoListRepository = mock(TodoListRepository.class);
        sortTasksOutputBoundary = mock(SortTasksOutputBoundary.class);
        sortTasksUseCase = new SortTasksUseCase(todoListRepository, sortTasksOutputBoundary);
        addTaskUseCase = new AddTaskUseCase(todoListRepository, mock(AddTaskOutputBoundary.class));
    }

    @Test
    public void testSortTasksByDeadlineAscending() {
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(3), "Course1");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course2");
        AddTaskRequestModel requestModel3 = new AddTaskRequestModel("Title3", "Description3", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course3");

        TodoList todoList = new TodoList();
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);
        addTaskUseCase.execute(requestModel3);

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        SortTasksRequestModel requestModel = new SortTasksRequestModel("deadline", true);
        sortTasksUseCase.execute(requestModel);

        verify(todoListRepository, times(4)).loadTodoList();

        ArgumentCaptor<SortTasksResponseModel> argumentCaptor = ArgumentCaptor.forClass(SortTasksResponseModel.class);
        verify(sortTasksOutputBoundary, times(1)).present(argumentCaptor.capture());

        SortTasksResponseModel capturedResponseModel = argumentCaptor.getValue();
        List<TaskData> tasks = capturedResponseModel.getTasks();

        assertEquals(3, tasks.size());
        assertEquals(requestModel2.getTitle(), tasks.get(0).getTitle());
        assertEquals(requestModel3.getTitle(), tasks.get(1).getTitle());
        assertEquals(requestModel1.getTitle(), tasks.get(2).getTitle());
    }

    @Test
    public void testSortTasksByDeadlineDescending() {
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(3), "Course1");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course2");
        AddTaskRequestModel requestModel3 = new AddTaskRequestModel("Title3", "Description3", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course3");

        TodoList todoList = new TodoList();
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);
        addTaskUseCase.execute(requestModel3);

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        SortTasksRequestModel requestModel = new SortTasksRequestModel("deadline", false);
        sortTasksUseCase.execute(requestModel);

        verify(todoListRepository, times(4)).loadTodoList();

        ArgumentCaptor<SortTasksResponseModel> argumentCaptor = ArgumentCaptor.forClass(SortTasksResponseModel.class);
        verify(sortTasksOutputBoundary, times(1)).present(argumentCaptor.capture());

        SortTasksResponseModel capturedResponseModel = argumentCaptor.getValue();
        List<TaskData> tasks = capturedResponseModel.getTasks();

        assertEquals(3, tasks.size());
        assertEquals(requestModel1.getTitle(), tasks.get(0).getTitle());
        assertEquals(requestModel3.getTitle(), tasks.get(1).getTitle());
        assertEquals(requestModel2.getTitle(), tasks.get(2).getTitle());
    }

    @Test
    public void testSortTasksByCourseAscending() {
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(3), "CourseC");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CourseA");
        AddTaskRequestModel requestModel3 = new AddTaskRequestModel("Title3", "Description3", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "CourseB");

        TodoList todoList = new TodoList();
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);
        addTaskUseCase.execute(requestModel3);

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        SortTasksRequestModel requestModel = new SortTasksRequestModel("course", true);
        sortTasksUseCase.execute(requestModel);

        verify(todoListRepository, times(4)).loadTodoList();

        ArgumentCaptor<SortTasksResponseModel> argumentCaptor = ArgumentCaptor.forClass(SortTasksResponseModel.class);
        verify(sortTasksOutputBoundary, times(1)).present(argumentCaptor.capture());

        SortTasksResponseModel capturedResponseModel = argumentCaptor.getValue();
        List<TaskData> tasks = capturedResponseModel.getTasks();

        assertEquals(3, tasks.size());
        assertEquals(requestModel2.getTitle(), tasks.get(0).getTitle());
        assertEquals(requestModel3.getTitle(), tasks.get(1).getTitle());
        assertEquals(requestModel1.getTitle(), tasks.get(2).getTitle());
    }

    @Test
    public void testSortTasksByCourseDescending() {
        AddTaskRequestModel requestModel1 = new AddTaskRequestModel("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(3), "CourseC");
        AddTaskRequestModel requestModel2 = new AddTaskRequestModel("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CourseA");
        AddTaskRequestModel requestModel3 = new AddTaskRequestModel("Title3", "Description3", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "CourseB");

        TodoList todoList = new TodoList();
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskUseCase.execute(requestModel1);
        addTaskUseCase.execute(requestModel2);
        addTaskUseCase.execute(requestModel3);

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        SortTasksRequestModel requestModel = new SortTasksRequestModel("course", false);
        sortTasksUseCase.execute(requestModel);

        verify(todoListRepository, times(4)).loadTodoList();

        ArgumentCaptor<SortTasksResponseModel> argumentCaptor = ArgumentCaptor.forClass(SortTasksResponseModel.class);
        verify(sortTasksOutputBoundary, times(1)).present(argumentCaptor.capture());

        SortTasksResponseModel capturedResponseModel = argumentCaptor.getValue();
        List<TaskData> tasks = capturedResponseModel.getTasks();

        assertEquals(3, tasks.size());
        assertEquals(requestModel1.getTitle(), tasks.get(0).getTitle());
        assertEquals(requestModel3.getTitle(), tasks.get(1).getTitle());
        assertEquals(requestModel2.getTitle(), tasks.get(2).getTitle());
    }
}
