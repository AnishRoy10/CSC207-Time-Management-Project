package interface_adapter.viewmodel;

import java.util.List;

/**
 * View model to load courses into the course list.
 */
public class CourseListViewModel {
    private boolean success;
    private String message;

    private List<String> courses;

    public void setSuccess(boolean success) { this.success = success; }

    public void setMessage(String message) { this.message = message; }

    public void setCourses(List<String> courses) { this.courses = courses; }

    public boolean isSuccess() { return success; }

    public String getMessage() { return message; }

    public List<String> getCourses() { return courses; }
}
