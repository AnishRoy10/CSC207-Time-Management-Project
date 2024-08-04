package app.gui;

import data_access.CourseDataAccessObject;
import data_access.FileCacheUserDataAccessObject;
import framework.view.CourseView;
import framework.view.CreateCoursePrompt;
import framework.view.JoinCoursePrompt;
import framework.view.LeaveCoursePrompt;
import interface_adapter.controller.CoursePromptController;
import interface_adapter.controller.CourseViewController;
import interface_adapter.presenter.CourseListPresenter;
import interface_adapter.presenter.CoursePromptPresenter;
import interface_adapter.presenter.CourseViewPresenter;
import interface_adapter.viewmodel.CourseListViewModel;
import interface_adapter.viewmodel.CoursePromptViewModel;
import interface_adapter.viewmodel.CourseViewModel;
import repositories.CourseRepository;
import repositories.UserRepository;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseInputBoundary;
import use_case.CourseUseCases.CreateCourseUseCase.CreateCourseUseCase;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseInputBoundary;
import use_case.CourseUseCases.JoinCourseUseCase.JoinCourseUseCase;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseInputBoundary;
import use_case.CourseUseCases.LeaveCourseUseCase.LeaveCourseUseCase;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesInputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesOutputBoundary;
import use_case.CourseUseCases.LoadCoursesUseCase.LoadCoursesUseCase;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseInputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseOutputBoundary;
import use_case.CourseUseCases.ViewCourseUseCase.ViewCourseUseCase;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Initialize the course view and its necessary use case objects.
 */
public class CourseInitializer {
    /**
     * Initialize the main course view.
     */
    public static void initializeView(String username) {
        try {
            String usersPath = "src/main/java/data_access/userCache.json";
            String coursesPath = "src/main/java/data_access/courseCache.json";

            UserRepository userDataAccessObject = new FileCacheUserDataAccessObject(usersPath);
            CourseRepository courseDataAccessObject = new CourseDataAccessObject(coursesPath);

            CourseViewModel viewModel = new CourseViewModel();
            CourseListViewModel listViewModel = new CourseListViewModel();

            ViewCourseOutputBoundary courseViewPresenter = new CourseViewPresenter(viewModel);
            LoadCoursesOutputBoundary courseListPresenter = new CourseListPresenter(listViewModel);

            ViewCourseInputBoundary courseViewInteractor = new ViewCourseUseCase(
                    courseViewPresenter, courseDataAccessObject
            );

            LoadCoursesInputBoundary courseListInteractor = new LoadCoursesUseCase(
                    courseListPresenter, userDataAccessObject
            );

            CourseViewController courseViewController = new CourseViewController(
                    courseViewInteractor, courseListInteractor
            );

            CourseView view = new CourseView(
                    username, courseViewController, viewModel, listViewModel
            );
            view.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the course creation prompt.
     */
    public static void initializeCreatePrompt(String username) {
        try {
            String usersPath = "src/main/java/data_access/userCache.json";
            String coursesPath = "src/main/java/data_access/courseCache.json";

            UserRepository userDataAccessObject = new FileCacheUserDataAccessObject(usersPath);
            CourseRepository courseDataAccessObject = new CourseDataAccessObject(coursesPath);
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            CoursePromptPresenter presenter = new CoursePromptPresenter(
                    viewModel, null, null);
            CreateCourseInputBoundary createCourseInteractor = new CreateCourseUseCase(
                    presenter, courseDataAccessObject, userDataAccessObject);
            CoursePromptController controller = new CoursePromptController(createCourseInteractor);

            new CreateCoursePrompt(username, controller, viewModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the join course prompt.
     * @param username The username of the current user.
     */
    public static void initializeJoinPrompt(String username) {
        try {
            String usersPath = "src/main/java/data_access/userCache.json";
            String coursesPath = "src/main/java/data_access/courseCache.json";

            UserRepository userDataAccessObject = new FileCacheUserDataAccessObject(usersPath);
            CourseRepository courseDataAccessObject = new CourseDataAccessObject(coursesPath);
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            CoursePromptPresenter presenter = new CoursePromptPresenter(
                    null, viewModel, null);
            JoinCourseInputBoundary joinCourseInteractor = new JoinCourseUseCase(
                    presenter, userDataAccessObject, courseDataAccessObject);
            CoursePromptController controller = new CoursePromptController(joinCourseInteractor);

            new JoinCoursePrompt(username, controller, viewModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the leave course prompt.
     * @param username The username of the current user.
     */
    public static void initializeLeavePrompt(String username) {
        try {
            String usersPath = "src/main/java/data_access/userCache.json";
            String coursesPath = "src/main/java/data_access/courseCache.json";

            UserRepository userDataAccessObject = new FileCacheUserDataAccessObject(usersPath);
            CourseRepository courseDataAccessObject = new CourseDataAccessObject(coursesPath);
            CoursePromptViewModel viewModel = new CoursePromptViewModel();
            CoursePromptPresenter presenter = new CoursePromptPresenter(
                    null, null, viewModel);
            LeaveCourseInputBoundary leaveCourseInteractor = new LeaveCourseUseCase(
                    presenter, userDataAccessObject, courseDataAccessObject);
            CoursePromptController controller = new CoursePromptController(leaveCourseInteractor);

            new LeaveCoursePrompt(username, controller, viewModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
