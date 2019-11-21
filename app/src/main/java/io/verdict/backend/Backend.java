package io.verdict.backend;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
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
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import io.verdict.R;

public class Backend {

    // Don't steal my key pls and thx.
    static final String yelpApiKey = "PuaW8VIj-ysAuJ3aTlw5JWPI_kMN31KwguyzEHOWjtW_Ck" +
            "Ohco62syp05jmQQp0R1xHFJYc2QRGcj5RI46iqVR35YmeWEjxtFxhBhsEzAWOGpHBuLRshgHTFqgHWXXYx";
    private static final String TAG = "Backend";
    private static final int SEARCH_RESULT_LIMIT = 30;
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final HashMap<String, String> databaseCache = new HashMap<>();
    private static JSONObject dbUserIndex = null;
    private UserDataGenerator userDataGenerator;
    private Random RNG;

    public Backend() {
        if (Backend.dbUserIndex == null) {
            pullDbUserIndex();
        }
        userDataGenerator = new UserDataGenerator(this);
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
     * Simple method to pull the userdata from firebase.
     */
    public void pullDbUserIndex() {
        databaseGet("META_USER_INDEX", new DatabaseListener() {
            @Override
            public void onStart(String key) {
            }

            @Override
            public void onSuccess(String key, String string) {
                if (string != null) {
                    try {
                        dbUserIndex = new JSONObject(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
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
                String value = null;
                if (dataSnapshot.exists()) {
                    value = (String) dataSnapshot.getValue();
                    databaseCache.put(key, value);
                }
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
     * @param string The serialized data to store in the firebase.
     */
    public void databasePut(String key, String string) {
        DatabaseReference dbRef = database.getReference(key);
        dbRef.setValue(string);
        databaseCache.put(key, string);
    }

    /**
     * @param name the clients name from the yelp API
     * @return the key used to look up data for this user the Firebase.
     */
    public String getUserKeyFromName(String name) {
        name = name.replace(".", "").replace("$", "")
                .replace("[", "").replace("]", "")
                .replace("#", "").replace("/", "")
                .replace(" ", "_");
        return "USER_" + name;
    }

    // Helper method to fetch a new yelp review
    private void fetchNewYelpReview(final JSONObject lawyer, final String id,
                                    final Boolean[] dbGetIsDone, final int doneIndex,
                                    final SearchQuarry searchQuarry, final String key,
                                    final RequestQueue requestQueue)
            throws JSONException, InterruptedException {
        final JSONObject obj = userDataGenerator.generateDataForLawyer(lawyer);
        new Thread() {
            @Override
            public void run() {
                new ReviewQuarry(id, new SearchListener() {
                    @Override
                    public void onFinish(JSONArray jsonArray) {
                        try {
                            obj.put("USER_REVIEWS", jsonArray);
                            dbGetIsDone[doneIndex] = true;
                            searchQuarry.putDbResponse(key, obj);
                            databasePut(key, obj.toString());
                            if (!Arrays.asList(dbGetIsDone).contains(false)) {
                                searchQuarry.setDbReady();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Log.e(TAG, message);
                    }
                }).search(requestQueue);
            }
        }.start();
        Thread.sleep(ReviewQuarry.WAIT_DELAY);
    }

    // Private method used in Backend#searchLawyers
    private void handleYelpSearchResponse(final Context context, final JSONObject jsonObject,
                                          final RequestQueue requestQueue,
                                          final SearchQuarry searchQuarry)
            throws JSONException {
        JSONArray results = (JSONArray) jsonObject.get("businesses");
        searchQuarry.setTotalResults(jsonObject.getInt("total"));
        final Boolean[] dbGetIsDone = new Boolean[results.length()];
        Arrays.fill(dbGetIsDone, false);
        for (int i = 0; i < results.length(); i++) {
            final int iFinal = i;
            final JSONObject lawyer = (JSONObject) results.get(i);
            final String key = getUserKeyFromName(lawyer.getString("name"));
            final String id = lawyer.getString("id");
            lawyer.put("USER_TYPE", "lawyer");
            databaseGet(key, new DatabaseListener() {
                @Override
                public void onStart(String key) {
                }

                @Override
                public void onSuccess(final String key, String string) {
                    try {
                        if (string == null ||
                                context.getResources().getBoolean(R.bool.force_generate) ||
                                new JSONObject(string)
                                        .getJSONArray("USER_REVIEWS").length() == 0) {
                            fetchNewYelpReview(lawyer, id, dbGetIsDone,
                                    iFinal, searchQuarry, key, requestQueue);
                        } else {
                            dbGetIsDone[iFinal] = true;
                            searchQuarry.putDbResponse(key, new JSONObject(string));
                            if (!Arrays.asList(dbGetIsDone).contains(false)) {
                                searchQuarry.setDbReady();
                            }
                        }
                    } catch (JSONException | InterruptedException e) {
                        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                    }
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.e(TAG, "Error for " + key + " : " + databaseError.getMessage());
                }
            });
            searchQuarry.putYelpResponse(key, lawyer);
        }
        searchQuarry.setYelpReady();
    }

    /**
     * Main exposed API to search for a lawyer and get data back.
     * Note that a SeachQuarry object is needed for the data management and listener.
     *
     * @param context      The context of the activity calling the search
     * @param searchQuarry The SearchQuarry instance that contains the search params
     *                     as well as the listener for the view updates.
     */
    synchronized public void searchLawyers(final Context context, final SearchQuarry searchQuarry) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    handleYelpSearchResponse(context, jsonObject, requestQueue, searchQuarry);
                } catch (JSONException e) {
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Volley already dumps an error message to the log
            }
        };
        String yelpSearchUrl = "https://api.yelp.com/v3/businesses/search?" +
                "location=" + searchQuarry.getLocation() +
                "&limit=" + Backend.SEARCH_RESULT_LIMIT +
                "&radius=40000" + // ~25 miles in meters.
                "&term=" + searchQuarry.getLawField() +
                " lawyer " + searchQuarry.getSearchPhrase();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                yelpSearchUrl,
                (String) null,
                responseListener,
                errorListener
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + yelpApiKey);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(request);
    }

    public JSONObject getDbUserIndex() {
        return dbUserIndex;
    }

    // TODO create API for forums requests

    // TODO create API for review + forum update
}
