package use_case;

import java.util.List;

/**
 * Interface for the output boundary of the to-do list use case.
 */
public interface TodoListOutputBoundary {

    /**
     * Presents the list of tasks.
     *
     * @param tasks The list of tasks to be presented.
     */
    void presentTasks(List<TaskResponseModel> tasks);
}
