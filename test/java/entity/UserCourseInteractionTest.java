package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserCourseInteractionTest {

    @Test
    public void testAddUserToCourse() {
        User user = new User("TestUser", "password", new User[]{}, new Course[]{});
        Course course = new Course("TestCourse", "Test Description");

        user.addCourse(course);

        assertTrue(course.containsUser("TestUser"));
        assertEquals(1, course.getUserNames().length);
        assertEquals("TestUser", course.getUserNames()[0]);
        assertTrue(user.getCourses().contains("TestCourse"));
    }

    @Test
    public void testAddCourseToUser() {
        User user = new User("TestUser", "password", new User[]{}, new Course[]{});
        Course course = new Course("TestCourse", "Test Description");

        course.addUser(user);

        assertTrue(course.containsUser("TestUser"));
        assertEquals(1, course.getUserNames().length);
        assertEquals("TestUser", course.getUserNames()[0]);
        assertTrue(user.getCourses().contains("TestCourse"));
    }
}