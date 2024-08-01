package framework.view;

import interface_adapter.controller.CoursePromptController;
import interface_adapter.viewmodel.CoursePromptViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateCoursePrompt {
    private final CoursePromptController controller;
    private final CoursePromptViewModel viewModel;

    private final JFrame frame;
    private final JPanel mainPanel;
    private JTextField nameField;
    private JTextField descField;

    public CreateCoursePrompt(CoursePromptController controller, CoursePromptViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        frame = new JFrame("Add Course");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        initNameComponents();
        initDescComponents();
        initButtonComponents();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void initNameComponents() {
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Course Name:");
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(frame.getMaximumSize().width - 25, 50));

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        mainPanel.add(namePanel, BorderLayout.NORTH);
    }

    private void initDescComponents() {
        JPanel descPanel = new JPanel();
        descPanel.setLayout(new BoxLayout(descPanel, BoxLayout.Y_AXIS));
        descPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel descLabel = new JLabel("Course Description:");
        descField = new JTextField();
        descField.setMaximumSize(new Dimension(frame.getMaximumSize().width - 25, 100));

        descPanel.add(descLabel);
        descPanel.add(descField);

        mainPanel.add(descPanel, BorderLayout.CENTER);
    }

    private void initButtonComponents() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> frame.dispose());

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> createCourse());

        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.add(createButton, BorderLayout.EAST);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createCourse() {
        String courseName = nameField.getText();
        String courseDesc = descField.getText();

        // only name is checked since a description is optional.
        if (courseName == null || courseName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Name field must not be empty.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        // execute use case
        controller.createCourse(courseName, courseDesc);

        if (viewModel.getResponse()) {
            frame.dispose();
            return;
        }

        JOptionPane.showMessageDialog(
                frame,
                viewModel.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}