package use_case;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;

/**
 * Use case for removing a task from the to-do list.
 */
public class RemoveTaskUseCase {
    private final TodoListRepository todoListRepository;

    /**
     * Constructs a RemoveTaskUseCase with the specified TodoListRepository.
     *
     * @param todoListRepository The repository for accessing the to-do list.
     */
    public RemoveTaskUseCase(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    /**
     * Executes the use case to remove a task from the to-do list.
     *
     * @param task The task to be removed.
     */
    public void execute(Task task) {
        // Load the current to-do list from the repository
        TodoList todoList = todoListRepository.loadTodoList();

        // Remove the task from the to-do list
        todoList.removeTask(task);

        // Save the updated to-do list back to the repository
        todoListRepository.saveTodoList(todoList);
    }
}
