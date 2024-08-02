package data_access;

import entity.Task;
import entity.TodoList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.TaskRepository;
import repositories.TodoListRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TodoListDAOTest {
    private SQLDatabaseHelper dbHelper;
    private TaskRepository taskRepository;
    private TodoListRepository todoListRepository;

    @BeforeEach
    void setUp() {
        dbHelper = new SQLDatabaseHelper("jdbc:sqlite:Saves/TestDB.db");
        dbHelper.initializeDatabase();
        taskRepository = new TaskDAO(dbHelper);
        todoListRepository = new TodoListDAO(taskRepository);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Tasks");
            stmt.execute("DELETE FROM Users");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testRemoveTaskFromTodoList() {
        String username = "removeTaskUser";
        TodoList todoList = new TodoList();

        Task task1 = new Task(username, "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");
        Task task2 = new Task(username, "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course 2");

        todoList.addTask(task1);
        todoList.addTask(task2);
        todoListRepository.WriteToCache(todoList, username);

        todoList.removeTask(task1);
        taskRepository.deleteTask(task1.getId()); // Ensure the task is removed from the database
        todoListRepository.WriteToCache(todoList, username);

        TodoList retrievedTodoList = todoListRepository.ReadFromCache(username);

        assertNotNull(retrievedTodoList);
        assertEquals(1, retrievedTodoList.getTasks().size());
        assertFalse(retrievedTodoList.getTasks().stream().anyMatch(task -> task.getTitle().equals("Task 1")));
        assertTrue(retrievedTodoList.getTasks().stream().anyMatch(task -> task.getTitle().equals("Task 2")));
    }

    @Test
    void testAddAndRetrieveTodoList() {
        String username = "testUser";
        TodoList todoList = new TodoList();

        Task task1 = new Task(username, "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");
        Task task2 = new Task(username, "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course 2");

        todoList.addTask(task1);
        todoList.addTask(task2);
        todoListRepository.WriteToCache(todoList, username);

        TodoList retrievedTodoList = todoListRepository.ReadFromCache(username);

        assertNotNull(retrievedTodoList);
        assertEquals(2, retrievedTodoList.getTasks().size());
        assertTrue(retrievedTodoList.getTasks().stream().anyMatch(task -> task.getTitle().equals("Task 1")));
        assertTrue(retrievedTodoList.getTasks().stream().anyMatch(task -> task.getTitle().equals("Task 2")));
    }

    @Test
    void testUpdateTaskInTodoList() {
        String username = "updateTaskUser";
        TodoList todoList = new TodoList();

        Task task = new Task(username, "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");

        todoList.addTask(task);
        todoListRepository.WriteToCache(todoList, username);

        // Update the task
        task.setTitle("Updated Task 1");
        todoListRepository.WriteToCache(todoList, username);

        TodoList retrievedTodoList = todoListRepository.ReadFromCache(username);

        assertNotNull(retrievedTodoList);
        assertEquals(1, retrievedTodoList.getTasks().size());
        assertTrue(retrievedTodoList.getTasks().stream().anyMatch(t -> t.getTitle().equals("Updated Task 1")));
    }

    @Test
    void testGetAllTasksForUser() {
        String username = "allTasksUser";
        TodoList todoList = new TodoList();

        Task task1 = new Task(username, "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");
        Task task2 = new Task(username, "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course 2");

        todoList.addTask(task1);
        todoList.addTask(task2);
        todoListRepository.WriteToCache(todoList, username);

        List<Task> allTasks = taskRepository.getAllTasks(username);

        assertNotNull(allTasks);
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.stream().anyMatch(task -> task.getTitle().equals("Task 1")));
        assertTrue(allTasks.stream().anyMatch(task -> task.getTitle().equals("Task 2")));
    }
}
