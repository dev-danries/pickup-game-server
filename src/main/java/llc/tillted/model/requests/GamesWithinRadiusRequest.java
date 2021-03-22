package llc.tillted.model.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GamesWithinRadiusRequest {
    @NotNull(message = "currentLatitude cannot be null")
    private double currentLatitude;
    @NotNull(message = "currentLongitude cannot be null")
    private double currentLongitude;
    @NotNull(message = "radius cannot be null")
    private double radius;
    @NotNull(message = "country cannot be null")
    private String country;
    @NotNull(message = "state cannot be null")
    private String state;
    @NotNull(message = "uid cannot be null")
    private String uid;
}
