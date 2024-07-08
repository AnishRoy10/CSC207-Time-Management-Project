package use_case;

import entity.TodoList;

public class LoadTodoListResponseModel {
    private final TodoList todoList;

    public LoadTodoListResponseModel(TodoList todoList) {
        this.todoList = todoList;
    }

    public TodoList getTodoList() {
        return todoList;
    }
}
