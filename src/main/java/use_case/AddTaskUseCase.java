package use_case;

import data_access.TodoListDataAccessObject;
import entity.Task;
import entity.TodoList;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case interactor for adding tasks to the to-do list.
 * It implements the input boundary and interacts with the data access object and output boundary.
 */
public class AddTaskUseCase implements TodoListInputBoundary {
    private final TodoList todoList;
    private final TodoListDataAccessObject todoListDataAccessObject;
    private final TodoListOutputBoundary outputBoundary;

    /**
     * Constructs an AddTaskUseCase with the specified to-do list, data access object, and output boundary.
     *
     * @param todoList                The to-do list entity
     * @param todoListDataAccessObject The data access object for persistence
     * @param outputBoundary          The output boundary for presenting tasks
     */
    public AddTaskUseCase(TodoList todoList, TodoListDataAccessObject todoListDataAccessObject, TodoListOutputBoundary outputBoundary) {
        this.todoList = todoList;
        this.todoListDataAccessObject = todoListDataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Adds a task to the to-do list.
     *
     * @param title       The title of the task
     * @param description The description of the task
     * @param startDate   The start date and time of the task
     * @param deadline    The deadline date and time for the task
     * @param course      The course associated with the task
     */
    @Override
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        Task task = new Task(title, description, startDate, deadline, course);
        todoList.addTask(task);
        saveTodoList();
    }

    /**
     * Loads tasks from the persistence layer and converts them to response models.
     *
     * @return The list of task response models
     */
    @Override
    public List<TaskResponseModel> loadTasks() {
        try {
            TodoList loadedTodoList = todoListDataAccessObject.loadTodoList();
            List<TaskResponseModel> taskResponseModels = loadedTodoList.getTasks().stream()
                    .map(task -> {
                        TaskResponseModel responseModel = new TaskResponseModel();
                        responseModel.setTitle(task.getTitle());
                        responseModel.setDescription(task.getDescription());
                        responseModel.setStartDate(task.getStartDate());
                        responseModel.setDeadline(task.getDeadline());
                        responseModel.setCourse(task.getCourse());
                        responseModel.setCompleted(task.isCompleted());
                        responseModel.setCompletionDate(task.getCompletionDate());
                        return responseModel;
                    })
                    .collect(Collectors.toList());
            outputBoundary.presentTasks(taskResponseModels);
            return taskResponseModels;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves the to-do list to the persistence layer.
     */
    private void saveTodoList() {
        try {
            todoListDataAccessObject.saveTodoList(todoList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
