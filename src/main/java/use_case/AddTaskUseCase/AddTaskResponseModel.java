package use_case.AddTaskUseCase;

import use_case.TaskData;

import java.util.List;

/**
 * Response model for adding a task.
 */
public class AddTaskResponseModel {
    private List<TaskData> tasks;
    private String title;

    public AddTaskResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public AddTaskResponseModel(List<TaskData> tasks, String title) {
        this.tasks = tasks;
        this.title = title;
    }

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
