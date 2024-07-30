package interface_adapter.presenter;

import interface_adapter.viewmodel.SetTimerViewModel;
import interface_adapter.viewmodel.RunningTimerViewModel;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerOutputBoundary;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerOutputData;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerOutputBoundary;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerOutputData;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerOutputBoundary;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerOutputData;

/**
 * Presenter for the timer, implementing the output boundaries for setting the timer,
 * updating the timer, and pausing the timer.
 */
public class TimerPresenter implements SetTimerOutputBoundary, UpdateTimerOutputBoundary,
        PauseTimerOutputBoundary {

    private final SetTimerViewModel setTimerViewModel;
    private final RunningTimerViewModel runningTimerViewModel;

    /**
     * Constructor for TimerPresenter.
     * @param setTimerViewModel view model for SetTimerView
     * @param runningTimerViewModel view model for RunningTimerView
     */
    public TimerPresenter(SetTimerViewModel setTimerViewModel,
                          RunningTimerViewModel runningTimerViewModel) {
        this.setTimerViewModel = setTimerViewModel;
        this.runningTimerViewModel = runningTimerViewModel;

    }

    /**
     * Prepares the view upon successful completion of the set timer use case.
     * @param response output data from the set timer use case
     */
    @Override
    public void prepareSuccessView(SetTimerOutputData response) {
        RunningTimerViewModel.setHOURS(Integer.toString(response.getHours()));
        RunningTimerViewModel.setMINUTES(Integer.toString(response.getMinutes()));
        RunningTimerViewModel.setSECONDS(Integer.toString(response.getSeconds()));
        runningTimerViewModel.setMessage("Success");

    }

    /**
     * Prepares the view upon failure to execute the set timer use case.
     * @param errorMessage string with reason for failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        runningTimerViewModel.setMessage(errorMessage);

    }

    /**
     * Prepares the view upon successful completion of the update timer use case.
     * @param response output data from the update timer use case
     */
    @Override
    public void prepareSuccessView(UpdateTimerOutputData response) {
        RunningTimerViewModel.setHOURS(Integer.toString(response.getHours()));
        RunningTimerViewModel.setMINUTES(Integer.toString(response.getMinutes()));
        RunningTimerViewModel.setSECONDS(Integer.toString(response.getSeconds()));
        runningTimerViewModel.setMessage("Success");
    }

    /**
     * Prepares the view upon successful completion of the pause timer use case.
     * @param response output data from the pause timer use case
     */
    @Override
    public void prepareSuccessView(PauseTimerOutputData response) {
        if (response.isPaused()) {
            RunningTimerViewModel.setPauseLabel("Resume");
        }
        else {
            RunningTimerViewModel.setPauseLabel("Pause");
        }
        runningTimerViewModel.setMessage("Success");
    }
}
