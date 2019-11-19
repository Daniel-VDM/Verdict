package io.verdict;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;

import java.util.Vector;

import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;
import io.verdict.backend.SearchListener;
import io.verdict.backend.SearchQuarry;

public class LandingPage extends AppCompatActivity {

    private static final String TAG = "LandingPage";
    private final Backend backend = new Backend();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseExample();
        searchLawyerExample();
    }

    // All initial data collection needs to be handled asynchronously
    private void searchLawyerExample() {
        SearchQuarry searchQuary = new SearchQuarry(37.422, -122.084,
                "Defence", "Good", new SearchListener() {
            @Override
            public void onFinish(JSONArray jsonArray) {
                Log.e(TAG, jsonArray.toString());
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, message);
            }
        });
        backend.searchLawyers(LandingPage.this, searchQuary);
    }

    private void databaseExample() {
        final Vector<Object> exampleData = new Vector<>();  // Use vector b/c it's thread safe.

        final DatabaseListener listener = new DatabaseListener() {
            @Override
            public void onStart(String key) {
                Log.d(TAG, "Quarry DB for " + key);
            }

            @Override
            public void onSuccess(String key, Object object) {
                this.attributes.put(key, object);  // Listener can store internal state.
                exampleData.add(object);
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
