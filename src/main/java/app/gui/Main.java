package app.gui;

import javax.swing.SwingUtilities;
import framework.view.WelcomeView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeView welcomeView = new WelcomeView();
            welcomeView.setVisible(true);
        });
    }
}