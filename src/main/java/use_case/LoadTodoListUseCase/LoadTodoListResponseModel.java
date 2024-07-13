package use_case.LoadTodoListUseCase;

import use_case.TaskData;

import java.util.List;

public class LoadTodoListResponseModel {
    private final List<TaskData> tasks;

    public LoadTodoListResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public List<TaskData> getTasks() {
        return tasks;
    }
}
