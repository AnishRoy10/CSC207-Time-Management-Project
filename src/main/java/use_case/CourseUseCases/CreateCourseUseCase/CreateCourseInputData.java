package use_case.CourseUseCases.CreateCourseUseCase;

public class CreateCourseInputData {
    private final String courseName;
    private final String courseDescription;

    public CreateCourseInputData(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }
}
