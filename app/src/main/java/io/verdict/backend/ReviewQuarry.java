package io.verdict.backend;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReviewQuarry {

    static final long WAIT_DELAY = 100;
    private static final String TAG = "ReviewQuarry";
    private String id;
    private SearchListener reviewListener;

    ReviewQuarry(String id, SearchListener reviewListener) {
        this.id = id;
        this.reviewListener = reviewListener;
    }

    synchronized void search(final RequestQueue requestQueue) {
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    // TODO: go through reviews and generate / update indexes
                    reviewListener.onFinish(jsonObject.getJSONArray("reviews"));
                } catch (JSONException e) {
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Volley already dumps an error message to the log
                reviewListener.onFinish(new JSONArray());
            }
        };

        String yelpSearchUrl = "https://api.yelp.com/v3/businesses/" + id + "/reviews";
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                yelpSearchUrl,
                (String) null,
                responseListener,
                errorListener
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + Backend.yelpApiKey);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy());
        requestQueue.add(request);
    }

    public String getId() {
        return id;
    }
}
