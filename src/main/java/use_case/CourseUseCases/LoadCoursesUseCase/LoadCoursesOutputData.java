package use_case.CourseUseCases.LoadCoursesUseCase;

import java.util.ArrayList;
import java.util.List;

public class LoadCoursesOutputData {
    private final boolean success;
    private final String message;

    private final List<String> courses;

    public LoadCoursesOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.courses = new ArrayList<>();
    }

    public LoadCoursesOutputData(boolean success, String message, List<String> courses) {
        this.success = success;
        this.message = message;
        this.courses = courses;
    }

    public boolean isSuccess() { return success; }

    public String getMessage() { return message; }

    public List<String> getCourses() { return courses; }
}
