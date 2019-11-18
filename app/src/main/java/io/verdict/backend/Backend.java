package io.verdict.backend;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Backend {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String TAG = "Backend";

    private HashMap<String, Object> cacheMap;

    public Backend() {
        cacheMap = new HashMap<>();
        // TODO constructors as needed, possibly from places API
    }

    static FirebaseDatabase getFirebaseInstance() {
        return Backend.database;
    }

    public void getDatabase(final String key, final DatabaseListener listener) {
        listener.onStart(key);
        if (cacheMap.containsKey(key)) {
            listener.onSuccess(key, cacheMap.get(key));
            return;
        }
        database.getReference(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onSuccess(key, dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    public void putDatabase(String key, Object object) {
        DatabaseReference dbRef = database.getReference(key);
        dbRef.setValue(object);
        cacheMap.put(key, object);
    }

}
