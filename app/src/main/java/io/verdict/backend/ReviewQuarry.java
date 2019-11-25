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
import java.util.Random;

public class ReviewQuarry {

    static final long WAIT_DELAY = 125;
    private static final String TAG = "ReviewQuarry";
    private String id;
    private String lawyerKey;
    private SearchListener reviewListener;

    ReviewQuarry(String id, String lawyerKey, SearchListener reviewListener) {
        this.id = id;
        this.lawyerKey = lawyerKey;
        this.reviewListener = reviewListener;
    }

    synchronized void search(final Backend backend, final RequestQueue requestQueue) {
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray reviews = jsonObject.getJSONArray("reviews");
                    JSONObject userIndex = backend.getDbUserIndex();
                    JSONArray users = userIndex.getJSONArray("USERS");
                    JSONObject reviewIndex = backend.getDbReviewIndex();
                    JSONObject userReviews = reviewIndex.getJSONObject("USER_REVIEWS");
                    for (int i = 0; i < reviews.length(); i++) {
                        JSONObject review = reviews.getJSONObject(i);
                        if (new Random().nextInt(100) < 17) {
                            review.put("VERIFIED", true);
                        } else {
                            review.put("VERIFIED", false);
                        }
                        JSONObject user = review.getJSONObject("user");
                        String key = Backend.getKeyFromName(user.getString("name"),
                                user.getString("id"));
                        user.put("USER_TYPE", "user");
                        user.put("KEY", key);
                        backend.databasePut(key, user.toString());
                        review.put("REVIEWER_KEY", key);
                        review.put("LAWYER_KEY", lawyerKey);
                        JSONObject reviewIndexEntry = new JSONObject();
                        reviewIndexEntry.put("REVIEWER_KEY", key);
                        reviewIndexEntry.put("LAWYER_KEY", lawyerKey);
                        if (!users.toString().contains(key)) {
                            users.put(key);
                            backend.databasePut("META_USER_INDEX", userIndex.toString());
                        }
                        if (!userReviews.has(key)) {
                            userReviews.put(key, new JSONArray());
                        }
                        JSONArray thisReviews = userReviews.getJSONArray(key);
                        if (!thisReviews.toString().contains(reviewIndexEntry.toString())) {
                            userReviews.getJSONArray(key).put(reviewIndexEntry);
                        }
                    }
                    backend.databasePut("META_REVIEW_INDEX", reviewIndex.toString());
                    reviewListener.onFinish(reviews);
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
