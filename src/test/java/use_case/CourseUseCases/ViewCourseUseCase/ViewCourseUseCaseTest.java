package use_case.CourseUseCases.ViewCourseUseCase;

import com.sun.source.tree.AssertTree;
import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import entity.Course;
import interface_adapter.presenter.CourseViewPresenter;
import interface_adapter.viewmodel.CourseViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;
import repositories.UserRepository;

import java.io.IOException;

public class ViewCourseUseCaseTest {
    private String coursepath = "test_courses.json";
    private CourseRepository courseDataAccessObject;

    @BeforeEach
    public void setUp() throws IOException {
        courseDataAccessObject = new CourseDataAccessObject(coursepath);
    }

    @Test
    public void testValidCourse() {
        Assertions.assertDoesNotThrow(() -> {
            CourseViewModel viewModel = new CourseViewModel();
            ViewCourseOutputBoundary presenter = new CourseViewPresenter(viewModel);
            ViewCourseInputBoundary interactor = new ViewCourseUseCase(presenter, courseDataAccessObject);

            Course course = new Course("Test Course", "Test Description");

            courseDataAccessObject.WriteToCache(course);

            ViewCourseInputData inputData = new ViewCourseInputData(
                    course.getName()
            );

            interactor.execute(inputData);

            Assertions.assertTrue(viewModel.isSuccess());
            Assertions.assertEquals(course.getDescription(), viewModel.getCourseDescription());
            Assertions.assertEquals(course.getTodoList(), viewModel.getTodoList());
            Assertions.assertEquals(course.getDailyLeaderboard(), viewModel.getDailyLeaderboard());
            Assertions.assertEquals(course.getMonthlyLeaderboard(), viewModel.getMonthlyLeaderboard());
            Assertions.assertEquals(course.getAllTimeLeaderboard(), viewModel.getAllTimeLeaderboard());
        });
    }

    @Test
    public void testCourseDoesNotExist() {
        Assertions.assertDoesNotThrow(() -> {
            CourseViewModel viewModel = new CourseViewModel();
            ViewCourseOutputBoundary presenter = new CourseViewPresenter(viewModel);
            ViewCourseInputBoundary interactor = new ViewCourseUseCase(presenter, courseDataAccessObject);

            ViewCourseInputData inputData = new ViewCourseInputData(
                    "Test Course 2"
            );

            interactor.execute(inputData);

            Assertions.assertFalse(viewModel.isSuccess());
        });
    }
}
