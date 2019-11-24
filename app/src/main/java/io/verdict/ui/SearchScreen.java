package io.verdict.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;
import io.verdict.backend.SearchListener;
import io.verdict.backend.SearchQuarry;
import io.verdict.ui.DetailScreen.DetailScreen;

public class SearchScreen extends AppCompatActivity {

    private static final String TAG = "SearchScreen";
    private final Backend backend = new Backend();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        final Button tabSearchButton = findViewById(R.id.tab_search_search);
        final Button tabForumButton = findViewById(R.id.tab_forum_search);
        final ImageView tabSearchHighlight = findViewById(R.id.tab_search_highlight_search);
        final ImageView tabForumHighlight = findViewById(R.id.tab_forum_highlight_search);

        tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        tabSearchHighlight.setImageAlpha(255);
        tabForumHighlight.setImageAlpha(0);

        tabSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
                tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
                tabSearchHighlight.setImageAlpha(255);
                tabForumHighlight.setImageAlpha(0);
            }
        });

        tabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
                tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
                tabSearchHighlight.setImageAlpha(0);
                tabForumHighlight.setImageAlpha(255);
            }
        });

        databaseExample();
        searchLawyerExample();  // Uncomment to test Detail screen
    }

    // All initial data collection needs to be handled asynchronously
    // Yelp tends to give more results with an empty search phrase.
    private void searchLawyerExample() {
        final String lawField = "Criminal";
        final SearchQuarry searchQuarry = new SearchQuarry("Berkeley",
                lawField, "", new SearchListener() {
            @Override
            public void onFinish(JSONArray jsonArray) {
                try {
                    JSONObject lawyer = jsonArray.getJSONObject(0);
                    Intent detailScreen = new Intent(SearchScreen.this, DetailScreen.class);
                    detailScreen.putExtra("data", lawyer.toString());
                    detailScreen.putExtra("lawField", lawField);
                    startActivity(detailScreen);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, message);
            }
        });

        // Needs thread to not block quarries.
        // In practice, ALL UI updates NEED to be called in UI thread, use 'runOnUiThread' function.
        new Thread() {
            @Override
            public void run() {
                backend.searchLawyers(SearchScreen.this, searchQuarry);
            }
        }.start();


    }

    private void databaseExample() {
        final Vector<Object> exampleData = new Vector<>();  // Use vector b/c it's thread safe.

        final DatabaseListener listener = new DatabaseListener() {
            @Override
            public void onStart(String key) {
                Log.d(TAG, "Quarry DB for " + key);
            }

            @Override
            public void onSuccess(String key, String string) {
                this.attributes.put(key, string);  // Listener can store internal state.
                exampleData.add(string);
                if (exampleData.size() >= 2) {
                    Log.e(TAG, exampleData.toString());
                    Log.e(TAG, this.attributes.toString());
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        };

        backend.databaseGet("test_string_0", listener);
        backend.databaseGet("test_string_1", listener);
        backend.databasePut("test_val", "foo-bar-baz");
        backend.databaseGet("test_val", listener);

        // Log msgs will appear in logcat & debug console
        Log.e(TAG, "This will execute before DB is returned");
    }
}
