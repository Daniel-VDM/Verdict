package io.verdict.ui.Forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.verdict.R;
import io.verdict.ui.SearchScreen.SearchScreen;

public class PostResponseActivity extends AppCompatActivity {

    Button submitButton;
    CheckBox isAnonymous;
    private Question question;
    private TextView question_name;
    private TextView question_likes;
    private TextView question_date;
    private TextView question_field;
    private TextView question_detail;

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
        question_name = findViewById(R.id.response_question_name);
        question_likes = findViewById(R.id.response_singe_thread_likes);
        question_date = findViewById(R.id.response_singe_thread_posted);
        question_field = findViewById(R.id.response_single_thread_lawfield);
        question_detail = findViewById(R.id.response_post_responses_detail);

        processIntent();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: connect this to the backend and hit the back button
                //       maybe even do a reload
            }
        });
    }

    private void processIntent() {
        // TODO: get questions from intent and set proper info
    }

}
