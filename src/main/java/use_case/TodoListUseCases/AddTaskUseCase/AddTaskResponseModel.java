package use_case.TodoListUseCases.AddTaskUseCase;

import use_case.TodoListUseCases.TaskData;

import java.util.List;

/**
 * The {@code AddTaskResponseModel} class represents the response data after a task has been successfully added.
 * It contains a list of all tasks in the to-do list and the title of the newly added task.
 */
public class AddTaskResponseModel {
    private List<TaskData> tasks;
    private String title;

    /**
     * Constructs a new {@code AddTaskResponseModel} with the specified list of tasks.
     *
     * @param tasks The list of tasks in the to-do list.
     */
    public AddTaskResponseModel(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs a new {@code AddTaskResponseModel} with the specified list of tasks and the title of the newly added task.
     *
     * @param tasks The list of tasks in the to-do list.
     * @param title The title of the newly added task.
     */
    public AddTaskResponseModel(List<TaskData> tasks, String title) {
        this.tasks = tasks;
        this.title = title;
    }

    /**
     * Returns the list of tasks in the to-do list.
     *
     * @return The list of tasks.
     */
    public List<TaskData> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of tasks in the to-do list.
     *
     * @param tasks The list of tasks to set.
     */
    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the title of the newly added task.
     *
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the newly added task.
     *
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
