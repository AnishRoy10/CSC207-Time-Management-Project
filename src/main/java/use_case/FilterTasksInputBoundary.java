package use_case;

import java.util.List;

public interface FilterTasksInputBoundary {
    List<TaskResponseModel> execute(FilterTasksRequestModel requestModel);
}
