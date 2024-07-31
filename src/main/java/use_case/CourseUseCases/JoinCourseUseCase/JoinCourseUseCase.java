package use_case.CourseUseCases.JoinCourseUseCase;

import java.io.IOException;

import entity.Course;
import entity.User;
import repositories.CourseRepository;
import repositories.UserRepository;

/**
 * Interactor for joining courses. This interactor ensures a logged-in user can only join an
 * existing course.
 */
public class JoinCourseUseCase implements JoinCourseInputBoundary {
    private final JoinCourseOutputBoundary presenter;
	private final UserRepository userDataAccessObject;
	private final CourseRepository courseDataAccessObject;

	/**
	 * Construct a new use case instance.
	 * @param presenter     		 presenter for user response
	 * @param userDataAccessObject   data access object for users
	 * @param courseDataAccessObject data access object for courses
	 */
	public JoinCourseUseCase(
		JoinCourseOutputBoundary presenter,
		UserRepository userDataAccessObject,
		CourseRepository courseDataAccessObject)
	{
		this.presenter = presenter;
		this.userDataAccessObject = userDataAccessObject;
		this.courseDataAccessObject = courseDataAccessObject;

	}

	/**
	 * Facilitates the process of joining a course.
	 * 
	 * @param inputData input data holding associated course and user
	 */
	@Override
	public void execute(JoinCourseInputData inputData) {
		try {
			String username = inputData.getUsername();
			String courseName = inputData.getCourseName();
			Course course = courseDataAccessObject.findByName(courseName);

			/// check if they are already in that course
			if (course.containsUser(username)) {
				JoinCourseOutputData outputData = new JoinCourseOutputData(
						false,
						"You are already in " + courseName + "."
				);
				presenter.present(outputData);
				return;
			}

			/// add them to the course
			User user = userDataAccessObject.ReadFromCache(username);
			user.addCourse(course);

			/// write changes
			userDataAccessObject.WriteToCache(user);
			courseDataAccessObject.WriteToCache(course);

			/// present the success
			JoinCourseOutputData outputData = new JoinCourseOutputData(
					true,
					"You have joined " + courseName + "."
			);
			presenter.present(outputData);
		} catch (IOException e) {
			/// something went very wrong
            JoinCourseOutputData outputData = new JoinCourseOutputData(
					false,
					"Something went wrong."
			);
			presenter.present(outputData);
        }
	}  
}
