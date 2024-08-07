package use_case.JoinCourseUseCase;

import java.io.IOException;

import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import entity.User;
import repositories.CourseRepository;
import repositories.UserRepository;

/**
 * Interactor for joining courses. This interactor ensures a logged in user can only join an
 * existing course.
 */
public class JoinCourseUseCase implements JoinCourseInputBoundary {
    private final JoinCourseOutputBoundary outputBoundary; 
	private final CourseRepository courseDataAccessObject;
	private final UserRepository userDataAccessObject;

	/**
	 * Construct a new use case instance.
	 * @param outputBoundary 		 output boundary for user response
	 * @param courseDataAccessObject data access object for courses
	 * @param userDataAccessObject   data access object for users
	 */
	public JoinCourseUseCase(
		JoinCourseOutputBoundary outputBoundary,
		CourseRepository courseDataAccessObject,
		UserRepository userDataAccessObject)
	{
		this.outputBoundary = outputBoundary;
		this.courseDataAccessObject = courseDataAccessObject;
		this.userDataAccessObject = userDataAccessObject;
	}

	/**
	 * Facilitates the process of joining a course.
	 * 
	 * @param inputData input data holding associated course and user
	 */
	@Override
	public void execute(JoinCourseRequestModel inputData) {
		String courseName = inputData.getCourseName();
		User user;

		// try to fetch the current user
		try {
			user = userDataAccessObject.ReadFromCache();
		} catch (IOException e) {
			JoinCourseResponseModel responseModel = new JoinCourseResponseModel(
				false,
				"An error occurred fetching the current user.");
			outputBoundary.present(responseModel);
			return;
		} 

		// check that the course exists
		if (!courseDataAccessObject.courseExists(courseName)) {
			JoinCourseResponseModel outputData = new JoinCourseResponseModel(
				false,
				"Course not found.");
			outputBoundary.present(outputData);
			return;
		}

		// finally, add the user to the course and present a response
		courseDataAccessObject.findByName(courseName).addUser(user);
		JoinCourseResponseModel outputData = new JoinCourseResponseModel(
			true,
			"User successfully added.");
		outputBoundary.present(outputData);
	}  
}
