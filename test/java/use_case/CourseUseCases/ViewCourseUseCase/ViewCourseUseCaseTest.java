package use_case.CourseUseCases.ViewCourseUseCase;

import data_access.CourseDataAccessObject;
import data_access.SQLDatabaseHelper;
import entity.Course;
import interface_adapter.presenter.CourseViewPresenter;
import interface_adapter.viewmodel.CourseViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.CourseRepository;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputData;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseOutputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseUseCase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewCourseUseCaseTest {
    private CourseRepository courseDataAccessObject;
    private SQLDatabaseHelper dbHelper;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    public void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        courseDataAccessObject = new CourseDataAccessObject(dbHelper);
    }

    @AfterEach
    public void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Courses");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
