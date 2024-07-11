import use_case.JoinCourseUseCase;

/**
 * CourseController connects the course presenter and use cases.
 */
public class CourseController {
  private final JoinCourseUseCase joinCourseUseCase;

  public CourseController(JoinCourseUseCase joinCourseUseCase) {
      this.joinCourseUseCase = joinCourseUseCase;
  }
}
