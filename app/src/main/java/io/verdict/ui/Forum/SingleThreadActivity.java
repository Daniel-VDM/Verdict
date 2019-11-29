package io.verdict.ui.Forum;

import android.annotation.SuppressLint;
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

import io.verdict.R;
import io.verdict.ui.SearchScreen.SearchScreen;

@SuppressWarnings("ConstantConditions")
public class SingleThreadActivity extends AppCompatActivity {
    private ListView answersList;
    private Question question;
    private SingleThreadAnswersAdapter tempAnswersAdapter;
    private TextView question_name;
    private TextView question_likes;
    private TextView question_date;
    private TextView question_field;
    private TextView question_detail;
    private Button submitResponse;
    private String lawField;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_single_thread);

        final Button single_threadTabSearchButton = findViewById(R.id.post_response_tab_search_search);
        final Button single_threadTabForumButton = findViewById(R.id.post_response_tab_forum_search);
        final ImageView single_threadTabSearchHighlight = findViewById(R.id.post_response_tab_search_highlight_search);
        final ImageView single_threadTabForumHighlight = findViewById(R.id.post_response_tab_forum_highlight_search);

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
        question_name = findViewById(R.id.question_name);
        question_likes = findViewById(R.id.singe_thread_likes);
        question_date = findViewById(R.id.singe_thread_posted);
        question_field = findViewById(R.id.single_thread_lawfield);
        question_detail = findViewById(R.id.post_responses_detail);
        submitResponse = findViewById(R.id.single_thread_submit_button);
        processIntent();

        question_name.setText(question.getquestion());
        question_likes.setText("Like (+" + question.getqRating() + ")");
        question_date.setText(question.getdate());
        question_field.setText(lawField);
        final String qDetails = question.getQuestionDetails();
        if (qDetails != null && qDetails.length() > 0) {
            question_detail.setText("Details: " + qDetails);
        } else {
            question_detail.setHeight(0);
        }

        tempAnswersAdapter = new SingleThreadAnswersAdapter(this, question.getanswers());
        answersList.setAdapter(tempAnswersAdapter);
        answersList.setFocusable(false);
        answersList.setClickable(false);

        submitResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleThreadActivity.this, PostResponseActivity.class);
                intent.putExtra("QUESTION", question.toString());
                intent.putExtra("LAW_FIELD", lawField);
                startActivity(intent);
            }
        });
    }

    private void processIntent() {
        try {
            Intent intent = getIntent();
            lawField = intent.getStringExtra("LAW_FIELD");
            JSONObject jsonQuestion = new JSONObject(intent.getStringExtra("QUESTION"));
            question = new Question(jsonQuestion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
