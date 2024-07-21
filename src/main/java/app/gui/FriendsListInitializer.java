package app.gui;

import data_access.FileCacheUserDataAccessObject;
import data_access.FriendsListDataAccessObject;
import framework.view.FriendsListView;
import interface_adapter.controller.FriendsListController;
import interface_adapter.presenter.FriendsListPresenter;
import interface_adapter.viewmodel.FriendsListViewModel;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputBoundary;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInteractor;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendInteractor;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendsInputBoundary;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInputBoundary;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInteractor;

import javax.swing.*;
import java.io.IOException;

public class FriendsListInitializer {
    public static void InitializeFriendsList(String username) {
        try {
            String activeDir = System.getProperty("user.dir");
            String filePath = (activeDir + "\\src\\main\\java\\data_access\\userCache.json");
            FileCacheUserDataAccessObject cacheDao = new FileCacheUserDataAccessObject(filePath);
            FriendsListDataAccessObject dao = new FriendsListDataAccessObject(cacheDao);

            FriendsListViewModel viewModel = new FriendsListViewModel();

            FriendsListPresenter presenter = new FriendsListPresenter(viewModel);

            AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(presenter, dao, username);

            RefreshFriendsInputBoundary refreshFriendsInteractor = new RefreshFriendInteractor(presenter, dao, username);

            RemoveFriendInputBoundary removeFriendInteractor = new RemoveFriendInteractor(presenter, dao, username);

            FriendsListController controller = new FriendsListController(addFriendInteractor, refreshFriendsInteractor, removeFriendInteractor);

            FriendsListView view = new FriendsListView(controller, viewModel, username);

            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error initializing FriendsList");
        }
    }
}
