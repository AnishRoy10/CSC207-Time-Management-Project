package java.use_case.CourseUseCases.LeaveCourseUseCase;

import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import entity.Course;
import entity.User;
import interface_adapter.presenter.CoursePromptPresenter;
import interface_adapter.viewmodel.CoursePromptViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;
import repositories.UserRepository;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputBoundary;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputData;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseOutputBoundary;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseUseCase;

import java.io.File;
import java.io.IOException;

public class LeaveCourseUseCaseTest {
    private String userpath = "test_userCache.json";
    private String coursepath = "test_courses.json";
    private UserRepository userDataAccessObject;
    private CourseRepository courseDataAccessObject;

    @BeforeEach
    public void setUp() throws IOException {
        userDataAccessObject = new FileCacheUserDataAccessObject(userpath);
        courseDataAccessObject = new CourseDataAccessObject(coursepath);
    }

    @AfterEach
    public void tearDown() throws IOException {
        new File(userpath).delete();
        new File(coursepath).delete();
    }

    @Test
    public void testLeaveCourseValid() {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            LeaveCourseOutputBoundary presenter = new CoursePromptPresenter(null, null, viewModel);
            LeaveCourseInputBoundary interactor = new LeaveCourseUseCase(presenter, userDataAccessObject, courseDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
            Course course = new Course("Test Course", "Test Description");

            user.addCourse(course);
            course.addUser(user);

            userDataAccessObject.WriteToCache(user);
            courseDataAccessObject.WriteToCache(course);

            Assertions.assertTrue(course.containsUser("Test User"));
            Assertions.assertTrue(user.getCourses().contains("Test Course"));

            LeaveCourseInputData inputData = new LeaveCourseInputData(
                    user.getUsername(),
                    course.getName()
            );

            interactor.execute(inputData);

            Assertions.assertTrue(viewModel.getResponse());
            Assertions.assertTrue(userDataAccessObject.ReadFromCache("Test User").getCourses().isEmpty());
            Assertions.assertEquals(0, courseDataAccessObject.findByName("Test Course").getUserNames().length);
        });
    }

    @Test
    public void testNotInCourse() {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            LeaveCourseOutputBoundary presenter = new CoursePromptPresenter(null, null, viewModel);
            LeaveCourseInputBoundary interactor = new LeaveCourseUseCase(presenter, userDataAccessObject, courseDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
            Course course = new Course("Test Course", "Test Description");

            userDataAccessObject.WriteToCache(user);
            courseDataAccessObject.WriteToCache(course);

            Assertions.assertFalse(course.containsUser("Test User"));
            Assertions.assertFalse(user.getCourses().contains("Test Course"));

            LeaveCourseInputData inputData = new LeaveCourseInputData(
                    user.getUsername(),
                    course.getName()
            );

            interactor.execute(inputData);

            Assertions.assertFalse(viewModel.getResponse());
            Assertions.assertTrue(userDataAccessObject.ReadFromCache("Test User").getCourses().isEmpty());
            Assertions.assertEquals(0, courseDataAccessObject.findByName("Test Course").getUserNames().length);
        });
    }

    @Test
    public void testCourseDoesNotExist() {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            LeaveCourseOutputBoundary presenter = new CoursePromptPresenter(null, null, viewModel);
            LeaveCourseInputBoundary interactor = new LeaveCourseUseCase(presenter, userDataAccessObject, courseDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});

            userDataAccessObject.WriteToCache(user);

            Assertions.assertEquals(0, user.getCourses().size());

            LeaveCourseInputData inputData = new LeaveCourseInputData(
                    user.getUsername(),
                    "Test Course"
            );

            interactor.execute(inputData);

            Assertions.assertFalse(viewModel.getResponse());
        });
    }
}
