package llc.tillted.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

    private String fieldName;

    private String fieldValue;

    private String message;
}
