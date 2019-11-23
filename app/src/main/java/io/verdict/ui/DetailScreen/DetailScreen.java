package io.verdict.ui.DetailScreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import io.verdict.R;
import io.verdict.backend.Backend;

@SuppressWarnings("ConstantConditions")
public class DetailScreen extends AppCompatActivity {

    private static final String TAG = "Detail Activity";

    private Backend backend;
    private JSONObject lawyer;
    private String lawField;
    private ImageView detailImage;
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_screen);

        Button tabSearchButton = findViewById(R.id.tab_search_detail);
        Button tabForumButton = findViewById(R.id.tab_forum_detail);
        ImageView tabSearchHighlight = findViewById(R.id.tab_search_highlight_detail);
        ImageView tabForumHighlight = findViewById(R.id.tab_forum_highlight_detail);
        detailImage = findViewById(R.id.DetailImage);

        tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        tabSearchHighlight.setImageAlpha(255);
        tabForumHighlight.setImageAlpha(0);

        backend = new Backend();
        processIntent();
        loadHeader();

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.DetailFragmentHolder);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.DetailTabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void processIntent() {
        try {
            Intent intent = getIntent();
            lawyer = new JSONObject(Objects.requireNonNull(intent.getStringExtra("data")));
            lawField = intent.getStringExtra("lawField");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadHeader() {
        try {
            final Object imageUrl = lawyer.getString("image_url");
            if (imageUrl != null) {  // TODO check if null condition is correct.
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = (InputStream) new URL(imageUrl.toString())
                                    .getContent();
                            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    detailImage.setImageBitmap(bitmap);
                                }
                            });
                        } catch (IOException ignore) {
                        }
                    }
                }.start();
            }

            // TODO rest of the relevant information (possibly add is open or close!!!)

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailAboutFragment(), "Overview");
        adapter.addFragment(new DetailReviewFragment(), "Reviews");
        adapter.addFragment(new DetailSubmitFragment(), "Leave a review");
        viewPager.setAdapter(adapter);
    }
}
