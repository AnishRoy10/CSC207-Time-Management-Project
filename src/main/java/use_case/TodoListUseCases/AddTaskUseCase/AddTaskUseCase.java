package use_case.TodoListUseCases.AddTaskUseCase;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;
import use_case.TaskData;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for adding a task to the to-do list.
 */
public class AddTaskUseCase implements AddTaskInputBoundary {
    private final TodoListRepository todoListRepository;
    private final AddTaskOutputBoundary addTaskOutputBoundary;

    public AddTaskUseCase(TodoListRepository todoListRepository, AddTaskOutputBoundary addTaskOutputBoundary) {
        this.todoListRepository = todoListRepository;
        this.addTaskOutputBoundary = addTaskOutputBoundary;
    }

    @Override
    public void execute(AddTaskRequestModel requestModel) {
        TodoList todoList = todoListRepository.loadTodoList();

        Task newTask = new Task(
                requestModel.getTitle(),
                requestModel.getDescription(),
                requestModel.getStartDate(),
                requestModel.getDeadline(),
                requestModel.getCourse()
        );

        todoList.addTask(newTask);
        todoListRepository.saveTodoList(todoList);

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

        AddTaskResponseModel responseModel = new AddTaskResponseModel(tasks, newTask.getTitle());
        addTaskOutputBoundary.present(responseModel);
    }
}
