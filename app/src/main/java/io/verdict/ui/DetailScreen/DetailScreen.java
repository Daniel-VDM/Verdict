package io.verdict.ui.DetailScreen;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.ui.Forum.TopicsActivity;

@SuppressWarnings({"ConstantConditions", "FieldCanBeLocal"})
public class DetailScreen extends AppCompatActivity {

    private static final String TAG = "Detail Activity";

    private Backend backend;
    private JSONObject lawyer;
    private String lawField;
    private ImageView detailImage;
    private ImageView detailLocationIcon;
    private ImageView detailPhoneIcon;
    private TextView detailName;
    private TextView detailLawyerType;
    private TextView detailAddress;
    private TextView detailPhone;
    private DetailReviewItemAdapter sectionsPageAdapter;
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
        detailLocationIcon = findViewById(R.id.DetailLocationIcon);
        detailPhoneIcon = findViewById(R.id.DetailPhoneIcon);
        detailName = findViewById(R.id.DetailName);
        detailLawyerType = findViewById(R.id.DetailLawyerType);
        detailPhone = findViewById(R.id.DetailPhone);
        detailAddress = findViewById(R.id.DetailAddr);

        tabSearchButton.setTextColor(getResources().getColor(R.color.colorTabTextSelected, null));
        tabForumButton.setTextColor(getResources().getColor(R.color.colorTabTextNotSelected, null));
        tabSearchHighlight.setImageAlpha(255);
        tabForumHighlight.setImageAlpha(0);

        tabForumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailScreen.this, TopicsActivity.class);
                startActivity(intent);
            }
        });

        backend = new Backend();
        processIntent();
        loadHeader();

        sectionsPageAdapter = new DetailReviewItemAdapter(getSupportFragmentManager());
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

    @SuppressLint("SetTextI18n")
    private void loadHeader() {
        try {
            final Object imageUrl = lawyer.getString("image_url");
            if (imageUrl != null && !imageUrl.equals("")) {
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
            detailName.setText(lawyer.getString("name"));
            detailLawyerType.setText(lawField);
            JSONArray addrArray = lawyer.getJSONObject("location")
                    .getJSONArray("display_address");
            StringBuilder addr = new StringBuilder();
            for (int i = 0; i < addrArray.length(); i++) {
                addr.append(addrArray.getString(i)).append("\n");
            }
            detailAddress.setText(addr.toString());
            detailPhone.setText(lawyer.getString("display_phone"));
            detailPhoneIcon.setClickable(true);

            final String phoneNumber = lawyer.getString("phone");
            detailPhoneIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailScreen.this);
                    try {
                        builder.setMessage("Do you want to call " + lawyer.getString("display_phone"))
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startPhoneActivity(phoneNumber);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            detailPhone.setClickable(true);
            detailPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailScreen.this);
                    try {
                        builder.setMessage("Do you want to call " + lawyer.getString("display_phone"))
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startPhoneActivity(phoneNumber);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            final JSONObject coordinates = lawyer.getJSONObject("coordinates");
            detailLocationIcon.setClickable(true);
            detailLocationIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailScreen.this);
                    builder.setMessage("Do you want to go to google maps?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        startMapActivity(coordinates.getString("latitude"),
                                                coordinates.getString("longitude"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            detailAddress.setClickable(true);
            detailAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailScreen.this);
                    try {
                        builder.setMessage("Do you want to look for "
                                + lawyer.getString("name") + " on google maps?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            startMapActivity(coordinates.getString("latitude"),
                                                    coordinates.getString("longitude"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startPhoneActivity(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",
                phoneNumber, null));
        startActivity(intent);
    }

    private void startMapActivity(String lat, String lng) throws JSONException {
        Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lng + "?q=" + lawyer.getString("name"));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        DetailReviewItemAdapter adapter = new DetailReviewItemAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailAboutFragment(), "Overview");
        adapter.addFragment(new DetailReviewFragment(), "Reviews");
        adapter.addFragment(new DetailSubmitFragment(), "Leave a review");
        viewPager.setAdapter(adapter);
    }

    public JSONObject getLawyer() {
        return lawyer;
    }

    public Backend getBackend() {
        return backend;
    }
}
