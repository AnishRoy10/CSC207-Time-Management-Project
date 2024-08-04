package interface_adapter.controller;

import entity.Course;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseInputBoundary;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseInputData;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseUseCase;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseInputBoundary;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseInputData;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseUseCase;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputBoundary;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputData;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseUseCase;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class CoursePromptControllerTest {
    private CreateCourseInputBoundary createCourseUseCase;
    private JoinCourseInputBoundary joinCourseUseCase;
    private LeaveCourseInputBoundary leaveCourseUseCase;
    private CoursePromptController createController;
    private CoursePromptController joinController;
    private CoursePromptController leaveController;

    @BeforeEach
    public void setUp() {
        createCourseUseCase = mock(CreateCourseUseCase.class);
        joinCourseUseCase = mock(JoinCourseUseCase.class);
        leaveCourseUseCase = mock(LeaveCourseUseCase.class);

        createController = new CoursePromptController(createCourseUseCase);
        joinController = new CoursePromptController(joinCourseUseCase);
        leaveController = new CoursePromptController(leaveCourseUseCase);
    }

    @Test
    public void testCreateCourse() {
        User user = new User("Test User", "TestPassword", new User[]{}, new Course[]{});

        CreateCourseInputData expectedInput = new CreateCourseInputData(
                "Test User",
                "Test Course",
                "Test Description"
        );

        createController.createCourse(user.getUsername(), "Test Course", "Test Description");

        verify(createCourseUseCase).execute(argThat(input ->
                input.getUsername().equals(expectedInput.getUsername()) &&
                        input.getCourseName().equals(expectedInput.getCourseName()) &&
                        input.getCourseDescription().equals(expectedInput.getCourseDescription())
        ));
    }

    @Test
    public void testJoinCourse() {
        User user = new User("Test User", "TestPassword", new User[]{}, new Course[]{});
        Course course = new Course("Test Course", "Test Description");

        JoinCourseInputData expectedInput = new JoinCourseInputData(
                "Test User",
                "Test Course"
        );

        joinController.joinCourse(user.getUsername(), course.getName());

        verify(joinCourseUseCase).execute(argThat(input ->
                input.getUsername().equals(expectedInput.getUsername()) &&
                        input.getCourseName().equals(expectedInput.getCourseName()))
        );
    }

    @Test
    public void testLeaveCourse() {
        User user = new User("Test User", "TestPassword", new User[]{}, new Course[]{});
        Course course = new Course("Test Course", "Test Description");

        LeaveCourseInputData expectedInput = new LeaveCourseInputData(
                "Test User",
                "Test Course"
        );

        leaveController.leaveCourse(user.getUsername(), course.getName());

        verify(leaveCourseUseCase).execute(argThat(input ->
                input.getUsername().equals(expectedInput.getUsername()) &&
                input.getCourseName().equals(expectedInput.getCourseName()))
        );
    }
}
