package io.verdict.ui.Forum;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.verdict.R;
import io.verdict.ui.SearchScreen.SearchScreen;


public class ThreadsActivity extends AppCompatActivity {
    private ListView threadsList;
    private String lawField;
    private ThreadListAdapter listAdapter;
    private FloatingActionButton submitThread;
    private SearchView search;
    private TextView highestRatingFilter;
    private TextView mostRecentFilter;
    private TextView noAnswersText;
    private ArrayList<Question> questions;
    private boolean isSortByDate;
    private boolean isSortByRating;

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
    }

    private void setupAdapter() {
        questions = createDummyQuestions(6);  // TODO: load fresh question from the backend.
        threadsList = findViewById(R.id.list_of_threads);
        listAdapter = new ThreadListAdapter(this, questions);
        threadsList.setAdapter(listAdapter);
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
        if (listAdapter.isCurrentEmpty()) {
            noAnswersText.setAlpha(1.0f);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAdapter();
        setupListeners();
    }

    // TODO hook this up to the backend and use loading spinner between fetch...
    private ArrayList<Question> createDummyQuestions(int size) {
        ArrayList<Question> result = new ArrayList<Question>();
        List<String> dummy_questions = new ArrayList<String>();
        dummy_questions.add("Should I take my case to court?");
        dummy_questions.add("Who is the best lawyer in my area?");
        dummy_questions.add("Who should I ask for help?");
        dummy_questions.add("What am I entitled to?");
        dummy_questions.add("What do I do now?");
        dummy_questions.add("How do I file a case?");

        for (int i = 0; i < size; i++) {
            String qTopic = lawField;
            String date = "01-11-" + String.format("%02d", new Random().nextInt(31));
            String qAuthor = "Anonymous";
            String question = dummy_questions.get(i);
            String questionDetails = "This is a test";
            Question q = new Question(qTopic, date, qAuthor, question, questionDetails, new Random().nextInt(20));
            result.add(q);

        }
        return result;
    }

    private void processIntent() {
        Intent intent = getIntent();
        lawField = intent.getStringExtra("LAW_FIELD");
    }
}
