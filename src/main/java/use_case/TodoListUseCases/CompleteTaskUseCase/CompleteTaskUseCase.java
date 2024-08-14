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
 * The {@code CompleteTaskUseCase} class implements the use case for toggling the completion status of a task.
 * It updates the task's status, awards points to the user if the task is completed for the first time,
 * and saves the updated task data to the repository.
 */
public class CompleteTaskUseCase implements CompleteTaskInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CompleteTaskOutputBoundary completeTaskOutputBoundary;
    private final LeaderboardRepository leaderboardRepository;

    /**
     * Constructs a new {@code CompleteTaskUseCase} with the specified repositories and output boundary.
     *
     * @param userRepository            The repository for user data.
     * @param taskRepository            The repository for task data.
     * @param completeTaskOutputBoundary The output boundary for presenting the result of the use case.
     * @param leaderboardRepository     The repository for leaderboard data.
     */
    public CompleteTaskUseCase(UserRepository userRepository, TaskRepository taskRepository, CompleteTaskOutputBoundary completeTaskOutputBoundary, LeaderboardRepository leaderboardRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.completeTaskOutputBoundary = completeTaskOutputBoundary;
        this.leaderboardRepository = leaderboardRepository;
    }

    /**
     * Executes the complete task use case by toggling the completion status of the task,
     * awarding points if applicable, and saving the updated task data.
     *
     * @param requestModel The {@link CompleteTaskRequestModel} containing the data needed to complete a task.
     */
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

            // Prepare task data for the response model
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

            // Prepare and present the response model
            CompleteTaskResponseModel responseModel = new CompleteTaskResponseModel(taskData, task.getId());
            completeTaskOutputBoundary.present(responseModel);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }
}
