package use_case.RemoveTaskUseCase;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;
import use_case.TaskData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Use case for removing a task.
 */
public class RemoveTaskUseCase implements RemoveTaskInputBoundary {
    private final TodoListRepository todoListRepository;
    private final RemoveTaskOutputBoundary removeTaskOutputBoundary;

    public RemoveTaskUseCase(TodoListRepository todoListRepository, RemoveTaskOutputBoundary removeTaskOutputBoundary) {
        this.todoListRepository = todoListRepository;
        this.removeTaskOutputBoundary = removeTaskOutputBoundary;
    }

    @Override
    public void execute(RemoveTaskRequestModel requestModel) {
        TodoList todoList = todoListRepository.loadTodoList();
        Optional<Task> taskOptional = todoList.getTasks().stream()
                .filter(task -> task.getId() == requestModel.getTaskId())
                .findFirst();

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            todoList.removeTask(task);
            todoListRepository.saveTodoList(todoList);

            List<TaskData> tasks = todoList.getTasks().stream()
                    .map(t -> new TaskData(
                            t.getId(),
                            t.getTitle(),
                            t.getDescription(),
                            t.getStartDate(),
                            t.getDeadline(),
                            t.isCompleted(),
                            t.getCourse(),
                            t.getCompletionDate()
                    ))
                    .collect(Collectors.toList());

            RemoveTaskResponseModel responseModel = new RemoveTaskResponseModel(tasks, task.getId());
            removeTaskOutputBoundary.present(responseModel);
        } else {
            throw new RuntimeException("Task not found");
        }
    }
}
