package use_case.TodoListUseCases.AddTaskUseCase;

import entity.Task;
import entity.User;
import repositories.TaskRepository;
import repositories.UserRepository;
import use_case.TaskData;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for adding a task to the to-do list.
 */
public class AddTaskUseCase implements AddTaskInputBoundary {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final AddTaskOutputBoundary addTaskOutputBoundary;

    public AddTaskUseCase(UserRepository userRepository, TaskRepository taskRepository, AddTaskOutputBoundary addTaskOutputBoundary) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.addTaskOutputBoundary = addTaskOutputBoundary;
    }

    @Override
    public void execute(AddTaskRequestModel requestModel) {
        try {
            Task newTask = new Task(requestModel.getUsername(), requestModel.getTitle(), requestModel.getDescription(), requestModel.getStartDate(), requestModel.getDeadline(), requestModel.getCourse());

            if (requestModel.getCourseName() != null) {
                // Save the task in the course's to-do list
                taskRepository.WriteToCache(newTask, requestModel.getUsername(), requestModel.getCourseName());
            } else {
                // Save the task in the user's personal to-do list
                taskRepository.WriteToCache(newTask, requestModel.getUsername());
            }

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

            AddTaskResponseModel responseModel = new AddTaskResponseModel(taskDataList, newTask.getTitle());
            addTaskOutputBoundary.present(responseModel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add task: " + e.getMessage());
        }
    }
}
