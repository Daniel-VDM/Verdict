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

    private static final String TAG = "Backend";
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final HashMap<String, Object> databaseCache = new HashMap<>();

    private String googlePlacesApiKey;
    private int serverWaitDelay;

    public Backend() {
        // TODO constructors as needed, possibly from places API
        this.serverWaitDelay = 1000;
        setGooglePlacesApiKey();
    }

    public Backend(int serverWaitDelay){
        this.serverWaitDelay = serverWaitDelay;
        setGooglePlacesApiKey();
    }

    /**
     * @return the firebase instance for more specific database control.
     */
    static FirebaseDatabase getFirebaseInstance() {
        return Backend.database;
    }

    private void setGooglePlacesApiKey(){
        databaseGet("GOOGLE_PACES_API_KEY", new DatabaseListener() {
            @Override
            public void onStart(String key) {
                Log.d(TAG, "Quarry DB for " + key);
            }

            @Override
            public void onSuccess(String key, Object object) {
                googlePlacesApiKey = (String) object;
                Log.d(TAG, "Google Place's API key was set");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
        try {
            Thread.sleep(serverWaitDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Firebase has to handle request asynchronously, so a listener if required
     * when quarrying firebase.
     *
     * Note that the return from the firebase is a java Object that needs to be typecast.
     *
     * @param key The key of the data to get from firebase
     * @param listener The return listener for the firebase response
     */
    public void databaseGet(final String key, final DatabaseListener listener) {
        listener.onStart(key);
        if (databaseCache.containsKey(key)) {
            listener.onSuccess(key, databaseCache.get(key));
            return;
        }
        database.getReference(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                databaseCache.put(key, value);
                listener.onSuccess(key, value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    /**
     * @param key The key of the data to set in the firebase.
     * @param object The data to store in the firebase.
     */
    public void databasePut(String key, Object object) {
        DatabaseReference dbRef = database.getReference(key);
        dbRef.setValue(object);
        databaseCache.put(key, object);
    }

    /**
     * Method for requesting web APIs
     *
     * @param httpUrl the request url.
     * @return the response as a string.
     */
    public String readHttp(String httpUrl) {
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
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            httpData = buf.toString();
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        } finally {
            try {
                assert stream != null;
                stream.close();
                urlConnection.disconnect();
            } catch (Exception e) {
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));
            }
        }
        return httpData;
    }

}
