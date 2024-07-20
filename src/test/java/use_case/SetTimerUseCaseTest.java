package use_case;

import entity.TodoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import use_case.SetTimerUseCase.SetTimerDataAccessInterface;
import use_case.SetTimerUseCase.SetTimerInteractor;
import use_case.SetTimerUseCase.SetTimerOutputBoundary;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SetTimerUseCaseTest {
    private SetTimerDataAccessInterface setTimerDataAccessInterface;
    private SetTimerOutputBoundary setTimerOutputBoundary;
    private SetTimerInteractor setTimerInteractor;

    @BeforeEach
    public void setUp() {
        setTimerDataAccessInterface = mock(SetTimerDataAccessInterface.class);
        setTimerOutputBoundary = mock(SetTimerOutputBoundary.class);
        setTimerInteractor = new SetTimerInteractor(setTimerDataAccessInterface, setTimerOutputBoundary);
    }

    @Test
    public void testSetTimerSuccess() {
        // Test successful initialization of the timer.
    }

    @Test
    public void testSetTimerZeroInput() {
        // Test when inputs are all zero.
    }
}
