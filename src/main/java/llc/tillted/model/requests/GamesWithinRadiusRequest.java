package llc.tillted.model.requests;

import llc.tillted.constants.Country;
import llc.tillted.constants.State;
import lombok.Data;

@Data
public class GamesWithinRadiusRequest {
    private double currentLatitude;
    private double currentLongitude;
    private double radius;
    private Country country;
    private State state;
}
