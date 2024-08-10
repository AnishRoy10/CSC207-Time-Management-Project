package framework.view;

import use_case.TodoListUseCases.TaskData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * TaskCard represents the UI component for displaying a task's details.
 */
public class TaskCard extends JPanel {
    private final TaskData task;
    private final JCheckBox completedCheckBox;
    private final JLabel titleLabel;
    private final JLabel deadlineLabel;
    private final JLabel courseLabel;
    private final JPanel detailsPanel;
    private final JLabel descriptionLabel;
    private final JLabel startDateLabel;
    private final JLabel completionDateLabel;

    private boolean isSelected;

    /**
     * Constructs a TaskCard with the given task data.
     *
     * @param task the task data to be displayed
     */
    public TaskCard(TaskData task) {
        this.task = task;
        this.isSelected = false;

        // Set layout and border for the TaskCard
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // Initialize and configure the completed checkbox
        completedCheckBox = new JCheckBox();
        completedCheckBox.setSelected(task.isCompleted());

        // Initialize and configure the title label
        titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Initialize and configure the deadline label
        deadlineLabel = new JLabel("Due: " + task.getDeadline().toString());
        deadlineLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Initialize and configure the course label
        courseLabel = new JLabel(task.getCourse());
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Initialize the details panel and set it to be initially invisible
        detailsPanel = new JPanel(new GridLayout(3, 1));
        detailsPanel.setVisible(false);

        // Initialize the description, start date, and completion date labels
        descriptionLabel = new JLabel("Description: " + task.getDescription());
        startDateLabel = new JLabel("Start Date: " + task.getStartDate().toString());
        completionDateLabel = new JLabel();
        updateCompletionDateLabel();

        // Add labels to the details panel
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(startDateLabel);
        detailsPanel.add(completionDateLabel);

        // Create and configure the header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(completedCheckBox, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(courseLabel, BorderLayout.EAST);

        // Add components to the TaskCard
        add(headerPanel, BorderLayout.NORTH);
        add(deadlineLabel, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds an ActionListener to the completion checkbox.
     *
     * @param listener the ActionListener to be added
     */
    public void addCompletionActionListener(ActionListener listener) {
        completedCheckBox.addActionListener(listener);
    }

    /**
     * Returns the task data associated with this TaskCard.
     *
     * @return the task data
     */
    public TaskData getTask() {
        return task;
    }

    /**
     * Returns whether this TaskCard is selected.
     *
     * @return true if the TaskCard is selected, false otherwise
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * Sets the selection state of this TaskCard.
     *
     * @param selected true to select the TaskCard, false to deselect it
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
        setBackground(selected ? Color.LIGHT_GRAY : Color.WHITE);
    }

    /**
     * Toggles the visibility of the details panel.
     */
    public void toggleDetails() {
        detailsPanel.setVisible(!detailsPanel.isVisible());
        revalidate();
        repaint();
    }

    /**
     * Updates the completion date label based on the task's completion status.
     */
    public void updateCompletionDateLabel() {
        if (task.isCompleted()) {
            completionDateLabel.setText("Completion Date: " + task.getCompletionDate().toString());
            completedCheckBox.setSelected(true);
        } else {
            completionDateLabel.setText("Completion Date: Not completed");
            completedCheckBox.setSelected(false);
        }
    }

    public void setCompleted(boolean isCompleted) {
        completedCheckBox.setSelected(isCompleted);
    }

    // Public getters for testing purposes
    public JCheckBox getCompletedCheckBox() {
        return completedCheckBox;
    }

    public JPanel getDetailsPanel() {
        return detailsPanel;
    }

    public JLabel getCompletionDateLabel() {
        return completionDateLabel;
    }
}