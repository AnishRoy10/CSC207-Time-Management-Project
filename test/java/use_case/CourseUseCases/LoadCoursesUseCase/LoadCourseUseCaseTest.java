package java.use_case.CourseUseCases.LoadCoursesUseCase;

import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import entity.Course;
import entity.User;
import interface_adapter.presenter.CourseListPresenter;
import interface_adapter.viewmodel.CourseListViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;
import repositories.UserRepository;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputData;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesOutputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesUseCase;

import java.io.File;
import java.io.IOException;

public class LoadCourseUseCaseTest {
    private String userpath = "test_userCache.json";
    private UserRepository userDataAccessObject;

    @BeforeEach
    public void setUp() throws IOException {
        userDataAccessObject = new FileCacheUserDataAccessObject(userpath);
    }

    @AfterEach
    public void tearDown() throws IOException {
        new File(userpath).delete();
    }

    @Test
    public void testLoadCoursesBasic() {
        Assertions.assertDoesNotThrow(() -> {
            CourseListViewModel viewModel = new CourseListViewModel();
            LoadCoursesOutputBoundary presenter = new CourseListPresenter(viewModel);
            LoadCoursesInputBoundary interactor = new LoadCoursesUseCase(presenter, userDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});

            Course crs1 = new Course("Test Course 1", "Test Description 1");
            Course crs2 = new Course("Test Course 2", "Test Description 2");
            Course crs3 = new Course("Test Course 3", "Test Description 3");

            user.addCourse(crs1);
            user.addCourse(crs2);
            user.addCourse(crs3);

            userDataAccessObject.WriteToCache(user);

            Assertions.assertEquals(3, user.getCourses().size());

            LoadCoursesInputData inputData = new LoadCoursesInputData(
                    user.getUsername()
            );

            interactor.execute(inputData);

            Assertions.assertTrue(viewModel.isSuccess());
            Assertions.assertEquals(3, viewModel.getCourses().size());
        });
    }

    @Test
    public void testLoadCoursesNone() {
        Assertions.assertDoesNotThrow(() -> {
            CourseListViewModel viewModel = new CourseListViewModel();
            LoadCoursesOutputBoundary presenter = new CourseListPresenter(viewModel);
            LoadCoursesInputBoundary interactor = new LoadCoursesUseCase(presenter, userDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});

            Assertions.assertEquals(0, user.getCourses().size());

            userDataAccessObject.WriteToCache(user);

            LoadCoursesInputData inputData = new LoadCoursesInputData(
                    user.getUsername()
            );

            interactor.execute(inputData);

            Assertions.assertTrue(viewModel.isSuccess());
            Assertions.assertEquals(0, viewModel.getCourses().size());
        });
    }
}
