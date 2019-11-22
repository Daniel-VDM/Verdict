package io.verdict.backend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SearchQuarry {
    static final long nextPageWait = 3000;

    private String location;
    private String lawField;
    private String searchPhrase;
    private int totalResults;
    private HashMap<String, JSONObject> yelpResponse;
    private HashMap<String, JSONObject> dbResponse;
    private SearchListener searchListener;
    private boolean yelpReady;
    private boolean dbReady;

    public SearchQuarry(String location, String lawField,
                        String searchPhrase, SearchListener searchListener) {
        this.location = location;
        this.lawField = lawField;
        this.searchPhrase = searchPhrase;
        this.yelpResponse = new HashMap<>();
        this.dbResponse = new HashMap<>();
        this.searchListener = searchListener;
        this.totalResults = 0;
        this.yelpReady = false;
        this.dbReady = false;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public String getLawField() {
        return lawField;
    }

    public String getLocation() {
        return location;
    }

    public void abort(){
        searchListener.onError("Abort");
    }

    public void forceFinish(){
        ready();
    }

    void putYelpResponse(String key, JSONObject jsonObject) {
        this.yelpResponse.put(key, jsonObject);
    }

    void putDbResponse(String key, JSONObject jsonObject) {
        this.dbResponse.put(key, jsonObject);
    }

    void setYelpReady() {
        this.yelpReady = true;
        if (dbReady) {
            ready();
        }
    }

    void setDbReady() {
        this.dbReady = true;
        if (yelpReady) {
            ready();
        }
    }

    private void clearResults() {
        yelpResponse.clear();
        dbResponse.clear();
        yelpReady = false;
        dbReady = false;
    }

    private void ready() {
        try {
            JSONArray response = new JSONArray();
            for (String key : yelpResponse.keySet()) {
                JSONObject yelpObject = yelpResponse.get(key);
                assert yelpObject != null;
                JSONObject dbObject = dbResponse.get(key);
                yelpObject.put("KEY", key);
                yelpObject.put("DATABASE_CONTENTS",
                        dbObject == null ? JSONObject.NULL : dbObject);
                response.put(yelpObject);
            }
            clearResults();
            searchListener.onFinish(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
