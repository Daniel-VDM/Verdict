package io.verdict.ui.DetailScreen;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.verdict.R;

@SuppressWarnings("ConstantConditions")
public class DetailAboutFragment extends Fragment {

    private static final String TAG = "Detail about";
    private TextView detailAboutHeader;
    private TextView detailAboutMe;
    private TextView detailLegalExpertise;
    private Button detailForumActivityButton;
    private Button detailWebsiteButton;
    private JSONObject lawyer;
    private JSONObject lawyerDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_about_fragment, container, false);

        detailAboutHeader = view.findViewById(R.id.DetailAboutHeader);
        detailAboutMe = view.findViewById(R.id.DetailAboutMe);
        detailLegalExpertise = view.findViewById(R.id.DetailLegalExpertise);
        detailForumActivityButton = view.findViewById(R.id.DetailForumActivityButton);
        detailWebsiteButton = view.findViewById(R.id.DetailWebsiteButton);
        try {
            lawyer = ((DetailScreen) getActivity()).getLawyer();
            lawyerDb = lawyer.getJSONObject("DATABASE_CONTENTS");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        loadAboutMe();
        loadLegalExp();
        setupButtons();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void loadAboutMe() {
        try {
            detailAboutHeader.setText("About " + lawyer.getString("name"));
            detailAboutMe.setText(lawyerDb.getJSONObject("ABOUT-ME").getString("text"));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void loadLegalExp() {
        try {
            JSONArray categories = lawyer.getJSONArray("categories");
            StringBuilder exp = new StringBuilder();
            for (int i = 0; i < categories.length(); i++) {
                JSONObject cat = categories.getJSONObject(i);
                exp.append("✓ ");
                exp.append(cat.getString("title")).append("\n");
            }
            detailLegalExpertise.setText(exp.toString());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    private void setupButtons() {
        detailForumActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Connect this to the forums section
            }
        });
        detailWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Would you like to open a web browser?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    String url = lawyer.getString("url");
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(url));
                                    startActivity(intent);
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
    }
}
