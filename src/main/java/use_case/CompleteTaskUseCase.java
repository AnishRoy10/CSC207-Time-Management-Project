package use_case;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for completing a task in the to-do list.
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
        Task task = todoList.getTasks().stream()
                .filter(t -> t.getId() == requestModel.getTaskId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.completeTask();
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

        CompleteTaskResponseModel responseModel = new CompleteTaskResponseModel(tasks);
        completeTaskOutputBoundary.present(responseModel);
    }
}
