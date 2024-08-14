package interface_adapter.viewmodel;

import use_case.TodoListUseCases.TaskData;

import java.util.ArrayList;
import java.util.List;

/**
 * The TodoListViewModel class serves as the ViewModel for the to-do list in the application.
 * It acts as an intermediary between the use case layer and the view layer, providing
 * a structure to hold and manage the task data that the user interface will display.
 */
public class TodoListViewModel {
    private List<TaskData> tasks; // List of tasks to be displayed in the to-do list
    private String errorMessage; // Error message to be displayed in case of any issues

    /**
     * Constructs an empty TodoListViewModel with an initialized empty task list.
     */
    public TodoListViewModel() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Retrieves the list of tasks.
     *
     * @return A list of TaskData objects representing the tasks in the to-do list.
     */
    public List<TaskData> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of tasks to be displayed in the to-do list.
     *
     * @param tasks A list of TaskData objects to set as the tasks.
     */
    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    /**
     * Retrieves the error message, if any, to be displayed to the user.
     *
     * @return The error message as a string.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message to be displayed to the user in case of an error.
     *
     * @param errorMessage The error message as a string.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
