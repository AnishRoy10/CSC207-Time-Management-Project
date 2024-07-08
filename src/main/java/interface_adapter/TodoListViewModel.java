package interface_adapter;

import interface_adapter.controller.TodoListController;
import use_case.TaskData;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ViewModel for the to-do list application.
 * Maintains the state and provides data to the View.
 */
public class TodoListViewModel {
    private TodoListController controller;
    private List<TaskData> tasks;

    public TodoListViewModel(TodoListController controller) {
        this.controller = controller;
    }

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the ViewModel.
     *
     * @param title       the title of the task
     * @param description the description of the task
     * @param startDate   the start date of the task
     * @param deadline    the deadline of the task
     * @param course      the course associated with the task
     */
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        controller.addTask(title, description, startDate, deadline, course);
        loadTodoList();
    }

    /**
     * Removes a task from the ViewModel by its ID.
     *
     * @param taskId the ID of the task to be removed
     */
    public void removeTask(int taskId) {
        controller.removeTask(taskId);
        loadTodoList();
    }

    /**
     * Completes a task in the ViewModel by its ID.
     *
     * @param taskId the ID of the task to be completed
     */
    public void completeTask(int taskId) {
        controller.completeTask(taskId);
        loadTodoList();
    }

    /**
     * Loads the to-do list into the ViewModel.
     */
    public void loadTodoList() {
        controller.loadTodoList();
    }
}
