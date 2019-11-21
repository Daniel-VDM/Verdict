package io.verdict.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SearchQuarry {
    static final long nextPageWait = 3000;

    private double lat;
    private double lng;
    private String lawField;
    private String searchPhrase;
    private String placesNextToken;
    private HashMap<String, JSONObject> placesResponse;
    private HashMap<String, JSONObject> dbResponse;
    private SearchListener searchListener;
    private boolean placesReady;
    private boolean dbReady;

    public SearchQuarry(double lat, double lng, String lawField,
                        String searchPhrase, SearchListener searchListener) {
        this.lat = lat;
        this.lng = lng;
        this.lawField = lawField;
        this.searchPhrase = searchPhrase;
        this.placesResponse = new HashMap<>();
        this.dbResponse = new HashMap<>();
        this.searchListener = searchListener;
        this.placesReady = false;
        this.dbReady = false;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public String getLawField() {
        return lawField;
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public String getPlacesNextToken() {
        return placesNextToken;
    }

    void setPlacesNextToken(String placesNextToken) {
        this.placesNextToken = placesNextToken;
    }

    void putPlacesResponse(String key, JSONObject jsonObject) {
        this.placesResponse.put(key, jsonObject);
    }

    void putDbResponse(String key, JSONObject jsonObject) {
        this.dbResponse.put(key, jsonObject);
    }

    void setPlacesReady() {
        this.placesReady = true;
        if (dbReady) {
            ready();
        }
    }

    void setDbReady() {
        this.dbReady = true;
        if (placesReady) {
            ready();
        }
    }

    private void clearResults() {
        placesResponse.clear();
        dbResponse.clear();
        placesReady = false;
        dbReady = false;
    }

    private void ready() {
        try {
            JSONArray response = new JSONArray();
            for (String key : placesResponse.keySet()) {
                JSONObject placesObject = placesResponse.get(key);
                assert placesObject != null;
                JSONObject dbObject = dbResponse.get(key);
                placesObject.put("DATABASE_CONTENTS",
                        dbObject == null ? JSONObject.NULL : dbObject);
                placesObject.put("KEY", key);
                response.put(placesObject);
            }
            clearResults();
            searchListener.onFinish(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
