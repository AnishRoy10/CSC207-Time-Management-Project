package use_case.TimerUseCases.UpdateTimerUseCase;

import entity.Timer;
import entity.User;
/**
 Use case interactor for the update timer use case.
 */
public class UpdateTimerInteractor implements UpdateTimerInputBoundary {
    final UpdateTimerDataAccessInterface userDataAccessObject;
    final UpdateTimerOutputBoundary userPresenter;

    public UpdateTimerInteractor(UpdateTimerDataAccessInterface userDataAccessObject,
                                 UpdateTimerOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

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
