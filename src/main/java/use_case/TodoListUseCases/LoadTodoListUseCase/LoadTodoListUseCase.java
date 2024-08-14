package use_case.TodoListUseCases.LoadTodoListUseCase;

import entity.Task;
import entity.User;
import repositories.UserRepository;
import repositories.TaskRepository;
import use_case.TodoListUseCases.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code LoadTodoListUseCase} class implements the use case for loading a user's to-do list.
 * It retrieves tasks from the repository and presents them to the output boundary.
 */
public class LoadTodoListUseCase implements LoadTodoListInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final LoadTodoListOutputBoundary loadTodoListOutputBoundary;

    /**
     * Constructs a new {@code LoadTodoListUseCase} with the specified repositories and output boundary.
     *
     * @param userRepository            The repository for user data.
     * @param taskRepository            The repository for task data.
     * @param loadTodoListOutputBoundary The output boundary for presenting the result of the use case.
     */
    public LoadTodoListUseCase(UserRepository userRepository, TaskRepository taskRepository, LoadTodoListOutputBoundary loadTodoListOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.loadTodoListOutputBoundary = loadTodoListOutputBoundary;
    }

    /**
     * Executes the load to-do list use case by retrieving tasks for the specified user and course (if any),
     * and presenting them to the output boundary.
     *
     * @param requestModel The {@link LoadTodoListRequestModel} containing the data needed to load the to-do list.
     */
    @Override
    public void execute(LoadTodoListRequestModel requestModel) {
        try {
            // Load the user
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Get the user's tasks from the repository
            List<Task> tasks;
            if (requestModel.getCourseName() != null) {
                // Load tasks for the specific course
                tasks = taskRepository.getAllTasks(user.getUsername(), requestModel.getCourseName());
                System.out.println("this part is getting touched.");
            } else {
                // Load personal tasks
                tasks = taskRepository.getAllTasks(user.getUsername());
            }

            // Convert tasks to TaskData for response
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

            // Present the response model
            loadTodoListOutputBoundary.present(new LoadTodoListResponseModel(taskDataList));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
