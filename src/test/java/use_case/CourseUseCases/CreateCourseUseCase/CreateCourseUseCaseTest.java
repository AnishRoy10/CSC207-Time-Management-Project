package use_case.CourseUseCases.CreateCourseUseCase;

import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import entity.Course;
import entity.User;
import interface_adapter.presenter.CoursePromptPresenter;
import interface_adapter.viewmodel.CoursePromptViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;
import repositories.UserRepository;

import java.io.File;
import java.io.IOException;

public class CreateCourseUseCaseTest {
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
    public void testValidCreation()  {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            CreateCourseOutputBoundary presenter = new CoursePromptPresenter(viewModel, null, null);
            CreateCourseInputBoundary interactor = new CreateCourseUseCase(presenter, courseDataAccessObject, userDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
            userDataAccessObject.WriteToCache(user);

            CreateCourseInputData inputData = new CreateCourseInputData(
                    user.getUsername(),
                    "Test Course",
                    "Test Description"
            );

            interactor.execute(inputData);

            Assertions.assertTrue(viewModel.getResponse());
            Assertions.assertFalse(userDataAccessObject.ReadFromCache("Test User").getCourses().isEmpty());
            Assertions.assertTrue(courseDataAccessObject.courseExists("Test Course"));
        });
    }

    @Test
    public void testCourseExists()  {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            CreateCourseOutputBoundary presenter = new CoursePromptPresenter(viewModel, null, null);
            CreateCourseInputBoundary interactor = new CreateCourseUseCase(presenter, courseDataAccessObject, userDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
            Course course = new Course("Test Course", "Test Description");

            userDataAccessObject.WriteToCache(user);
            courseDataAccessObject.WriteToCache(course);

            CreateCourseInputData inputData =  new CreateCourseInputData(
                    user.getUsername(),
                    "Test Course",
                    "Test Description"
            );

            interactor.execute(inputData);

            Assertions.assertFalse(viewModel.getResponse());
            Assertions.assertTrue(user.getCourses().isEmpty());
            Assertions.assertTrue(courseDataAccessObject.courseExists("Test Course"));
        });
    }
}
