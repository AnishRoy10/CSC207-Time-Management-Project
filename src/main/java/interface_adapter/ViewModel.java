package interface_adapter;

public abstract class ViewModel {

    private String viewName;

    public ViewModel(String viewName) {
        this.viewName = viewName;
    }
    public String getViewName() {
        return viewName;
    }

}
