package framework.view;

import use_case.TaskData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

/**
 * TaskCard represents the UI component for displaying a task's details.
 */
public class TaskCard extends JPanel {
    private TaskData task;
    private JCheckBox completedCheckBox;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JLabel deadlineLabel;
    private JLabel courseLabel;
    private JLabel completionDateLabel;

    /**
     * Constructs a new TaskCard.
     *
     * @param task The task to display in this card.
     */
    public TaskCard(TaskData task) {
        this.task = task;
        initializeComponents();
        layoutComponents();
    }

    /**
     * Initializes the UI components for this card.
     */
    private void initializeComponents() {
        completedCheckBox = new JCheckBox();
        completedCheckBox.setSelected(task.isCompleted());
        completedCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                task.setCompleted(completedCheckBox.isSelected());
                updateCompletionDateLabel();
            }
        });

        titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        descriptionLabel = new JLabel("<html><body style='width: 200px'>" + task.getDescription() + "</body></html>");
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        deadlineLabel = new JLabel("Due: " + (task.getDeadline() != null ? task.getDeadline().format(formatter) : "No deadline"));
        deadlineLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        courseLabel = new JLabel("Course: " + (task.getCourse() != null ? task.getCourse() : "No course"));
        courseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        completionDateLabel = new JLabel();
        updateCompletionDateLabel();
    }

    /**
     * Updates the completion date label based on the task's completion status.
     */
    private void updateCompletionDateLabel() {
        if (task.isCompleted()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            completionDateLabel.setText("Completed on: " + (task.getCompletionDate() != null ? task.getCompletionDate().format(formatter) : "No completion date"));
        } else {
            completionDateLabel.setText("");
        }
    }

    /**
     * Lays out the UI components within this card.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);
        textPanel.add(deadlineLabel);
        textPanel.add(courseLabel);
        textPanel.add(completionDateLabel);

        add(completedCheckBox, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);

        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
