package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The User class represents a user of the program.
 */
public class User implements Serializable {
  // The username of this user
  private String username;

  // Friends list of this user
  private final FriendsList friends;

  // Courses this user is in
  private final ArrayList<Course> courses;

  // Todolist associated with this user
  private final TodoList todo;

  /**
   * Construct a new User object.
   * 
   * @param username username for this user
   * @param friends  friends for this user
   * @param courses  courses this user is in
   */
  public User(String username, User[] friends, Course[] courses) {
      this.username = username;
      this.friends = new FriendsList(friends);
      this.courses = new ArrayList<>();
      this.courses.addAll(Arrays.asList(courses));
      this.todo = new TodoList();
  }

  /**
   * Get this users username.
   * 
   * @return the username of this user
   */
  public String getUsername() {
      return this.username;
  }

  /**
   * Change this users username.
   * 
   * @param username the new username for this user
   */
  public void setUsername(String username) {
      this.username = username;
  }

  /**
   * Retrieve this users friendslist
   */
    public FriendsList getFriends() {
        return this.friends;
    }

  /**
   * Add a friend to this user.
   * 
   * @param user the new friend for this user
   */
  public void addFriend(User user) {
      this.friends.addFriend(user);
  }

  /**
   * Attempt to remove a friend from this user.
   * 
   * @param user the friend to remove
   */
  public void removeFriend(User user) {
      this.friends.removeFriend(user);
  }

  /**
   * Add a task to this users todolist.
   * 
   * @param task the task to add
   */
  public void addTask(Task task) {
      this.todo.addTask(task);
  }

  /**
   * Remove a task from this users todolist.
   * 
   * @param task the task to remove
   */
  public void removeTask(Task task) {
      this.todo.removeTask(task);
  }

  /**
   * Retrieve this users courses
   */
  public ArrayList<Course> getCourses() {
      return this.courses;
  }
  /**
   * Place this user in a new course.
   * 
   * @param course the course to put this user in
   */
  public void addCourse(Course course) {
      course.addUser(this);
      if (!this.courses.contains(course)) {
          this.courses.add(course);
      }
  }

  /**
   * Attempts to remove this user from the target course.
   * Returns whether the removal was successful.
   * 
   * @param course the course to remove this user from
   * @return       success value of the method
   */
  public boolean removeCourse(Course course) {
      if (this.courses.contains(course)) {
          return course.removeUser(this);
      }
      return false;
  }
}
