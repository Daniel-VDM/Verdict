package io.verdict.ui.Forum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;
import io.verdict.ui.SearchScreen.SearchScreen;


public class ThreadsActivity extends AppCompatActivity {
    private static final String TAG = "ThreadsActivity";

    private ListView threadsList;
    private String lawField;
    private String initFilterString;
    private ThreadListAdapter listAdapter;
    private FloatingActionButton submitThread;
    private SearchView search;
    private TextView highestRatingFilter;
    private TextView mostRecentFilter;
    private TextView noAnswersText;
    private ArrayList<Question> questions;
    private boolean isSortByDate;
    private boolean isSortByRating;
    private Backend backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_threads);

        final Button threadsTabSearchButton = findViewById(R.id.threads_tab_search_search);
        final Button threadsTabForumButton = findViewById(R.id.threads_tab_forum_search);
        final ImageView threadsTabSearchHighlight = findViewById(R.id.threads_tab_search_highlight_search);
        final ImageView threadsTabForumHighlight = findViewById(R.id.threads_tab_forum_highlight_search);

        threadsTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        threadsTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        threadsTabForumHighlight.setImageAlpha(255);
        threadsTabSearchHighlight.setImageAlpha(0);

        threadsTabSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThreadsActivity.this, SearchScreen.class);
                startActivity(intent);
            }
        });
        isSortByDate = false;
        isSortByRating = false;
        questions = new ArrayList<>();
        backend = new Backend();

        processIntent();

        ((TextView) findViewById(R.id.forum_section_header)).setText(lawField);
        submitThread = findViewById(R.id.forum_section_writeNew);
        search = findViewById(R.id.threads_filter_content);
        highestRatingFilter = findViewById(R.id.highest_rating);
        highestRatingFilter.setClickable(true);
        mostRecentFilter = findViewById(R.id.most_recent);
        mostRecentFilter.setClickable(true);
        noAnswersText = findViewById(R.id.threads_no_question);
        noAnswersText.setAlpha(0.0f);

        ArrayAdapter<CharSequence> threads_topics_adapter = ArrayAdapter
                .createFromResource(this, R.array.law_topics, android.R.layout.simple_spinner_item);
        threads_topics_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setupAdapter();
        setupListeners();
        updateQuestions();
    }

    @SuppressLint("SetTextI18n")
    private void updateQuestions() {
        backend.getForumQuestions(lawField, new DatabaseListener() {
            @Override
            public void onStart(String key) {
                noAnswersText.setAlpha(1.0f);
                noAnswersText.setText("Fetching forum data...");
                Log.d(TAG, "Fetching forum data for thread");
            }

            @Override
            public void onSuccess(String key, String value) {
                try {
                    questions = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(value);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        questions.add(new Question(jsonObject));
                    }
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            noAnswersText.setText("No questions have been asked :(");
                            setupAdapter();
                        }
                    });
                } catch (JSONException e) {
                    noAnswersText.setAlpha(1.0f);
                    noAnswersText.setText("Error loading forum data...");
                    Log.e(TAG, e.toString());
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                noAnswersText.setAlpha(1.0f);
                noAnswersText.setText("Error loading forum data...");
                Log.e(TAG, databaseError.toString());
            }
        });
    }

    private void setupAdapter() {
        threadsList = findViewById(R.id.list_of_threads);
        listAdapter = new ThreadListAdapter(this, questions);
        threadsList.setAdapter(listAdapter);
        if (listAdapter.isCurrentEmpty()) {
            noAnswersText.setAlpha(1.0f);
        } else {
            noAnswersText.setAlpha(0.0f);
        }
        if (initFilterString != null) {
            search.setQuery(initFilterString, false);
            listAdapter.filter(initFilterString);
            if (listAdapter.isCurrentEmpty()) {
                noAnswersText.setAlpha(1.0f);
            }
        }
    }

    private void setupListeners() {
        submitThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThreadsActivity.this, PostThreadActivity.class);
                startActivity(intent);
            }
        });
        highestRatingFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noAnswersText.setAlpha(0.0f);
                isSortByRating = true;
                isSortByDate = false;
                listAdapter.sortByRating();
                if (listAdapter.isCurrentEmpty()) {
                    noAnswersText.setAlpha(1.0f);
                }
            }
        });
        mostRecentFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noAnswersText.setAlpha(0.0f);
                isSortByRating = false;
                isSortByDate = true;
                listAdapter.sortByDate();
                if (listAdapter.isCurrentEmpty()) {
                    noAnswersText.setAlpha(1.0f);
                }
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                initFilterString = null;
                noAnswersText.setAlpha(0.0f);
                listAdapter.filter(s);
                if (isSortByDate) {
                    listAdapter.sortByDate();
                } else if (isSortByRating) {
                    listAdapter.sortByRating();
                }
                if (listAdapter.isCurrentEmpty()) {
                    noAnswersText.setAlpha(1.0f);
                }
                return true;
            }
        });
        threadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ThreadsActivity.this, SingleThreadActivity.class);
                intent.putExtra("LAW_FIELD", lawField);
                Question question = questions.get(i);
                intent.putExtra("QUESTION", question.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listAdapter.clear();
        updateQuestions();
    }

    private void processIntent() {
        Intent intent = getIntent();
        lawField = intent.getStringExtra("LAW_FIELD");
        if (intent.hasExtra("FILTER_KEY")) {
            initFilterString = intent.getStringExtra("FILTER_KEY");
        }
    }
}
