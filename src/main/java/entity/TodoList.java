package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code TodoList} class represents a list of tasks that a user can manage.
 * This class provides functionality for adding, removing, retrieving, sorting,
 * and filtering tasks based on various criteria such as due date, completion status, and course.
 */
public class TodoList implements Serializable {
    private static final long serialVersionUID = 2L; // Serial version UID for serialization compatibility
    private List<Task> tasks;

    /**
     * Constructs an empty {@code TodoList}.
     */
    public TodoList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the to-do list.
     *
     * @param task The {@code Task} to be added to the list.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the to-do list.
     *
     * @param task The {@code Task} to be removed from the list.
     */
    public void removeTask(Task task) {
        tasks.removeIf(t -> t.getId().equals(task.getId()));
    }

    /**
     * Retrieves the list of tasks in the to-do list.
     *
     * @return A {@code List} of {@code Task} objects representing the tasks in the to-do list.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of tasks in the to-do list.
     *
     * @param tasks The {@code List} of {@code Task} objects to set in the to-do list.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Sorts the tasks in the to-do list by their due date.
     *
     * @param ascending If {@code true}, sorts the tasks in ascending order of due date;
     *                  otherwise, sorts in descending order.
     * @return A {@code List} of {@code Task} objects sorted by due date.
     */
    public List<Task> sortByDueDate(boolean ascending) {
        return tasks.stream()
                .sorted(ascending ? Comparator.comparing(Task::getDeadline) : Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Sorts the tasks in the to-do list by their completion status.
     *
     * @param ascending If {@code true}, sorts the tasks in ascending order of completion status
     *                  (incomplete tasks first); otherwise, sorts in descending order.
     * @return A {@code List} of {@code Task} objects sorted by completion status.
     */
    public List<Task> sortByCompletionStatus(boolean ascending) {
        return tasks.stream()
                .sorted(ascending ? Comparator.comparing(Task::isCompleted) : Comparator.comparing(Task::isCompleted).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Sorts the tasks in the to-do list by the course they are associated with.
     *
     * @param ascending If {@code true}, sorts the tasks in ascending order of course name;
     *                  otherwise, sorts in descending order.
     * @return A {@code List} of {@code Task} objects sorted by course.
     */
    public List<Task> sortByCourse(boolean ascending) {
        return tasks.stream()
                .sorted(ascending ? Comparator.comparing(Task::getCourse) : Comparator.comparing(Task::getCourse).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Filters the tasks in the to-do list to exclude completed tasks if specified.
     *
     * @param hideCompleted If {@code true}, returns a list of tasks that excludes completed tasks;
     *                      otherwise, returns all tasks in the list.
     * @return A {@code List} of {@code Task} objects, possibly excluding completed tasks based on the input flag.
     */
    public List<Task> filterCompletedTasks(boolean hideCompleted) {
        return hideCompleted ? tasks.stream().filter(task -> !task.isCompleted()).collect(Collectors.toList()) : tasks;
    }

    /**
     * Returns a string representation of the to-do list, including all tasks contained within it.
     *
     * @return A {@code String} representing the to-do list and its tasks.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task).append("\n");
        }
        return sb.toString();
    }
}