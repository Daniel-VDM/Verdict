package io.verdict.ui.SearchResults;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.SearchListener;
import io.verdict.backend.SearchQuarry;
import io.verdict.ui.SearchScreen.SearchScreen;

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

        final ProgressBar resultsLoadingSpinner = findViewById(R.id.results_loading_spinner);
        final TextView resultsLoadingText = findViewById(R.id.results_loading_text);
        final RecyclerView resultsRecycler = findViewById(R.id.results_recycler);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resultsRecycler.setLayoutManager(layoutManager);

        final SearchQuarry searchQuarry = new SearchQuarry(location, legalField, lawyer, new SearchListener() {
            @Override
            public void onFinish(final JSONArray jsonArray) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultsLoadingSpinner.setAlpha(0);
                        resultsLoadingText.setAlpha(0);
                        ResultsRecyclerAdapter resultsAdapter = new ResultsRecyclerAdapter(jsonArray);
                        resultsRecycler.setAdapter(resultsAdapter);
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
