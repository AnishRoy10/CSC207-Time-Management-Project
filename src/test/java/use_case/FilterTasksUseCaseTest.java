//package use_case;
//
//import entity.Task;
//import entity.TodoList;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import repositories.TodoListRepository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
///**
// * Unit tests for FilterTasksUseCase.
// */
//public class FilterTasksUseCaseTest {
//    private TodoListRepository todoListRepository;
//    private FilterTasksUseCase filterTasksUseCase;
//    private AddTaskUseCase addTaskUseCase;
//    private RemoveTaskUseCase removeTaskUseCase;
//
//    @BeforeEach
//    public void setUp() {
//        todoListRepository = mock(TodoListRepository.class);
//        filterTasksUseCase = new FilterTasksUseCase(todoListRepository);
//        addTaskUseCase = new AddTaskUseCase(todoListRepository);
//        removeTaskUseCase = new RemoveTaskUseCase(todoListRepository);
//    }
//
//    private void addTaskToRepository(Task task) {
//        addTaskUseCase.execute(task);
//    }
//
//    private void removeTaskFromRepository(Task task) {
//        removeTaskUseCase.execute(task);
//    }
//
//    @Test
//    public void testFilterCompletedTasks() {
//        TodoList todoList = new TodoList();
//        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
//        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");
//        task2.completeTask();
//
//        when(todoListRepository.loadTodoList()).thenReturn(todoList);
//
//        addTaskToRepository(task1);
//        addTaskToRepository(task2);
//
//        List<Task> filteredTasks = filterTasksUseCase.execute(true);
//
//        assertEquals(1, filteredTasks.size());
//        assertEquals(task1, filteredTasks.get(0));
//    }
//
//    @Test
//    public void testShowAllTasks() {
//        TodoList todoList = new TodoList();
//        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
//        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");
//        task2.completeTask();
//
//        when(todoListRepository.loadTodoList()).thenReturn(todoList);
//
//        addTaskToRepository(task1);
//        addTaskToRepository(task2);
//
//        List<Task> filteredTasks = filterTasksUseCase.execute(false);
//
//        assertEquals(2, filteredTasks.size());
//        assertEquals(task1, filteredTasks.get(0));
//        assertEquals(task2, filteredTasks.get(1));
//    }
//
//    @Test
//    public void testFilterAllTasksCompleted() {
//        TodoList todoList = new TodoList();
//        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
//        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");
//        task1.completeTask();
//        task2.completeTask();
//
//        when(todoListRepository.loadTodoList()).thenReturn(todoList);
//
//        addTaskToRepository(task1);
//        addTaskToRepository(task2);
//
//        List<Task> filteredTasks = filterTasksUseCase.execute(true);
//
//        assertEquals(0, filteredTasks.size());
//    }
//
//    @Test
//    public void testFilterNoTasksCompleted() {
//        TodoList todoList = new TodoList();
//        Task task1 = new Task("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
//        Task task2 = new Task("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");
//
//        when(todoListRepository.loadTodoList()).thenReturn(todoList);
//
//        addTaskToRepository(task1);
//        addTaskToRepository(task2);
//
//        List<Task> filteredTasks = filterTasksUseCase.execute(true);
//
//        assertEquals(2, filteredTasks.size());
//        assertEquals(task1, filteredTasks.get(0));
//        assertEquals(task2, filteredTasks.get(1));
//    }
//}
