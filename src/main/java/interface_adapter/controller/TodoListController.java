package interface_adapter.controller;

import use_case.TodoListInputBoundary;
import use_case.TaskResponseModel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for the to-do list use case.
 * It interacts with the input boundary to perform actions.
 */
public class TodoListController {
    private final TodoListInputBoundary todoListInputBoundary;

    /**
     * Constructs a TodoListController with the specified input boundary.
     *
     * @param todoListInputBoundary The input boundary for the to-do list use case
     */
    public TodoListController(TodoListInputBoundary todoListInputBoundary) {
        this.todoListInputBoundary = todoListInputBoundary;
    }

    /**
     * Adds a task using the use case.
     *
     * @param title       The title of the task
     * @param description The description of the task
     * @param startDate   The start date and time of the task
     * @param deadline    The deadline date and time for the task
     * @param course      The course associated with the task
     */
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        todoListInputBoundary.addTask(title, description, startDate, deadline, course);
    }

    /**
     * Loads tasks from the use case.
     *
     * @return The list of task response models
     */
    public List<TaskResponseModel> loadTasks() {
        return todoListInputBoundary.loadTasks();
    }
}
