package use_case.TodoListUseCases.CompleteTaskUseCase;

import entity.Task;
import entity.Leaderboard;
import entity.User;
import repositories.LeaderboardRepository;
import repositories.UserRepository;
import repositories.TaskRepository;
import use_case.TodoListUseCases.TaskData;

import java.io.IOException;
import java.util.Map;

/**
 * Use case for toggling the completion status of a task.
 */
public class CompleteTaskUseCase implements CompleteTaskInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CompleteTaskOutputBoundary completeTaskOutputBoundary;
    private final LeaderboardRepository leaderboardRepository;

    public CompleteTaskUseCase(UserRepository userRepository, TaskRepository taskRepository, CompleteTaskOutputBoundary completeTaskOutputBoundary, LeaderboardRepository leaderboardRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
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

            // Get the task from the repository
            Task task = taskRepository.ReadFromCache(requestModel.getTaskId());
            if (task == null) {
                throw new RuntimeException("Task not found");
            }

            // Toggle the task completion status
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

            // Determine if task is for a course or user and save accordingly
            if (requestModel.getCourseName() != null) {
                taskRepository.WriteToCache(task, user.getUsername(), requestModel.getCourseName());
            } else {
                taskRepository.WriteToCache(task, user.getUsername());
            }

            TaskData taskData = new TaskData(
                    task.getId(),
                    task.getUsername(),
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
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }
}
