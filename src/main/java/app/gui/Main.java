package app.gui;

import javax.swing.SwingUtilities;
import framework.view.WelcomeView;

/**
 * The Main class serves as the entry point for the application.
 * It initializes and displays the welcome view, prompting the user to log in or sign up.
 */
public class Main {
    /**
     * The main method is the entry point of the application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeView welcomeView = new WelcomeView();
            welcomeView.setVisible(true); // Make the welcome view visible to the user
        });
    }
}