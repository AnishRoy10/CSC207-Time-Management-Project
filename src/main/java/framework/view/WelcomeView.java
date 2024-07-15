package framework.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import app.gui.LoginInitializer;
import app.gui.SignupInitializer;

/**
 * The WelcomeView class represents the initial screen of the application,
 * offering options for the user to either log in or sign up.
 * This view serves as the entry point for users, guiding them to the authentication process.
 */
public class WelcomeView extends JFrame {
    /**
     * Constructor for WelcomeView. Sets up the UI components and layout for the welcome screen.
     */
    public WelcomeView() {
        setTitle("Welcome");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    /**
     * Initializes the components of the WelcomeView.
     * This method sets up the buttons for login and sign up, along with their action listeners.
     */
    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(2, 1));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginInitializer.initializeLogin();
                dispose(); // Close the welcome view
            }
        });

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.PLAIN, 20));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupInitializer.initializeSignup();
                dispose(); // Close the welcome view
            }
        });

        panel.add(loginButton);
        panel.add(signupButton);

        add(panel);
    }

    /**
     * The main method serves as the entry point for the application.
     * It creates and displays the WelcomeView window, guiding the user to either log in or sign up.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeView welcomeView = new WelcomeView();
            welcomeView.setVisible(true);
        });
    }
}