package use_case.TodoListUseCases.AddTaskUseCase;

import entity.Task;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TodoListUseCases.TaskData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code AddTaskUseCase} class implements the use case for adding a task to the to-do list.
 * It validates the input data, creates a new task, and interacts with the repository to save the task.
 */
public class AddTaskUseCase implements AddTaskInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final AddTaskOutputBoundary addTaskOutputBoundary;

    /**
     * Constructs a new {@code AddTaskUseCase} with the specified repositories and output boundary.
     *
     * @param userRepository         The repository for user data.
     * @param taskRepository         The repository for task data.
     * @param addTaskOutputBoundary  The output boundary for presenting the result of the use case.
     */
    public AddTaskUseCase(UserRepository userRepository, TaskRepository taskRepository, AddTaskOutputBoundary addTaskOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.addTaskOutputBoundary = addTaskOutputBoundary;
    }

    /**
     * Executes the add task use case by validating the input, creating a new task,
     * and saving it to the appropriate to-do list. If any required fields are missing,
     * it returns an error message.
     *
     * @param requestModel The {@link AddTaskRequestModel} containing the data needed to add a task.
     */
    @Override
    public void execute(AddTaskRequestModel requestModel) {
        try {
            // Validate the required fields
            if (requestModel.getTitle() == null || requestModel.getTitle().isEmpty() ||
                    requestModel.getDescription() == null || requestModel.getDescription().isEmpty() ||
                    requestModel.getStartDate() == null ||
                    requestModel.getDeadline() == null ||
                    requestModel.getCourse() == null || requestModel.getCourse().isEmpty()) {

                // Return an error response with a message
                String errorMessage = "All fields (title, description, start date, deadline, course) must be provided.";
                addTaskOutputBoundary.presentError(errorMessage);
                return;
            }

            // Create a new Task entity with the validated data
            Task newTask = new Task(
                    requestModel.getUsername(),
                    requestModel.getTitle(),
                    requestModel.getDescription(),
                    requestModel.getStartDate(),
                    requestModel.getDeadline(),
                    requestModel.getCourse()
            );

            // Save the task in the appropriate to-do list (personal or course-specific)
            if (requestModel.getCourseName() != null) {
                taskRepository.WriteToCache(newTask, requestModel.getUsername(), requestModel.getCourseName());
            } else {
                taskRepository.WriteToCache(newTask, requestModel.getUsername());
            }

            // Retrieve all tasks after addition and map them to TaskData
            List<Task> tasks = requestModel.getCourseName() != null ?
                    taskRepository.getAllTasks(requestModel.getUsername(), requestModel.getCourseName()) :
                    taskRepository.getAllTasks(requestModel.getUsername());

            List<TaskData> taskDataList = tasks.stream()
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
            AddTaskResponseModel responseModel = new AddTaskResponseModel(taskDataList, newTask.getTitle());
            addTaskOutputBoundary.present(responseModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add task: " + e.getMessage());
        }
    }
}
