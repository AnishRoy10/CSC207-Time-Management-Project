package interface_adapter.viewmodel;

import java.util.List;

/**
 * View model to load courses into the course list.
 */
public class CourseListViewModel {
    private List<String> courses;

    public void setCourses(List<String> courses) { this.courses = courses; }

    public List<String> getCourses() { return courses; }
}
