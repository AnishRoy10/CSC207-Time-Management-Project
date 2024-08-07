package use_case.FriendsListUseCases;

import data_access.FileCacheUserDataAccessObject;
import data_access.FriendsListDataAccessObject;
import entity.Course;
import entity.User;
import interface_adapter.presenter.FriendsListPresenter;
import interface_adapter.viewmodel.FriendsListViewModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputData;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInteractor;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendInputData;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendInteractor;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInputData;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInteractor;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RefreshFriendUseCaseTest {
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
    void testRefreshOneNewFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        User user2 = new User("testUser2", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            fileCacheUserDAO.WriteToCache(user2);
            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");
            RefreshFriendInteractor refreshFriendUseCase = new RefreshFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            RefreshFriendInputData inputData2 = new RefreshFriendInputData();

            addFriendUseCase.execute(inputData);
            assert viewModel.getDisplayedListModel().isEmpty();

            refreshFriendUseCase.execute(inputData2);
            assert viewModel.getDisplayedListModel().size() == 1;
        });
    }

    @Test
    void testRefreshOneLessFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        User user2 = new User("testUser2", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            fileCacheUserDAO.WriteToCache(user2);
            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");
            RefreshFriendInteractor refreshFriendUseCase = new RefreshFriendInteractor(presenter, dao, "testUser");
            RemoveFriendInteractor removeFriendUseCase = new RemoveFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            RefreshFriendInputData inputData2 = new RefreshFriendInputData();

            addFriendUseCase.execute(inputData);
            assert viewModel.getDisplayedListModel().isEmpty();

            refreshFriendUseCase.execute(inputData2);
            assert viewModel.getDisplayedListModel().size() == 1;

            RemoveFriendInputData inputData3 = new RemoveFriendInputData("testUser2");
            removeFriendUseCase.execute(inputData3);

            refreshFriendUseCase.execute(inputData2);
            assert viewModel.getDisplayedListModel().isEmpty();

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
            RefreshFriendInteractor refreshFriendUseCase = new RefreshFriendInteractor(presenter, dao, "testUser");
            RefreshFriendInputData inputData3 = new RefreshFriendInputData();

            refreshFriendUseCase.execute(inputData3);
            assert viewModel.getDisplayedListModel().isEmpty();

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            addFriendUseCase.execute(inputData);

            AddFriendInputData inputData2 = new AddFriendInputData("testUser3");
            addFriendUseCase.execute(inputData2);

            assert viewModel.getFriendsList().exportFriendsNames().size() == 2;

            refreshFriendUseCase.execute(inputData3);
            assert viewModel.getDisplayedListModel().size() == 2;
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
            RefreshFriendInteractor refreshFriendUseCase = new RefreshFriendInteractor(presenter, dao, "testUser");
            RefreshFriendInputData inputData1 = new RefreshFriendInputData();

            AddFriendInputData inputData = new AddFriendInputData("testUser");
            refreshFriendUseCase.execute(inputData1);
            assert viewModel.getDisplayedListModel().isEmpty();

            addFriendUseCase.execute(inputData);
            refreshFriendUseCase.execute(inputData1);
            assert viewModel.getDisplayedListModel().isEmpty();
        });
    }
    @Test
    void RemoveMultipleFriendUseCase() {
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
            RemoveFriendInteractor removeFriendUseCase = new RemoveFriendInteractor(presenter, dao, "testUser");
            RefreshFriendInteractor refreshFriendUseCase = new RefreshFriendInteractor(presenter, dao, "testUser");
            RefreshFriendInputData inputData1 = new RefreshFriendInputData();

            refreshFriendUseCase.execute(inputData1);
            assert viewModel.getDisplayedListModel().isEmpty();

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            addFriendUseCase.execute(inputData);

            AddFriendInputData inputData2 = new AddFriendInputData("testUser3");
            addFriendUseCase.execute(inputData2);

            assert viewModel.getFriendsList().exportFriendsNames().size() == 2;
            refreshFriendUseCase.execute(inputData1);

            assert viewModel.getDisplayedListModel().size() == 2;

            RemoveFriendInputData inputData3 = new RemoveFriendInputData("testUser2");
            removeFriendUseCase.execute(inputData3);

            RemoveFriendInputData inputData4 = new RemoveFriendInputData("testUser3");
            removeFriendUseCase.execute(inputData4);

            refreshFriendUseCase.execute(inputData1);
            assert viewModel.getDisplayedListModel().isEmpty();
        });
    }
}
