package use_case.TimerUseCases.PauseTimerUseCase;

import entity.Timer;
import entity.User;

/**
 * Class representing the use case interactor for the pause timer use case.
 */
public class PauseTimerInteractor implements PauseTimerInputBoundary {
    final PauseTimerDataAccessInterface userDataAccessObject;
    final PauseTimerOutputBoundary userPresenter;

    /**
     * Constructor for PauseTimerInteractor.
     * @param setTimerDataAccessInterface data access interface allowing the interactor to access the data access object
     * @param setTimerOutputBoundary output boundary allowing the interactor to access the presenter
     */
    public PauseTimerInteractor(PauseTimerDataAccessInterface setTimerDataAccessInterface,
                                PauseTimerOutputBoundary setTimerOutputBoundary) {
        this.userDataAccessObject = setTimerDataAccessInterface;
        this.userPresenter = setTimerOutputBoundary;
    }

    /**
     * Executes the pause timer use case. If timer is paused, resumes the timer via the timer entity
     * and the opposite otherwise.
     * @param pauseTimerInputData input data for pause timer use case
     */
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
