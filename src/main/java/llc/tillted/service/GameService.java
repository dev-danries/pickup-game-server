package llc.tillted.service;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import llc.tillted.model.Game;
import llc.tillted.repository.GameRepositoryFirebase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static llc.tillted.constants.Country.*;
import static llc.tillted.constants.States.*;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private final GameRepositoryFirebase gameRepositoryFirebase;

    public GameService(GameRepositoryFirebase gameRepositoryFirebase) {
        this.gameRepositoryFirebase = gameRepositoryFirebase;
    }

    public void test() {
        var country = UNITED_STATES.getRawValue();
        var state = PENNSYLVANIA.getRawValue();

        try {
            List<QueryDocumentSnapshot> documentSnapshots = gameRepositoryFirebase.queryByCity(country, state);
            for (QueryDocumentSnapshot document : documentSnapshots) {
                if (document.exists()) {
                    var game = document.toObject(Game.class);
                    LOGGER.info(game.toString());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error Retrieving Data From Firestore");
            e.printStackTrace();
        }
    }

}
