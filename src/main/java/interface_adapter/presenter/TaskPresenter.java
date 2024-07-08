package interface_adapter.presenter;

import use_case.TodoListOutputBoundary;
import use_case.TaskResponseModel;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The TaskPresenter class is responsible for formatting tasks for display in the user interface.
 */
public class TaskPresenter implements TodoListOutputBoundary {
    private List<TaskResponseModel> taskResponseModels;

    /**
     * Constructs a TaskPresenter.
     */
    public TaskPresenter() {
        // Default constructor
    }

    /**
     * Presents tasks by saving them internally as TaskResponseModels.
     *
     * @param taskResponseModels The list of task response models to be presented
     */
    @Override
    public void presentTasks(List<TaskResponseModel> taskResponseModels) {
        this.taskResponseModels = taskResponseModels;
    }

    /**
     * Returns a list of TaskResponseModels.
     *
     * @return The list of TaskResponseModels
     */
    public List<TaskResponseModel> getTaskResponseModels() {
        return taskResponseModels;
    }

    /**
     * Returns a formatted string of all tasks.
     *
     * @return A formatted string representation of all tasks
     */
    public String getFormattedTasks() {
        return taskResponseModels.stream()
                .map(this::formatTask)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Formats a single task for display.
     *
     * @param task The task to be formatted
     * @return A formatted string representation of the task
     */
    private String formatTask(TaskResponseModel task) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(task.getTitle()).append("\n");
        if (!task.getDescription().isEmpty()) {
            sb.append("Description: ").append(task.getDescription()).append("\n");
        }
        sb.append("Start Date: ").append(task.getStartDate()).append("\n");
        sb.append("Deadline: ").append(task.getDeadline()).append("\n");
        sb.append("Course: ").append(task.getCourse() != null ? task.getCourse() : "None").append("\n");
        sb.append("Completed: ").append(task.isCompleted() ? "Yes" : "No");
        if (task.isCompleted()) {
            sb.append("\nCompletion Date: ").append(task.getCompletionDate());
        }
        return sb.toString();
    }
}
