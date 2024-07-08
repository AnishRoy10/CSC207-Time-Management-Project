package interface_adapter.controller;

import use_case.*;

import java.time.LocalDateTime;

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
     */
    public void addTask(String title, String description, LocalDateTime startDate, LocalDateTime deadline, String course) {
        AddTaskRequestModel requestModel = new AddTaskRequestModel(title, description, startDate, deadline, course);
        addTaskUseCase.execute(requestModel);
    }

    /**
     * Removes a task from the to-do list.
     *
     * @param taskId the ID of the task to be removed
     */
    public void removeTask(int taskId) {
        RemoveTaskRequestModel requestModel = new RemoveTaskRequestModel(taskId);
        removeTaskUseCase.execute(requestModel);
    }

    /**
     * Marks a task as completed.
     *
     * @param taskId the ID of the task to be completed
     */
    public void completeTask(int taskId) {
        CompleteTaskRequestModel requestModel = new CompleteTaskRequestModel(taskId);
        completeTaskUseCase.execute(requestModel);
    }

    /**
     * Sorts the tasks in the to-do list.
     *
     * @param criterion the sorting criterion
     * @param ascending whether the sorting should be in ascending order
     */
    public void sortTasks(String criterion, boolean ascending) {
        SortTasksRequestModel requestModel = new SortTasksRequestModel(criterion, ascending);
        sortTasksUseCase.execute(requestModel);
    }

    /**
     * Filters the tasks in the to-do list based on completion status.
     *
     * @param showCompleted whether to show completed tasks
     */
    public void filterTasks(boolean showCompleted) {
        FilterTasksRequestModel requestModel = new FilterTasksRequestModel(showCompleted);
        filterTasksUseCase.execute(requestModel);
    }

    /**
     * Loads the to-do list.
     */
    public void loadTodoList() {
        LoadTodoListRequestModel requestModel = new LoadTodoListRequestModel();
        loadTodoListUseCase.execute(requestModel);
    }
}
