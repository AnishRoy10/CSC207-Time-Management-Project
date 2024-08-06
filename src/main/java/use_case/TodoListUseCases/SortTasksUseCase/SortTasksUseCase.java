package use_case.TodoListUseCases.SortTasksUseCase;

import entity.Task;
import entity.User;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for sorting tasks in the to-do list.
 */
public class SortTasksUseCase implements SortTasksInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final SortTasksOutputBoundary sortTasksOutputBoundary;

    public SortTasksUseCase(UserRepository userRepository, TaskRepository taskRepository, SortTasksOutputBoundary sortTasksOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.sortTasksOutputBoundary = sortTasksOutputBoundary;
    }

    @Override
    public void execute(SortTasksRequestModel requestModel) {
        try {
            // Load the user
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Get the user's tasks from the repository
            List<Task> tasks = taskRepository.getAllTasks(user.getUsername());

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

            if (!requestModel.isAscending()) {
                comparator = comparator.reversed();
            }

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

            SortTasksResponseModel responseModel = new SortTasksResponseModel(sortedTasks);
            sortTasksOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
