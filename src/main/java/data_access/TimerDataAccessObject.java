package data_access;

import entity.Timer;
import entity.User;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerDataAccessInterface;

public class TimerDataAccessObject implements SetTimerDataAccessInterface {
    FileCacheUserDataAccessObject userDataAccessObject;

    public TimerDataAccessObject(FileCacheUserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }
    @Override
    public void save(Timer timer) {
        try {
            User user = userDataAccessObject.ReadFromCache();
            if (user != null) {
                user.addTimer(timer);
            }
            userDataAccessObject.WriteToCache(user);
        }
        catch (Exception e) {

        }

    }
}
