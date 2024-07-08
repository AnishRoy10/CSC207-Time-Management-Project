package use_case;

import entity.TodoList;

/**
 * Interface for the persistence use case of the TodoList.
 */
public interface TodoListPersistenceUseCase {
    void saveTodoList(TodoList todoList);
    TodoList loadTodoList();
}
