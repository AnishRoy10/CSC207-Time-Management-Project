package use_case.TodoListUseCases.CompleteTaskUseCase;

import entity.Task;
import entity.TodoList;
import entity.User;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.Optional;

/**
 * Use case for toggling the completion status of a task.
 */
public class CompleteTaskUseCase implements CompleteTaskInputBoundary {
    private final UserRepository userRepository;
    private final CompleteTaskOutputBoundary completeTaskOutputBoundary;

    public CompleteTaskUseCase(UserRepository userRepository, CompleteTaskOutputBoundary completeTaskOutputBoundary) {
        this.userRepository = userRepository;
        this.completeTaskOutputBoundary = completeTaskOutputBoundary;
    }

    @Override
    public void execute(CompleteTaskRequestModel requestModel) {
        try {
            // Load the user
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Get the user's to-do list
            TodoList todoList = user.getTodoList();
            Optional<Task> taskOptional = todoList.getTasks().stream()
                    .filter(task -> task.getId().equals(requestModel.getTaskId()))
                    .findFirst();

            if (taskOptional.isPresent()) {
                Task task = taskOptional.get();
                task.toggleTaskCompletion();
                userRepository.WriteToCache(user);

                TaskData taskData = new TaskData(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStartDate(),
                        task.getDeadline(),
                        task.isCompleted(),
                        task.getCourse(),
                        task.getCompletionDate()
                );

                CompleteTaskResponseModel responseModel = new CompleteTaskResponseModel(taskData, task.getId());
                completeTaskOutputBoundary.present(responseModel);
            } else {
                throw new RuntimeException("Task not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }
}
