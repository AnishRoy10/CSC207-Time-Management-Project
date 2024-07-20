package data_access;

import entity.Course;
import entity.Timer;
import entity.User;
import use_case.SetTimerUseCase.SetTimerDataAccessInterface;
import use_case.UpdateTimerUseCase.UpdateTimerDataAccessInterface;

public class InMemoryTimerDataAccessObject implements SetTimerDataAccessInterface,
        UpdateTimerDataAccessInterface {
    private User user;

    @Override
    public void save(Timer timer) {
        user = new User("", "", new User[0], new Course[0]);
        user.addTimer(timer);
    }

    @Override
    public User load() {
        return user;
    }
}
