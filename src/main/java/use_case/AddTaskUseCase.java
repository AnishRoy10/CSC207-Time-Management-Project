package use_case;

import entity.Task;
import entity.TodoList;
import repositories.TodoListRepository;

/**
 * Use case for adding a task to the to-do list.
 */
public class AddTaskUseCase {
    private final TodoListRepository todoListRepository;

    /**
     * Constructs an AddTaskUseCase with the specified TodoListRepository.
     *
     * @param todoListRepository The repository for accessing the to-do list.
     */
    public AddTaskUseCase(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    /**
     * Executes the use case to add a task to the to-do list.
     *
     * @param task The task to be added.
     */
    public void execute(Task task) {
        // Load the current to-do list from the repository
        TodoList todoList = todoListRepository.loadTodoList();

        // Add the task to the to-do list
        todoList.addTask(task);

        // Save the updated to-do list back to the repository
        todoListRepository.saveTodoList(todoList);
    }
}
