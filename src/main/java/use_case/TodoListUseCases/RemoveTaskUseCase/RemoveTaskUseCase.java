package use_case.TodoListUseCases.RemoveTaskUseCase;

import entity.Task;
import entity.User;
import repositories.UserRepository;
import repositories.TaskRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for removing a task.
 */
public class RemoveTaskUseCase implements RemoveTaskInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final RemoveTaskOutputBoundary removeTaskOutputBoundary;

    public RemoveTaskUseCase(UserRepository userRepository, TaskRepository taskRepository, RemoveTaskOutputBoundary removeTaskOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.removeTaskOutputBoundary = removeTaskOutputBoundary;
    }

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

            RemoveTaskResponseModel responseModel = new RemoveTaskResponseModel(taskDataList, task.getId());
            removeTaskOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
