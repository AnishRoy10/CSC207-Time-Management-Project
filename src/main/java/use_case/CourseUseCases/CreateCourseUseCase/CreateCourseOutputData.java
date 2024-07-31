package use_case.CourseUseCases.CreateCourseUseCase;

public class CreateCourseOutputData {
    private final boolean response;
    private final String message;

    public CreateCourseOutputData(boolean response, String message) {
        this.response = response;
        this.message = message;
    }

    public boolean getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }
}
