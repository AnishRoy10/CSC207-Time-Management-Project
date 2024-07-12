package framework.view;

import use_case.TaskData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    public TaskCard(TaskData task) {
        this.task = task;
        this.isSelected = false;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        completedCheckBox = new JCheckBox();
        completedCheckBox.setSelected(task.isCompleted());
        completedCheckBox.addActionListener(e -> task.setCompleted(completedCheckBox.isSelected()));

        titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        deadlineLabel = new JLabel("Due: " + task.getDeadline().toString());
        deadlineLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        courseLabel = new JLabel(task.getCourse());
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        detailsPanel = new JPanel(new GridLayout(3, 1));
        detailsPanel.setVisible(false);

        descriptionLabel = new JLabel("Description: " + task.getDescription());
        startDateLabel = new JLabel("Start Date: " + task.getStartDate().toString());
        completionDateLabel = new JLabel();
        updateCompletionDateLabel();

        detailsPanel.add(descriptionLabel);
        detailsPanel.add(startDateLabel);
        detailsPanel.add(completionDateLabel);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(completedCheckBox, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(courseLabel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
        add(deadlineLabel, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectTaskCard();
                } else if (e.getClickCount() == 2) {
                    toggleDetails();
                    selectTaskCard();
                }
            }
        });
    }

    private void selectTaskCard() {
        Container parent = getParent();
        if (parent instanceof JPanel) {
            for (Component component : ((JPanel) parent).getComponents()) {
                if (component instanceof TaskCard) {
                    ((TaskCard) component).setSelected(false);
                }
            }
        }
        setSelected(true);
    }

    public TaskData getTask() {
        return task;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        setBackground(selected ? Color.LIGHT_GRAY : Color.WHITE);
    }

    public void toggleDetails() {
        detailsPanel.setVisible(!detailsPanel.isVisible());
        revalidate();
        repaint();
    }

    private void updateCompletionDateLabel() {
        if (task.isCompleted()) {
            completionDateLabel.setText("Completion Date: " + task.getCompletionDate().toString());
        } else {
            completionDateLabel.setText("Completion Date: Not completed");
        }
    }
}
