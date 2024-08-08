package use_case.TodoListUseCases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskDataTest {
    private TaskData taskData;
    private UUID id;
    private String username;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime deadline;
    private boolean completed;
    private String course;
    private LocalDateTime completionDate;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        username = "testUser";
        title = "Test Task";
        description = "Test Description";
        startDate = LocalDateTime.now();
        deadline = LocalDateTime.now().plusDays(1);
        completed = false;
        course = "Test Course";
        completionDate = LocalDateTime.now().plusDays(2);

        taskData = new TaskData(id, username, title, description, startDate, deadline, completed, course, completionDate);
    }

    @Test
    void testGetId() {
        assertEquals(id, taskData.getId());
    }

    @Test
    void testSetId() {
        UUID newId = UUID.randomUUID();
        taskData.setId(newId);
        assertEquals(newId, taskData.getId());
    }

    @Test
    void testGetUsername() {
        assertEquals(username, taskData.getUsername());
    }

    @Test
    void testSetUsername() {
        String newUsername = "newUser";
        taskData.setUsername(newUsername);
        assertEquals(newUsername, taskData.getUsername());
    }

    @Test
    void testGetTitle() {
        assertEquals(title, taskData.getTitle());
    }

    @Test
    void testSetTitle() {
        String newTitle = "New Task";
        taskData.setTitle(newTitle);
        assertEquals(newTitle, taskData.getTitle());
    }

    @Test
    void testGetDescription() {
        assertEquals(description, taskData.getDescription());
    }

    @Test
    void testSetDescription() {
        String newDescription = "New Description";
        taskData.setDescription(newDescription);
        assertEquals(newDescription, taskData.getDescription());
    }

    @Test
    void testGetStartDate() {
        assertEquals(startDate, taskData.getStartDate());
    }

    @Test
    void testSetStartDate() {
        LocalDateTime newStartDate = LocalDateTime.now().plusDays(3);
        taskData.setStartDate(newStartDate);
        assertEquals(newStartDate, taskData.getStartDate());
    }

    @Test
    void testGetDeadline() {
        assertEquals(deadline, taskData.getDeadline());
    }

    @Test
    void testSetDeadline() {
        LocalDateTime newDeadline = LocalDateTime.now().plusDays(4);
        taskData.setDeadline(newDeadline);
        assertEquals(newDeadline, taskData.getDeadline());
    }

    @Test
    void testIsCompleted() {
        assertEquals(completed, taskData.isCompleted());
    }

    @Test
    void testSetCompleted() {
        taskData.setCompleted(true);
        assertTrue(taskData.isCompleted());
    }

    @Test
    void testGetCourse() {
        assertEquals(course, taskData.getCourse());
    }

    @Test
    void testSetCourse() {
        String newCourse = "New Course";
        taskData.setCourse(newCourse);
        assertEquals(newCourse, taskData.getCourse());
    }

    @Test
    void testGetCompletionDate() {
        assertEquals(completionDate, taskData.getCompletionDate());
    }

    @Test
    void testSetCompletionDate() {
        LocalDateTime newCompletionDate = LocalDateTime.now().plusDays(5);
        taskData.setCompletionDate(newCompletionDate);
        assertEquals(newCompletionDate, taskData.getCompletionDate());
    }
}
