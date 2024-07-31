package framework.view;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import interface_adapter.controller.CoursePromptController;
import interface_adapter.viewmodel.CoursePromptViewModel;

public class JoinCoursePrompt {
    private final String username;
    private final CoursePromptController controller;
    private final CoursePromptViewModel viewModel;

    private final JFrame frame;
    private final JTextField courseNameField;

    public JoinCoursePrompt(String username, CoursePromptController controller, CoursePromptViewModel viewModel) {
        this.username = username;
        this.controller = controller;
        this.viewModel = viewModel;

        frame = new JFrame("Join Course");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(450, 200);

        JPanel labelPanel = new JPanel();
        labelPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel fieldPanel = new JPanel();
        fieldPanel.setBorder(new EmptyBorder(10 , 10, 10, 10));

        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameField = new JTextField();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));

        labelPanel.add(courseNameLabel);
        fieldPanel.add(courseNameField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> frame.dispose());

        JButton joinButton = new JButton("Join");
        joinButton.addActionListener(e -> attemptToJoinCourse());

        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.add(joinButton, BorderLayout.EAST);
        
        frame.add(labelPanel, BorderLayout.NORTH);
        frame.add(fieldPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void attemptToJoinCourse() {
        String courseName = courseNameField.getText();

        if (courseName == null || courseName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                frame,
                "Course field must not be empty.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.joinCourse(username, courseName);

        if (viewModel.getResponse()) {
            frame.dispose();
            return;
        }

        JOptionPane.showMessageDialog(
            frame,
            viewModel.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
