package interface_adapter.viewmodel;

import framework.view.RunningTimerView;
import interface_adapter.ViewModel;

/**
 * ViewModel for the running timer view, providing a data structure to
 * hold the timer data.
 */
public class RunningTimerViewModel extends ViewModel {

    public static final String TITLE_LABEL = "Timer View";
    public static String PAUSE_LABEL = "Pause";
    public static final String RETURN_LABEL = "Return";
    public static String HOURS;
    public static String MINUTES;
    public static String SECONDS;

    private String message;

    public RunningTimerViewModel(String viewName) {
        super(viewName);
    }

    public static void setHOURS(String HOURS) {
        RunningTimerViewModel.HOURS = HOURS;
    }

    public static void setMINUTES(String MINUTES) {
        RunningTimerViewModel.MINUTES = MINUTES;
    }

    public static void setSECONDS(String SECONDS) {
        RunningTimerViewModel.SECONDS = SECONDS;
    }

    public static void setPauseLabel(String pauseLabel) {
        RunningTimerViewModel.PAUSE_LABEL = pauseLabel;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}
