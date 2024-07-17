package interface_adapter.join_course;

import entity.User;

/**
 * View model for the join course prompt, providing a user to join to a course.
 */
public class JoinCourseViewModel {
		private User user;

		public JoinCourseViewModel(User user) {
				this.user = user;
		}

		public User getUser() {
				return user;
		}

		public void setUser(User user) {
				this.user = user;
		}
}
