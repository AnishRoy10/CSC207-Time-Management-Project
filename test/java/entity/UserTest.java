package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user1;
    private User user2;
    private Course course1;
    private Course course2;
    private Task task1;
    private Task task2;
    private CalendarEvent event1;
    private Timer timer;

    @BeforeEach
    void setUp() {
        user1 = new User("user1", "password1", new User[]{}, new Course[]{});
        user2 = new User("user2", "password2", new User[]{user1}, new Course[]{});
        course1 = new Course("course1", "Course 1");
        course2 = new Course("course2", "Course 2");
        task1 = new Task("user1", "Task 1", "Description 1", LocalDateTime.now(), LocalDateTime.now().plusDays(1), "course1");
        task2 = new Task("user1", "Task 2", "Description 2", LocalDateTime.now(), LocalDateTime.now().plusDays(2), "course2");
        event1 = new CalendarEvent("Event 1", "Description 1", "High", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        timer = new Timer(0, 0, 0);
    }

    @Test
    void testGetUsername() {
        assertEquals("user1", user1.getUsername());
    }

    @Test
    void testSetUsername() {
        user1.setUsername("newUser1");
        assertEquals("newUser1", user1.getUsername());
    }

    @Test
    void testAddFriend() {
        user1.addFriend(user2);
        assertTrue(user1.getFriends().exportFriendsNames().contains(user2.getUsername()));
    }

    @Test
    void testRemoveFriend() {
        user1.addFriend(user2);
        user1.removeFriend(user2);
        assertFalse(user1.getFriends().exportFriendsNames().contains(user2.getUsername()));
    }

    @Test
    void testGetPassword() {
        assertEquals(user1.getPassword(), "password1");
    }

    @Test
    void testSetPassword() {
        user1.setPassword("newPassword1");
        assertTrue(user1.verifyPassword("newPassword1"));
    }

    @Test
    void testVerifyPassword() {
        assertTrue(user1.verifyPassword("password1"));
        assertFalse(user1.verifyPassword("wrongPassword"));
    }


    @Test
    void testGetFriends() {
        user1.addFriend(user2);
        assertTrue(user1.getFriends().exportFriendsNames().contains(user2.getUsername()));
    }

    @Test
    void testAddTask() {
        user1.addTask(task1);
        assertTrue(user1.getTodoList().getTasks().contains(task1));
    }

    @Test
    void testRemoveTask() {
        user1.addTask(task1);
        user1.removeTask(task1);
        assertFalse(user1.getTodoList().getTasks().contains(task1));
    }

    @Test
    void testAddCourse() {
        user1.addCourse(course1);
        assertTrue(user1.getCourses().contains(course1));
        assertTrue(course1.getUsers().contains(user1));
    }

    @Test
    void testRemoveCourse() {
        user1.addCourse(course1);
        assertTrue(user1.removeCourse(course1));
        assertFalse(user1.getCourses().contains(course1));
        assertFalse(course1.getUsers().contains(user1));
    }

    @Test
    void testGetCourses() {
        user1.addCourse(course1);
        assertTrue(user1.getCourses().contains(course1));
    }

    @Test
    void testGetScore() {
        assertEquals(0, user1.getScore());
    }

    @Test
    void testSetScore() {
        user1.setScore(100);
        assertEquals(100, user1.getScore());
    }

    @Test
    void testGetTimer() {
        user1.addTimer(timer);
        assertEquals(timer, user1.getTimer());
    }

    @Test
    void testAddTimer() {
        user1.addTimer(timer);
        assertEquals(timer, user1.getTimer());
    }

    @Test
    void testGetTodoList() {
        user1.addTask(task1);
        assertTrue(user1.getTodoList().getTasks().contains(task1));
    }

    @Test
    void testGetCalendar() {
        user1.addEvent(event1);
        assertTrue(user1.getCalendar().getAllEvents().contains(event1));
    }

    @Test
    void testGetEvents() {
        user1.addEvent(event1);
        assertTrue(user1.getEvents().contains(event1));
    }

    @Test
    void testAddEvent() {
        user1.addEvent(event1);
        assertTrue(user1.getCalendar().getAllEvents().contains(event1));
    }

    @Test
    void testEquals() {
        User user3 = new User("user1", "differentPassword", new User[]{}, new Course[]{});
        assertEquals(user1, user3);
    }

    @Test
    void testHashCode() {
        User user3 = new User("user1", "differentPassword", new User[]{}, new Course[]{});
        assertEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testGetANumber() {
        assertEquals(5, user1.getANumber());
    }

    @Test
    void testSetANumber() {
        user1.setANumber(10);
        assertEquals(10, user1.getANumber());
    }
}
