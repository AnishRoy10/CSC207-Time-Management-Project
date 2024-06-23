package interface_adapter.controller;

import use_case.AddTaskUseCase;
import java.time.LocalDateTime;
import entity.Course;

/**
 * The TodoListController class acts as a mediator between the UI and the use case.
 */
public class TodoListController {
    private final AddTaskUseCase addTaskUseCase;

    /**
     * Constructs a TodoListController with the specified AddTaskUseCase.
     *
     * @param addTaskUseCase The use case for adding tasks
     */
    public TodoListController(AddTaskUseCase addTaskUseCase) {
        this.addTaskUseCase = addTaskUseCase;
    }

    /**
     * Adds a task using the use case.
     *
     * @param description The description of the task
     * @param startDate   The start date and time of the task
     * @param deadline    The deadline date and time for the task
     * @param course      The course associated with the task
     */
    public void addTask(String description, LocalDateTime startDate, LocalDateTime deadline, Course course) {
        addTaskUseCase.addTask(description, startDate, deadline, course);
    }
}
