package use_case;

import entity.TodoList;
import repositories.TodoListRepository;

public class LoadTodoListUseCase implements LoadTodoListInputBoundary {
    private final TodoListRepository todoListRepository;
    private final LoadTodoListOutputBoundary loadTodoListOutputBoundary;

    public LoadTodoListUseCase(TodoListRepository todoListRepository, LoadTodoListOutputBoundary loadTodoListOutputBoundary) {
        this.todoListRepository = todoListRepository;
        this.loadTodoListOutputBoundary = loadTodoListOutputBoundary;
    }

    @Override
    public TodoList execute(LoadTodoListRequestModel requestModel) {
        TodoList todoList = todoListRepository.loadTodoList();
        loadTodoListOutputBoundary.present(new LoadTodoListResponseModel(todoList));
        return todoList;
    }
}
