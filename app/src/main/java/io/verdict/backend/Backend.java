package io.verdict.backend;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class Backend {

    private static final String TAG = "Backend";
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final HashMap<String, Object> databaseCache = new HashMap<>();
    // Don't steal my key pls and thx.
    private static final String googlePlacesApiKey = "AIzaSyBofzJmsX2Lue1J3xALbOuy8j91y9EVO78";
    private Random RNG;

    public Backend() {
        RNG = new Random();
    }

    /**
     * @return the firebase instance for more specific database control.
     */
    public static FirebaseDatabase getFirebaseInstance() {
        return Backend.database;
    }

    /**
     * Simple method to clear this classes cache.
     */
    public static void clearCache() {
        Backend.databaseCache.clear();
    }

    /**
     * Firebase has to handle request asynchronously, so a listener if required
     * when quarrying firebase.
     * Note that the return from the firebase is a java Object that needs to be typecast.
     *
     * @param key      The key of the data to get from firebase
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
                Object value = null;
                if (dataSnapshot.exists()) {
                    value = dataSnapshot.getValue();
                    databaseCache.put(key, value);
                }
                // TODO: generate fake data for the Firebase.
                listener.onSuccess(key, value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    /**
     * @param key    The key of the data to set in the firebase.
     * @param object The data to store in the firebase.
     */
    public void databasePut(String key, Object object) {
        DatabaseReference dbRef = database.getReference(key);
        dbRef.setValue(object);
        databaseCache.put(key, object);
    }

    /**
     * @param name the clients name from the google places API
     * @return the key used to look up data for this user the Firebase.
     */
    public String getUserKeyFromName(String name) {
        name = name.replace(".", "").replace("$", "")
                .replace("[", "").replace("]", "")
                .replace("#", "").replace("/", "")
                .replace(" ", "_");
        return "USER_" + name;
    }

    // Private method used in Backend#searchLawyers
    private void handlePlacesResponse(JSONObject jsonObject, final SearchQuarry searchQuarry)
            throws JSONException {
        JSONArray results = (JSONArray) jsonObject.get("results");
        if (jsonObject.has("next_page_token")) {
            searchQuarry.setPlacesNextToken(jsonObject.getString("next_page_token"));
        } else {
            searchQuarry.setPlacesNextToken(null);
        }
        final Boolean[] dbGetIsDone = new Boolean[results.length()];
        Arrays.fill(dbGetIsDone, false);
        for (int i = 0; i < results.length(); i++) {
            final int iFinal = i;
            JSONObject lawyer = (JSONObject) results.get(i);
            final String key = getUserKeyFromName(lawyer.getString("name"));
            databaseGet(key, new DatabaseListener() {
                @Override
                public void onStart(String key) {
                }

                @Override
                public void onSuccess(String key, Object object) {
                    // TODO: generate random price for price rating...
                    dbGetIsDone[iFinal] = true;
                    searchQuarry.putDbResponse(key, (JSONObject) object);
                    if (!Arrays.asList(dbGetIsDone).contains(false)) {
                        searchQuarry.setDbReady();
                    }
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.e(TAG, "Error for " + key + " : " + databaseError.getMessage());
                }
            });
            searchQuarry.putPlacesResponse(key, lawyer);
        }
        searchQuarry.setPlacesReady();
    }

    /**
     * Main exposed API to search for a lawyer and get data back.
     * Note that a SeachQuarry object is needed for the data management and listener.
     *
     * @param context      The context of the activity calling the search
     * @param searchQuarry The SearchQuarry instance that contains the search params
     *                     as well as the listener for the view updates.
     */
    public void searchLawyers(Context context, final SearchQuarry searchQuarry) {
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    handlePlacesResponse(jsonObject, searchQuarry);
                } catch (JSONException e) {
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, Objects.requireNonNull(error.getMessage()));
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String placesUrl;
        if (searchQuarry.getPlacesNextToken() != null) {
            placesUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                    "pagetoken=" + searchQuarry.getPlacesNextToken() +
                    "&key=" + googlePlacesApiKey;
            try {
                Thread.sleep(SearchQuarry.nextPageWait);  // Play it safe...
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            placesUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                    "location=" + searchQuarry.getLat() + "," + searchQuarry.getLng() +
                    "&radius=80467.2" + // 50 miles in meters.
                    "&keyword=lawyer%20" + searchQuarry.getLawField() +
                    "%20" + searchQuarry.getSearchPhrase() +
                    "&key=" + googlePlacesApiKey;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                placesUrl,
                (String) null,
                responseListener,
                errorListener
        );
        requestQueue.add(request);
    }

    // TODO create API for forums requests

    // TODO create API for review + forum update
}
