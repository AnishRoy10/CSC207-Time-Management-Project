package use_case.RemoveFriendUseCase;

import entity.FriendsList;
import data_access.FileCacheUserDataAccessObject;
import entity.User;

import java.io.IOException;

public class RemoveFriendInteractor implements RemoveFriendInputBoundary{
    private final RemoveFriendOutputBoundary removeFriendOutputBoundary;
    private final FileCacheUserDataAccessObject dao;
    public RemoveFriendInteractor(FriendsList friendsList, RemoveFriendOutputBoundary removeFriendOutputBoundary, String name) throws IOException {
        this.removeFriendOutputBoundary = removeFriendOutputBoundary;
        this.dao = new FileCacheUserDataAccessObject();
    }

    @Override
    public void execute(RemoveFriendInputData inputData) throws IOException, ClassNotFoundException {
        if(dao.ReadFromCache() == null){
            return;
        }
        User user = dao.ReadFromCache();
        user.getFriends().removeUserByName(inputData.getFriend_name());
        RemoveFriendOutputData outputData = new RemoveFriendOutputData(user.getFriends());

    }
}
