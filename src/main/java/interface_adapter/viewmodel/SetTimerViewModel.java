package interface_adapter.viewmodel;

import interface_adapter.ViewModel;

/**
 * ViewModel for the set timer view, providing a data structure to hold label titles.
 */
public class SetTimerViewModel extends ViewModel {

    public static final String TITLE_LABEL = "Set Timer View";
    public static final String HOURS_LABEL = "Hours";
    public static final String MINUTES_LABEL = "Minutes";
    public static final String SECONDS_LABEL = "Seconds";

    public static final String SET_TIMER_BUTTON_LABEL = "Set Timer";


    public SetTimerViewModel(String viewName) {
        super(viewName);
    }

}
