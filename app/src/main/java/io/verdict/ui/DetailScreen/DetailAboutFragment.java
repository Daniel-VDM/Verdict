package io.verdict.ui.DetailScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
            lawyer = ((DetailScreen)getActivity()).getLawyer();
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
    private void loadAboutMe(){
        try {
            detailAboutHeader.setText("About " + lawyer.getString("name"));
            detailAboutMe.setText(lawyerDb.getJSONObject("ABOUT-ME").getString("text"));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void loadLegalExp(){

    }

    private void setupButtons(){

    }
}
