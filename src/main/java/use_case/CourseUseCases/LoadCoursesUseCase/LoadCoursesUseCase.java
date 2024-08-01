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
    private final LoadCoursesOutputBoundary presenter;
    private final UserRepository userDataAccessObject;

    public LoadCoursesUseCase(LoadCoursesOutputBoundary presenter, UserRepository userDataAccessObject) {
        this.presenter = presenter;
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void execute(LoadCoursesInputData inputData) {
        try {
            String username = inputData.getUsername();
            User user = userDataAccessObject.ReadFromCache(username);

            ArrayList<String> courseNames = new ArrayList<>(user.getCourses());

            LoadCoursesOutputData outputData = new LoadCoursesOutputData(
                    true,
                    "",
                    courseNames
            );
            presenter.present(outputData);
        } catch (IOException e) {
            LoadCoursesOutputData outputData = new LoadCoursesOutputData(
                    false,
                    "Something went wrong loading your courses."
            );
            presenter.present(outputData);
        }
    }
}
