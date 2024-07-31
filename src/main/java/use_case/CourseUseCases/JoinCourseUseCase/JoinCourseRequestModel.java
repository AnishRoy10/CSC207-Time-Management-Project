package use_case.CourseUseCases.JoinCourseUseCase;

/**
 * An object to represent input data when joining a course.
 */
public class JoinCourseRequestModel {
    final private String courseName;

    /**
     * Construct a new Input Data object.
     * 
     * @param courseName the name of the course
     */
    public JoinCourseRequestModel(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Get the course name associated with this data object.
     * @return the course name
     */
    String getCourseName() {
        return courseName;
    }
}
