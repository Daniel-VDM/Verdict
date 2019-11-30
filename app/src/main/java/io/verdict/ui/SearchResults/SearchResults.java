package io.verdict.ui.SearchResults;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.SearchListener;
import io.verdict.backend.SearchQuarry;
import io.verdict.ui.Forum.ThreadsActivity;
import io.verdict.ui.Forum.TopicsActivity;
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
        final String legalField = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LEGALFIELD);
        final String location = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LOCATION);
        final String lawyer = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LAWYER);

        final ProgressBar resultsLoadingSpinner = findViewById(R.id.results_loading_spinner);
        final TextView resultsLoadingText = findViewById(R.id.results_loading_text);

        final TextView resultsInfoText = findViewById(R.id.results_info);
        final ImageView resultsInfoDivider = findViewById(R.id.results_divider);
        final CardView resultsForumTopicCard = findViewById(R.id.results_forum_topic_card);
        final TextView resultsForumTopicText = findViewById(R.id.results_forum_topic_text);

        final RecyclerView resultsRecycler = findViewById(R.id.results_recycler);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resultsRecycler.setLayoutManager(layoutManager);

        final SearchQuarry searchQuarry = new SearchQuarry(location, legalField, lawyer, new SearchListener() {
            @Override
            public void onFinish(final JSONArray jsonArray) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultsLoadingSpinner.setVisibility(View.INVISIBLE);
                        resultsLoadingText.setVisibility(View.INVISIBLE);

                        resultsInfoText.setVisibility(View.VISIBLE);
                        resultsInfoDivider.setVisibility(View.VISIBLE);
                        String searchArea = location;
                        if (jsonArray.length() > 0) {
                            try {
                                JSONObject firstResultLocation = jsonArray.getJSONObject(0).getJSONObject("location");
                                String city = firstResultLocation.getString("city");
                                String state = firstResultLocation.getString("state");
                                String zip = firstResultLocation.getString("zip_code");
                                searchArea = String.format("%s %s, %s", city, state, zip);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String resultsInfoString = String.format(getString(R.string.results_info_text), jsonArray.length(), legalField, searchArea);
                        resultsInfoText.setText(resultsInfoString);

                        resultsForumTopicCard.setVisibility(View.VISIBLE);
                        resultsForumTopicCard.setClickable(true);
                        resultsForumTopicCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(SearchResults.this, ThreadsActivity.class);
                                intent.putExtra("LAW_FIELD", legalField);
                                startActivity(intent);
                            }
                        });
                        String resultsForumTopicString = String.format(getString(R.string.results_forum_topic_card_text), legalField);
                        Spannable resultsForumTopicSpannable = new SpannableString(resultsForumTopicString);
                        resultsForumTopicSpannable.setSpan(new UnderlineSpan(), resultsForumTopicString.length() - 11, resultsForumTopicString.length(), 0);
                        resultsForumTopicText.setText(resultsForumTopicSpannable);

                        ResultsRecyclerAdapter resultsAdapter = new ResultsRecyclerAdapter(jsonArray, legalField, SearchResults.this);
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

        tabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResults.this, TopicsActivity.class);
                startActivity(intent);
            }
        });
    }

}
