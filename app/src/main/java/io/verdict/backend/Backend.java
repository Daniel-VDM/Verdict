package io.verdict.backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

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

    private String readHttp(String httpUrl) {
        String httpData = "";
        InputStream stream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(httpUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buf = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            httpData = buf.toString();
            reader.close();
        } catch (Exception e) {
            Log.e("HttpRequestHandler", Objects.requireNonNull(e.getMessage()));
        } finally {
            try {
                assert stream != null;
                stream.close();
                urlConnection.disconnect();
            } catch (Exception e) {
                Log.e("HttpRequestHandler", Objects.requireNonNull(e.getMessage()));
            }
        }
        return httpData;
    }

}
