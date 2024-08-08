package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest {
    private TodoList todoList;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        todoList = new TodoList();

        task1 = new Task("user1", "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Course 1");
        task2 = new Task("user1", "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "Course 2");
        task3 = new Task("user1", "Task 3", "Description 3", LocalDateTime.now(), LocalDateTime.now().plusDays(3), "Course 3");

        todoList.addTask(task1);
        todoList.addTask(task2);
        todoList.addTask(task3);
    }

    @Test
    void testAddTask() {
        Task newTask = new Task("user1", "New Task", "New Description", LocalDateTime.now(), LocalDateTime.now().plusDays(4), "New Course");
        todoList.addTask(newTask);

        assertTrue(todoList.getTasks().contains(newTask));
    }

    @Test
    void testRemoveTask() {
        todoList.removeTask(task2);
        assertFalse(todoList.getTasks().contains(task2));
    }

    @Test
    void testGetTasks() {
        List<Task> tasks = todoList.getTasks();
        assertEquals(3, tasks.size());
    }

    @Test
    void testSetTasks() {
        List<Task> newTasks = new ArrayList<>();
        Task newTask = new Task("user1", "Another Task", "Another Description", LocalDateTime.now(), LocalDateTime.now().plusDays(5), "Another Course");
        newTasks.add(newTask);
        todoList.setTasks(newTasks);

        assertEquals(1, todoList.getTasks().size());
        assertTrue(todoList.getTasks().contains(newTask));
    }

    @Test
    void testSortByDueDateAscending() {
        List<Task> sortedTasks = todoList.sortByDueDate(true);
        assertEquals(task1, sortedTasks.get(0));
        assertEquals(task2, sortedTasks.get(1));
        assertEquals(task3, sortedTasks.get(2));
    }

    @Test
    void testSortByDueDateDescending() {
        List<Task> sortedTasks = todoList.sortByDueDate(false);
        assertEquals(task3, sortedTasks.get(0));
        assertEquals(task2, sortedTasks.get(1));
        assertEquals(task1, sortedTasks.get(2));
    }

    @Test
    void testSortByCompletionStatusAscending() {
        task1.setCompleted(true);
        task2.setCompleted(false);
        task3.setCompleted(true);

        List<Task> sortedTasks = todoList.sortByCompletionStatus(true);
        assertEquals(task2, sortedTasks.get(0));
        assertEquals(task1, sortedTasks.get(1));
        assertEquals(task3, sortedTasks.get(2));
    }

    @Test
    void testSortByCompletionStatusDescending() {
        task1.setCompleted(true);
        task2.setCompleted(false);
        task3.setCompleted(true);

        List<Task> sortedTasks = todoList.sortByCompletionStatus(false);
        assertEquals(task1, sortedTasks.get(0));
        assertEquals(task3, sortedTasks.get(1));
        assertEquals(task2, sortedTasks.get(2));
    }

    @Test
    void testSortByCourseAscending() {
        List<Task> sortedTasks = todoList.sortByCourse(true);
        assertEquals(task1, sortedTasks.get(0));
        assertEquals(task3, sortedTasks.get(2));
        assertEquals(task2, sortedTasks.get(1));
    }

    @Test
    void testSortByCourseDescending() {
        List<Task> sortedTasks = todoList.sortByCourse(false);
        assertEquals(task2, sortedTasks.get(1));
        assertEquals(task3, sortedTasks.get(0));
        assertEquals(task1, sortedTasks.get(2));
    }

    @Test
    void testFilterCompletedTasksHideCompleted() {
        task1.setCompleted(true);
        task2.setCompleted(false);
        task3.setCompleted(true);

        List<Task> filteredTasks = todoList.filterCompletedTasks(true);
        assertEquals(1, filteredTasks.size());
        assertEquals(task2, filteredTasks.get(0));
    }

    @Test
    void testFilterCompletedTasksShowAll() {
        task1.setCompleted(true);
        task2.setCompleted(false);
        task3.setCompleted(true);

        List<Task> filteredTasks = todoList.filterCompletedTasks(false);
        assertEquals(3, filteredTasks.size());
    }

    @Test
    void testToString() {
        String result = todoList.toString();
        assertTrue(result.contains(task1.toString()));
        assertTrue(result.contains(task2.toString()));
        assertTrue(result.contains(task3.toString()));
    }
}
