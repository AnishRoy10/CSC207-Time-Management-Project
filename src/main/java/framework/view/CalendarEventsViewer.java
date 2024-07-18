package framework.view;
import com.github.lgooddatepicker.components.*;
import com.github.lgooddatepicker.demo.FullDemo;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.optionalusertools.DateHighlightPolicy;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import com.privatejgoodies.forms.factories.CC;
import entity.Calendar;
import entity.CalendarEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.*;
import java.time.Month;
import java.util.List;

import framework.view.EventCard;
import use_case.TaskData;

public class CalendarEventsViewer {
    private static Calendar calendar;
    private static JFrame frame = new JFrame();
    private static JPanel panel = new JPanel();
    private static JPanel eventListPanel;
    private static CalendarPanel calendarPanel;
    private JButton eventViewerButton = new JButton("View Events On Selected Day");
    private static JTextField nameField;
    private static JTextArea descriptionArea;
    private static DateTimePicker startDatePicker;
    private static DateTimePicker endDatePicker;
    private static JTextField priorityLevelField;
    public CalendarEventsViewer(Calendar calendar) {
        this.calendar = calendar;
        frame.setTitle("Calendar Events Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setHighlightPolicy(new EventHighlightPolicy());

        calendarPanel = new CalendarPanel(dateSettings);
        calendarPanel.setSelectedDate(LocalDate.now());
        calendarPanel.setBorder(new LineBorder(Color.lightGray));

        panel.add(calendarPanel, BorderLayout.NORTH);
        panel.add(eventViewerButton, BorderLayout.WEST);
        eventViewerButton.addActionListener(e -> showEventsOnDay());

        eventListPanel = new JPanel(new GridBagLayout());
        JScrollPane eventListScrollPane = new JScrollPane(eventListPanel);
        eventListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        eventListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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


    private static GridBagConstraints getConstraints(int gridx, int gridy, int gridwidth) {
        return getConstraints(gridx, gridy, gridwidth, 17);
    }

    private static GridBagConstraints getConstraints(int gridx, int gridy, int gridwidth, int anchor) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 0;
        gc.anchor = anchor;
        gc.gridx = gridx;
        gc.gridy = gridy;
        gc.gridwidth = gridwidth;
        return gc;
    }


    // Makes sure that days with events on them are highlighted in the calendar representation
    private static class EventHighlightPolicy implements DateHighlightPolicy {
        private EventHighlightPolicy() {
        }

        public HighlightInformation getHighlightInformationOrNull(LocalDate date) {
            LocalDateTime startOfDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0);
            LocalDateTime endOfDate = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59);
            if (calendar.hasEventsBetweenDates(startOfDate, endOfDate)) {
                return new HighlightInformation(Color.green, (Color) null, "This day has an event");
            } else {
                return null;
            }
        }
    }

    private void showEventsOnDay() {
        eventListPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        LocalDate date = calendarPanel.getSelectedDate();
        LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                0, 0);
        LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                23, 59);
        for (CalendarEvent calEvent : calendar.eventsBetweenDates(start, end)) {
            JPanel eventPanel = new EventCard(calEvent);
            eventListPanel.add(eventPanel, gbc);
            eventListPanel.revalidate();
            eventListPanel.repaint();
            frame.setVisible(true);
            panel.setVisible(true);
            eventListPanel.setVisible(true);

        }
    }
    private Component createLabeledComponent(String label, Component component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.NORTH);
        panel.add(component, BorderLayout.CENTER);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 0, 10, 0));
        return panel;
    }
    private void addEvent(){
        String name = nameField.getText();
        String description = descriptionArea.getText();
        LocalDateTime startDate = startDatePicker.getDateTimeStrict();
        LocalDateTime endDate = endDatePicker.getDateTimeStrict();
        String priorityLevel = priorityLevelField.getText();
        CalendarEvent newEvent = new CalendarEvent(name, description, priorityLevel, startDate, endDate);
        calendar.addEvent(newEvent);
        showEventsOnDay();
    }


        public static void main(String[] args) {
            LocalDateTime start = LocalDateTime.of(2024, Month.JULY, 15, 15, 30);
            LocalDateTime end = LocalDateTime.of(2024, Month.JULY, 15, 16, 30);
            CalendarEvent eventer = new CalendarEvent("Awesome title name", "An awesome description",
                    null, start, end);
            LocalDateTime startTwo = LocalDateTime.of(2024, Month.JULY, 16, 17, 30);
            LocalDateTime endTwo = LocalDateTime.of(2024, Month.JULY, 16, 18, 45);
            CalendarEvent eventerTwo = new CalendarEvent("Less awesome name title", "Less awesome description",
                    "Low", startTwo, endTwo);
            LocalDateTime startThree = LocalDateTime.of(2024, Month.JULY, 15, 2, 30);
            LocalDateTime endThree = LocalDateTime.of(2024, Month.JULY, 15, 5, 0);
            CalendarEvent eventerThree = new CalendarEvent("Even more awesome name title", "Even more awesome description",
                    "High", startThree, endThree);
            Calendar calendar = new Calendar();
            calendar.addEvent(eventer);
            calendar.addEvent(eventerTwo);
            calendar.addEvent(eventerThree);
            new CalendarEventsViewer(calendar);
        }
}
