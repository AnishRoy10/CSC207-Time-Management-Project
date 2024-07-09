package interface_adapter.setTimer;

import use_case.timer.SetTimerInputBoundary;
import use_case.timer.SetTimerInputData;

public class SetTimerController {
    final SetTimerInputBoundary userSetTimerUseCaseInteractor;

    public SetTimerController(SetTimerInputBoundary userSetTimerUseCaseInteractor) {
        this.userSetTimerUseCaseInteractor = userSetTimerUseCaseInteractor;
    }

    public void execute(String hours, String minutes, String seconds) {
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
}
