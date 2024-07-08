package use_case;

import java.util.List;

/**
 * Interface for the output boundary of the to-do list use case.
 * It defines the method to present tasks.
 */
public interface TodoListOutputBoundary {
    void presentTasks(List<TaskResponseModel> tasks);
}
