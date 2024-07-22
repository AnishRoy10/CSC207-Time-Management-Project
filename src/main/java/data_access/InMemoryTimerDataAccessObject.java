package data_access;

import entity.Course;
import entity.Timer;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerDataAccessInterface;
import entity.User;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerDataAccessInterface;

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
