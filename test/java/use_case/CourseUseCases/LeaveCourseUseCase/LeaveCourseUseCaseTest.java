package use_case.CourseUseCases.LeaveCourseUseCase;

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
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputBoundary;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputData;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseOutputBoundary;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseUseCase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaveCourseUseCaseTest {
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

//    @Test
//    public void testLeaveCourseValid() throws IOException {
//        CoursePromptViewModel viewModel = new CoursePromptViewModel();
//        LeaveCourseOutputBoundary presenter = new CoursePromptPresenter(null, null, viewModel);
//        LeaveCourseInputBoundary interactor = new LeaveCourseUseCase(presenter, userDataAccessObject, courseDataAccessObject);
//
//        User user = new User("Test User", "TestPassword1", new User[]{}, new Course[]{});
//        Course course = new Course("Test Course", "Test Description");
//
//        user.addCourse(course);
//        course.addUser(user);
//
//        userDataAccessObject.WriteToCache(user);
//        courseDataAccessObject.WriteToCache(course);
//
//        Assertions.assertTrue(course.containsUser("Test User"));
//        Assertions.assertTrue(user.getCourses().contains("Test Course"));
//
//        LeaveCourseInputData inputData = new LeaveCourseInputData(
//                user.getUsername(),
//                course.getName()
//        );
//
//        interactor.execute(inputData);
//
//        Assertions.assertTrue(viewModel.getResponse());
//
//        User updatedUser = userDataAccessObject.ReadFromCache("Test User");
//        Course updatedCourse = courseDataAccessObject.findByName("Test Course");
//        Assertions.assertTrue(updatedUser.getCourses().isEmpty());
//        Assertions.assertEquals(0, updatedCourse.getUserNames().length);
//    }

    @Test
    public void testNotInCourse() throws IOException {
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

        User updatedUser = userDataAccessObject.ReadFromCache("Test User");
        Course updatedCourse = courseDataAccessObject.findByName("Test Course");
        Assertions.assertTrue(updatedUser.getCourses().isEmpty());
        Assertions.assertEquals(0, updatedCourse.getUserNames().length);
    }

    @Test
    public void testCourseDoesNotExist() throws IOException {
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
    }
}
