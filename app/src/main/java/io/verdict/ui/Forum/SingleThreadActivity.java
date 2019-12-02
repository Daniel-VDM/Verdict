package io.verdict.ui.Forum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;

import org.json.JSONException;
import org.json.JSONObject;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;
import io.verdict.ui.SearchScreen.SearchScreen;

@SuppressWarnings("ConstantConditions")
public class SingleThreadActivity extends AppCompatActivity {
    private final static String TAG = "SingleThreadActivity";

    private ListView answersList;
    private Question question;
    private SingleThreadAnswersAdapter answerAdapter;
    private TextView question_name;
    private TextView question_likes;
    private boolean clickedLiked;
    private TextView question_date;
    private TextView question_field;
    private TextView question_detail;
    private TextView noQuestions;
    private Button submitResponse;
    private String lawField;
    private Backend backend;

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

        backend = new Backend();
        answersList = findViewById(R.id.thread_answer_list);
        question_name = findViewById(R.id.question_name);
        question_likes = findViewById(R.id.singe_thread_likes);
        clickedLiked = false;
        question_date = findViewById(R.id.singe_thread_posted);
        question_field = findViewById(R.id.single_thread_lawfield);
        question_detail = findViewById(R.id.post_responses_detail);
        submitResponse = findViewById(R.id.single_thread_submit_button);
        noQuestions = findViewById(R.id.post_no_answer_text);
        processIntent();
        setupScreen();
    }

    @SuppressLint("SetTextI18n")
    private void setupScreen() {
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

        answerAdapter = new SingleThreadAnswersAdapter(this, question.getanswers(), question);
        answersList.setAdapter(answerAdapter);
        answersList.setFocusable(false);
        answersList.setClickable(false);

        question_likes.setClickable(true);
        question_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clickedLiked) {  // Naive check, just for demo purposes.
                    clickedLiked = true;
                    question.setqRating(question.getqRating() + 1);
                    question_likes.setText("Like (+" + question.getqRating() + ")");
                    Toast toast = Toast.makeText(view.getContext(),
                            "You liked this question!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 10);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(view.getContext(),
                            "You have already liked this question",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 10);
                    toast.show();
                }
            }
        });

        submitResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleThreadActivity.this, PostResponseActivity.class);
                intent.putExtra("QUESTION", question.toString());
                intent.putExtra("LAW_FIELD", lawField);
                startActivity(intent);
            }
        });

        if (!answerAdapter.isEmpty()) {
            noQuestions.setAlpha(0.0f);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(500);  // Needed to wait for Firebase to update.
                } catch (InterruptedException e) {
                    Log.e(TAG, e.toString());
                }
                backend.getForumQuestion(lawField, question.getUUID(), new DatabaseListener() {
                    @Override
                    public void onStart(String key) {
                        Log.d(TAG, "Fetching question: " + key);
                    }

                    @Override
                    public void onSuccess(String key, String value) {
                        try {
                            JSONObject jsonObject = new JSONObject(value);
                            question = new Question(jsonObject);
                            setupScreen();
                            answerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e(TAG, e.toString());
                        }
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.toString());
                    }
                });
            }
        }.start();
    }

    private void processIntent() {
        Intent intent = getIntent();
        lawField = intent.getStringExtra("LAW_FIELD");
        try {
            JSONObject jsonQuestion = new JSONObject(intent.getStringExtra("QUESTION"));
            question = new Question(jsonQuestion);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
