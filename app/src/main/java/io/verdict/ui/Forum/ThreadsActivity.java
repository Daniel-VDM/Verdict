package io.verdict.ui.Forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.verdict.R;
import io.verdict.ui.SearchScreen.SearchScreen;


public class ThreadsActivity extends AppCompatActivity {
    private ListView threadsList;
    private ThreadListAdapter tempAdapter;

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

        //add Sort By logic
        ArrayAdapter<CharSequence> threads_topics_adapter = ArrayAdapter.createFromResource(this, R.array.law_topics, android.R.layout.simple_spinner_item);
        threads_topics_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*ThreadsAdapter threadadapter = new ThreadsAdapter(createDummyQuestions(6));
        /*final RecyclerView recList = (RecyclerView) findViewById(R.id.threadCardList);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recList.setLayoutManager(llm);

        recList.setAdapter(threadadapter);*/

        final ListView threadsList = findViewById(R.id.list_of_threads);
        tempAdapter = new ThreadListAdapter(this, createDummyQuestions(6));
        threadsList.setAdapter(tempAdapter);
    }


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
            String qTopic = "Personal Injury";
            String date = "01-11-19";
            String qAuthor = "Anonymous";
            String question = dummy_questions.get(i);
            Question q = new Question(qTopic, date, qAuthor, question);
            result.add(q);

        }
        return result;
    }
}
