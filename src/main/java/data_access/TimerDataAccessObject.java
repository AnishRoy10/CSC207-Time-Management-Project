package data_access;

import entity.Timer;
import entity.User;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerDataAccessInterface;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerDataAccessInterface;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerDataAccessInterface;

public class TimerDataAccessObject implements SetTimerDataAccessInterface,
        UpdateTimerDataAccessInterface, PauseTimerDataAccessInterface {
    FileCacheUserDataAccessObject userDataAccessObject;

    public TimerDataAccessObject(FileCacheUserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public User load() {
        try {
            return userDataAccessObject.ReadFromCache();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        try {
            userDataAccessObject.WriteToCache(user);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
