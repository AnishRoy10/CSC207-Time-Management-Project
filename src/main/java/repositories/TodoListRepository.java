package repositories;

import entity.TodoList;

/**
 * Repository interface for accessing the to-do list.
 */
public interface TodoListRepository {
    /**
     * Loads the to-do list from the data source.
     *
     * @return The loaded to-do list.
     */
    TodoList loadTodoList();

    /**
     * Saves the to-do list to the data source.
     *
     * @param todoList The to-do list to be saved.
     */
    void saveTodoList(TodoList todoList);
}
