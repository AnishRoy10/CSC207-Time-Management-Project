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
	Course loadCourse();

	/**
	 * Saves the course.
	 * @param course the course to be saved
	 */
	void saveCourse(Course course);

	/**
	 * See if a course exists.
	 * 
	 * @param course the course to check
	 * @return       the success value
	 */
	boolean courseExists(Course course);
}
