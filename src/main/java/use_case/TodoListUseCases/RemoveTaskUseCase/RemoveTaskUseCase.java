package use_case.TodoListUseCases.RemoveTaskUseCase;

import entity.Task;
import entity.User;
import repositories.UserRepository;
import repositories.TaskRepository;
import use_case.TodoListUseCases.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code RemoveTaskUseCase} class implements the use case for removing a task from the to-do list.
 * It handles the logic for removing a task, updating the repository, and presenting the updated task list.
 */
public class RemoveTaskUseCase implements RemoveTaskInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RemoveTaskOutputBoundary removeTaskOutputBoundary;

    /**
     * Constructs a new {@code RemoveTaskUseCase} with the specified repositories and output boundary.
     *
     * @param userRepository           The repository for user data.
     * @param taskRepository           The repository for task data.
     * @param removeTaskOutputBoundary The output boundary for presenting the result of the use case.
     */
    public RemoveTaskUseCase(UserRepository userRepository, TaskRepository taskRepository, RemoveTaskOutputBoundary removeTaskOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.removeTaskOutputBoundary = removeTaskOutputBoundary;
    }

    /**
     * Executes the remove task use case by removing the specified task from the repository,
     * updating the user's or course's task list, and presenting the updated list to the output boundary.
     *
     * @param requestModel The {@link RemoveTaskRequestModel} containing the data needed to remove the task.
     */
    @Override
    public void execute(RemoveTaskRequestModel requestModel) {
        try {
            // Load the user
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Get the user's task
            Task task = taskRepository.ReadFromCache(requestModel.getTaskId());
            if (task == null) {
                throw new RuntimeException("Task not found");
            }

            // Remove the task from the repository
            taskRepository.deleteTask(task.getId());

            // Fetch updated tasks list for the user or course
            List<Task> tasks;
            if (requestModel.getCourseName() != null) {
                tasks = taskRepository.getAllTasks(user.getUsername(), requestModel.getCourseName());
            } else {
                tasks = taskRepository.getAllTasks(user.getUsername());
            }

            // Convert tasks to TaskData for response
            List<TaskData> taskDataList = tasks.stream()
                    .map(t -> new TaskData(
                            t.getId(),
                            t.getUsername(),
                            t.getTitle(),
                            t.getDescription(),
                            t.getStartDate(),
                            t.getDeadline(),
                            t.isCompleted(),
                            t.getCourse(),
                            t.getCompletionDate()
                    ))
                    .collect(Collectors.toList());

            // Present the response model
            RemoveTaskResponseModel responseModel = new RemoveTaskResponseModel(taskDataList, task.getId());
            removeTaskOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
