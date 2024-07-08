package data_access;

import entity.Task;
import entity.TodoList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TodoListRepository;
import use_case.AddTaskUseCase;
import use_case.LoadTodoListUseCase;
import use_case.RemoveTaskUseCase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoListDataAccessObjectTest {
    private static final String DIRECTORY_PATH = "saves";
    private static final String FILE_PATH = DIRECTORY_PATH + "/todo_list.json";
    private TodoListRepository todoListRepository;
    private AddTaskUseCase addTaskUseCase;
    private RemoveTaskUseCase removeTaskUseCase;
    private LoadTodoListUseCase loadTodoListUseCase;

    @BeforeEach
    public void setUp() throws IOException {
        todoListRepository = new TodoListDataAccessObject();
        addTaskUseCase = new AddTaskUseCase(todoListRepository);
        removeTaskUseCase = new RemoveTaskUseCase(todoListRepository);
        loadTodoListUseCase = new LoadTodoListUseCase(todoListRepository);
        cleanUp();
        ensureTestFileExists();
    }

    @AfterEach
    public void tearDown() {
        cleanUp();
    }

    private void ensureTestFileExists() throws IOException {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("{\"tasks\":[]}");
            }
        }
    }

    private void cleanUp() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testRemoveTask() {
        // Create a task and add it to the to-do list
        Task task = new Task("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        System.out.println("Adding task: " + task);
        addTaskUseCase.execute(task);

        // Load the to-do list
        TodoList todoListAfterAdd = loadTodoListUseCase.execute();
        System.out.println("TodoList after adding task: " + todoListAfterAdd.getTasks());

        // Remove the task from the to-do list
        removeTaskUseCase.execute(task);

        // Load the to-do list
        TodoList loadedTodoList = loadTodoListUseCase.execute();
        System.out.println("TodoList after removing task: " + loadedTodoList.getTasks());

        // Verify the to-do list is empty
        assertEquals(0, loadedTodoList.getTasks().size());
    }
}
