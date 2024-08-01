package use_case.TodoListUseCases.LoadTodoListUseCase;

import entity.Task;
import entity.User;
import repositories.UserRepository;
import repositories.TaskRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for loading the to-do list.
 */
public class LoadTodoListUseCase implements LoadTodoListInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final LoadTodoListOutputBoundary loadTodoListOutputBoundary;

    public LoadTodoListUseCase(UserRepository userRepository, TaskRepository taskRepository, LoadTodoListOutputBoundary loadTodoListOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.loadTodoListOutputBoundary = loadTodoListOutputBoundary;
    }

    @Override
    public void execute(LoadTodoListRequestModel requestModel) {
        try {
            // Load the user
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Get the user's tasks from the repository
            List<Task> tasks = taskRepository.getAllTasks(user.getUsername());
            List<TaskData> taskDataList = tasks.stream()
                    .map(task -> new TaskData(
                            task.getId(),
                            task.getUsername(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getStartDate(),
                            task.getDeadline(),
                            task.isCompleted(),
                            task.getCourse(),
                            task.getCompletionDate()
                    ))
                    .collect(Collectors.toList());

            loadTodoListOutputBoundary.present(new LoadTodoListResponseModel(taskDataList));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }
}
