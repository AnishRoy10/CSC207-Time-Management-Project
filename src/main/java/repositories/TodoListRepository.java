package repositories;

import entity.TodoList;

import java.util.UUID;

/**
 * The TodoListRepository interface provides methods to manage to-do lists in the database.
 */
public interface TodoListRepository {
    /**
     * Writes a TodoList object to the database.
     *
     * @param todoList The TodoList object to write to the database.
     * @param username The username associated with the TodoList.
     */
    void WriteToCache(TodoList todoList, String username);

    /**
     * Writes a course-specific TodoList object to the database.
     *
     * @param todoList The TodoList object to write to the database.
     * @param username The username associated with the TodoList.
     * @param courseName The course name associated with the TodoList.
     */
    void WriteToCache(TodoList todoList, String username, String courseName);

    /**
     * Reads a TodoList object from the database by username.
     *
     * @param username The username associated with the TodoList.
     * @return The TodoList object associated with the username.
     */
    TodoList ReadFromCache(String username);

    /**
     * Reads a course-specific TodoList object from the database by username and course name.
     *
     * @param username The username associated with the TodoList.
     * @param courseName The course name associated with the TodoList.
     * @return The TodoList object associated with the username and course name.
     */
    TodoList ReadFromCache(String username, String courseName);

    /**
     * Removes a task from the database by its ID.
     *
     * @param taskId The ID of the task to be removed.
     */
    void removeTaskFromCache(UUID taskId);
}
