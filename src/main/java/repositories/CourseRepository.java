package repositories;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;

/**
 * Repository for accessing courses.
 */
public interface CourseRepository {
	/**
     * Fetch a list of existing courses.
     *
     * @return                        list of courses
     * @throws IOException            if an error occurs reading the cache
     * @throws ClassNotFoundException if no course object is found
     */
	ArrayList<Course> ReadFromCache() throws IOException, ClassNotFoundException;

	/**
	 * See if a course exists.
	 * 
	 * @param courseName  the name of the course to check
	 * @return            the success value
	 */
	boolean courseExists(String courseName) throws IOException, ClassNotFoundException;

	/**
	 * Fetch a course by its name.
	 * 
	 * @param courseName the name of the target course
	 * @return           the course, if it exists, or null
	 */
	Course findByName(String courseName) throws IOException, ClassNotFoundException;
}
