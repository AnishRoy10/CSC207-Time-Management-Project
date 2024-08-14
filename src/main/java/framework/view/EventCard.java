package framework.view;

import entity.CalendarEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * EventCard represents the UI component for displaying an event's details.
 */
public class EventCard extends JPanel {
    private final CalendarEvent event;
    private final JLabel nameLabel;
    private final JPanel detailsPanel;
    private final JTextArea textArea;
    private final JScrollPane descriptionPanel;
    private final JPanel fillerCenterPanel;
    private final JLabel fillerLabel;
    private final JLabel startDateLabel;
    private final JLabel endDateLabel;
    private final JLabel statusLabel;
    private final JLabel priorityLevelLabel;
    private JButton removeEventButton = new JButton("Remove");

    public EventCard(CalendarEvent event) {
        this.event = event;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);


        nameLabel = new JLabel(event.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        endDateLabel = new JLabel("End Date and Time: " + (event.getHasEndDate() ? event.getEndDate() : "N/A"));

        detailsPanel = new JPanel(new GridLayout(5, 1));
        detailsPanel.setVisible(true);

        textArea = new JTextArea("Description:" + "\n" +
                (event.getHasDescription() ? event.getDescription() : "No description"), 12, 22);
        descriptionPanel = new JScrollPane(textArea);
        descriptionPanel.setVisible(true);

        fillerCenterPanel = new JPanel(new GridLayout(1, 4));
        fillerCenterPanel.setVisible(true);

        startDateLabel = new JLabel("Start Date and Time: " + event.getStartDate().toString());
        statusLabel = new JLabel("Status: " + (event.getStatus()));
        priorityLevelLabel = new JLabel("Priority Level: " + event.getPriorityLevel());
        fillerLabel = new JLabel("");

        detailsPanel.add(startDateLabel);
        detailsPanel.add(endDateLabel);
        detailsPanel.add(statusLabel);
        detailsPanel.add(priorityLevelLabel);
        detailsPanel.add(removeEventButton);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(nameLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(fillerCenterPanel, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.WEST);
        add(descriptionPanel, BorderLayout.CENTER);
    }

    // Getters for the button and event
    public JButton getRemoveEventButton() {return removeEventButton;}
    public CalendarEvent getEvent() {return event;}
}