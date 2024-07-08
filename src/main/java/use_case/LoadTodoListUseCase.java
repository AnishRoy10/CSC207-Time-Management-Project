package use_case;

import entity.TodoList;
import repositories.TodoListRepository;

public class LoadTodoListUseCase {
    private final TodoListRepository todoListRepository;

    public LoadTodoListUseCase(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    public TodoList execute() {
        return todoListRepository.loadTodoList();
    }
}
