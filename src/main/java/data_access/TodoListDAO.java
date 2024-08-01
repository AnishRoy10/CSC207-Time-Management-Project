package data_access;

import entity.Task;
import entity.TodoList;
import repositories.TaskRepository;
import repositories.TodoListRepository;

import java.util.List;

/**
 * The TodoListDAO class handles CRUD operations for TodoList objects in an SQLite database.
 * It implements the TodoListRepository interface.
 */
public class TodoListDAO implements TodoListRepository {
    private final TaskRepository taskRepository;

    /**
     * Constructor for TodoListDAO.
     *
     * @param taskRepository An instance of TaskRepository for managing task-related operations.
     */
    public TodoListDAO(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Writes a TodoList object to the database.
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
     * Reads a TodoList object from the database by username.
     *
     * @param username The username associated with the TodoList.
     * @return The TodoList object associated with the username.
     */
    @Override
    public TodoList ReadFromCache(String username) {
        List<Task> tasks = taskRepository.getAllTasks(username);
        TodoList todoList = new TodoList();
        todoList.setTasks(tasks);
        return todoList;
    }
}
