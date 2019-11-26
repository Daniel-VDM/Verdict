package io.verdict.forum;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;

import java.util.Vector;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;
import io.verdict.backend.SearchListener;
import io.verdict.backend.SearchQuarry;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class TopicsActivity extends AppCompatActivity {

    private ListView topicsList;
    private TopicsAdapter tAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        final Button topicsTabSearchButton = findViewById(R.id.topics_tab_search_search);
        final Button topicsTabForumButton = findViewById(R.id.topics_tab_forum_search);
        final ImageView topicsTabSearchHighlight = findViewById(R.id.topics_tab_search_highlight_search);
        final ImageView topicsTabForumHighlight = findViewById(R.id.topics_tab_forum_highlight_search);

        topicsTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        topicsTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        topicsTabForumHighlight.setImageAlpha(255);
        topicsTabSearchHighlight.setImageAlpha(0);

        topicsTabSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicsTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
                topicsTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
                topicsTabSearchHighlight.setImageAlpha(255);
                topicsTabForumHighlight.setImageAlpha(0);
            }
        });

        topicsTabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicsTabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
                topicsTabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
                topicsTabSearchHighlight.setImageAlpha(0);
                topicsTabForumHighlight.setImageAlpha(255);
            }
        });

        final ListView topicsList = findViewById(R.id.topics_list);
        ArrayList<Topic> topics_list_content = new ArrayList<>();

        //Might want to move this, stil need to change some images
        String[] law_topics = this.getResources().getStringArray(R.array.law_topics);
        topics_list_content.add(new Topic(R.drawable.ic_briefcase, law_topics[0])); //maritime
        topics_list_content.add(new Topic(R.drawable.ic_briefcase, law_topics[1])); //bankruptcy
        topics_list_content.add(new Topic(R.drawable.ic_briefcase, law_topics[2])); //business
        topics_list_content.add(new Topic(R.drawable.ic_briefcase, law_topics[3])); //civil rights
        topics_list_content.add(new Topic(R.drawable.ic_handcuffs, law_topics[4])); //criminal
        topics_list_content.add(new Topic(R.drawable.ic_camera, law_topics[5])); //entertainment
        topics_list_content.add(new Topic(R.drawable.ic_park, law_topics[6])); //environmental
        topics_list_content.add(new Topic(R.drawable.ic_briefcase, law_topics[7])); //family
        topics_list_content.add(new Topic(R.drawable.ic_briefcase, law_topics[8])); //health
        topics_list_content.add(new Topic(R.drawable.ic_plane, law_topics[9])); //immigration
        topics_list_content.add(new Topic(R.drawable.ic_briefcase, law_topics[10])); //intellectual prop
        topics_list_content.add(new Topic(R.drawable.ic_tax, law_topics[11])); //international
        topics_list_content.add(new Topic(R.drawable.ic_briefcase, law_topics[12])); //labor
        topics_list_content.add(new Topic(R.drawable.ic_star, law_topics[13])); //military
        topics_list_content.add(new Topic(R.drawable.ic_wounded, law_topics[14])); //personal injury
        topics_list_content.add(new Topic(R.drawable.ic_home, law_topics[15])); //real estate
        topics_list_content.add(new Topic(R.drawable.ic_tax, law_topics[16])); //tax

        tAdapter = new TopicsAdapter(this,topics_list_content);
        topicsList.setAdapter(tAdapter);


    }

}
