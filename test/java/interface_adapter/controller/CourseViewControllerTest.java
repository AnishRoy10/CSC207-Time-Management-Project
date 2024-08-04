package interface_adapter.controller;

import entity.Course;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputData;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesUseCase;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputData;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseUseCase;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class CourseViewControllerTest {
    private ViewCourseInputBoundary viewCourseUseCase;
    private LoadCoursesInputBoundary loadCoursesUseCase;
    private CourseViewController controller;

    @BeforeEach
    public void setUp() {
        viewCourseUseCase = mock(ViewCourseUseCase.class);
        loadCoursesUseCase = mock(LoadCoursesUseCase.class);

        controller = new CourseViewController(viewCourseUseCase, loadCoursesUseCase);
    }

    @Test
    public void testLoadCourses() {
        Course crs1 = new Course("Test 1", "Test Description 1");
        Course crs2 = new Course("Test 2", "Test Description 2");
        Course crs3 = new Course("Test 3", "Test Description 3");

        User user = new User("Test User", "Test Password", new User[] {}, new Course[] {});

        controller.loadCourses("Test User");

        LoadCoursesInputData expectedReq = new LoadCoursesInputData(
                "Test User"
        );

        verify(loadCoursesUseCase).execute(argThat(input ->
                Objects.equals(input.getUsername(), expectedReq.getUsername()))
        );
    }

    @Test
    public void testViewCourse() {
        Course crs = new Course("Test 1", "Test Description 1");
        User user = new User("Test User", "Test Password", new User[] {}, new Course[] {});

        user.addCourse(crs);
        crs.addUser(user);

        controller.viewCourse("Test 1");

        ViewCourseInputData expectedReq = new ViewCourseInputData(
                "Test 1"
        );

        verify(viewCourseUseCase).execute(argThat(input ->
                Objects.equals(input.getCourseName(), expectedReq.getCourseName()))
        );
    }
}
