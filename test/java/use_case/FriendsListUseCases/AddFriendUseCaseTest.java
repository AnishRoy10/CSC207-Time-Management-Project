package use_case.FriendsListUseCases;

import data_access.FriendsListDataAccessObject;
import entity.Course;
import entity.User;
import data_access.FileCacheUserDataAccessObject;
import interface_adapter.presenter.FriendsListPresenter;
import interface_adapter.viewmodel.FriendsListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputData;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInteractor;


import java.io.File;
import java.io.IOException;


import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AddFriendUseCaseTest {
    private FileCacheUserDataAccessObject fileCacheUserDAO;
    private final String testFilePath = "friendsusecasetest.json";
    private FriendsListDataAccessObject dao;

    @BeforeEach
    void setUp() throws IOException {
        fileCacheUserDAO = new FileCacheUserDataAccessObject(testFilePath);
        dao = new FriendsListDataAccessObject(fileCacheUserDAO);
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(testFilePath);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        User user2 = new User("testUser2", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            fileCacheUserDAO.WriteToCache(user2);
            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser2");

            addFriendUseCase.execute(inputData);

            assert viewModel.getFriendsList().exportFriendsNames().size() == 1;
        });
    }

    @Test
    void testAddMultipleFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        User user2 = new User("testUser2", "password", new User[]{}, new Course[]{});
        User user3 = new User("testUser3", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            fileCacheUserDAO.WriteToCache(user2);
            fileCacheUserDAO.WriteToCache(user3);
            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            addFriendUseCase.execute(inputData);

            AddFriendInputData inputData2 = new AddFriendInputData("testUser3");
            addFriendUseCase.execute(inputData2);

            assert viewModel.getFriendsList().exportFriendsNames().size() == 2;
        });
    }

    @Test
    void testAddSelfToFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);

            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser");
            addFriendUseCase.execute(inputData);

            assert viewModel.getFriendsList().exportFriendsNames().isEmpty();
        });
    }

    @Test
    void testAddNonexistentToFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertThrows(NullPointerException.class, () -> {
            fileCacheUserDAO.WriteToCache(user);

            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("test");
            addFriendUseCase.execute(inputData);
        });
    }
}
