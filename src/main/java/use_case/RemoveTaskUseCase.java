package use_case;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for removing a task from the to-do list.
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
        Task task = todoList.getTasks().stream()
                .filter(t -> t.getId() == requestModel.getTaskId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));

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

        RemoveTaskResponseModel responseModel = new RemoveTaskResponseModel(tasks);
        removeTaskOutputBoundary.present(responseModel);
    }
}
