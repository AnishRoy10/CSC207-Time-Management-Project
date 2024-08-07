package interface_adapter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInputData;
import use_case.FriendsListUseCases.AddFriendUseCase.AddFriendInteractor;
import use_case.FriendsListUseCases.RefreshFriendsUseCase.RefreshFriendInteractor;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInputData;
import use_case.FriendsListUseCases.RemoveFriendUseCase.RemoveFriendInteractor;


import java.io.IOException;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FriendListControllerTest {
    private FriendsListController controller;
    private AddFriendInteractor addFriendUseCase;
    private RefreshFriendInteractor refreshFriendUseCase;
    private RemoveFriendInteractor removeFriendUseCase;

    @BeforeEach
    public void setUp() throws IOException, ClassNotFoundException {
        addFriendUseCase = mock(AddFriendInteractor.class);
        refreshFriendUseCase = mock(RefreshFriendInteractor.class);
        removeFriendUseCase = mock(RemoveFriendInteractor.class);

        controller = new FriendsListController(addFriendUseCase, refreshFriendUseCase, removeFriendUseCase);

    }

    @Test
    public void testAddFriend() throws IOException, ClassNotFoundException {
        AddFriendInputData expectedInputData = new AddFriendInputData("user1");
        controller.addFriend("user1");
        verify(addFriendUseCase).execute(argThat(request -> request.getUser().equals(expectedInputData.getUser())));
    }
    @Test
    public void testRemoveFriend() throws IOException, ClassNotFoundException {
        RemoveFriendInputData expectedInputData = new RemoveFriendInputData("user1");
        controller.removeFriend("user1");
        verify(removeFriendUseCase).execute(argThat(request -> request.getFriend_name().equals(expectedInputData.getFriend_name())));
    }
}
