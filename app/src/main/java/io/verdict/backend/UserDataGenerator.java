package io.verdict.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class UserDataGenerator {
    private static final String TAG = "UserDataGenerator";

    private Backend backend;
    private Random RNG;

    UserDataGenerator(final Backend backend) {
        this.backend = backend;
        RNG = new Random();
    }


    private JSONObject generateLawyerReview(JSONObject targetLawyer, String sourceLawyerKey,
                                            double rating) {
        JSONObject review = new JSONObject();
        // TODO: generate peer reviews based on index from backend...
        // Prob read from json file and randomly choose a generic review depending on rating...
        return review;
    }

    private JSONObject generateLawyerAboutMe(JSONObject lawyer) {
        JSONObject aboutMe = new JSONObject();
        // TODO: create about-me page...
        // Prob read from a json file and randomly choose a generic about me page....
        return aboutMe;
    }

    /**
     * This is the main internal method that generates data for a lawyer.
     * This method also updates the firebase's USER_INDEX metadata if it adds new reviewers.
     *
     * @param lawyer Json object for the Yelp api
     * @return The data object associated with LAWYER's KEY in Firebase.
     */
    JSONObject generateDataForLawyer(JSONObject lawyer) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("ABOUT-ME", generateLawyerAboutMe(lawyer));
        JSONArray storedLawyers = backend.getDbUserIndex().getJSONArray("LAWYERS");
        List<String> allLawyers = new ArrayList<>();
        for (int i = 0; i < storedLawyers.length(); i++) {
            allLawyers.add(storedLawyers.getString(i));
        }
        Collections.shuffle(allLawyers);
        int numReviews = RNG.nextInt(allLawyers.size() / 10);
        double rating = lawyer.getDouble("rating");
        JSONArray lawyerReviews = new JSONArray();
        for (int i = 0; i < numReviews; i++) {
            if (RNG.nextDouble() < 0.5) {
                String sourceLawyerKey = allLawyers.get(i);
                JSONObject review = generateLawyerReview(lawyer, sourceLawyerKey, rating);
                review.put("REVIEWER_KEY", sourceLawyerKey);
                review.put("LAWYER_KEY", Backend.getKeyFromName(
                        lawyer.getString("name"), lawyer.getString("id")));
                lawyerReviews.put(review);
                synchronized (this) {
                    JSONObject reviewIndex = backend.getDbReviewIndex();
                    JSONObject peerReviews = reviewIndex.getJSONObject("PEER_REVIEWS");
                    if (!peerReviews.has(sourceLawyerKey)) {
                        peerReviews.put(sourceLawyerKey, new JSONArray());
                    }
                    JSONArray thisReviews = peerReviews.getJSONArray(sourceLawyerKey);
                    if (!thisReviews.toString().contains(review.toString())) {
                        peerReviews.getJSONArray(sourceLawyerKey).put(review);
                    }
                    backend.databasePut("META_REVIEW_INDEX", reviewIndex.toString());
                }
            }
        }
        data.put("PEER_REVIEWS", lawyerReviews);
        return data;
    }

    // TODO: handle generation of forums reviews, probably do this based on index
}
