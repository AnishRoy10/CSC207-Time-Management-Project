package repositories;

import java.io.IOException;
import java.util.Map;

import entity.Course;

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
}
