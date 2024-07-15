package framework.view;

import interface_adapter.controller.UserSignupController;
import interface_adapter.viewmodel.UserSignupViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The UserSignupView class represents the GUI for user signup.
 */
public class UserSignupView extends JFrame {
    private final UserSignupController userSignupController;
    private final UserSignupViewModel userSignupViewModel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel messageLabel;

    public UserSignupView(UserSignupController userSignupController, UserSignupViewModel userSignupViewModel) {
        this.userSignupController = userSignupController;
        this.userSignupViewModel = userSignupViewModel;

        setTitle("User Signup");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    /**
     * Initializes the components of the signup view.
     */
    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        // Initialize all components
        initializeFields();
        initializeLabels(panel);
        initializeCheckbox(panel);
        initializeButton(panel);
        initializeMessageLabel(panel);

        add(panel);
    }

    /**
     * Initializes the text fields for username, password, and confirm password.
     */
    private void initializeFields() {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
    }

    /**
     * Initializes and adds the labels to the panel.
     *
     * @param panel The panel to add the labels to.
     */
    private void initializeLabels(JPanel panel) {
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
    }

    /**
     * Initializes and adds the show password checkbox to the panel.
     *
     * @param panel The panel to add the checkbox to.
     */
    private void initializeCheckbox(JPanel panel) {
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckbox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                    confirmPasswordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                    confirmPasswordField.setEchoChar('*');
                }
            }
        });

        panel.add(showPasswordCheckbox);
    }

    /**
     * Initializes and adds the signup button to the panel.
     *
     * @param panel The panel to add the button to.
     */
    private void initializeButton(JPanel panel) {
        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                userSignupController.signup(username, password, confirmPassword);
                messageLabel.setText(userSignupViewModel.getMessage());
            }
        });

        panel.add(signupButton);
    }

    /**
     * Initializes and adds the message label to the panel.
     *
     * @param panel The panel to add the message label to.
     */
    private void initializeMessageLabel(JPanel panel) {
        messageLabel = new JLabel();
        panel.add(new JLabel()); // Placeholder for grid alignment
        panel.add(messageLabel);
    }
}
