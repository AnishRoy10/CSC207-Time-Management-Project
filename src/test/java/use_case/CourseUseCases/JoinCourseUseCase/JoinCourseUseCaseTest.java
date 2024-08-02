package use_case.CourseUseCases.JoinCourseUseCase;

import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import entity.Course;
import entity.User;
import interface_adapter.presenter.CoursePromptPresenter;
import interface_adapter.viewmodel.CoursePromptViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;
import repositories.UserRepository;

import java.io.IOException;

public class JoinCourseUseCaseTest {
    private String userpath = "test_userCache.json";
    private String coursepath = "test_courses.json";
    private UserRepository userDataAccessObject;
    private CourseRepository courseDataAccessObject;

    @BeforeEach
    public void setUp() throws IOException {
        userDataAccessObject = new FileCacheUserDataAccessObject(userpath);
        courseDataAccessObject = new CourseDataAccessObject(coursepath);
    }

    @Test
    public void testValidJoin() {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            JoinCourseOutputBoundary presenter = new CoursePromptPresenter(null, viewModel, null);
            JoinCourseInputBoundary interactor = new JoinCourseUseCase(presenter, userDataAccessObject, courseDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
            Course course = new Course("Test Course", "Test Description");

            userDataAccessObject.WriteToCache(user);
            courseDataAccessObject.WriteToCache(course);

            Assertions.assertFalse(course.containsUser(user.getUsername()));

            JoinCourseInputData inputData = new JoinCourseInputData(
                    user.getUsername(),
                    course.getName()
            );

            interactor.execute(inputData);

            Assertions.assertTrue(viewModel.getResponse());
            Assertions.assertFalse(userDataAccessObject.ReadFromCache("Test User").getCourses().isEmpty());
            Assertions.assertTrue(courseDataAccessObject.findByName("Test Course").containsUser(user.getUsername()));
        });
    }

    @Test
    public void testAlreadyInCourse() {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            JoinCourseOutputBoundary presenter = new CoursePromptPresenter(null, viewModel, null);
            JoinCourseInputBoundary interactor = new JoinCourseUseCase(presenter, userDataAccessObject, courseDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
            Course course = new Course("Test Course", "Test Description");

            user.addCourse(course);
            course.addUser(user);

            Assertions.assertTrue(course.containsUser(user.getUsername()));

            userDataAccessObject.WriteToCache(user);
            courseDataAccessObject.WriteToCache(course);

            JoinCourseInputData inputData = new JoinCourseInputData(
                    user.getUsername(),
                    course.getName()
            );

            interactor.execute(inputData);

            Assertions.assertFalse(viewModel.getResponse());
        });
    }

    @Test
    public void testCourseDoesNotExist() {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            JoinCourseOutputBoundary presenter = new CoursePromptPresenter(null, viewModel, null);
            JoinCourseInputBoundary interactor = new JoinCourseUseCase(presenter, userDataAccessObject, courseDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});

            userDataAccessObject.WriteToCache(user);

            JoinCourseInputData inputData = new JoinCourseInputData(
                    user.getUsername(),
                    "Test Course"
            );

            interactor.execute(inputData);

            Assertions.assertFalse(viewModel.getResponse());
        });
    }
}
