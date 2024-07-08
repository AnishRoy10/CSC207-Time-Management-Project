package use_case;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;

import java.util.List;

/**
 * Use case for filtering tasks in the to-do list.
 */
public class FilterTasksUseCase {
    private final TodoListRepository todoListRepository;

    /**
     * Constructs a FilterTasksUseCase with the specified TodoListRepository.
     *
     * @param todoListRepository The repository for accessing the to-do list.
     */
    public FilterTasksUseCase(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    /**
     * Executes the use case to filter completed tasks.
     *
     * @param hideCompleted If true, hides completed tasks; otherwise, returns all tasks.
     * @return A filtered list of tasks.
     */
    public List<Task> execute(boolean hideCompleted) {
        TodoList todoList = todoListRepository.loadTodoList();
        return todoList.filterCompletedTasks(hideCompleted);
    }
}
