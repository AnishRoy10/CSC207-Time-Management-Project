package use_case.TimerUseCases.UpdateTimerUseCase;

import entity.Timer;
import entity.User;
/**
 * Class representing the use case interactor for the update timer use case.
 */
public class UpdateTimerInteractor implements UpdateTimerInputBoundary {
    final UpdateTimerDataAccessInterface userDataAccessObject;
    final UpdateTimerOutputBoundary userPresenter;

    /**
     * Constructor for UpdateTimerInteractor
     * @param userDataAccessObject data access interface allowing the interactor to access the data access object
     * @param userPresenter output boundary allowing the interactor to access the presenter
     */
    public UpdateTimerInteractor(UpdateTimerDataAccessInterface userDataAccessObject,
                                 UpdateTimerOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

    /**
     * Executes the update timer use case. Updates elapsed time in the Timer entity.
     * @param updateTimerInputData input data for update timer use case
     */
    @Override
    public void execute(UpdateTimerInputData updateTimerInputData) {
        User user = userDataAccessObject.load();
        Timer timer = user.getTimer();
        timer.updateElapsed_time();
        long remaining_time = timer.timerLength() - timer.getElapsed_time();

        int hours = (int) (remaining_time / 3600000);
        int minutes = (int) ((remaining_time - hours*3600000) / 60000);
        int seconds = (int) ((remaining_time - hours*3600000 - minutes*60000) / 1000);

        UpdateTimerOutputData updateTimerOutputData = new UpdateTimerOutputData(hours, minutes, seconds);
        userPresenter.prepareSuccessView(updateTimerOutputData);
    }
}
