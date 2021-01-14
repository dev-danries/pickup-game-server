package llc.tillted.model.responses;

import llc.tillted.model.ApiError;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ValidationErrorResponse {

    @Builder.Default private String timestamp = LocalDateTime.now().toString();

    @Builder.Default private String message = "There was a problem processing your request";

    @Builder.Default private List<ApiError> errors = new ArrayList<>();

}
