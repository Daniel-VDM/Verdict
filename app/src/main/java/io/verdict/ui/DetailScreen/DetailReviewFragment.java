package io.verdict.ui.DetailScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.verdict.R;

@SuppressWarnings("ConstantConditions")
public class DetailReviewFragment extends Fragment {

    private final static String TAG = "Detail Review";

    private RecyclerView peerReviews;
    private RecyclerView clientReviews;
    private JSONObject lawyer;
    private JSONObject lawyerDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_review_fragment, container, false);
        peerReviews = view.findViewById(R.id.DetailPeerReviews);
        clientReviews = view.findViewById(R.id.DetailClientReviews);
        peerReviews.setNestedScrollingEnabled(false);
        clientReviews.setNestedScrollingEnabled(false);
        try {
            lawyer = ((DetailScreen) getActivity()).getLawyer();
            lawyerDb = lawyer.getJSONObject("DATABASE_CONTENTS");
            setupPeerReviews();
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }


        return view;
    }

    private void setupPeerReviews() throws JSONException {
        JSONArray reviews = lawyerDb.getJSONArray("PEER_REVIEWS");
        reviews.put(reviews.getJSONObject(0));
        DetailPeerViewAdapter adapter = new DetailPeerViewAdapter(getContext(), reviews);
        peerReviews.setAdapter(adapter);
        peerReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        clientReviews.setAdapter(adapter);  // TODO create own setup...
        clientReviews.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
