package use_case.TodoListUseCases.FilterTasksUseCase;

import entity.Task;
import entity.TodoList;
import entity.User;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for filtering tasks in the to-do list.
 */
public class FilterTasksUseCase implements FilterTasksInputBoundary {
    private final UserRepository userRepository;
    private final FilterTasksOutputBoundary filterTasksOutputBoundary;

    public FilterTasksUseCase(UserRepository userRepository, FilterTasksOutputBoundary filterTasksOutputBoundary) {
        this.userRepository = userRepository;
        this.filterTasksOutputBoundary = filterTasksOutputBoundary;
    }

    @Override
    public void execute(FilterTasksRequestModel requestModel) {
        try {
            // Load the user's to-do list
            User user = userRepository.findByUsername(requestModel.getUsername());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            TodoList todoList = user.getTodoList();
            List<Task> filteredTasks = todoList.getTasks().stream()
                    .filter(task -> !(requestModel.isHideCompleted() && task.isCompleted()))
                    .collect(Collectors.toList());

            List<TaskData> tasks = filteredTasks.stream()
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

            FilterTasksResponseModel responseModel = new FilterTasksResponseModel(tasks);
            filterTasksOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }
}
