package data_access;

import entity.Task;
import entity.TodoList;
import repositories.TaskRepository;
import repositories.TodoListRepository;

import java.util.List;
import java.util.UUID;

/**
 * The TodoListDAO class handles CRUD operations for TodoList objects in an SQLite database.
 * It implements the TodoListRepository interface.
 */
public class TodoListDAO implements TodoListRepository {
    private final TaskRepository taskRepository;

    /**
     * Constructor for TodoListDAO.
     *
     * @param taskRepository An instance of TaskRepository for managing task data.
     */
    public TodoListDAO(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Writes a TodoList object to the database. This method writes each task in the TodoList.
     *
     * @param todoList The TodoList object to write to the database.
     * @param username The username associated with the TodoList.
     */
    @Override
    public void WriteToCache(TodoList todoList, String username) {
        for (Task task : todoList.getTasks()) {
            taskRepository.WriteToCache(task, username);
        }
    }

    /**
     * Writes a TodoList object to the database for a specific course. This method writes each task in the TodoList.
     *
     * @param todoList The TodoList object to write to the database.
     * @param username The username associated with the TodoList.
     * @param courseName The name of the course.
     */
    @Override
    public void WriteToCache(TodoList todoList, String username, String courseName) {
        for (Task task : todoList.getTasks()) {
            taskRepository.WriteToCache(task, username, courseName);
        }
    }

    /**
     * Reads a TodoList object from the database for the specified username.
     *
     * @param username The username associated with the TodoList.
     * @return The TodoList object read from the database.
     */
    @Override
    public TodoList ReadFromCache(String username) {
        List<Task> tasks = taskRepository.getAllTasks(username);
        TodoList todoList = new TodoList();
        for (Task task : tasks) {
            todoList.addTask(task);
        }
        return todoList;
    }

    /**
     * Reads a TodoList object from the database for the specified username and course.
     *
     * @param username The username associated with the TodoList.
     * @param courseName The name of the course.
     * @return The TodoList object read from the database.
     */
    @Override
    public TodoList ReadFromCache(String username, String courseName) {
        List<Task> tasks = taskRepository.getAllTasks(username, courseName);
        TodoList todoList = new TodoList();
        for (Task task : tasks) {
            todoList.addTask(task);
        }
        return todoList;
    }

    /**
     * Removes a task from the database by its ID.
     *
     * @param taskId The ID of the task to be removed.
     */
    @Override
    public void removeTaskFromCache(UUID taskId) {
        taskRepository.deleteTask(taskId);
    }
}
