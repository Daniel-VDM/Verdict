package io.verdict.backend;

import org.json.JSONException;
import org.json.JSONObject;


public class UserDataGenerator {
    private static final String TAG = "UserDataGenerator";

    private Backend backend;

    UserDataGenerator(final Backend backend) {
        this.backend = backend;
    }

    private JSONObject generateUserReview(JSONObject lawyer) {
        return null;
    }

    private JSONObject generateLawyerReview(JSONObject lawyer) {
        return null;
    }

    JSONObject generateNewUser() throws JSONException {
        JSONObject user = new JSONObject();
        user.put("USER_TYPE", "user");
        return null;
    }

    JSONObject generateDataForUser(JSONObject user) throws JSONException {
        // TODO: generate reviews based on index from backed (& update index).
        JSONObject data = new JSONObject();
        return null;
    }

    /**
     * This is the main internal method that generates data for a lawyer.
     * This method also updates the firebase's USER_INDEX metadata if it adds new reviewers.
     *
     * @param lawyer Json object for the Yelp api
     * @return New data for the lawyer
     */
    JSONObject generateDataForLawyer(JSONObject lawyer) throws JSONException {
        // TODO: generate peer reviews based on index from backend (& update index)
        JSONObject data = new JSONObject();
        return null;
    }
}
