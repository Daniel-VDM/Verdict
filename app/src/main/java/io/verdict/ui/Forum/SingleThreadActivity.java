package io.verdict.ui.Forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.verdict.R;
import io.verdict.ui.SearchScreen.SearchScreen;

@SuppressWarnings("ConstantConditions")
public class SingleThreadActivity extends AppCompatActivity {
    private ListView answersList;
    private Question question;
    private TemporaryAnswersAdapter tempAnswersAdapter;
    private String lawField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_thread);

        final Button single_threadTabSearchButton = findViewById(R.id.single_thread_tab_search_search);
        final Button single_threadTabForumButton = findViewById(R.id.single_thread_tab_forum_search);
        final ImageView single_threadTabSearchHighlight = findViewById(R.id.single_thread_tab_search_highlight_search);
        final ImageView single_threadTabForumHighlight = findViewById(R.id.single_thread_tab_forum_highlight_search);

        single_threadTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        single_threadTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        single_threadTabForumHighlight.setImageAlpha(255);
        single_threadTabSearchHighlight.setImageAlpha(0);

        single_threadTabSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleThreadActivity.this, SearchScreen.class);
                startActivity(intent);
            }
        });

        answersList = findViewById(R.id.thread_answer_list);
        processIntent();

        tempAnswersAdapter = new TemporaryAnswersAdapter(this, question.getanswers());
        answersList.setAdapter(tempAnswersAdapter);


        final TextView question_name = findViewById(R.id.question_name);
        question_name.setText("+30      Will my case go to trial?");

    }

    private void processIntent() {
        try {
            Intent intent = getIntent();
            intent.getStringExtra("LAW_FIELD");
            JSONObject jsonQuestion = new JSONObject(intent.getStringExtra("QUESTION"));
            question = new Question(jsonQuestion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
