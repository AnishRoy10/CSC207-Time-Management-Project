package use_case.TodoListUseCases.FilterTasksUseCase;

import entity.Task;
import entity.User;
import repositories.UserRepository;
import repositories.TaskRepository;
import use_case.TodoListUseCases.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code FilterTasksUseCase} class implements the use case for filtering tasks in the to-do list.
 * It filters tasks based on whether completed tasks should be hidden and presents the filtered tasks to the output boundary.
 */
public class FilterTasksUseCase implements FilterTasksInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final FilterTasksOutputBoundary filterTasksOutputBoundary;

    /**
     * Constructs a new {@code FilterTasksUseCase} with the specified repositories and output boundary.
     *
     * @param userRepository           The repository for user data.
     * @param taskRepository           The repository for task data.
     * @param filterTasksOutputBoundary The output boundary for presenting the result of the use case.
     */
    public FilterTasksUseCase(UserRepository userRepository, TaskRepository taskRepository, FilterTasksOutputBoundary filterTasksOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.filterTasksOutputBoundary = filterTasksOutputBoundary;
    }

    /**
     * Executes the filter tasks use case by filtering tasks based on the specified criteria
     * and presenting the filtered tasks to the output boundary.
     *
     * @param requestModel The {@link FilterTasksRequestModel} containing the data needed to filter tasks.
     */
    @Override
    public void execute(FilterTasksRequestModel requestModel) {
        try {
            // Load the user's tasks
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Retrieve tasks based on course or user
            List<Task> tasks;
            if (requestModel.getCourseName() != null) {
                tasks = taskRepository.getAllTasks(user.getUsername(), requestModel.getCourseName());
            } else {
                tasks = taskRepository.getAllTasks(user.getUsername());
            }

            // Filter tasks based on completion status
            List<Task> filteredTasks = tasks.stream()
                    .filter(task -> !(requestModel.isHideCompleted() && task.isCompleted()))
                    .collect(Collectors.toList());

            // Convert tasks to TaskData for response
            List<TaskData> taskDataList = filteredTasks.stream()
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

            // Prepare and present the response model
            FilterTasksResponseModel responseModel = new FilterTasksResponseModel(taskDataList);
            filterTasksOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
