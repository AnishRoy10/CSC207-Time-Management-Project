package use_case.CourseUseCases.CreateCourseUseCase;

import data_access.SQLDatabaseHelper;
import data_access.CourseDataAccessObject;
import data_access.UserDAO;
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
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseInputBoundary;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseInputData;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseOutputBoundary;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseUseCase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateCourseUseCaseTest {
    private UserRepository userDataAccessObject;
    private CourseRepository courseDataAccessObject;
    private SQLDatabaseHelper dbHelper;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    public void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userDataAccessObject = new UserDAO(dbHelper);
        courseDataAccessObject = new CourseDataAccessObject(dbHelper);
    }

    @AfterEach
    public void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Users");
            stmt.execute("DELETE FROM Courses");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testValidCreation() {
        CoursePromptViewModel viewModel = new CoursePromptViewModel();
        CreateCourseOutputBoundary presenter = new CoursePromptPresenter(viewModel, null, null);
        CreateCourseInputBoundary interactor = new CreateCourseUseCase(presenter, courseDataAccessObject, userDataAccessObject);

        User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
        try {
            userDataAccessObject.WriteToCache(user);
        } catch (Exception e) {
            Assertions.fail("Exception thrown while saving user: " + e.getMessage());
        }

        CreateCourseInputData inputData = new CreateCourseInputData(
                user.getUsername(),
                "Test Course",
                "Test Description"
        );

        interactor.execute(inputData);

        Assertions.assertTrue(viewModel.getResponse());
        User updatedUser = null;
        try {
            updatedUser = userDataAccessObject.ReadFromCache("Test User");
        } catch (Exception e) {
            Assertions.fail("Exception thrown while reading user: " + e.getMessage());
        }
        Assertions.assertNotNull(updatedUser);
        Assertions.assertFalse(updatedUser.getCourses().isEmpty());
        Assertions.assertTrue(courseDataAccessObject.courseExists("Test Course"));
    }

    @Test
    public void testCourseExists() {
        Assertions.assertDoesNotThrow(() -> {
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            CreateCourseOutputBoundary presenter = new CoursePromptPresenter(viewModel, null, null);
            CreateCourseInputBoundary interactor = new CreateCourseUseCase(presenter, courseDataAccessObject, userDataAccessObject);

            User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
            Course course = new Course("Test Course", "Test Description");

            try {
                userDataAccessObject.WriteToCache(user);
                courseDataAccessObject.WriteToCache(course);
            } catch (Exception e) {
                Assertions.fail("Exception thrown while saving user or course: " + e.getMessage());
            }

            CreateCourseInputData inputData = new CreateCourseInputData(
                    user.getUsername(),
                    "Test Course",
                    "Test Description"
            );

            interactor.execute(inputData);

            Assertions.assertFalse(viewModel.getResponse());
            User updatedUser = null;
            try {
                updatedUser = userDataAccessObject.ReadFromCache("Test User");
            } catch (Exception e) {
                Assertions.fail("Exception thrown while reading user: " + e.getMessage());
            }
            Assertions.assertNotNull(updatedUser);
            Assertions.assertTrue(updatedUser.getCourses().isEmpty());
            Assertions.assertTrue(courseDataAccessObject.courseExists("Test Course"));
        });
    }
}
