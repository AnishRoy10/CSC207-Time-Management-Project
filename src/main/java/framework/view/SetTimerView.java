package framework.view;

import interface_adapter.setTimer.SetTimerController;
import interface_adapter.setTimer.SetTimerPresenter;
import interface_adapter.setTimer.SetTimerViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SetTimerView extends JPanel implements ActionListener, PropertyChangeListener{
    public final String viewName = "Set timer";

    private final SetTimerViewModel setTimerViewModel;
    private final SetTimerController setTimerController;

    public SetTimerView(SetTimerController setTimerController,
                        SetTimerViewModel setTimerViewModel) {
        this.setTimerController = setTimerController;
        this.setTimerViewModel = setTimerViewModel;
        setTimerViewModel.addPropertyChangeListener(this);
    }

    public void actionPerformed(ActionEvent evt) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
