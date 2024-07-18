package interface_adapter.JoinCourseUseCase;

/**
 * View model for the join course prompt.
 */
public class JoinCourseViewModel {
	private boolean response;
	private String message;

	public boolean getResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
