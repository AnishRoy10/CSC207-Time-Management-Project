package use_case.TodoListUseCases.RemoveTaskUseCase;

import entity.Task;
import entity.User;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Use case for removing a task.
 */
public class RemoveTaskUseCase implements RemoveTaskInputBoundary {
    private final UserRepository userRepository;
    private final RemoveTaskOutputBoundary removeTaskOutputBoundary;

    public RemoveTaskUseCase(UserRepository userRepository, RemoveTaskOutputBoundary removeTaskOutputBoundary) {
        this.userRepository = userRepository;
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

            // Get the user's to-do list
            Optional<Task> taskOptional = user.getTodoList().getTasks().stream()
                    .filter(task -> task.getId() == requestModel.getTaskId())
                    .findFirst();

            if (taskOptional.isPresent()) {
                Task task = taskOptional.get();
                user.getTodoList().removeTask(task);
                userRepository.WriteToCache(user);

                List<TaskData> tasks = user.getTodoList().getTasks().stream()
                        .map(t -> new TaskData(
                                t.getId(),
                                t.getTitle(),
                                t.getDescription(),
                                t.getStartDate(),
                                t.getDeadline(),
                                t.isCompleted(),
                                t.getCourse(),
                                t.getCompletionDate()
                        ))
                        .collect(Collectors.toList());

                RemoveTaskResponseModel responseModel = new RemoveTaskResponseModel(tasks, task.getId());
                removeTaskOutputBoundary.present(responseModel);
            } else {
                throw new RuntimeException("Task not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }
}
