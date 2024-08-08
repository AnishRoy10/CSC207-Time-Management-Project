package interface_adapter.controller;

import use_case.TodoListUseCases.AddTaskUseCase.AddTaskInputBoundary;
import use_case.TodoListUseCases.AddTaskUseCase.AddTaskRequestModel;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskInputBoundary;
import use_case.TodoListUseCases.CompleteTaskUseCase.CompleteTaskRequestModel;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksInputBoundary;
import use_case.TodoListUseCases.FilterTasksUseCase.FilterTasksRequestModel;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListInputBoundary;
import use_case.TodoListUseCases.LoadTodoListUseCase.LoadTodoListRequestModel;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskInputBoundary;
import use_case.TodoListUseCases.RemoveTaskUseCase.RemoveTaskRequestModel;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksInputBoundary;
import use_case.TodoListUseCases.SortTasksUseCase.SortTasksRequestModel;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Controller for the to-do list, coordinating the use cases and handling user interactions.
 */
public class TodoListController {
    private final AddTaskInputBoundary addTaskUseCase;
    private final RemoveTaskInputBoundary removeTaskUseCase;
    private final CompleteTaskInputBoundary completeTaskUseCase;
    private final SortTasksInputBoundary sortTasksUseCase;
    private final FilterTasksInputBoundary filterTasksUseCase;
    private final LoadTodoListInputBoundary loadTodoListUseCase;

    /**
     * Constructs a TodoListController with the specified use cases.
     *
     * @param addTaskUseCase        the use case for adding tasks
     * @param removeTaskUseCase     the use case for removing tasks
     * @param completeTaskUseCase   the use case for completing tasks
     * @param sortTasksUseCase      the use case for sorting tasks
     * @param filterTasksUseCase    the use case for filtering tasks
     * @param loadTodoListUseCase   the use case for loading the to-do list
     */
    public TodoListController(AddTaskInputBoundary addTaskUseCase, RemoveTaskInputBoundary removeTaskUseCase,
                              CompleteTaskInputBoundary completeTaskUseCase, SortTasksInputBoundary sortTasksUseCase,
                              FilterTasksInputBoundary filterTasksUseCase, LoadTodoListInputBoundary loadTodoListUseCase) {
        this.addTaskUseCase = addTaskUseCase;
        this.removeTaskUseCase = removeTaskUseCase;
        this.completeTaskUseCase = completeTaskUseCase;
        this.sortTasksUseCase = sortTasksUseCase;
        this.filterTasksUseCase = filterTasksUseCase;
        this.loadTodoListUseCase = loadTodoListUseCase;
    }

    /**
     * Adds a task to the to-do list.
     *
     * @param title       the title of the task
     * @param description the description of the task
     * @param startDate   the start date of the task
     * @param deadline    the deadline of the task
     * @param course      the course associated with the task
     * @param username    the username of the user
     */
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course, String username) {
        AddTaskRequestModel requestModel = new AddTaskRequestModel(title, description, startDate, deadline, course, username);
        addTaskUseCase.execute(requestModel);
    }

    /**
     * Adds a task to the course-specific to-do list.
     *
     * @param title       the title of the task
     * @param description the description of the task
     * @param startDate   the start date of the task
     * @param deadline    the deadline of the task
     * @param course      the course associated with the task
     * @param username    the username of the user
     * @param courseName  the name of the course
     */
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course, String username, String courseName) {
        AddTaskRequestModel requestModel = new AddTaskRequestModel(title, description, startDate, deadline, course, username, courseName);
        addTaskUseCase.execute(requestModel);
    }

    /**
     * Removes a task from the to-do list.
     *
     * @param taskId   the ID of the task to be removed
     * @param username the username of the user
     */
    public void removeTask(UUID taskId, String username) {
        RemoveTaskRequestModel requestModel = new RemoveTaskRequestModel(taskId, username);
        removeTaskUseCase.execute(requestModel);
    }

    /**
     * Removes a task from the course-specific to-do list.
     *
     * @param taskId    the ID of the task to be removed
     * @param username  the username of the user
     * @param courseName the name of the course
     */
    public void removeTask(UUID taskId, String username, String courseName) {
        RemoveTaskRequestModel requestModel = new RemoveTaskRequestModel(taskId, username, courseName);
        removeTaskUseCase.execute(requestModel);
    }

    /**
     * Toggles the completion status of a task.
     *
     * @param taskId   the ID of the task to be toggled
     * @param username the username of the user
     */
    public void toggleTaskCompletion(UUID taskId, String username) {
        CompleteTaskRequestModel requestModel = new CompleteTaskRequestModel(taskId, username);
        completeTaskUseCase.execute(requestModel);
    }

    /**
     * Toggles the completion status of a course-specific task.
     *
     * @param taskId    the ID of the task to be toggled
     * @param username  the username of the user
     * @param courseName the name of the course
     */
    public void toggleTaskCompletion(UUID taskId, String username, String courseName) {
        CompleteTaskRequestModel requestModel = new CompleteTaskRequestModel(taskId, username, courseName);
        completeTaskUseCase.execute(requestModel);
    }

    /**
     * Sorts the tasks in the to-do list.
     *
     * @param criterion the sorting criterion
     * @param ascending whether the sorting should be in ascending order
     * @param username  the username of the user
     */
    public void sortTasks(String criterion, boolean ascending, String username) {
        SortTasksRequestModel requestModel = new SortTasksRequestModel(criterion, ascending, username);
        sortTasksUseCase.execute(requestModel);
    }

    /**
     * Sorts the tasks in the course-specific to-do list.
     *
     * @param criterion the sorting criterion
     * @param ascending whether the sorting should be in ascending order
     * @param username  the username of the user
     * @param courseName the name of the course
     */
    public void sortTasks(String criterion, boolean ascending, String username, String courseName) {
        SortTasksRequestModel requestModel = new SortTasksRequestModel(criterion, ascending, username, courseName);
        sortTasksUseCase.execute(requestModel);
    }

    /**
     * Filters the tasks in the to-do list based on completion status.
     *
     * @param showCompleted whether to show completed tasks
     * @param username      the username of the user
     */
    public void filterTasks(boolean showCompleted, String username) {
        FilterTasksRequestModel requestModel = new FilterTasksRequestModel(showCompleted, username);
        filterTasksUseCase.execute(requestModel);
    }

    /**
     * Filters the tasks in the course-specific to-do list based on completion status.
     *
     * @param showCompleted whether to show completed tasks
     * @param username      the username of the user
     * @param courseName    the name of the course
     */
    public void filterTasks(boolean showCompleted, String username, String courseName) {
        FilterTasksRequestModel requestModel = new FilterTasksRequestModel(showCompleted, username, courseName);
        filterTasksUseCase.execute(requestModel);
    }

    /**
     * Loads the to-do list.
     *
     * @param username the username of the user
     */
    public void loadTodoList(String username) {
        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel(username);
        loadTodoListUseCase.execute(requestModel);
    }

    /**
     * Loads the course-specific to-do list.
     *
     * @param username  the username of the user
     * @param courseName the name of the course
     */
    public void loadTodoList(String username, String courseName) {
        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel(username, courseName);
        loadTodoListUseCase.execute(requestModel);
    }
}
