package framework.view;

import entity.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TaskCard represents the UI component for displaying a task's details.
 */
public class TaskCard extends JPanel {
    private Task task;
    private JCheckBox completedCheckBox;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JLabel deadlineLabel;
    private JLabel courseLabel;

    /**
     * Constructs a new TaskCard.
     *
     * @param task The task to display in this card.
     */
    public TaskCard(Task task) {
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
            }
        });

        titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        descriptionLabel = new JLabel(task.getDescription());
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        deadlineLabel = new JLabel("Due: " + (task.getDeadline() != null ? task.getDeadline().toString() : "No deadline"));
        deadlineLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        courseLabel = new JLabel(task.getCourse() != null ? task.getCourse().getName() : "No course");
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    /**
     * Lays out the UI components within this card.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        JPanel textPanel = new JPanel(new GridLayout(0, 1));
        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);
        textPanel.add(deadlineLabel);
        textPanel.add(courseLabel);

        add(completedCheckBox, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
