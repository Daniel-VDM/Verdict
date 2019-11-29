package io.verdict.ui.Forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.verdict.R;
import io.verdict.ui.SearchScreen.SearchScreen;

public class TopicsActivity extends AppCompatActivity {

    private TopicsAdapter tAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_topics);
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
                Intent intent = new Intent(TopicsActivity.this, SearchScreen.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.forum_topics_writeNew);

        final ListView topicsList = findViewById(R.id.topics_list);
        ArrayList<Topic> topics_list_content = new ArrayList<>();

        //Might want to move this, stil need to change some images
        final String[] law_topics = this.getResources().getStringArray(R.array.law_topics);
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
        topicsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String lawField = law_topics[i];
                Intent intent = new Intent(TopicsActivity.this, ThreadsActivity.class);
                intent.putExtra("LAW_FIELD", lawField);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopicsActivity.this, PostThreadActivity.class);
                startActivity(intent);
            }
        });
    }

}
