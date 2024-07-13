package use_case;

import entity.Task;
import entity.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import repositories.TodoListRepository;
import use_case.RemoveTaskUseCase.RemoveTaskOutputBoundary;
import use_case.RemoveTaskUseCase.RemoveTaskRequestModel;
import use_case.RemoveTaskUseCase.RemoveTaskResponseModel;
import use_case.RemoveTaskUseCase.RemoveTaskUseCase;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RemoveTaskUseCaseTest {
    private TodoListRepository todoListRepository;
    private RemoveTaskOutputBoundary removeTaskOutputBoundary;
    private RemoveTaskUseCase removeTaskUseCase;

    @BeforeEach
    public void setUp() {
        todoListRepository = mock(TodoListRepository.class);
        removeTaskOutputBoundary = mock(RemoveTaskOutputBoundary.class);
        removeTaskUseCase = new RemoveTaskUseCase(todoListRepository, removeTaskOutputBoundary);
    }

    @Test
    public void testExecute() {
        // Test removing a task
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        TodoList todoList = new TodoList();
        todoList.addTask(task);
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        RemoveTaskRequestModel requestModel = new RemoveTaskRequestModel(task.getId());

        removeTaskUseCase.execute(requestModel);

        verify(todoListRepository, times(1)).loadTodoList();
        verify(todoListRepository, times(1)).saveTodoList(todoList);

        ArgumentCaptor<RemoveTaskResponseModel> argumentCaptor = ArgumentCaptor.forClass(RemoveTaskResponseModel.class);
        verify(removeTaskOutputBoundary, times(1)).present(argumentCaptor.capture());

        RemoveTaskResponseModel capturedResponseModel = argumentCaptor.getValue();
        List<TaskData> tasks = capturedResponseModel.getTasks();
        assertEquals(0, tasks.size());

        assertEquals(0, todoList.getTasks().size());
    }

    @Test
    public void testRemoveNonExistentTask() {
        // Test attempting to remove a non-existent task
        TodoList todoList = new TodoList();
        when(todoListRepository.loadTodoList()).thenReturn(todoList);

        RemoveTaskRequestModel requestModel = new RemoveTaskRequestModel(999); // Non-existent ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            removeTaskUseCase.execute(requestModel);
        });

        assertEquals("Task not found", exception.getMessage());

        verify(todoListRepository, times(1)).loadTodoList();
        verify(todoListRepository, never()).saveTodoList(todoList); // Should not save since the task doesn't exist
        verify(removeTaskOutputBoundary, never()).present(any(RemoveTaskResponseModel.class));
    }
}
