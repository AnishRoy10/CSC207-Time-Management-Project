package use_case;

import entity.TodoList;
import repositories.TodoListRepository;

import java.util.List;
import java.util.stream.Collectors;

public class LoadTodoListUseCase implements LoadTodoListInputBoundary {
    private final TodoListRepository todoListRepository;
    private final LoadTodoListOutputBoundary loadTodoListOutputBoundary;

    public LoadTodoListUseCase(TodoListRepository todoListRepository, LoadTodoListOutputBoundary loadTodoListOutputBoundary) {
        this.todoListRepository = todoListRepository;
        this.loadTodoListOutputBoundary = loadTodoListOutputBoundary;
    }

    @Override
    public void execute(LoadTodoListRequestModel requestModel) {
        TodoList todoList = todoListRepository.loadTodoList();
        List<TaskData> tasks = todoList.getTasks().stream()
                .map(task -> new TaskData(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStartDate(),
                        task.getDeadline(),
                        task.isCompleted(),
                        task.getCourse(),
                        task.getCompletionDate()
                ))
                .collect(Collectors.toList());
        loadTodoListOutputBoundary.present(new LoadTodoListResponseModel(tasks));
    }
}
