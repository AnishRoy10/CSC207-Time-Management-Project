package use_case;

import entity.Task;
import entity.TodoList;
import entity.CalendarEvent;

import java.time.LocalDateTime;

/**
 * Use case for adding a task to the to-do list.
 */
public class AddTaskUseCase {
    private TodoList todoList;

    /**
     * Constructs an AddTaskUseCase with the specified to-do list.
     *
     * @param todoList The to-do list to which tasks will be added
     */
    public AddTaskUseCase(TodoList todoList) {
        this.todoList = todoList;
    }

    /**
     * Adds a task to the to-do list.
     *
     * @param title       The title of the task
     * @param description The description of the task (optional)
     * @param startDate   The start date and time of the task
     * @param deadline    The deadline date and time for the task
     * @param course      The course associated with the task (optional, as a string)
     */
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        Task task = new Task(title, description, startDate, deadline, course);
        todoList.addTask(task);
    }

    /**
     * Converts a CalendarEvent to a Task and adds it to the to-do list.
     *
     * @param event The calendar event to convert to a task
     */
    public void convertEventToTask(CalendarEvent event) {
        Task task = new Task(
                event.getName(),
                event.getDescription(),
                event.getStartDate(),
                event.getEndDate(),
                null // Assuming course information is not relevant for the event-to-task conversion
        );
        todoList.addTask(task);
    }

    /**
     * Gets the to-do list.
     *
     * @return The to-do list
     */
    public TodoList getTodoList() {
        return todoList;
    }
}
