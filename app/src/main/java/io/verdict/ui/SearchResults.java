package io.verdict.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.SearchListener;
import io.verdict.backend.SearchQuarry;

public class SearchResults extends AppCompatActivity {

    private static final String TAG = "SearchResults";
    private final Backend backend = new Backend();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        setupTabs();

        Intent intent = getIntent();
        String legalField = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LEGALFIELD);
        String location = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LOCATION);
        String lawyer = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LAWYER);

        final TextView textView = findViewById(R.id.textView);
        textView.setText(legalField);
        final TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(location);
        final TextView textView3 = findViewById(R.id.textView3);
        textView3.setText(lawyer);
        final TextView textView4 = findViewById(R.id.textView4);

        final SearchQuarry searchQuarry = new SearchQuarry(location, legalField, lawyer, new SearchListener() {
            @Override
            public void onFinish(JSONArray jsonArray) {
                final String jsonArrayString = jsonArray.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView4.setText(jsonArrayString);
                    }
                });
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, message);
            }
        });

        new Thread() {
            @Override
            public void run() {
                backend.searchLawyers(SearchResults.this, searchQuarry);
            }
        }.start();

    }

    private void setupTabs() {
        final Button tabSearchButton = findViewById(R.id.tab_search_results);
        final Button tabForumButton = findViewById(R.id.tab_forum_results);
        final ImageView tabSearchHighlight = findViewById(R.id.tab_search_highlight_results);
        final ImageView tabForumHighlight = findViewById(R.id.tab_forum_highlight_results);

        tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        tabSearchHighlight.setImageAlpha(255);
        tabForumHighlight.setImageAlpha(0);

        tabSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
//                tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
//                tabSearchHighlight.setImageAlpha(255);
//                tabForumHighlight.setImageAlpha(0);
            }
        });

        tabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
//                tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
//                tabSearchHighlight.setImageAlpha(0);
//                tabForumHighlight.setImageAlpha(255);
            }
        });
    }

}
