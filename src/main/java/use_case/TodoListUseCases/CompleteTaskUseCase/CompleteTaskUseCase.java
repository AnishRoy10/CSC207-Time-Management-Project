package use_case.TodoListUseCases.CompleteTaskUseCase;

import entity.Task;
import entity.TodoList;
import entity.Leaderboard;
import entity.User;
import repositories.LeaderboardRepository;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.time.LocalDateTime;
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
                userRepository.WriteToCache(user);

                if (task.isCompleted()) {
                    // Update all relevant leaderboards
                    updateLeaderboardScores(user.getUsername(), task.getCompletionDate());
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
            } else {
                throw new RuntimeException("Task not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }

    private void updateLeaderboardScores(String username, LocalDateTime completionDate) throws IOException {
        Map<String, Leaderboard> leaderboards = leaderboardRepository.readFromCache();

        // Update Daily Leaderboard
        Leaderboard dailyLeaderboard = leaderboards.get("Daily");
        if (dailyLeaderboard != null) {
            dailyLeaderboard.addScore(username, 1); // Assuming 1 point per task
        }

        // Update Monthly Leaderboard
        Leaderboard monthlyLeaderboard = leaderboards.get("Monthly");
        if (monthlyLeaderboard != null) {
            monthlyLeaderboard.addScore(username, 1); // Assuming 1 point per task
        }

        // Update All-Time Leaderboard
        Leaderboard allTimeLeaderboard = leaderboards.get("AllTime");
        if (allTimeLeaderboard != null) {
            allTimeLeaderboard.addScore(username, 1); // Assuming 1 point per task
        }

        leaderboardRepository.writeToCache(leaderboards);
    }
}
