package use_case.SetTimerUseCase;

import entity.Timer;

/**
Use case interactor for the set timer use case.
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
        int hours = (int) (timerLength / 3600000);
        int minutes = (int) ((timerLength - hours*3600000) / 60000);
        int seconds = (int) ((timerLength - hours*3600000 - minutes*60000) / 1000);

        SetTimerOutputData setTimerOutputData = new SetTimerOutputData(hours, minutes, seconds);
        userPresenter.prepareSuccessView(setTimerOutputData);
    }
}
