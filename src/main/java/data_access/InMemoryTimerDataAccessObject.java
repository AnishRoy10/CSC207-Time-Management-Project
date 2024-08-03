package data_access;

import entity.Course;
import entity.Timer;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerDataAccessInterface;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerDataAccessInterface;
import entity.User;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerDataAccessInterface;

public class InMemoryTimerDataAccessObject implements SetTimerDataAccessInterface,
        UpdateTimerDataAccessInterface, PauseTimerDataAccessInterface {
    private User user;

    @Override
    public User load() {
        return user;
    }

    @Override
    public void save(User user) {
        this.user = user;
    }
}
