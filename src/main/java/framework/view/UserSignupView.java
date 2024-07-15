package framework.view;

import interface_adapter.controller.UserSignupController;
import interface_adapter.viewmodel.UserSignupViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * The SignupView class represents the user interface for the signup process.
 */
public class UserSignupView extends JFrame {
    private final UserSignupController signupController;
    private final UserSignupViewModel signupViewModel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel messageLabel;
    private JButton signupButton;

    /**
     * Constructs the SignupView with the specified controller and view model.
     *
     * @param signupController The controller for handling signup actions.
     * @param signupViewModel  The view model for displaying signup results.
     */
    public UserSignupView(UserSignupController signupController, UserSignupViewModel signupViewModel) {
        this.signupController = signupController;
        this.signupViewModel = signupViewModel;

        setTitle("User Signup");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        initializeComponents();
        addComponentsToFrame();
        addEventListeners();
    }

    private void initializeComponents() {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        messageLabel = new JLabel();
        signupButton = new JButton("Sign Up");
    }

    private void addComponentsToFrame() {
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Confirm Password:"));
        add(confirmPasswordField);
        add(new JLabel());
        add(signupButton);
        add(new JLabel());
        add(messageLabel);
    }

    private void addEventListeners() {
        signupButton.addActionListener(e -> handleSignup());
    }

    private void handleSignup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        signupController.signup(username, password, confirmPassword);

        // Update the message label with the response from the view model
        messageLabel.setText(signupViewModel.getMessage());
    }
}