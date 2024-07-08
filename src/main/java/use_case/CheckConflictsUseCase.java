package use_case;

import entity.Calendar;
import entity.CalendarEvent;

import java.util.ArrayList;

public class CheckConflictsUseCase {
    private Calendar calendar;

    /**
     * Checks for conflicts in the calendar between events
     *
     * @param calendar The calendar which is checked for conflicts
     */

    public CheckConflictsUseCase(Calendar calendar) {this.calendar = calendar;}

    // Helper function for conflictChecker that returns the events in the order of start date.
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
    //Events conflict for the method showConflicts
    // public insertreturntypehere showConflicts(Calendar calendar) {}
}
