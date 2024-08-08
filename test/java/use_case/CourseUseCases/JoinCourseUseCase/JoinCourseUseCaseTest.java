package use_case.CourseUseCases.JoinCourseUseCase;

import data_access.CourseDataAccessObject;
import data_access.SQLDatabaseHelper;
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
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseInputBoundary;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseInputData;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseOutputBoundary;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseUseCase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class JoinCourseUseCaseTest {
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
