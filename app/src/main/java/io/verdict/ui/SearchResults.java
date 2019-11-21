package io.verdict.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.verdict.R;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        setupTabs();

        Intent intent = getIntent();
        String legalField = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LEGALFIELD);
        String location = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LOCATION);
        String lawyer = intent.getStringExtra(SearchScreen.SEARCH_TEXT_LAWYER);

        TextView textView = findViewById(R.id.textView);
        textView.setText(legalField);
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(location);
        TextView textView3 = findViewById(R.id.textView3);
        textView3.setText(lawyer);
    }

    private void setupTabs() {
        final Button tabSearchButton = findViewById(R.id.tab_search_results);
        final Button tabForumButton = findViewById(R.id.tab_forum_results);
        final ImageView tabSearchHighlight = findViewById(R.id.tab_search_highlight_results);
        final ImageView tabForumHighlight = findViewById(R.id.tab_forum_highlight_results);

        tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        tabSearchHighlight.setImageAlpha(255);
        tabForumHighlight.setImageAlpha(0);

        tabSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
//                tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
//                tabSearchHighlight.setImageAlpha(255);
//                tabForumHighlight.setImageAlpha(0);
            }
        });

        tabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
//                tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
//                tabSearchHighlight.setImageAlpha(0);
//                tabForumHighlight.setImageAlpha(255);
            }
        });
    }

}
