package framework.view;

import interface_adapter.controller.CoursePromptController;
import interface_adapter.viewmodel.CoursePromptViewModel;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Prompt to allow users to leave a course.
 * <br/>
 * The purpose of a prompt is to be more modular and potentially scalable.
 */
public class LeaveCoursePrompt {
    private final String username;
    private final CoursePromptController controller;
    private final CoursePromptViewModel viewModel;

    private final JFrame frame;
    private final JPanel mainPanel;
    private JTextField nameField;

    /**
     * Instantiate a new prompt view.
     * @param username The username of the current user.
     */
    public LeaveCoursePrompt(String username, CoursePromptController controller, CoursePromptViewModel viewModel) {
        this.username = username;
        this.controller = controller;
        this.viewModel = viewModel;

        frame = new JFrame("Leave Course");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 200);
        frame.setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        initCourseNameComponents();
        initButtonComponents();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void initCourseNameComponents() {
        JPanel namePanel = new JPanel();
        namePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel courseNameLabel = new JLabel("Course Name:");
        namePanel.add(courseNameLabel);

        nameField = new JTextField();

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        fieldPanel.add(nameField);

        mainPanel.add(namePanel, BorderLayout.NORTH);
        mainPanel.add(fieldPanel, BorderLayout.CENTER);
    }

    private void initButtonComponents() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> frame.dispose());

        JButton joinButton = new JButton("Leave");
        joinButton.addActionListener(e -> leaveCourse());

        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.add(joinButton, BorderLayout.EAST);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /// Main use case method to remove a user from the provided course.
    private void leaveCourse() {
        String courseName = nameField.getText();

        // ensure a course name was provided
        if (courseName == null || courseName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Course name field must not be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        controller.leaveCourse(username, courseName);

        /// if the use case was successful, close prompt
        if (viewModel.getResponse()) {
            frame.dispose();
            return;
        }

        /// if the use case failed, show error message
        JOptionPane.showMessageDialog(
                frame,
                viewModel.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}