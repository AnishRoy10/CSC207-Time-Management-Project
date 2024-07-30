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

    /**
     * RunningTimerViewModel constructor.
     * @param viewName name of the view
     */
    public RunningTimerViewModel(String viewName) {
        super(viewName);
    }

    /**
     * Sets the text for the hours label.
     * @param HOURS hours left in the timer
     */
    public static void setHOURS(String HOURS) {
        RunningTimerViewModel.HOURS = HOURS;
    }

    /**
     * Sets the text for the minutes label.
     * @param MINUTES minutes left in the timer
     */
    public static void setMINUTES(String MINUTES) {
        RunningTimerViewModel.MINUTES = MINUTES;
    }

    /**
     * Sets the text for the seconds label.
     * @param SECONDS seconds left in the timer
     */
    public static void setSECONDS(String SECONDS) {
        RunningTimerViewModel.SECONDS = SECONDS;
    }

    /**
     * Sets the text for the pause label.
     * @param pauseLabel string saying paused/resume depending on the state of timer
     */
    public static void setPauseLabel(String pauseLabel) {
        RunningTimerViewModel.PAUSE_LABEL = pauseLabel;
    }

    /**
     * Gets the message set after use case completion.
     * @return string representing whether the use case was successful or not
     */
    public String getMessage() { return message; }

    /**
     * Sets the message representing whether the use case was successful.
     * @param message string to set the message to
     */
    public void setMessage(String message) { this.message = message; }
}
