package use_case.TodoListUseCases.SortTasksUseCase;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;
import use_case.TaskData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for sorting tasks in the to-do list.
 */
public class SortTasksUseCase implements SortTasksInputBoundary {
    private final TodoListRepository todoListRepository;
    private final SortTasksOutputBoundary sortTasksOutputBoundary;

    public SortTasksUseCase(TodoListRepository todoListRepository, SortTasksOutputBoundary sortTasksOutputBoundary) {
        this.todoListRepository = todoListRepository;
        this.sortTasksOutputBoundary = sortTasksOutputBoundary;
    }

    @Override
    public void execute(SortTasksRequestModel requestModel) {
        TodoList todoList = todoListRepository.loadTodoList();

        Comparator<Task> comparator;

        switch (requestModel.getCriteria().toLowerCase()) {
            case "title":
                comparator = Comparator.comparing(Task::getTitle);
                break;
            case "deadline":
                comparator = Comparator.comparing(Task::getDeadline);
                break;
            case "course":
                comparator = Comparator.comparing(Task::getCourse);
                break;
            case "completion":
                comparator = Comparator.comparing(Task::isCompleted);
                break;
            default:
                throw new IllegalArgumentException("Unknown sorting criteria: " + requestModel.getCriteria());
        }

        if (!requestModel.isAscending()) {
            comparator = comparator.reversed();
        }

        List<TaskData> sortedTasks = todoList.getTasks().stream()
                .sorted(comparator)
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

        SortTasksResponseModel responseModel = new SortTasksResponseModel(sortedTasks);
        sortTasksOutputBoundary.present(responseModel);
    }
}
