package io.verdict.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.verdict.R;
import io.verdict.backend.Backend;

public class DetailScreen extends AppCompatActivity {

    private Backend backend;
    private JSONObject lawyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        Button tabSearchButton = findViewById(R.id.tab_search_detail);
        Button tabForumButton = findViewById(R.id.tab_forum_detail);
        ImageView tabSearchHighlight = findViewById(R.id.tab_search_highlight_detail);
        ImageView tabForumHighlight = findViewById(R.id.tab_forum_highlight_detail);

        tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        tabSearchHighlight.setImageAlpha(255);
        tabForumHighlight.setImageAlpha(0);

        backend = new Backend();
        processIntent();
    }

    private void processIntent(){
        try {
            Intent intent = getIntent();
            lawyer = new JSONObject(Objects.requireNonNull(intent.getStringExtra("data")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
