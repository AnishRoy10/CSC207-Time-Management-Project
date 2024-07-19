package data_access;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entity.Task;
import entity.TodoList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TodoListRepository;
import use_case.*;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskUseCase;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskUseCase;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListOutputBoundary;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListRequestModel;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListResponseModel;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListUseCase;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskUseCase;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoListDataAccessObjectTest2 {
    private static final String DIRECTORY_PATH = "saves";
    private static final String FILE_PATH = DIRECTORY_PATH + "/todo_list.json";
    private TodoListRepository todoListRepository;
    private AddTaskUseCase addTaskUseCase;
    private RemoveTaskUseCase removeTaskUseCase;
    private LoadTodoListUseCase loadTodoListUseCase;
    private CompleteTaskUseCase completeTaskUseCase;
    private Gson gson;
    private TestLoadTodoListOutputBoundary loadTodoListOutputBoundary;

    @BeforeEach
    public void setUp() throws IOException {
        todoListRepository = new TodoListDataAccessObject();
        loadTodoListOutputBoundary = new TestLoadTodoListOutputBoundary();
        addTaskUseCase = new AddTaskUseCase(todoListRepository, task -> {});
        removeTaskUseCase = new RemoveTaskUseCase(todoListRepository, task -> {});
        loadTodoListUseCase = new LoadTodoListUseCase(todoListRepository, loadTodoListOutputBoundary);
        completeTaskUseCase = new CompleteTaskUseCase(todoListRepository, task -> {});
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .create();
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
    public void testSaveAndLoadTodoList() throws IOException {
        // Create a new task and add it to the to-do list
        AddTaskRequestModel addTaskRequest = new AddTaskRequestModel("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        addTaskUseCase.execute(addTaskRequest);

        // Load the to-do list
        LoadTodoListRequestModel loadRequest = new LoadTodoListRequestModel();
        loadTodoListUseCase.execute(loadRequest);

        // Verify the to-do list contents
        TodoList loadedTodoList = convertToTodoList(loadTodoListOutputBoundary.getTasks());
        assertEquals(1, loadedTodoList.getTasks().size());
        Task loadedTask = loadedTodoList.getTasks().get(0);
        assertEquals(addTaskRequest.getTitle(), loadedTask.getTitle());
        assertEquals(addTaskRequest.getDescription(), loadedTask.getDescription());
        assertEquals(addTaskRequest.getCourse(), loadedTask.getCourse());

        // Verify the JSON file is created and contains the expected data
        File file = new File(FILE_PATH);
        assertTrue(file.exists());

        // Read the file and verify its contents
        try (FileReader reader = new FileReader(file)) {
            Type todoListType = new TypeToken<TodoList>() {}.getType();
            TodoList todoListFromFile = gson.fromJson(reader, todoListType);

            assertEquals(1, todoListFromFile.getTasks().size());
            Task taskFromFile = todoListFromFile.getTasks().get(0);
            assertEquals(addTaskRequest.getTitle(), taskFromFile.getTitle());
            assertEquals(addTaskRequest.getDescription(), taskFromFile.getDescription());
            assertEquals(addTaskRequest.getCourse(), taskFromFile.getCourse());
        }
    }

    private TodoList convertToTodoList(List<TaskData> taskDataList) {
        TodoList todoList = new TodoList();
        for (TaskData taskData : taskDataList) {
            Task task = new Task(
                    taskData.getTitle(),
                    taskData.getDescription(),
                    taskData.getStartDate(),
                    taskData.getDeadline(),
                    taskData.getCourse()
            );
            task.setId(taskData.getId());
            task.setCompleted(taskData.isCompleted());
            task.setCompletionDate(taskData.getCompletionDate());
            todoList.addTask(task);
        }
        return todoList;
    }

    private static class TestLoadTodoListOutputBoundary implements LoadTodoListOutputBoundary {
        private List<TaskData> tasks;

        @Override
        public void present(LoadTodoListResponseModel responseModel) {
            tasks = responseModel.getTasks();
        }

        public List<TaskData> getTasks() {
            return tasks;
        }
    }
}
