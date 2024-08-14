package use_case.FriendsListUseCases;

import data_access.FriendsListDataAccessObject;
import data_access.SQLDatabaseHelper;
import entity.Course;
import entity.User;
import interface_adapter.presenter.FriendsListPresenter;
import interface_adapter.viewmodel.FriendsListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputData;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInteractor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddFriendUseCaseTest {
    private SQLDatabaseHelper dbHelper;
    private FriendsListDataAccessObject dao;

    @BeforeEach
    void setUp() throws IOException {
        dbHelper = new SQLDatabaseHelper("jdbc:sqlite:saves/TestDB.db");
        dbHelper.initializeDatabase();
        dao = new FriendsListDataAccessObject(dbHelper);
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = dbHelper.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        User user2 = new User("testUser2", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            dao.writeUser(user);
            dao.writeUser(user2);
            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            addFriendUseCase.execute(inputData);

            // Reload user to get updated friends list
            User updatedUser = dao.loadUser("testUser");
            System.out.println("Friends List after adding testUser2: " + updatedUser.getFriends().exportFriendsNames());
            assertEquals(1, updatedUser.getFriends().exportFriendsNames().size());
        });
    }

    @Test
    void testAddMultipleFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        User user2 = new User("testUser2", "password", new User[]{}, new Course[]{});
        User user3 = new User("testUser3", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            dao.writeUser(user);
            dao.writeUser(user2);
            dao.writeUser(user3);
            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            addFriendUseCase.execute(inputData);

            AddFriendInputData inputData2 = new AddFriendInputData("testUser3");
            addFriendUseCase.execute(inputData2);

            // Reload user to get updated friends list
            User updatedUser = dao.loadUser("testUser");
            System.out.println("Friends List after adding testUser2 and testUser3: " + updatedUser.getFriends().exportFriendsNames());
            assertEquals(2, updatedUser.getFriends().exportFriendsNames().size());
        });
    }

    @Test
    void testAddSelfToFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            dao.writeUser(user);

            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser");
            addFriendUseCase.execute(inputData);

            // Reload user to get updated friends list
            User updatedUser = dao.loadUser("testUser");
            System.out.println("Friends List after trying to add self: " + updatedUser.getFriends().exportFriendsNames());
            assertEquals(0, updatedUser.getFriends().exportFriendsNames().size());
        });
    }

    @Test
    void testAddNonexistentToFriendUseCase() throws IOException {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            dao.writeUser(user);
        });
        FriendsListViewModel viewModel = new FriendsListViewModel();
        FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
        AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");

        AddFriendInputData inputData = new AddFriendInputData("nonexistentUser");
        Exception exception = assertThrows(IOException.class, () -> addFriendUseCase.execute(inputData));
        assertEquals("User not found", exception.getMessage());
    }
}
