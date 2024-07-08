package use_case;

import java.util.List;

/**
 * Response model for loading the to-do list.
 */
public class LoadTodoListResponseModel {
    private List<TaskData> tasks;

    public LoadTodoListResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    // Getter and setter

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}
