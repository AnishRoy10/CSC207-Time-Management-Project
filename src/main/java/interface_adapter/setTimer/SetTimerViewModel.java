package interface_adapter.setTimer;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class SetTimerViewModel extends ViewModel {

    public SetTimerViewModel(String viewName) {
        super(viewName);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void firePropertyChanged() {

    }
}
