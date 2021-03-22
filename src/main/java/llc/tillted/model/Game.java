package llc.tillted.model;

import com.google.cloud.Timestamp;
import lombok.Data;

import java.util.List;

@Data
public class Game {

    private Timestamp date;
    private String id;
    private int maxNoUsers;
    private Location location;
    private String sportType;
    private List<String> users;

    @Data
    public static class Location {
        private Double lat;
        private Double lng;
        private String address;
    }

}
