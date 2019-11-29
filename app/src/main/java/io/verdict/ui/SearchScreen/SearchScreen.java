package io.verdict.ui.SearchScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;
import io.verdict.backend.SearchListener;
import io.verdict.backend.SearchQuarry;
import io.verdict.ui.DetailScreen.DetailScreen;
import io.verdict.ui.Forum.TopicsActivity;
import io.verdict.ui.SearchResults.SearchResults;

public class SearchScreen extends AppCompatActivity {

    private static final String TAG = "SearchScreen";
    private final Backend backend = new Backend();

    public static final String SEARCH_TEXT_LEGALFIELD = "io.verdict.searchscreen.legalfield";
    public static final String SEARCH_TEXT_LOCATION = "io.verdict.searchscreen.location";
    public static final String SEARCH_TEXT_LAWYER = "io.verdict.searchscreen.lawyer";

    public static String[] LEGAL_FIELDS = {
            "Admiralty",
            "Bankruptcy",
            "Business",
            "Civil Rights",
            "Criminal",
            "Entertainment",
            "Environmental",
            "Family",
            "Health",
            "Immigration",
            "Intellectual Property",
            "International",
            "Labor",
            "Military",
            "Personal Injury",
            "Real Estate",
            "Tax"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        setupTabs();
        setupLegalFieldSpinner();

    }

    private void setupTabs() {
        final Button tabSearchButton = findViewById(R.id.tab_search_search);
        final Button tabForumButton = findViewById(R.id.tab_forum_search);
        final ImageView tabSearchHighlight = findViewById(R.id.tab_search_highlight_search);
        final ImageView tabForumHighlight = findViewById(R.id.tab_forum_highlight_search);

        tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        tabSearchHighlight.setImageAlpha(255);
        tabForumHighlight.setImageAlpha(0);

        tabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchScreen.this, TopicsActivity.class);
                startActivity(intent);
            }
        });
    }

    private class HintAdapter extends ArrayAdapter<String> {
        public HintAdapter(Context theContext, int theLayoutResId, List<String> objects) {
            super(theContext, theLayoutResId, objects);
        }
        @Override
        public int getCount() {
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }

    private void setupLegalFieldSpinner() {
        Spinner legalFieldSpinner = findViewById(R.id.search_by_legal);
        ArrayList<String> legalFieldsList = new ArrayList<>();
        Collections.addAll(legalFieldsList, LEGAL_FIELDS);
        legalFieldsList.add(getResources().getString(R.string.search_hint_legal_area));

        HintAdapter adapter = new HintAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, legalFieldsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        legalFieldSpinner.setAdapter(adapter);
        legalFieldSpinner.setSelection(adapter.getCount());
        legalFieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == LEGAL_FIELDS.length) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(android.R.color.tab_indicator_text));
                }
                ((TextView) parent.getChildAt(0)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void searchForResults(View view) {
        Intent intent = new Intent(this, SearchResults.class);

        Spinner searchByLegalSpinner = findViewById(R.id.search_by_legal);
        String legalField = searchByLegalSpinner.getSelectedItem().toString();
        intent.putExtra(SEARCH_TEXT_LEGALFIELD, legalField);

        EditText searchByLocationText = findViewById(R.id.search_by_location);
        String location = searchByLocationText.getText().toString();
        intent.putExtra(SEARCH_TEXT_LOCATION, location);

        EditText searchByLawyerText = findViewById(R.id.search_by_lawyer);
        String lawyer = searchByLawyerText.getText().toString();
        intent.putExtra(SEARCH_TEXT_LAWYER, lawyer);
        
        startActivity(intent);
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
                    JSONObject lawyer = jsonArray.getJSONObject(new Random().nextInt(jsonArray.length()));
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
