package use_case.TodoListUseCases.SortTasksUseCase;

import entity.Task;
import entity.User;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TodoListUseCases.TaskData;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code SortTasksUseCase} class implements the use case for sorting tasks in the to-do list.
 * It handles the logic for sorting tasks based on various criteria, updating the repository, and presenting the sorted task list.
 */
public class SortTasksUseCase implements SortTasksInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final SortTasksOutputBoundary sortTasksOutputBoundary;

    /**
     * Constructs a new {@code SortTasksUseCase} with the specified repositories and output boundary.
     *
     * @param userRepository         The repository for user data.
     * @param taskRepository         The repository for task data.
     * @param sortTasksOutputBoundary The output boundary for presenting the result of the use case.
     */
    public SortTasksUseCase(UserRepository userRepository, TaskRepository taskRepository, SortTasksOutputBoundary sortTasksOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.sortTasksOutputBoundary = sortTasksOutputBoundary;
    }

    /**
     * Executes the sort tasks use case by sorting the specified user's tasks based on the given criteria
     * and presenting the sorted list to the output boundary.
     *
     * @param requestModel The {@link SortTasksRequestModel} containing the data needed to sort the tasks.
     */
    @Override
    public void execute(SortTasksRequestModel requestModel) {
        try {
            // Load the user
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Get the user's tasks from the repository
            List<Task> tasks;
            if (requestModel.getCourseName() != null) {
                tasks = taskRepository.getAllTasks(user.getUsername(), requestModel.getCourseName());
            } else {
                tasks = taskRepository.getAllTasks(user.getUsername());
            }

            // Determine the comparator based on the sorting criteria
            Comparator<Task> comparator;
            switch (requestModel.getCriteria().toLowerCase()) {
                case "title":
                    comparator = Comparator.comparing(Task::getTitle);
                    break;
                case "deadline":
                    comparator = Comparator.comparing(Task::getDeadline);
                    break;
                case "course":
                    comparator = Comparator.comparing(Task::getCourse);
                    break;
                case "completion":
                    comparator = Comparator.comparing(Task::isCompleted).reversed(); // Modified to sort by completion status
                    break;
                default:
                    throw new IllegalArgumentException("Unknown sorting criteria: " + requestModel.getCriteria());
            }

            // Reverse the order if not ascending
            if (!requestModel.isAscending()) {
                comparator = comparator.reversed();
            }

            // Sort the tasks and convert to TaskData for response
            List<TaskData> sortedTasks = tasks.stream()
                    .sorted(comparator)
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

            // Present the sorted tasks to the output boundary
            SortTasksResponseModel responseModel = new SortTasksResponseModel(sortedTasks);
            sortTasksOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
