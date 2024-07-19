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

import interface_adapter.JoinCourseUseCase.JoinCourseController;
import interface_adapter.JoinCourseUseCase.JoinCourseViewModel;

public class JoinCoursePrompt {
    private JoinCourseController controller;
    private JoinCourseViewModel viewModel;

    private JFrame frame;
    private JPanel labelPanel;
    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JLabel courseNameLabel;
    private JTextField courseNameField;
    private JButton joinButton;
    private JButton cancelButton;

    public JoinCoursePrompt(JoinCourseController controller, JoinCourseViewModel viewModel) {
        this.controller = controller;

        frame = new JFrame("Join Course");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(450, 200);

        labelPanel = new JPanel();
        labelPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        fieldPanel = new JPanel();
        fieldPanel.setBorder(new EmptyBorder(10 , 10, 10, 10));

        courseNameLabel = new JLabel("Course Name:");
        courseNameField = new JTextField();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));

        labelPanel.add(courseNameLabel);
        fieldPanel.add(courseNameField);;

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> frame.dispose());

        joinButton = new JButton("Join");
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

        controller.execute(courseName);

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
