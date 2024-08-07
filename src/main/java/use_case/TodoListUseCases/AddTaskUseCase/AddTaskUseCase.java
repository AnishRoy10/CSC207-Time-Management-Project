package use_case.TodoListUseCases.AddTaskUseCase;

import entity.Task;
import entity.User;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for adding a task to the to-do list.
 */
public class AddTaskUseCase implements AddTaskInputBoundary {
    private final UserRepository userRepository;
    private final AddTaskOutputBoundary addTaskOutputBoundary;

    public AddTaskUseCase(UserRepository userRepository, AddTaskOutputBoundary addTaskOutputBoundary) {
        this.userRepository = userRepository;
        this.addTaskOutputBoundary = addTaskOutputBoundary;
    }

    @Override
    public void execute(AddTaskRequestModel requestModel) {
        try {
            // Load the user
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Add the task to the user's to-do list
            Task newTask = new Task(
                    requestModel.getTitle(),
                    requestModel.getDescription(),
                    requestModel.getStartDate(),
                    requestModel.getDeadline(),
                    requestModel.getCourse()
            );

            user.getTodoList().addTask(newTask);
            userRepository.WriteToCache(user);

            List<TaskData> tasks = user.getTodoList().getTasks().stream()
                    .map(task -> new TaskData(
                            task.getId(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getStartDate(),
                            task.getDeadline(),
                            task.isCompleted(),
                            task.getCourse(),
                            task.getCompletionDate()
                    ))
                    .collect(Collectors.toList());

            AddTaskResponseModel responseModel = new AddTaskResponseModel(tasks, newTask.getTitle());
            addTaskOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
