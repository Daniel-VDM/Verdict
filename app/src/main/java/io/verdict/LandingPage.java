package io.verdict;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;

import java.util.Vector;

import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;

public class LandingPage extends AppCompatActivity {

    private static final String TAG = "LandingPage";
    private final Backend backend = new Backend();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseExample();
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

        backend.getDatabase("test_string_0", listener);
        backend.getDatabase("test_string_1", listener);
        backend.putDatabase("test_val", "foo-bar-baz");
        backend.getDatabase("test_val", listener);

        // Log msgs will appear in logcat & debug console
        Log.e(TAG, "This will execute before DB is returned");
    }
}
