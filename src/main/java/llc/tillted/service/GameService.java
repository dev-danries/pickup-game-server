package llc.tillted.service;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import llc.tillted.model.Game;
import llc.tillted.model.requests.GamesWithinRadiusRequest;
import llc.tillted.repository.GameRepositoryFirebase;
import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private static final double CONVERSION_TO_MILES_FROM_METERS_CONST = 0.000621371192;

    private final GameRepositoryFirebase gameRepositoryFirebase;

    public GameService(GameRepositoryFirebase gameRepositoryFirebase) {
        this.gameRepositoryFirebase = gameRepositoryFirebase;
    }

    public List<Game> gamesWithinRadius(GamesWithinRadiusRequest request) {
        List<Game> response = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documentSnapshots = gameRepositoryFirebase.queryByState(request.getCountry(),
                    request.getState());
            filterResultsByUID(documentSnapshots, request.getUid());
            for (QueryDocumentSnapshot document: documentSnapshots) {
                if (document.exists()) {
                    var game = document.toObject(Game.class);
                    double distance = calculateDistanceInMiles(request.getCurrentLatitude(),
                            request.getCurrentLongitude(), game.getLocation());
                    if (distance < request.getRadius() && !game.getUsers().contains(request.getUid())) {
                        response.add(game);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error Retrieving Data From Firestore");
            e.printStackTrace();
        }

        return response;
    }

    private double calculateDistanceInMiles(double latitude, double longitude, Game.Location location) {
        GeodesicData g = Geodesic.WGS84.Inverse(latitude, longitude, location.getLat(), location.getLng());
        return g.s12 * CONVERSION_TO_MILES_FROM_METERS_CONST;
    }

    private void filterResultsByUID(List<QueryDocumentSnapshot> documentSnapshots, String uid) {
    }

}
