package use_case.CourseUseCases.LoadCoursesUseCase;

import repositories.UserRepository;

import java.io.IOException;

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
            /// TODO: with interface, fetch all courses and filter by which the user is in
            throw new IOException();
        } catch (IOException e) {
            LoadCoursesOutputData outputData = new LoadCoursesOutputData(
                    false,
                    "Something went wrong loading your courses."
            );
            presenter.present(outputData);
        }
    }
}
