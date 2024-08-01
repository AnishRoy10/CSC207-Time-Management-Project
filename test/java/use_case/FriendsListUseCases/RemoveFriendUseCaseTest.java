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
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInputData;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInteractor;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RemoveFriendUseCaseTest {
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
    void testRemoveFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        User user2 = new User("testUser2", "password", new User[]{}, new Course[]{});
        assertDoesNotThrow(() -> {
            fileCacheUserDAO.WriteToCache(user);
            fileCacheUserDAO.WriteToCache(user2);
            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            AddFriendInteractor addFriendUseCase = new AddFriendInteractor(presenter, dao, "testUser");
            RemoveFriendInteractor removeFriendUseCase = new RemoveFriendInteractor(presenter, dao, "testUser");

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            addFriendUseCase.execute(inputData);
            //at this point testUser should have testUser2 as a friend
            assert viewModel.getFriendsList().exportFriendsNames().size() == 1;

            RemoveFriendInputData inputData2 = new RemoveFriendInputData("testUser2");
            removeFriendUseCase.execute(inputData2);
            assert viewModel.getFriendsList().exportFriendsNames().isEmpty();
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

            AddFriendInputData inputData = new AddFriendInputData("testUser2");
            addFriendUseCase.execute(inputData);

            AddFriendInputData inputData2 = new AddFriendInputData("testUser3");
            addFriendUseCase.execute(inputData2);

            assert viewModel.getFriendsList().exportFriendsNames().size() == 2;

            RemoveFriendInputData inputData3 = new RemoveFriendInputData("testUser2");
            removeFriendUseCase.execute(inputData3);

            RemoveFriendInputData inputData4 = new RemoveFriendInputData("testUser3");
            removeFriendUseCase.execute(inputData4);

            assert viewModel.getFriendsList().exportFriendsNames().isEmpty();
        });
    }

    @Test
    void testRemoveNonexistentFriendUseCase() {
        User user = new User("testUser", "password", new User[]{}, new Course[]{});
        assertThrows(NullPointerException.class, () -> {
            fileCacheUserDAO.WriteToCache(user);

            FriendsListViewModel viewModel = new FriendsListViewModel();
            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);
            RemoveFriendInteractor removeFriendUseCase = new RemoveFriendInteractor(presenter, dao, "testUser");

            RemoveFriendInputData inputData = new RemoveFriendInputData("test");
            removeFriendUseCase.execute(inputData);
        });
    }
}