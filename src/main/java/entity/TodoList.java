package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The TodoList class represents a list of tasks.
 * It allows adding, removing, and retrieving tasks.
 */
public class TodoList {
    private List<Task> tasks;

    public TodoList() {
        this.tasks = new ArrayList<>();
    }

    // Adds a task to the to-do list
    public void addTask(Task task) {
        tasks.add(task);
    }

    // Removes a task from the to-do list
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    // Returns the list of tasks
    public List<Task> getTasks() {
        return tasks;
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