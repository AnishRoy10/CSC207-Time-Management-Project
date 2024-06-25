package interface_adapter.presenter;


import entity.TodoList;
import entity.Task;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The TaskPresenter class is responsible for formatting tasks for display in the user interface.
 */
public class TaskPresenter {
    private TodoList todoList;

    /**
     * Constructs a TaskPresenter with the specified to-do list.
     *
     * @param todoList The to-do list whose tasks will be formatted
     */
    public TaskPresenter(TodoList todoList) {
        this.todoList = todoList;
    }

    /**
     * Returns a formatted string of all tasks in the to-do list.
     *
     * @return A formatted string representation of all tasks
     */
    public String getFormattedTasks() {
        List<Task> tasks = todoList.getTasks();
        return tasks.stream()
                .map(this::formatTask)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Formats a single task for display.
     *
     * @param task The task to be formatted
     * @return A formatted string representation of the task
     */
    private String formatTask(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(task.getTitle()).append("\n");
        if (!task.getDescription().isEmpty()) {
            sb.append("Description: ").append(task.getDescription()).append("\n");
        }
        sb.append("Start Date: ").append(task.getStartDate()).append("\n");
        sb.append("Deadline: ").append(task.getDeadline()).append("\n");
        sb.append("Course: ").append(task.getCourse() != null ? task.getCourse().getName() : "None").append("\n");
        sb.append("Completed: ").append(task.isCompleted() ? "Yes" : "No");
        return sb.toString();
    }
}
