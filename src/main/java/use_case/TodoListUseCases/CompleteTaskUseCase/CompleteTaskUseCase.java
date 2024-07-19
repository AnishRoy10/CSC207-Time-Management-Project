package use_case.TodoListUseCases.CompleteTaskUseCase;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;
import use_case.TaskData;

import java.util.Optional;

/**
 * Use case for toggling the completion status of a task.
 */
public class CompleteTaskUseCase implements CompleteTaskInputBoundary {
    private final TodoListRepository todoListRepository;
    private final CompleteTaskOutputBoundary completeTaskOutputBoundary;

    public CompleteTaskUseCase(TodoListRepository todoListRepository, CompleteTaskOutputBoundary completeTaskOutputBoundary) {
        this.todoListRepository = todoListRepository;
        this.completeTaskOutputBoundary = completeTaskOutputBoundary;
    }

    @Override
    public void execute(CompleteTaskRequestModel requestModel) {
        TodoList todoList = todoListRepository.loadTodoList();
        Optional<Task> taskOptional = todoList.getTasks().stream()
                .filter(task -> task.getId() == requestModel.getTaskId())
                .findFirst();

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.toggleTaskCompletion();  // Use the new toggle method
            todoListRepository.saveTodoList(todoList);

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
    }
}
