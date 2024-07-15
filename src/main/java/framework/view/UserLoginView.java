package framework.view;

import interface_adapter.controller.UserLoginController;
import interface_adapter.viewmodel.UserLoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The UserLoginView class represents the GUI for user login.
 */
public class UserLoginView extends JFrame {
    private final UserLoginController userLoginController;
    private final UserLoginViewModel userLoginViewModel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public UserLoginView(UserLoginController userLoginController, UserLoginViewModel userLoginViewModel) {
        this.userLoginController = userLoginController;
        this.userLoginViewModel = userLoginViewModel;

        setTitle("User Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    /**
     * Initializes the components of the login view.
     */
    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        // Initialize all components
        initializeFields();
        initializeLabels(panel);
        initializeCheckbox(panel);
        initializeButton(panel);
        initializeMessageLabel(panel);

        add(panel);
    }

    /**
     * Initializes the text fields for username and password.
     */
    private void initializeFields() {
        usernameField = new JTextField();
        passwordField = new JPasswordField();
    }

    /**
     * Initializes and adds the labels to the panel.
     *
     * @param panel The panel to add the labels to.
     */
    private void initializeLabels(JPanel panel) {
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
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
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });

        panel.add(showPasswordCheckbox);
    }

    /**
     * Initializes and adds the login button to the panel.
     *
     * @param panel The panel to add the button to.
     */
    private void initializeButton(JPanel panel) {
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                userLoginController.login(username, password);
                messageLabel.setText(userLoginViewModel.getMessage());
            }
        });

        panel.add(loginButton);
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

