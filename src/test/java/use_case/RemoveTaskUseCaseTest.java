package use_case;

import entity.Task;
import entity.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TodoListRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RemoveTaskUseCaseTest {
    private TodoListRepository todoListRepository;
    private RemoveTaskUseCase removeTaskUseCase;

    @BeforeEach
    public void setUp() {
        todoListRepository = mock(TodoListRepository.class);
        removeTaskUseCase = new RemoveTaskUseCase(todoListRepository);
    }

    @Test
    public void testExecute() {
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        TodoList todoList = new TodoList();
        todoList.addTask(task);

        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        removeTaskUseCase.execute(task);

        verify(todoListRepository, times(1)).loadTodoList();
        verify(todoListRepository, times(1)).saveTodoList(todoList);

        assertEquals(0, todoList.getTasks().size());
    }
}
