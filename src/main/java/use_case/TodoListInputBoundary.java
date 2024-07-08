package use_case;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for the input boundary of the to-do list use case.
 * It defines the methods to add and load tasks.
 */
public interface TodoListInputBoundary {
    void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course);
    List<TaskResponseModel> loadTasks();
}
