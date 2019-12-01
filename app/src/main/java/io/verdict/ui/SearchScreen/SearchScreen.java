package io.verdict.ui.SearchScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.ui.Forum.TopicsActivity;
import io.verdict.ui.SearchResults.SearchResults;

public class SearchScreen extends AppCompatActivity {

    public static final String SEARCH_TEXT_LEGALFIELD = "io.verdict.searchscreen.legalfield";
    public static final String SEARCH_TEXT_LOCATION = "io.verdict.searchscreen.location";
    public static final String SEARCH_TEXT_LAWYER = "io.verdict.searchscreen.lawyer";
    public static String[] LEGAL_FIELDS;

    // TODO: Verify inputs and catch network errors...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);
        LEGAL_FIELDS = this.getResources().getStringArray(R.array.law_topics);

        new Backend();  // Init some static vars from db.
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
}
