package use_case;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for filtering tasks in the to-do list.
 */
public class FilterTasksUseCase implements FilterTasksInputBoundary {
    private final TodoListRepository todoListRepository;
    private final FilterTasksOutputBoundary filterTasksOutputBoundary;

    public FilterTasksUseCase(TodoListRepository todoListRepository, FilterTasksOutputBoundary filterTasksOutputBoundary) {
        this.todoListRepository = todoListRepository;
        this.filterTasksOutputBoundary = filterTasksOutputBoundary;
    }

    @Override
    public void execute(FilterTasksRequestModel requestModel) {
        TodoList todoList = todoListRepository.loadTodoList();

        List<Task> filteredTasks = todoList.getTasks().stream()
                .filter(task -> !(requestModel.isHideCompleted() && task.isCompleted()))
                .collect(Collectors.toList());

        List<TaskData> tasks = filteredTasks.stream()
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

        FilterTasksResponseModel responseModel = new FilterTasksResponseModel(tasks);
        filterTasksOutputBoundary.present(responseModel);
    }
}
