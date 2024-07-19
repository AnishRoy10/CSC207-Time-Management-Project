package data_access;

import entity.Task;
import entity.TodoList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TodoListRepository;
import use_case.*;
import use_case.AddTaskUseCase.AddTaskRequestModel;
import use_case.AddTaskUseCase.AddTaskUseCase;
import use_case.CompleteTaskUseCase.CompleteTaskUseCase;
import use_case.LoadTodoListUseCase.LoadTodoListOutputBoundary;
import use_case.LoadTodoListUseCase.LoadTodoListRequestModel;
import use_case.LoadTodoListUseCase.LoadTodoListResponseModel;
import use_case.LoadTodoListUseCase.LoadTodoListUseCase;
import use_case.RemoveTaskUseCase.RemoveTaskRequestModel;
import use_case.RemoveTaskUseCase.RemoveTaskUseCase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoListDataAccessObjectTest {
    private static final String DIRECTORY_PATH = "saves";
    private static final String FILE_PATH = DIRECTORY_PATH + "/todo_list.json";
    private TodoListRepository todoListRepository;
    private AddTaskUseCase addTaskUseCase;
    private RemoveTaskUseCase removeTaskUseCase;
    private LoadTodoListUseCase loadTodoListUseCase;
    private CompleteTaskUseCase completeTaskUseCase;
    private TestLoadTodoListOutputBoundary loadTodoListOutputBoundary;

    @BeforeEach
    public void setUp() throws IOException {
        todoListRepository = new TodoListDataAccessObject();
        loadTodoListOutputBoundary = new TestLoadTodoListOutputBoundary();
        addTaskUseCase = new AddTaskUseCase(todoListRepository, task -> {});
        removeTaskUseCase = new RemoveTaskUseCase(todoListRepository, task -> {});
        loadTodoListUseCase = new LoadTodoListUseCase(todoListRepository, loadTodoListOutputBoundary);
        completeTaskUseCase = new CompleteTaskUseCase(todoListRepository, task -> {});
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
        AddTaskRequestModel addTaskRequest = new AddTaskRequestModel("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        addTaskUseCase.execute(addTaskRequest);

        // Load the to-do list
        LoadTodoListRequestModel loadRequest = new LoadTodoListRequestModel();
        loadTodoListUseCase.execute(loadRequest);

        // Verify the task was added
        TodoList loadedTodoList = convertToTodoList(loadTodoListOutputBoundary.getTasks());
        Task taskToRemove = loadedTodoList.getTasks().get(0);
        System.out.println("TodoList after adding task: " + loadedTodoList.getTasks());

        // Remove the task from the to-do list
        RemoveTaskRequestModel removeTaskRequest = new RemoveTaskRequestModel(taskToRemove.getId());
        removeTaskUseCase.execute(removeTaskRequest);

        // Load the to-do list
        loadTodoListUseCase.execute(loadRequest);
        loadedTodoList = convertToTodoList(loadTodoListOutputBoundary.getTasks());
        System.out.println("TodoList after removing task: " + loadedTodoList.getTasks());

        // Verify the to-do list is empty
        assertEquals(0, loadedTodoList.getTasks().size());
    }

    @Test
    public void testAddMultipleTasks() {
        // Create multiple tasks
        AddTaskRequestModel addTaskRequest1 = new AddTaskRequestModel("Title1", "Description1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course1");
        AddTaskRequestModel addTaskRequest2 = new AddTaskRequestModel("Title2", "Description2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course2");

        addTaskUseCase.execute(addTaskRequest1);
        addTaskUseCase.execute(addTaskRequest2);

        // Load the to-do list
        LoadTodoListRequestModel loadRequest = new LoadTodoListRequestModel();
        loadTodoListUseCase.execute(loadRequest);
        TodoList loadedTodoList = convertToTodoList(loadTodoListOutputBoundary.getTasks());

        // Verify the to-do list contents
        assertEquals(2, loadedTodoList.getTasks().size());
        assertEquals(addTaskRequest1.getTitle(), loadedTodoList.getTasks().get(0).getTitle());
        assertEquals(addTaskRequest2.getTitle(), loadedTodoList.getTasks().get(1).getTitle());
    }

    @Test
    public void testSaveAndLoadTodoList() {
        // Create a new task and add it to the to-do list
        AddTaskRequestModel addTaskRequest = new AddTaskRequestModel("Title", "Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course");
        addTaskUseCase.execute(addTaskRequest);

        // Load the to-do list
        LoadTodoListRequestModel loadRequest = new LoadTodoListRequestModel();
        loadTodoListUseCase.execute(loadRequest);
        TodoList loadedTodoList = convertToTodoList(loadTodoListOutputBoundary.getTasks());

        // Verify the to-do list contents
        assertEquals(1, loadedTodoList.getTasks().size());
        Task loadedTask = loadedTodoList.getTasks().get(0);
        assertEquals(addTaskRequest.getTitle(), loadedTask.getTitle());
        assertEquals(addTaskRequest.getDescription(), loadedTask.getDescription());
        assertEquals(addTaskRequest.getCourse(), loadedTask.getCourse());
    }

    @Test
    public void testLoadTodoListWhenFileDoesNotExist() {
        // Ensure the test file does not exist
        cleanUp();

        // Load the to-do list
        LoadTodoListRequestModel loadRequest = new LoadTodoListRequestModel();
        loadTodoListUseCase.execute(loadRequest);
        TodoList loadedTodoList = convertToTodoList(loadTodoListOutputBoundary.getTasks());

        // Verify the to-do list is empty
        assertEquals(0, loadedTodoList.getTasks().size());
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
