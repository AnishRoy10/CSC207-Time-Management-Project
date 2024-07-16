package use_case.join_course;

import entity.Course;
import entity.User;
import repositories.CourseRepository;

/**
 * Interactor for joining courses.
 */
public class JoinCourseUseCase implements JoinCourseInputBoundary {
    private final JoinCourseOutputBoundary outputBoundary; 
	private final CourseRepository repository;

	/**
	 * Construct a new use case instance.
	 * @param repository	 repository for courses
	 * @param outputBoundary output boundary for user response
	 */
    public JoinCourseUseCase(CourseRepository repository, JoinCourseOutputBoundary outputBoundary) {
		this.repository = repository;
        this.outputBoundary = outputBoundary;
    }
  
	/**
	 * Facilitates the process of joining a course.
	 * 
	 * @param inputData input data holding associated course and user
	 */
    @Override
    public void execute(JoinCourseRequestModel inputData) {
		Course course = inputData.getCourse();
		User user = inputData.getUser();

		if (!repository.courseExists(course)) {
			JoinCourseResponseModel outputData = new JoinCourseResponseModel(false, "Course not found.");
			outputBoundary.present(outputData);
			return;
		}

		course.addUser(user);
		JoinCourseResponseModel outputData = new JoinCourseResponseModel(true, "User successfully added.");
		outputBoundary.present(outputData);
    }  
}
