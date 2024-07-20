package repositories;

import entity.TodoList;
import java.io.IOException;

/**
 * TodoListRepository provides an interface for TodoList data access operations.
 */
public interface TodoListRepository {
    /**
     * Loads the TodoList for a specific user.
     *
     * @param username The username of the user.
     * @return The TodoList of the user.
     * @throws IOException If an I/O error occurs.
     */
    TodoList loadTodoList(String username) throws IOException;

    /**
     * Saves the TodoList for a specific user.
     *
     * @param username The username of the user.
     * @param todoList The TodoList to save.
     * @throws IOException If an I/O error occurs.
     */
    void saveTodoList(String username, TodoList todoList) throws IOException;
}
