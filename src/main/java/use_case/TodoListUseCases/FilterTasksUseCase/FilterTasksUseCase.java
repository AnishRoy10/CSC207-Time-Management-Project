package use_case.TodoListUseCases.FilterTasksUseCase;

import entity.Task;
import entity.User;
import repositories.UserRepository;
import repositories.TaskRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for filtering tasks in the to-do list.
 */
public class FilterTasksUseCase implements FilterTasksInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final FilterTasksOutputBoundary filterTasksOutputBoundary;

    public FilterTasksUseCase(UserRepository userRepository, TaskRepository taskRepository, FilterTasksOutputBoundary filterTasksOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.filterTasksOutputBoundary = filterTasksOutputBoundary;
    }

    @Override
    public void execute(FilterTasksRequestModel requestModel) {
        try {
            // Load the user's tasks
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            List<Task> tasks;
            if (requestModel.getCourseName() != null) {
                tasks = taskRepository.getAllTasks(user.getUsername(), requestModel.getCourseName());
            } else {
                tasks = taskRepository.getAllTasks(user.getUsername());
            }

            List<Task> filteredTasks = tasks.stream()
                    .filter(task -> !(requestModel.isHideCompleted() && task.isCompleted()))
                    .collect(Collectors.toList());

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

            FilterTasksResponseModel responseModel = new FilterTasksResponseModel(taskDataList);
            filterTasksOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error", e);
        }
    }
}
