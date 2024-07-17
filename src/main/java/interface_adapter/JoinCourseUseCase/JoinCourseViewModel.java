package interface_adapter.JoinCourseUseCase;

/**
 * View model for the join course prompt.
 */
public class JoinCourseViewModel {
		private String message;

		public JoinCourseViewModel(String message) {
				this.message = message;
		}

		public String getMessage() {
				return message;
		}

		public void setMessage(String message) {
				this.message = message;
		}
}
