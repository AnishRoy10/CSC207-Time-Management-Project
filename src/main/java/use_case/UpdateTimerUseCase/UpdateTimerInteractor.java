package use_case.UpdateTimerUseCase;

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
        //execute use case
    }
}
