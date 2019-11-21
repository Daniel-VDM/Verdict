package io.verdict.backend;

import com.google.firebase.database.DatabaseError;

import java.util.HashMap;

public interface DatabaseListener {
    HashMap<String, Object> attributes = new HashMap<>();

    void onStart(String key);

    void onSuccess(String key, String value);

    void onFailed(DatabaseError databaseError);
}
