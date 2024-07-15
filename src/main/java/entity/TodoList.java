package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The TodoList class represents a list of tasks.
 * It allows adding, removing, retrieving, sorting, and filtering tasks.
 */
public class TodoList implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serial version UID
    private List<Task> tasks;

    /**
     * Constructs an empty TodoList.
     */
    public TodoList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the to-do list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the to-do list.
     *
     * @param task The task to be removed.
     */
    public void removeTask(Task task) {
        tasks.removeIf(existingTask -> existingTask.equals(task));
    }

    /**
     * Returns the list of tasks.
     *
     * @return A list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets the list of tasks.
     *
     * @param tasks The list of tasks to set.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Sorts the tasks by due date.
     *
     * @param ascending If true, sorts in ascending order; otherwise, sorts in descending order.
     * @return A sorted list of tasks by due date.
     */
    public List<Task> sortByDueDate(boolean ascending) {
        return tasks.stream()
                .sorted(ascending ? Comparator.comparing(Task::getDeadline) : Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Sorts the tasks by completion status.
     *
     * @param ascending If true, sorts in ascending order; otherwise, sorts in descending order.
     * @return A sorted list of tasks by completion status.
     */
    public List<Task> sortByCompletionStatus(boolean ascending) {
        return tasks.stream()
                .sorted(ascending ? Comparator.comparing(Task::isCompleted) : Comparator.comparing(Task::isCompleted).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Sorts the tasks by course.
     *
     * @param ascending If true, sorts in ascending order; otherwise, sorts in descending order.
     * @return A sorted list of tasks by course.
     */
    public List<Task> sortByCourse(boolean ascending) {
        return tasks.stream()
                .sorted(ascending ? Comparator.comparing(Task::getCourse) : Comparator.comparing(Task::getCourse).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Filters the tasks to exclude completed tasks if specified.
     *
     * @param hideCompleted If true, hides completed tasks; otherwise, returns all tasks.
     * @return A list of tasks with completed tasks excluded if specified.
     */
    public List<Task> filterCompletedTasks(boolean hideCompleted) {
        return hideCompleted ? tasks.stream().filter(task -> !task.isCompleted()).collect(Collectors.toList()) : tasks;
    }

    /**
     * Returns a string representation of the to-do list.
     *
     * @return A string representation of the to-do list.
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
