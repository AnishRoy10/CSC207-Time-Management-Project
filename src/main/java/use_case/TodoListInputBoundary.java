package use_case;

import java.time.LocalDateTime;

/**
 * Interface for the input boundary of the to-do list use case.
 */
public interface TodoListInputBoundary {

    /**
     * Adds a task to the to-do list.
     *
     * @param title       The title of the task.
     * @param description The description of the task (optional).
     * @param startDate   The start date and time of the task.
     * @param deadline    The deadline date and time for the task.
     * @param course      The course associated with the task (optional, as a string).
     */
    void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course);

    /**
     * Loads tasks from the to-do list.
     */
    void loadTasks();
}
