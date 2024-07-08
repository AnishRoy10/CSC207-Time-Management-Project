package use_case;

import entity.Task;
import entity.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TodoListRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for SortTasksUseCase.
 */
public class SortTasksUseCaseTest {
    private TodoListRepository todoListRepository;
    private SortTasksUseCase sortTasksUseCase;
    private AddTaskUseCase addTaskUseCase;
    private RemoveTaskUseCase removeTaskUseCase;

    @BeforeEach
    public void setUp() {
        todoListRepository = mock(TodoListRepository.class);
        sortTasksUseCase = new SortTasksUseCase(todoListRepository);
        addTaskUseCase = new AddTaskUseCase(todoListRepository);
        removeTaskUseCase = new RemoveTaskUseCase(todoListRepository);
    }

    private void addTaskToRepository(Task task) {
        addTaskUseCase.execute(task);
    }

    private void removeTaskFromRepository(Task task) {
        removeTaskUseCase.execute(task);
    }

    @Test
    public void testSortByDueDateAscending() {
        TodoList todoList = new TodoList();
        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskToRepository(task1);
        addTaskToRepository(task2);

        List<Task> sortedTasks = sortTasksUseCase.execute("dueDate", true);

        assertEquals(task1, sortedTasks.get(0));
        assertEquals(task2, sortedTasks.get(1));
    }

    @Test
    public void testSortByDueDateDescending() {
        TodoList todoList = new TodoList();
        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskToRepository(task1);
        addTaskToRepository(task2);

        List<Task> sortedTasks = sortTasksUseCase.execute("dueDate", false);

        assertEquals(task2, sortedTasks.get(0));
        assertEquals(task1, sortedTasks.get(1));
    }

    @Test
    public void testSortByCompletionStatusAscending() {
        TodoList todoList = new TodoList();
        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");
        task2.completeTask();

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskToRepository(task1);
        addTaskToRepository(task2);

        List<Task> sortedTasks = sortTasksUseCase.execute("completionStatus", true);

        assertEquals(task1, sortedTasks.get(0));
        assertEquals(task2, sortedTasks.get(1));
    }

    @Test
    public void testSortByCompletionStatusDescending() {
        TodoList todoList = new TodoList();
        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");
        task2.completeTask();

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskToRepository(task1);
        addTaskToRepository(task2);

        List<Task> sortedTasks = sortTasksUseCase.execute("completionStatus", false);

        assertEquals(task2, sortedTasks.get(0));
        assertEquals(task1, sortedTasks.get(1));
    }

    @Test
    public void testSortByCourseAscending() {
        TodoList todoList = new TodoList();
        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CourseA");
        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "CourseB");

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskToRepository(task1);
        addTaskToRepository(task2);

        List<Task> sortedTasks = sortTasksUseCase.execute("course", true);

        assertEquals(task1, sortedTasks.get(0));
        assertEquals(task2, sortedTasks.get(1));
    }

    @Test
    public void testSortByCourseDescending() {
        TodoList todoList = new TodoList();
        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "CourseA");
        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "CourseB");

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskToRepository(task1);
        addTaskToRepository(task2);

        List<Task> sortedTasks = sortTasksUseCase.execute("course", false);

        assertEquals(task2, sortedTasks.get(0));
        assertEquals(task1, sortedTasks.get(1));
    }
}
