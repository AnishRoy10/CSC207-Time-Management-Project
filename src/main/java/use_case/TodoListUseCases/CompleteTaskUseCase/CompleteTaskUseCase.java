package use_case.TodoListUseCases.CompleteTaskUseCase;

import entity.Task;
import entity.TodoList;
import entity.Leaderboard;
import entity.User;
import repositories.LeaderboardRepository;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Use case for toggling the completion status of a task.
 */
public class CompleteTaskUseCase implements CompleteTaskInputBoundary {
    private final UserRepository userRepository;
    private final CompleteTaskOutputBoundary completeTaskOutputBoundary;
    private final LeaderboardRepository leaderboardRepository;

    public CompleteTaskUseCase(UserRepository userRepository, CompleteTaskOutputBoundary completeTaskOutputBoundary, LeaderboardRepository leaderboardRepository) {
        this.userRepository = userRepository;
        this.completeTaskOutputBoundary = completeTaskOutputBoundary;
        this.leaderboardRepository = leaderboardRepository;
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

                if (task.isCompleted() && !task.isPointsAwarded()) {
                    // Update all relevant leaderboards
                    Map<String, Leaderboard> leaderboards = leaderboardRepository.readFromCache();
                    for (Leaderboard leaderboard : leaderboards.values()) {
                        leaderboard.taskCompleted(user.getUsername(), 500);
                    }
                    leaderboardRepository.writeToCache(leaderboards);

                    // Set pointsAwarded to true
                    task.setPointsAwarded(true);
                }

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
                userRepository.WriteToCache(user);
            } else {
                throw new RuntimeException("Task not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }
}