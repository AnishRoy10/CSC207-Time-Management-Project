package interface_adapter.controller;

import entity.Task;
import use_case.AddTaskUseCase;
import data_access.TodoListDataAccessObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The TodoListController class acts as a mediator between the UI and the use case.
 */
public class TodoListController {
    private final AddTaskUseCase addTaskUseCase;
    private final TodoListDataAccessObject todoListDataAccessObject;

    /**
     * Constructs a TodoListController with the specified AddTaskUseCase and TodoListDataAccessObject.
     *
     * @param addTaskUseCase        The use case for adding tasks
     * @param todoListDataAccessObject The data access object for task persistence
     */
    public TodoListController(AddTaskUseCase addTaskUseCase, TodoListDataAccessObject todoListDataAccessObject) {
        this.addTaskUseCase = addTaskUseCase;
        this.todoListDataAccessObject = todoListDataAccessObject;
    }

    /**
     * Adds a task using the use case and saves it to the persistence layer.
     *
     * @param title       The title of the task
     * @param description The description of the task
     * @param startDate   The start date and time of the task
     * @param deadline    The deadline date and time for the task
     * @param course      The course associated with the task
     */
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        addTaskUseCase.addTask(title, description, startDate, deadline, course);
        saveTasks();
    }

    /**
     * Loads tasks from the persistence layer.
     *
     * @return The list of tasks
     */
    public List<Task> loadTasks() {
        try {
            return todoListDataAccessObject.loadTasks();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves tasks to the persistence layer.
     */
    private void saveTasks() {
        try {
            todoListDataAccessObject.saveTasks(addTaskUseCase.getTodoList().getTasks());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
