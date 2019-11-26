package io.verdict.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import io.verdict.R;

public class SingleThreadActivity extends AppCompatActivity {

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
        
    }
}
