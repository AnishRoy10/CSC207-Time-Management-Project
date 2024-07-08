package use_case;

import data_access.TodoListDataAccessObject;
import entity.Task;
import entity.TodoList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for adding a task to the to-do list.
 */
public class AddTaskUseCase implements TodoListInputBoundary {
    private final TodoListDataAccessObject todoListDataAccessObject;
    private final TodoListOutputBoundary todoListOutputBoundary;

    /**
     * Constructs an AddTaskUseCase with the specified data access object and output boundary.
     *
     * @param todoListDataAccessObject The data access object for the to-do list
     * @param todoListOutputBoundary   The output boundary for presenting tasks
     */
    public AddTaskUseCase(TodoListDataAccessObject todoListDataAccessObject, TodoListOutputBoundary todoListOutputBoundary) {
        this.todoListDataAccessObject = todoListDataAccessObject;
        this.todoListOutputBoundary = todoListOutputBoundary;
    }

    @Override
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        Task task = new Task(title, description, startDate, deadline, course);
        try {
            TodoList todoList = todoListDataAccessObject.loadTodoList();
            todoList.addTask(task);
            todoListDataAccessObject.saveTodoList(todoList);
            loadTasks();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadTasks() {
        try {
            TodoList todoList = todoListDataAccessObject.loadTodoList();
            List<TaskResponseModel> taskResponseModels = todoList.getTasks().stream()
                    .map(TaskResponseModel::new)
                    .collect(Collectors.toList());
            todoListOutputBoundary.presentTasks(taskResponseModels);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
