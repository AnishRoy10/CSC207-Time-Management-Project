package interface_adapter.presenter;

import use_case.TaskResponseModel;
import use_case.TodoListOutputBoundary;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Presenter for the to-do list use case.
 * It formats the task response models for display in the user interface.
 */
public class TaskPresenter implements TodoListOutputBoundary {

    /**
     * Presents the tasks by formatting them for display.
     *
     * @param tasks The list of task response models
     */
    @Override
    public void presentTasks(List<TaskResponseModel> tasks) {
        // Format tasks for UI display if needed
    }

    /**
     * Returns a formatted string of all tasks in the to-do list.
     *
     * @param tasks The list of task response models
     * @return A formatted string representation of all tasks
     */
    public String getFormattedTasks(List<TaskResponseModel> tasks) {
        return tasks.stream()
                .map(this::formatTask)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Formats a single task for display.
     *
     * @param task The task response model to be formatted
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
