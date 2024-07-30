package data_access;

import entity.User;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerDataAccessInterface;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerDataAccessInterface;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerDataAccessInterface;

/**
 * Class representing the data access object for timer use cases.
 */
public class TimerDataAccessObject implements SetTimerDataAccessInterface,
        UpdateTimerDataAccessInterface, PauseTimerDataAccessInterface {
    FileCacheUserDataAccessObject userDataAccessObject;

    /**
     * Constructor for TimerDataAccessObject.
     * @param userDataAccessObject data access object to get the user entity for current user
     */
    public TimerDataAccessObject(FileCacheUserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    /**
     * Loads the user entity of the current user.
     * @return user entity of the current user.
     */
    @Override
    public User load() {
        try {
            return userDataAccessObject.ReadFromCache();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the user entity of the current user.
     * @param user user entity to be saved to cache
     */
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
