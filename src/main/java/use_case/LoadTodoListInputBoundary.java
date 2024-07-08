package use_case;

import entity.TodoList;

public interface LoadTodoListInputBoundary {
    TodoList execute(LoadTodoListRequestModel requestModel);
}
