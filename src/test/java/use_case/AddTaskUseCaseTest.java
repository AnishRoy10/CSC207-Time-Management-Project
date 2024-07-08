package use_case;

import entity.Task;
import entity.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TodoListRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddTaskUseCaseTest {
    private TodoListRepository todoListRepository;
    private AddTaskUseCase addTaskUseCase;

    @BeforeEach
    public void setUp() {
        todoListRepository = mock(TodoListRepository.class);
        addTaskUseCase = new AddTaskUseCase(todoListRepository);
    }

    @Test
    public void testExecute() {
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        TodoList todoList = new TodoList();

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        addTaskUseCase.execute(task);

        verify(todoListRepository, times(1)).loadTodoList();
        verify(todoListRepository, times(1)).saveTodoList(todoList);

        assertEquals(1, todoList.getTasks().size());
        assertEquals(task, todoList.getTasks().get(0));
    }
}
