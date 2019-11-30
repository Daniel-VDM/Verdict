package io.verdict.ui.Forum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.verdict.R;
import io.verdict.ui.SearchScreen.SearchScreen;

public class PostResponseActivity extends AppCompatActivity {

    Button submitButton;
    CheckBox isAnonymous;
    private String lawField;
    private Question question;
    private TextView question_q;
    private TextView question_likes;
    private TextView question_date;
    private TextView question_field;
    private TextView question_detail;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_post_response);

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
                Intent intent = new Intent(PostResponseActivity.this, SearchScreen.class);
                startActivity(intent);
            }
        });

        isAnonymous = findViewById(R.id.postResponse_anonymous);
        submitButton = findViewById(R.id.postResponse_submitButton);
        question_q = findViewById(R.id.response_question_name);
        question_likes = findViewById(R.id.response_singe_thread_likes);
        question_date = findViewById(R.id.response_singe_thread_posted);
        question_field = findViewById(R.id.response_single_thread_lawfield);
        question_detail = findViewById(R.id.response_post_responses_detail);

        processIntent();

        question_q.setText(question.getquestion());
        question_likes.setText(question.getqRating() + " likes");
        question_date.setText(question.getdate());
        question_field.setText(lawField);
        final String qDetails = question.getQuestionDetails();
        if (qDetails != null && qDetails.length() > 0) {
            question_detail.setText("Details: " + qDetails);
        } else {
            question_detail.setHeight(0);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(),
                        "Your answer has been submitted!",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 10);
                toast.show();
                onBackPressed();
            }
        });
    }

    private void processIntent() {
        try {
            Intent intent = getIntent();
            lawField = intent.getStringExtra("LAW_FIELD");
            JSONObject jsonObject = new JSONObject(Objects.requireNonNull(
                    intent.getStringExtra("QUESTION")));
            question = new Question(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
