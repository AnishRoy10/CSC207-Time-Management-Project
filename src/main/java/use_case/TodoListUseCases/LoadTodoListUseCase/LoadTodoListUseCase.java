package use_case.TodoListUseCases.LoadTodoListUseCase;

import entity.TodoList;
import entity.User;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LoadTodoListUseCase implements LoadTodoListInputBoundary {
    private final UserRepository userRepository;
    private final LoadTodoListOutputBoundary loadTodoListOutputBoundary;

    public LoadTodoListUseCase(UserRepository userRepository, LoadTodoListOutputBoundary loadTodoListOutputBoundary) {
        this.userRepository = userRepository;
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

            // Get the user's to-do list
            TodoList todoList = user.getTodoList();
            List<TaskData> tasks = todoList.getTasks().stream()
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
            loadTodoListOutputBoundary.present(new LoadTodoListResponseModel(tasks));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }
}
