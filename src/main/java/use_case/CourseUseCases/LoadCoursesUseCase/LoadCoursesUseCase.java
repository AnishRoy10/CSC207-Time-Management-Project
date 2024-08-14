package use_case.CourseUseCases.LoadCoursesUseCase;

import entity.Course;
import entity.User;
import repositories.UserRepository;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interactor class to load courses.
 */
public class LoadCoursesUseCase implements LoadCoursesInputBoundary {
    /// The output boundary to present information to.
    private final LoadCoursesOutputBoundary presenter;

    /// The data access object for users to fetch courses a user is in.
    private final UserRepository userDataAccessObject;

    /**
     * Construct a new interactor instance.
     * @param presenter            The output boundary to present to.
     * @param userDataAccessObject The user data access interface to read from.
     */
    public LoadCoursesUseCase(LoadCoursesOutputBoundary presenter, UserRepository userDataAccessObject) {
        this.presenter = presenter;
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void execute(LoadCoursesInputData inputData) {
        try {
            String username = inputData.getUsername();
            User user = userDataAccessObject.ReadFromCache(username);

            /// Get the courses the user is in.
            ArrayList<String> courseNames = new ArrayList<>(user.getCourses());

            /// Create a success object.
            LoadCoursesOutputData outputData = new LoadCoursesOutputData(
                    true,
                    "",
                    courseNames
            );
            presenter.present(outputData);
        } catch (IOException e) {
            /// Create a failure object.
            LoadCoursesOutputData outputData = new LoadCoursesOutputData(
                    false,
                    "Something went wrong loading your courses."
            );
            presenter.present(outputData);
        }
    }
}
