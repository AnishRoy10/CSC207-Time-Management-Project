package framework.view;
import com.github.lgooddatepicker.components.*;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import entity.Calendar;
import entity.CalendarEvent;
import interface_adapter.AddEvent.AddEventController;
import interface_adapter.AddEvent.AddEventViewModel;
import interface_adapter.ViewEvents.ViewEventsController;
import interface_adapter.ViewEvents.ViewEventsViewModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.io.IOException;
import java.util.List;
import java.awt.*;
import java.time.*;
import java.time.Month;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class CalendarView {
    private static ViewEventsViewModel viewEventsViewModel;
    private static ViewEventsController viewEventsController;
    private static AddEventController addEventController;
    private static AddEventViewModel addEventViewModel;
    private final JFrame frame = new JFrame();
    private final JPanel panel;
    private final JPanel eventListPanel;
    private final CalendarPanel calendarPanel;
    private final JButton eventViewerButton = new JButton("View Events On Selected Day");
    private final JTextField nameField;
    private final JTextArea descriptionArea;
    private final DateTimePicker startDatePicker;
    private final DateTimePicker endDatePicker;
    private final JTextField priorityLevelField;
    private JFrame parentFrame;

    // Initializing controllers, view models, and setting up jpanels.
    public CalendarView(ViewEventsViewModel viewEventsViewModel, ViewEventsController viewEventsController,
                        AddEventController addEventController, AddEventViewModel addEventViewModel) {
        this.viewEventsViewModel = viewEventsViewModel;
        this.viewEventsController = viewEventsController;
        this.addEventController = addEventController;
        this.addEventViewModel = addEventViewModel;

        frame.revalidate();
        frame.repaint();
        frame.setTitle("Calendar Screen");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /**
         * when calendarview is closed it reverts back to the option menu,
         * deleting and the calendar panel to avoid bugs.
         */
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (parentFrame != null) {
                    parentFrame.setVisible(true);
                }
                panel.removeAll();
                panel.revalidate();
                panel.repaint();
                panel.setVisible(true);
            }
        });
        // Settings for the visual calendar
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setHighlightPolicy(new EventHighlightPolicy());

        // Implementation of the visual calendar
        calendarPanel = new CalendarPanel(dateSettings);
        calendarPanel.setSelectedDate(LocalDate.now());
        calendarPanel.setBorder(new LineBorder(Color.lightGray));

        // creating the panel which holds the visual panel, along with
        // its view events button.
        panel = new JPanel();
        panel.add(calendarPanel, BorderLayout.NORTH);
        panel.add(eventViewerButton, BorderLayout.WEST);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        eventViewerButton.addActionListener(e -> showEventsOnDay());

        // Initializing panel for showing the events cards.
        eventListPanel = new JPanel(new GridBagLayout());
        JScrollPane eventListScrollPane = new JScrollPane(eventListPanel);
        eventListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        eventListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // Panel for inputting the new event to be added to the calendar
        JPanel eventAddingPanel = new JPanel();
        eventAddingPanel.setLayout(new BoxLayout(eventAddingPanel, BoxLayout.Y_AXIS));
        eventAddingPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        eventAddingPanel.setBackground(Color.WHITE);

        nameField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        startDatePicker = new DateTimePicker();
        endDatePicker = new DateTimePicker();
        priorityLevelField = new JTextField();

        eventAddingPanel.add(createLabeledComponent("Title:", nameField));
        eventAddingPanel.add(createLabeledComponent("Description:", new JScrollPane(descriptionArea)));
        eventAddingPanel.add(createLabeledComponent("Start Date:", startDatePicker));
        eventAddingPanel.add(createLabeledComponent("End Date:", endDatePicker));
        eventAddingPanel.add(createLabeledComponent("PriorityLevel:", priorityLevelField));

        JButton addTaskButton = new JButton("Add Event");
        addTaskButton.addActionListener(e -> addEvent());

        eventAddingPanel.add(Box.createVerticalStrut(10));
        eventAddingPanel.add(addTaskButton);

        // Setting the frame settings and adding panels
        frame.add(eventAddingPanel, BorderLayout.SOUTH);
        frame.add(eventListScrollPane, BorderLayout.WEST);
        frame.add(panel, BorderLayout.EAST);

        frame.pack();
        frame.validate();
        int maxWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        int maxHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        frame.setSize(maxWidth / 4 * 3, maxHeight / 8 * 7);
        frame.setLocation(maxWidth / 8, maxHeight / 16);
        frame.setVisible(true);
    }

    // Makes sure that days with events on them are highlighted in the calendar representation
    private static class EventHighlightPolicy implements DateHighlightPolicy {
        private EventHighlightPolicy() {
        }

        public HighlightInformation getHighlightInformationOrNull(LocalDate date) {
            try {
                viewEventsController.execute(date);
                if (!viewEventsViewModel.getEventListToBeShown().isEmpty()) {
                    return new HighlightInformation(Color.green, (Color) null, "This day has an event");
                } else {
                    return null;
                }
            }
            catch (IOException | ClassNotFoundException e) {return null;}
        }
    }

     /** Method called when view events button is pressed, which activates the ViewEvents use case
      * proccess throughout the relevant controller, and getting the relevant information through
      * the relevant view model.
      */
    private void showEventsOnDay(){
        try {
            eventListPanel.removeAll();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = GridBagConstraints.RELATIVE;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            LocalDate date = calendarPanel.getSelectedDate();
            viewEventsController.execute(date);
            for (CalendarEvent calEvent : viewEventsViewModel.getEventListToBeShown()) {
                JPanel eventPanel = new EventCard(calEvent);
                eventListPanel.add(eventPanel, gbc);
                eventListPanel.revalidate();
                eventListPanel.repaint();
                frame.setVisible(true);
                panel.setVisible(true);
                eventListPanel.setVisible(true);

            }
        }
        catch (IOException | ClassNotFoundException ignored) {System.out.println("IOException Found");}
    }
    // Method for creating a labelled comonent on the eventadding panel
    private Component createLabeledComponent(String label, Component component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 0, 10, 0));
        return panel;
    }

    /**
     * method called when add event button is pushed. Calls the relevant controller for the
     * add event use case to be executed.
     */
    private void addEvent(){
        String name = nameField.getText().toString();
        String description = descriptionArea.getText().toString();
        LocalDateTime startDate = startDatePicker.getDateTimeStrict();
        LocalDateTime endDate = endDatePicker.getDateTimeStrict();
        String priorityLevel = priorityLevelField.getText().toString().trim();
        try{
        addEventController.execute(name, description, startDate, endDate, priorityLevel);
        if (addEventViewModel.getStartEndError()) {
            JOptionPane.showMessageDialog(frame,
                    "Event must start and end on the same day", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (addEventViewModel.getPriorityLevelError()) {
            JOptionPane.showMessageDialog(frame,
                    "Priority Level must be either 'High', 'Normal', or 'Low'",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else if (addEventViewModel.getStartAfterEndError()) {
            JOptionPane.showMessageDialog(frame,
                    "Start time must be before End time",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {this.showEventsOnDay();}
        }
        catch (IOException | ClassNotFoundException e) {}
    }
}
