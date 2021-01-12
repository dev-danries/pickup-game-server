package llc.tillted.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameRepositoryFirebase {

    private final Firestore db;

    public GameRepositoryFirebase(Firestore db) {
        this.db = db;
    }

    public List<QueryDocumentSnapshot> queryByState(String country, String state) throws Exception {
        ApiFuture<QuerySnapshot> future =
                db.collection("games").document(country).collection(state).get();

        return future.get().getDocuments();
    }

}
