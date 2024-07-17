package repositories;

import entity.Course;

/**
 * Repository for accessing courses.
 */
public interface CourseRepository {
	/**
	 * Loads the course.
	 * @return the course to get
	 */
	Course loadCourse(String courseName);

	/**
	 * Saves the course.
	 * @param course the course to be saved
	 */
	void saveCourse(Course course);

	/**
	 * See if a course exists.
	 * 
	 * @param courseName  the name of the course to check
	 * @return            the success value
	 */
	boolean courseExists(String courseName);
}
