package java.use_case.CourseUseCases.ViewCourseUseCase;

import com.sun.source.tree.AssertTree;
import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import entity.Course;
import interface_adapter.presenter.CourseViewPresenter;
import interface_adapter.viewmodel.CourseViewModel;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;
import repositories.UserRepository;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputData;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseOutputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseUseCase;

import java.io.File;
import java.io.IOException;

public class ViewCourseUseCaseTest {
    private String coursepath = "test_courses.json";
    private CourseRepository courseDataAccessObject;

    @BeforeEach
    public void setUp() throws IOException {
        courseDataAccessObject = new CourseDataAccessObject(coursepath);
    }

    @AfterEach
    public void tearDown() throws IOException {
        new File(coursepath).delete();
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
            Assertions.assertEquals(course.getTodoList().getTasks(), viewModel.getTodoList().getTasks());
            Assertions.assertEquals(course.getDailyLeaderboard().getScores(), viewModel.getDailyLeaderboard().getScores());
            Assertions.assertEquals(course.getMonthlyLeaderboard().getScores(), viewModel.getMonthlyLeaderboard().getScores());
            Assertions.assertEquals(course.getAllTimeLeaderboard().getScores(), viewModel.getAllTimeLeaderboard().getScores());
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
