package interface_adapter.controller;

import use_case.TodoListInputBoundary;
import java.time.LocalDateTime;

/**
 * The TodoListController class acts as a mediator between the UI and the use case.
 */
public class TodoListController {
    private final TodoListInputBoundary todoListInputBoundary;

    /**
     * Constructs a TodoListController with the specified TodoListInputBoundary.
     *
     * @param todoListInputBoundary The input boundary for adding tasks
     */
    public TodoListController(TodoListInputBoundary todoListInputBoundary) {
        this.todoListInputBoundary = todoListInputBoundary;
    }

    /**
     * Adds a task using the input boundary.
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
     * Loads tasks using the input boundary.
     */
    public void loadTasks() {
        todoListInputBoundary.loadTasks();
    }
}
