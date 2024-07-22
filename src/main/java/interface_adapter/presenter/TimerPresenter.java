package interface_adapter.presenter;

import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerOutputBoundary;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerOutputData;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerOutputBoundary;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerOutputData;

/**
 * Presenter for the timer, implementing the output boundaries for setting the timer.
 */
public class TimerPresenter implements SetTimerOutputBoundary, UpdateTimerOutputBoundary {

    private final SetTimerViewModel setTimerViewModel;
    private final RunningTimerViewModel runningTimerViewModel;

    public TimerPresenter(SetTimerViewModel setTimerViewModel,
                          RunningTimerViewModel runningTimerViewModel) {
        this.setTimerViewModel = setTimerViewModel;
        this.runningTimerViewModel = runningTimerViewModel;

    }

    @Override
    public void prepareSuccessView(SetTimerOutputData response) {
        RunningTimerViewModel.setHOURS(Integer.toString(response.getHours()));
        RunningTimerViewModel.setMINUTES(Integer.toString(response.getMinutes()));
        RunningTimerViewModel.setSECONDS(Integer.toString(response.getSeconds()));
        runningTimerViewModel.setMessage("Success");

    }

    @Override
    public void prepareFailView(String errorMessage) {
        runningTimerViewModel.setMessage(errorMessage);

    }

    @Override
    public void prepareSuccessView(UpdateTimerOutputData response) {
        RunningTimerViewModel.setHOURS(Integer.toString(response.getHours()));
        RunningTimerViewModel.setMINUTES(Integer.toString(response.getMinutes()));
        RunningTimerViewModel.setSECONDS(Integer.toString(response.getSeconds()));
        runningTimerViewModel.setMessage("Success");
    }
}
