package interface_adapter.presenter;

import use_case.TaskData;
import use_case.*;

import java.util.List;

/**
 * Presenter for the to-do list, implementing the output boundaries for adding, removing, and completing tasks.
 */
public class TodoListPresenter implements AddTaskOutputBoundary, RemoveTaskOutputBoundary, CompleteTaskOutputBoundary {

    /**
     * Presents the response model for adding a task.
     *
     * @param responseModel the response model containing the added task information
     */
    @Override
    public void present(AddTaskResponseModel responseModel) {
        // Print the task title
        System.out.println("Task added: " + responseModel.getTitle());

        // Print all tasks
        List<TaskData> tasks = responseModel.getTasks();
        tasks.forEach(task -> System.out.println("Task: " + task.getTitle()));
    }

    /**
     * Presents the response model for removing a task.
     *
     * @param responseModel the response model containing the removed task information
     */
    @Override
    public void present(RemoveTaskResponseModel responseModel) {
        // Print the task ID of the removed task
        System.out.println("Task removed with ID: " + responseModel.getTaskId());

        // Print all remaining tasks
        List<TaskData> tasks = responseModel.getTasks();
        tasks.forEach(task -> System.out.println("Task: " + task.getTitle()));
    }

    /**
     * Presents the response model for completing a task.
     *
     * @param responseModel the response model containing the completed task information
     */
    @Override
    public void present(CompleteTaskResponseModel responseModel) {
        // Print the task ID of the completed task
        System.out.println("Task completed with ID: " + responseModel.getTaskId());

        // Print the completed task
        TaskData taskData = responseModel.getTaskData();
        System.out.println("Completed Task: " + taskData.getTitle());
    }
}
