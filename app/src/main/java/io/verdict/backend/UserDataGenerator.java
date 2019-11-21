package io.verdict.backend;

import org.json.JSONException;
import org.json.JSONObject;


public class UserDataGenerator {
    private static final String TAG = "UserDataGenerator";

    private Backend backend;

    UserDataGenerator(final Backend backend) {
        this.backend = backend;
    }

    private JSONObject generateUser(){
        return null;
    }

    private JSONObject generateLawyerReview(JSONObject lawyer) {
        return null;
    }

    /**
     * This is the main internal method that generates data for a lawyer.
     * This method also updates the firebase's USER_INDEX metadata if it adds new reviewers.
     *
     * @param lawyer Json object for the Yelp api
     * @return The data object associated with LAWYER's KEY in Firebase.
     */
    JSONObject generateDataForLawyer(JSONObject lawyer) throws JSONException {
        // TODO: generate peer reviews based on index from backend (& update index)
        // TODO: create about-me page...
        // TODO: store extra
        // TODO: store / create forum thread data in
        JSONObject data = new JSONObject();
        return data;
    }
}
