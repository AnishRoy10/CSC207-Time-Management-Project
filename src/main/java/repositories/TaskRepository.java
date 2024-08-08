package repositories;

import entity.Task;

import java.util.List;
import java.util.UUID;

/**
 * The TaskRepository interface provides methods to manage tasks in the database.
 */
public interface TaskRepository {
    /**
     * Writes a Task object to the database.
     *
     * @param task The Task object to write to the database.
     * @param username The username associated with the Task.
     */
    void WriteToCache(Task task, String username);

    /**
     * Writes a course-specific Task object to the database.
     *
     * @param task The Task object to write to the database.
     * @param username The username associated with the Task.
     * @param courseName The course name associated with the Task.
     */
    void WriteToCache(Task task, String username, String courseName);

    /**
     * Reads a Task object from the database by task ID.
     *
     * @param taskId The ID of the Task to read from the database.
     * @return The Task object associated with the task ID.
     */
    Task ReadFromCache(UUID taskId);

    /**
     * Retrieves all Task objects associated with a specific user from the database.
     *
     * @param username The username associated with the Tasks.
     * @return A list of Task objects for the specified user.
     */
    List<Task> getAllTasks(String username);

    /**
     * Retrieves all course-specific Task objects associated with a specific user from the database.
     *
     * @param username The username associated with the Tasks.
     * @param courseName The course name associated with the Tasks.
     * @return A list of course-specific Task objects for the specified user.
     */
    List<Task> getAllTasks(String username, String courseName);

    /**
     * Deletes a Task object from the database by task ID.
     *
     * @param taskId The ID of the Task to delete from the database.
     */
    void deleteTask(UUID taskId);
}
