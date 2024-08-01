package repositories;

import entity.Task;

import java.util.List;
import java.util.UUID;

/**
 * The TaskRepository interface provides methods to manage tasks in the database.
 */
public interface TaskRepository {

    /**
     * Writes a Task object to the database. If the task already exists, updates the task's data.
     *
     * @param task The Task object to write to the database.
     * @param username The username associated with the task.
     */
    void WriteToCache(Task task, String username);

    /**
     * Reads a Task object from the database by task ID.
     *
     * @param id The task ID to read.
     * @return The Task object with the specified ID, or null if not found.
     */
    Task ReadFromCache(UUID id);

    /**
     * Retrieves all tasks associated with a specific username from the database.
     *
     * @param username The username associated with the tasks.
     * @return A list of Task objects.
     */
    List<Task> getAllTasks(String username);

    /**
     * Deletes a task by task ID from the database.
     *
     * @param id The task ID to delete.
     */
    void deleteTask(UUID id);
}
