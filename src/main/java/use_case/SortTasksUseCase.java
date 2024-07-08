package use_case;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;

import java.util.List;

/**
 * Use case for sorting tasks in the to-do list.
 */
public class SortTasksUseCase {
    private final TodoListRepository todoListRepository;

    /**
     * Constructs a SortTasksUseCase with the specified TodoListRepository.
     *
     * @param todoListRepository The repository for accessing the to-do list.
     */
    public SortTasksUseCase(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    /**
     * Executes the use case to sort tasks by the specified criteria.
     *
     * @param criteria  The sorting criteria (e.g., "dueDate", "completionStatus", "course").
     * @param ascending If true, sorts in ascending order; otherwise, sorts in descending order.
     * @return A sorted list of tasks.
     */
    public List<Task> execute(String criteria, boolean ascending) {
        TodoList todoList = todoListRepository.loadTodoList();
        switch (criteria) {
            case "dueDate":
                return todoList.sortByDueDate(ascending);
            case "completionStatus":
                return todoList.sortByCompletionStatus(ascending);
            case "course":
                return todoList.sortByCourse(ascending);
            default:
                throw new IllegalArgumentException("Unknown sorting criteria: " + criteria);
        }
    }
}
