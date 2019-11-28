package io.verdict.ui.Forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.verdict.R;
import io.verdict.ui.SearchScreen.SearchScreen;


public class ThreadsActivity extends AppCompatActivity {
    private ListView threadsList;
    private String lawField;
    private ThreadListAdapter listAdapter;
    private FloatingActionButton submitThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

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

        processIntent();

        ((TextView)findViewById(R.id.forum_section_header)).setText(lawField);
        submitThread = findViewById(R.id.forum_section_writeNew);

        // TODO add Sort By logic  (possibly rework button placement).
        ArrayAdapter<CharSequence> threads_topics_adapter = ArrayAdapter.createFromResource(this, R.array.law_topics, android.R.layout.simple_spinner_item);
        threads_topics_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayList<Question> questions =  createDummyQuestions(6);

        threadsList = findViewById(R.id.list_of_threads);
        listAdapter = new ThreadListAdapter(this, questions);
        threadsList.setAdapter(listAdapter);

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
        submitThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThreadsActivity.this, PostThreadActivity.class);
                startActivity(intent);
            }
        });
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
            String date = "01-11-19";
            String qAuthor = "Anonymous";
            String question = dummy_questions.get(i);
            Question q = new Question(qTopic, date, qAuthor, question);
            result.add(q);

        }
        return result;
    }

    private void processIntent(){
        Intent intent = getIntent();
        lawField = intent.getStringExtra("LAW_FIELD");
    }
}
