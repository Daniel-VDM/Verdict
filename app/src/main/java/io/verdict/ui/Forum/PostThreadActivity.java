package io.verdict.ui.Forum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.ui.SearchScreen.SearchScreen;

public class PostThreadActivity extends AppCompatActivity {

    private static final String TAG = "PostThreadActivity";

    Spinner selectCategory;
    EditText threadTitle;
    EditText threadDescription;
    Button submitButton;
    Backend backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_post_thread);

        final Button single_threadTabSearchButton = findViewById(R.id.post_thread_tab_search_search);
        final Button single_threadTabForumButton = findViewById(R.id.post_thread_tab_forum_search);
        final ImageView single_threadTabSearchHighlight = findViewById(R.id.post_thread_tab_search_highlight_search);
        final ImageView single_threadTabForumHighlight = findViewById(R.id.post_thread_tab_forum_highlight_search);

        single_threadTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        single_threadTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        single_threadTabForumHighlight.setImageAlpha(255);
        single_threadTabSearchHighlight.setImageAlpha(0);

        single_threadTabSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostThreadActivity.this, SearchScreen.class);
                startActivity(intent);
            }
        });

        selectCategory = findViewById(R.id.post_thead_topic_list);
        threadTitle = findViewById(R.id.postThread_titleContent);
        threadDescription = findViewById(R.id.postThread_descriptionContent);
        submitButton = findViewById(R.id.postThread_submitButton);
        backend = new Backend();

        final ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.law_topics)));
        arrayList.add(0, "Select Legal Field");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList);
        selectCategory.setAdapter(arrayAdapter);
        selectCategory.setSelection(0);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = threadTitle.getText().toString();
                if (selectCategory.getSelectedItem().equals("Select Legal Field")
                        || title.length() < 1) {
                    Toast toast = Toast.makeText(view.getContext(),
                            "You have missing fields!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 10);
                    toast.show();
                } else {
                    String detail = threadDescription.getText().toString();
                    Date date = new Date();
                    Question newQuestion = new Question(selectCategory.getSelectedItem().toString(),
                            Question.dateFormat.format(date), title, detail,
                            0, new ArrayList<Answer>());
                    backend.putForumQuestion(newQuestion);
                    Toast toast = Toast.makeText(view.getContext(),
                            "Your question has been submitted!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 10);
                    toast.show();
                    onBackPressed();
                }
            }
        });
    }
}
