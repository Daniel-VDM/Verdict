package io.verdict.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;
import io.verdict.R;


public class ThreadsActivity extends AppCompatActivity {

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
                threadsTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
                threadsTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
                threadsTabSearchHighlight.setImageAlpha(255);
                threadsTabForumHighlight.setImageAlpha(0);
            }
        });

        threadsTabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threadsTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
                threadsTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
                threadsTabSearchHighlight.setImageAlpha(0);
                threadsTabForumHighlight.setImageAlpha(255);
            }
        });

        //add Sort By logic
        final Spinner threads_spinner = findViewById(R.id.threads_topic_spinner);
        ArrayAdapter<CharSequence> threads_topics_adapter = ArrayAdapter.createFromResource(this, R.array.law_topics, android.R.layout.simple_spinner_item);
        threads_topics_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        threads_spinner.setAdapter(threads_topics_adapter);
        //change to set current topic using intent. currently set to Personal Injury
        threads_spinner.setSelection(14);

        RecyclerView recList = (RecyclerView) findViewById(R.id.threadCardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recList.setLayoutManager(llm);

        ThreadsAdapter threadadapter = new ThreadsAdapter(createDummyQuestions(1));
        recList.setAdapter(threadadapter);


    }

    private List<Question> createDummyQuestions(int size) {

        List<Question> result = new ArrayList<Question>();
        String qTopic = "Personal Injury";
        String date = "01-11-19";
        String qAuthor = "Anonymous";
        String question = "Should I take my case to court?";
        Question q = new Question(qTopic, date, qAuthor, question);
        result.add(q);
        return result;
    }
}
