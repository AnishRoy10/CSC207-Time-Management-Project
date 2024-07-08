package use_case;

import data_access.TodoListDataAccessObject;
import entity.TodoList;

import java.io.IOException;

/**
 * Use case for persisting the to-do list.
 */
public class FileTodoListPersistenceUseCase {
    private final TodoListDataAccessObject todoListDataAccessObject;

    /**
     * Constructs a FileTodoListPersistenceUseCase with the specified TodoListDataAccessObject.
     *
     * @param todoListDataAccessObject The data access object for task persistence
     */
    public FileTodoListPersistenceUseCase(TodoListDataAccessObject todoListDataAccessObject) {
        this.todoListDataAccessObject = todoListDataAccessObject;
    }

    /**
     * Saves the to-do list to the file.
     *
     * @param todoList The to-do list to save
     * @throws IOException If an I/O error occurs while saving the to-do list
     */
    public void saveTodoList(TodoList todoList) throws IOException {
        todoListDataAccessObject.saveTodoList(todoList);
    }

    /**
     * Loads the to-do list from the file.
     *
     * @return The loaded to-do list
     * @throws IOException            If an I/O error occurs while loading the to-do list
     * @throws ClassNotFoundException If the class of the loaded object cannot be found
     */
    public TodoList loadTodoList() throws IOException, ClassNotFoundException {
        return todoListDataAccessObject.loadTodoList();
    }
}
