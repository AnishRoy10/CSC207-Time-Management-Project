package use_case;

import entity.Task;
import entity.TodoList;
import entity.Course;
import java.time.LocalDateTime;

/**
 * The AddTaskUseCase class handles the business logic of adding a task to the TodoList.
 */
public class AddTaskUseCase {
    private final TodoList todoList;

    public AddTaskUseCase(TodoList todoList) {
        this.todoList = todoList;
    }

    public void addTask(String description, LocalDateTime startDate, LocalDateTime deadline, Course course) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be null or empty");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        Task task = new Task(description, startDate, deadline, course);
        todoList.addTask(task);
    }
}
