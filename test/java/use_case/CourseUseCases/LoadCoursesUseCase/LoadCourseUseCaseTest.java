package use_case.CourseUseCases.LoadCoursesUseCase;

import data_access.SQLDatabaseHelper;
import data_access.UserDAO;
import entity.Course;
import entity.User;
import interface_adapter.presenter.CourseListPresenter;
import interface_adapter.viewmodel.CourseListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.UserRepository;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputData;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesOutputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesUseCase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadCourseUseCaseTest {
    private UserRepository userDataAccessObject;
    private SQLDatabaseHelper dbHelper;
    private static final String DB_URL = "jdbc:sqlite:Saves/TestDB.db";

    @BeforeEach
    public void setUp() {
        dbHelper = new SQLDatabaseHelper(DB_URL);
        dbHelper.initializeDatabase();
        userDataAccessObject = new UserDAO(dbHelper);
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
