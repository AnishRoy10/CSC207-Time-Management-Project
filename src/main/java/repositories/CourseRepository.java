package repositories;

import java.io.IOException;
import java.util.Map;

import entity.Course;
import entity.Task;
import entity.User;

/**
 * Repository for accessing courses.
 */
public interface CourseRepository {
	/**
     * Fetch a list of existing courses.
     *
     * @return list of courses
     * @throws IOException            if an error occurs reading the cache
     * @throws ClassNotFoundException if no course object is found
     */
	Map<String, Course> ReadFromCache() throws IOException, ClassNotFoundException;

	/**
	 * Save a course object.
	 * @param course the course to save
	 * @return       if the course was successfully saved
	 */
	boolean WriteToCache(Course course) throws IOException;
	
	/**
	 * See if a course exists.
	 * 
	 * @param courseName  the name of the course to check
	 * @return            the success value
	 */
	boolean courseExists(String courseName);

	/**
	 * Fetch a course by its name.
	 * 
	 * @param courseName the name of the target course
	 * @return           the course, if it exists, or null
	 */
	Course findByName(String courseName);

	/**
	 * Add a user to a course by name.
	 *
	 * @param courseName the name of the course to add the user to.
	 * @param user       the user to add to the course.
	 * @return           if the user could be added to the course successfully.
	 */
	boolean addToCourse(String courseName, User user);

	/**
	 * Add a task to a course.
	 *
	 * @param courseName the name of the course to add the task to.
	 * @param task       the task to add to the course.
	 * @return           if the task could be added successfully
	 */
	boolean addTask(String courseName, Task task);
}
