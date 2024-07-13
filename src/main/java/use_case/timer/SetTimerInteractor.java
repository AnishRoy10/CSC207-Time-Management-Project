package use_case.timer;

import entity.Timer;
import java.util.concurrent.TimeUnit;

/*
The SetTimeUseCase class handles the use case of setting the time for
the use_case.timer and creating a Timer class to represent the Timer.
 */
public class SetTimerInteractor implements SetTimerInputBoundary {
    final SetTimerDataAccessInterface userDataAccessObject;
    final SetTimerOutputBoundary userPresenter;

    public SetTimerInteractor(SetTimerDataAccessInterface setTimerDataAccessInterface,
                              SetTimerOutputBoundary setTimerOutputBoundary) {
        this.userDataAccessObject = setTimerDataAccessInterface;
        this.userPresenter = setTimerOutputBoundary;
    }


    public void execute(SetTimerInputData setTimerInputData) {
        Timer timer = new Timer(setTimerInputData.getHours(),
                setTimerInputData.getMinutes(),
                setTimerInputData.getSeconds());

        userDataAccessObject.save(timer);

        SetTimerOutputData setTimerOutputData = new SetTimerOutputData();
        userPresenter.prepareSuccessView(setTimerOutputData);
    }
}
