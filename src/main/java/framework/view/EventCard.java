package framework.view;

import entity.CalendarEvent;
import entity.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TaskCard represents the UI component for displaying a task's details.
 */
public class EventCard extends JPanel {
    private CalendarEvent event;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JLabel statusLabel;
    private JLabel priorityLabel;

    /**
     * Constructs a new TaskCard.
     *
     * @param event The Calendar Event to display in this card.
     */
    public EventCard(CalendarEvent event) {
        this.event = event;
        initializeComponents();
        layoutComponents();
    }

    /**
     * Initializes the UI components for this card.
     */
    private void initializeComponents() {
        nameLabel = new JLabel(event.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        descriptionLabel = new JLabel(event.getDescription());
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));

        startTimeLabel = new JLabel("Starts " + event.getStartDate());
        startTimeLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        endTimeLabel = new JLabel("Ends " +
                (event.getHasEndDate() ? event.getHasEndDate() : "Event does not have end date"));
        endTimeLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        statusLabel = new JLabel("Event Status: " + event.getStatus());
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        priorityLabel = new JLabel("Priority Level: " +
                (event.getHasPriorityLevel() ? event.getPriorityLevel() : "Event does not have priority level"));
        priorityLabel.setFont(new Font("Arial", Font.ITALIC, 12));
    }
        /**
         * Lays out the UI components within this card.
         */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        JPanel textPanel = new JPanel(new GridLayout(0, 1));
        textPanel.add(nameLabel);
        textPanel.add(descriptionLabel);
        textPanel.add(startTimeLabel);
        textPanel.add(endTimeLabel);
        textPanel.add(statusLabel);
        textPanel.add(priorityLabel);

        add(textPanel, BorderLayout.CENTER);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}