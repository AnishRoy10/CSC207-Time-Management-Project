package use_case;

import java.util.List;

/**
 * Response model for removing a task.
 */
public class RemoveTaskResponseModel {
    private List<TaskData> tasks;
    private int taskId;

    public RemoveTaskResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public RemoveTaskResponseModel(List<TaskData> tasks, int taskId) {
        this.tasks = tasks;
        this.taskId = taskId;
    }

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
