package use_case.UpdateTimerUseCase;

import entity.Timer;
import entity.User;

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

        int hours = (int) (remaining_time / 36000000);
        int minutes = (int) ((remaining_time - hours*36000000) / 60000);
        int seconds = (int) ((remaining_time - hours*36000000 - minutes*60000) / 1000);

        UpdateTimerOutputData updateTimerOutputData = new UpdateTimerOutputData(hours, minutes, seconds);
        userPresenter.prepareSuccessView(updateTimerOutputData);
    }
}
