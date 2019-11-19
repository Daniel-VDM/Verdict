package io.verdict.backend;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchQuarry {
    private double lat;
    private double lng;
    private String lawField;
    private String searchPhrase;
    private String placesNextToken;
    private JSONArray placesResponse;
    private JSONArray dbResponse;
    private SearchListener searchListener;

    public SearchQuarry(double lat, double lng, String lawField,
                        String searchPhrase, SearchListener searchListener) {
        this.lat = lat;
        this.lng = lng;
        this.lawField = lawField;
        this.searchPhrase = searchPhrase;
        this.placesResponse = new JSONArray();
        this.dbResponse = new JSONArray();
        this.searchListener = searchListener;
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

    public JSONArray getPlacesResponse() {
        return placesResponse;
    }

    public void putPlacesResponse(JSONObject jsonObject) {
        this.placesResponse.put(jsonObject);
    }

    public JSONArray getDbResponse() {
        return dbResponse;
    }

    public void putDbResponse(JSONObject jsonObject) {
        this.dbResponse.put(jsonObject);
    }

    public String getPlacesNextToken() {
        return placesNextToken;
    }

    public void setPlacesNextToken(String placesNextToken) {
        this.placesNextToken = placesNextToken;
    }

    void ready() {
//        JSONArray result = new JSONArray();
        JSONArray result = placesResponse;  // TODO: join the 2 arrays together for final result.
        searchListener.onFinish(result);
    }
}
