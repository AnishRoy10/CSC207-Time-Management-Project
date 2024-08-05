package interface_adapter.controller;

import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerInputBoundary;
import use_case.TimerUseCases.PauseTimerUseCase.PauseTimerInputData;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInputBoundary;
import use_case.TimerUseCases.SetTimerUseCase.SetTimerInputData;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerInputBoundary;
import use_case.TimerUseCases.UpdateTimerUseCase.UpdateTimerInputData;

/**
 * Controller for the timer which handles the set timer use case.
 */
public class TimerController {
    final SetTimerInputBoundary userSetTimerUseCaseInteractor;
    final UpdateTimerInputBoundary userUpdateTimerUseCaseInteractor;
    final PauseTimerInputBoundary userPauseTimerUseCaseInteractor;

    /**
     * Constructs the controller with the Set Timer use case
     * @param userSetTimerUseCaseInteractor use case for setting the timer
     */
    public TimerController(SetTimerInputBoundary userSetTimerUseCaseInteractor,
                           UpdateTimerInputBoundary userUpdateTimerUseCaseInteractor,
                           PauseTimerInputBoundary userPauseTimerUseCaseInteractor) {
        this.userSetTimerUseCaseInteractor = userSetTimerUseCaseInteractor;
        this.userUpdateTimerUseCaseInteractor = userUpdateTimerUseCaseInteractor;
        this.userPauseTimerUseCaseInteractor = userPauseTimerUseCaseInteractor;
    }

    /**
     * Converts each string to integers to be given as inputs for the set timer interactor.
     * @param hours hours the timer lasts for
     * @param minutes minutes the timer lasts for
     * @param seconds seconds the timer lasts for
     */
    public void execute_set_timer(String hours, String minutes, String seconds) {
        int int_hours;
        int int_minutes;
        int int_seconds;

        try {
            int_hours = Integer.parseInt(hours);
            int_minutes = Integer.parseInt(minutes);
            int_seconds = Integer.parseInt(seconds);
        }
        catch (NumberFormatException e) {
            int_hours = 0; // temporary error catch
            int_minutes = 0;
            int_seconds = 0;
        }

        SetTimerInputData setTimerInputData = new SetTimerInputData(int_hours,
                int_minutes, int_seconds);

        userSetTimerUseCaseInteractor.execute(setTimerInputData);
    }

    /**
     * Executes the update timer use case.
     */
    public void execute_update_timer() {
        UpdateTimerInputData updateTimerInputData = new UpdateTimerInputData();
        userUpdateTimerUseCaseInteractor.execute(updateTimerInputData);
    }

    /**
     * Executes the pause timer use case.
     * @param paused boolean which represents whether the timer is currently paused
     */
    public void execute_pause_timer(boolean paused) {
        PauseTimerInputData pauseTimerInputData = new PauseTimerInputData(paused);
        userPauseTimerUseCaseInteractor.execute(pauseTimerInputData);

    }
}
