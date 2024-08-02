package interface_adapter;

/**
 * Abstract class for view model.
 */
public abstract class ViewModel {

    private String viewName;

    /**
     * Constructor for ViewModel.
     * @param viewName name of view model
     */
    public ViewModel(String viewName) {
        this.viewName = viewName;
    }

    /**
     * Getter for view name
     * @return name of view model
     */
    public String getViewName() {
        return viewName;
    }

}
