package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;
    private Task anotherTask;

    @BeforeEach
    void setUp() {
        task = new Task("user1", "Test Task", "Test Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Test Course");
        anotherTask = new Task("user2", "Another Task", "Another Description", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Another Course");
    }

    @Test
    void testGetId() {
        assertNotNull(task.getId());
    }

    @Test
    void testSetId() {
        UUID newId = UUID.randomUUID();
        task.setId(newId);
        assertEquals(newId, task.getId());
    }

    @Test
    void testGetUsername() {
        assertEquals("user1", task.getUsername());
    }

    @Test
    void testSetUsername() {
        task.setUsername("newUser");
        assertEquals("newUser", task.getUsername());
    }

    @Test
    void testGetTitle() {
        assertEquals("Test Task", task.getTitle());
    }

    @Test
    void testSetTitle() {
        task.setTitle("New Title");
        assertEquals("New Title", task.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals("Test Description", task.getDescription());
    }

    @Test
    void testSetDescription() {
        task.setDescription("New Description");
        assertEquals("New Description", task.getDescription());
    }

    @Test
    void testIsCompleted() {
        assertFalse(task.isCompleted());
    }

    @Test
    void testSetCompleted() {
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    void testGetStartDate() {
        assertNotNull(task.getStartDate());
    }

    @Test
    void testSetStartDate() {
        LocalDateTime newStartDate = LocalDateTime.now().plusDays(2);
        task.setStartDate(newStartDate);
        assertEquals(newStartDate, task.getStartDate());
    }

    @Test
    void testGetDeadline() {
        assertNotNull(task.getDeadline());
    }

    @Test
    void testSetDeadline() {
        LocalDateTime newDeadline = LocalDateTime.now().plusDays(3);
        task.setDeadline(newDeadline);
        assertEquals(newDeadline, task.getDeadline());
    }

    @Test
    void testGetCourse() {
        assertEquals("Test Course", task.getCourse());
    }

    @Test
    void testSetCourse() {
        task.setCourse("New Course");
        assertEquals("New Course", task.getCourse());
    }

    @Test
    void testGetCompletionDate() {
        assertNull(task.getCompletionDate());
    }

    @Test
    void testSetCompletionDate() {
        LocalDateTime newCompletionDate = LocalDateTime.now();
        task.setCompletionDate(newCompletionDate);
        assertEquals(newCompletionDate, task.getCompletionDate());
    }

    @Test
    void testToggleTaskCompletion() {
        task.toggleTaskCompletion();
        assertTrue(task.isCompleted());
        assertNotNull(task.getCompletionDate());

        task.toggleTaskCompletion();
        assertFalse(task.isCompleted());
        assertNull(task.getCompletionDate());
    }

    @Test
    void testCompleteTask() {
        task.completeTask();
        assertTrue(task.isCompleted());
        assertNotNull(task.getCompletionDate());
    }

    @Test
    void testIsPointsAwarded() {
        assertFalse(task.isPointsAwarded());
    }

    @Test
    void testSetPointsAwarded() {
        task.setPointsAwarded(true);
        assertTrue(task.isPointsAwarded());
    }

    @Test
    void testToString() {
        String expected = task.getId() + ": Test Task: Test Description - Start: " + task.getStartDate() + ", Deadline: " + task.getDeadline() + ", Course: Test Course, Completed: No";
        assertEquals(expected, task.toString());
    }

    @Test
    void testEquals() {
        anotherTask.setId(task.getId());
        anotherTask.setUsername(task.getUsername());
        anotherTask.setTitle(task.getTitle());
        anotherTask.setDescription(task.getDescription());
        anotherTask.setStartDate(task.getStartDate());
        anotherTask.setDeadline(task.getDeadline());
        anotherTask.setCourse(task.getCourse());

        assertTrue(task.equals(anotherTask));
    }

    @Test
    void testHashCode() {
        anotherTask.setId(task.getId());
        anotherTask.setUsername(task.getUsername());
        anotherTask.setTitle(task.getTitle());
        anotherTask.setDescription(task.getDescription());
        anotherTask.setStartDate(task.getStartDate());
        anotherTask.setDeadline(task.getDeadline());
        anotherTask.setCourse(task.getCourse());

        assertEquals(task.hashCode(), anotherTask.hashCode());
    }
}
