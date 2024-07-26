package interface_adapter.controller;

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

    /**
     * Constructs the controller with the Set Timer use case
     * @param userSetTimerUseCaseInteractor use case for setting the timer
     */
    public TimerController(SetTimerInputBoundary userSetTimerUseCaseInteractor,
                           UpdateTimerInputBoundary userUpdateTimerUseCaseInteractor) {
        this.userSetTimerUseCaseInteractor = userSetTimerUseCaseInteractor;
        this.userUpdateTimerUseCaseInteractor = userUpdateTimerUseCaseInteractor;
    }

    /**
     * Sets the timer for the user.
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

    public void execute_update_timer() {
        UpdateTimerInputData updateTimerInputData = new UpdateTimerInputData();
        userUpdateTimerUseCaseInteractor.execute(updateTimerInputData);
    }
}
