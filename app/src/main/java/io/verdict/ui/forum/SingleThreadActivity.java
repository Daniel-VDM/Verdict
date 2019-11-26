package io.verdict.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.verdict.R;

public class SingleThreadActivity extends AppCompatActivity {
    private ListView answersList;
    private TemporaryAnswersAdapter tempAnswersAdapter;

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
                single_threadTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
                single_threadTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
                single_threadTabSearchHighlight.setImageAlpha(255);
                single_threadTabForumHighlight.setImageAlpha(0);
            }
        });

        single_threadTabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                single_threadTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
                single_threadTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
                single_threadTabSearchHighlight.setImageAlpha(0);
                single_threadTabForumHighlight.setImageAlpha(255);
            }
        });


        final ListView answersList = findViewById(R.id.thread_answer_list);
        tempAnswersAdapter = new TemporaryAnswersAdapter(this,createDummyAnswers(6));
        answersList.setAdapter(tempAnswersAdapter);


        final TextView question_name = findViewById(R.id.question_name);
        question_name.setText("+30      Will my case go to trial?");

        
    }

    private ArrayList<Answer> createDummyAnswers(int size) {
        ArrayList<Answer> result = new ArrayList<>();
        List<String> dummy_answers = new ArrayList<String>();
        dummy_answers.add("No you should not.");
        dummy_answers.add("This depends on law 1001.");
        dummy_answers.add("I think you can try to settle out of court.");
        dummy_answers.add("You are entitled to a lot of money.");
        dummy_answers.add("No.");
        dummy_answers.add("Absolutely");

        for (int i = 0; i < size; i++) {
            String aQuestion = "Should I take my case to court?";
            String aDate = "01-11-19";
            String aAuthor = "Anonymous";
            String answer_text = dummy_answers.get(i);
            Answer a = new Answer(aQuestion, aDate, aAuthor, answer_text);
            result.add(a);

        }
        return result;
    }



}
