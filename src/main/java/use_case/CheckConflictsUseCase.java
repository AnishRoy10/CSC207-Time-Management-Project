package use_case;

import entity.Calendar;
import entity.CalendarEvent;

import java.util.ArrayList;
 // Not yet implemented
public class CheckConflictsUseCase {
    private Calendar calendar;

    /**
     * Checks for conflicts in the calendar between events
     *
     * @param calendar The calendar which is checked for conflicts
     */

    public CheckConflictsUseCase(Calendar calendar) {this.calendar = calendar;}

    // Helper function for conflictChecker and showConflicts that returns the events in the order of start date.
    // If the start dates are the same, return in the order they come in.
    private ArrayList<CalendarEvent> comesFirst(CalendarEvent eventOne, CalendarEvent eventTwo) {
        if (eventTwo.getStartDate().isBefore(eventOne.getStartDate())) {
            ArrayList<CalendarEvent> orderedArray = new ArrayList<CalendarEvent>(2);
            orderedArray.add(eventTwo);
            orderedArray.add(eventOne);
            return orderedArray;
        }
        else {
            ArrayList<CalendarEvent> orderedArray = new ArrayList<CalendarEvent>(2);
            orderedArray.add(eventOne);
            orderedArray.add(eventTwo);
            return orderedArray;
        }
    }

    // Helper method for showConflicts that says whether two events conflict
    private boolean conflictChecker(CalendarEvent eventOne, CalendarEvent eventTwo) {
        if (eventOne.getStartDate() == eventTwo.getStartDate()) {return true;}
        else {
            CalendarEvent firstEvent = comesFirst(eventOne, eventTwo).get(0);
            CalendarEvent secondEvent = comesFirst(eventOne, eventTwo).get(1);
            if (firstEvent.getHasEndDate()) {
                return firstEvent.getEndDate().isAfter(secondEvent.getStartDate());
            }
            else {return false;}
        }
    }
    // Must find out what data type to use to store the information of which
    // Events conflict for the method showConflicts
    public ArrayList<ArrayList<CalendarEvent>> showConflicts(Calendar calendar) {
        ArrayList<ArrayList<CalendarEvent>> conflictList = new ArrayList<ArrayList<CalendarEvent>>();
        // Double loop which loop over all pairs of events in the Calendar, adding conflicting pairs to conflictList
        for (CalendarEvent event : calendar.getAllEvents()) {
            for (CalendarEvent anotherEvent : calendar.getAllEvents()) {
                // Condition that the events are not the same
                boolean conditionOne = !event.equals(anotherEvent);
                // Condition that the two events indeed have a conflict
                boolean conditionTwo = conflictChecker(event, anotherEvent);
                // Condition that the events have not already been added (in order of startTime) to conflictList
                boolean conditionThree = ! conflictList.contains(comesFirst(event, anotherEvent));
                // If conditions are met, then the set of conflicting events are added
                // in a list to conflictList (in order)
                if (conditionOne && conditionTwo && conditionThree) {
                    conflictList.add(comesFirst(event, anotherEvent));
                }
            }
        }
        return conflictList;
    }
}
