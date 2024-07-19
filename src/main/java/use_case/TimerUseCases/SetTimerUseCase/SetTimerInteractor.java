package use_case.TimerUseCases.SetTimerUseCase;

import entity.Timer;

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

        long timerLength = timer.timerLength();
        int hours = (int) (timerLength / 36000000);
        int minutes = (int) ((timerLength - hours*36000000) / 60000);
        int seconds = (int) ((timerLength - hours*36000000 - minutes*60000) / 1000);
        SetTimerOutputData setTimerOutputData = new SetTimerOutputData(hours, minutes, seconds);
        userPresenter.prepareSuccessView(setTimerOutputData);
    }
}
