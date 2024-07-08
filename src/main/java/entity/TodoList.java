package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The TodoList class represents a list of tasks.
 * It allows adding, removing, and retrieving tasks.
 */
public class TodoList implements Serializable {
    private static final long serialVersionUID = 1L; // Add a serial version UID

    private List<Task> tasks;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task).append("\n");
        }
        return sb.toString();
    }
}
