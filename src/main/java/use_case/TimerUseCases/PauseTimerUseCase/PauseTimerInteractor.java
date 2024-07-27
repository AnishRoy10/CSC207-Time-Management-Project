package use_case.TimerUseCases.PauseTimerUseCase;

public class PauseTimerInteractor implements PauseTimerInputBoundary {
    final PauseTimerDataAccessInterface userDataAccessObject;
    final PauseTimerOutputBoundary userPresenter;

    public PauseTimerInteractor(PauseTimerDataAccessInterface setTimerDataAccessInterface,
                                PauseTimerOutputBoundary setTimerOutputBoundary) {
        this.userDataAccessObject = setTimerDataAccessInterface;
        this.userPresenter = setTimerOutputBoundary;
    }

    public void execute(PauseTimerInputData pauseTimerInputData) {

    }
}
