package use_case.TimerUseCases.PauseTimerUseCase;

import entity.Timer;
import entity.User;

public class PauseTimerInteractor implements PauseTimerInputBoundary {
    final PauseTimerDataAccessInterface userDataAccessObject;
    final PauseTimerOutputBoundary userPresenter;

    public PauseTimerInteractor(PauseTimerDataAccessInterface setTimerDataAccessInterface,
                                PauseTimerOutputBoundary setTimerOutputBoundary) {
        this.userDataAccessObject = setTimerDataAccessInterface;
        this.userPresenter = setTimerOutputBoundary;
    }

    public void execute(PauseTimerInputData pauseTimerInputData) {
         User user = userDataAccessObject.load();
         Timer timer = user.getTimer();
         PauseTimerOutputData pauseTimerOutputData;
         if (pauseTimerInputData.isPaused()) {
             timer.resume();
             pauseTimerOutputData = new PauseTimerOutputData(false);
         }
         else {
             timer.pause();
             pauseTimerOutputData = new PauseTimerOutputData(true);
         }
         userDataAccessObject.save(user);
         userPresenter.prepareSuccessView(pauseTimerOutputData);

    }
}
