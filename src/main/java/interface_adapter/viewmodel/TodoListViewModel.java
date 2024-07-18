package interface_adapter.viewmodel;

import use_case.TaskData;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for the to-do list, providing a data structure to hold the task data.
 */
public class TodoListViewModel {
    private List<TaskData> tasks;

    public TodoListViewModel() {
        this.tasks = new ArrayList<>();
    }

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}
