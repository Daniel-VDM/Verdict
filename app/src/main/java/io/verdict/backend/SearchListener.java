package io.verdict.backend;

import org.json.JSONArray;

public interface SearchListener {
    void onFinish(JSONArray jsonArray);

    void onError(String message);
}
