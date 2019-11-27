package io.verdict.ui.DetailScreen;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import io.verdict.R;

@SuppressWarnings("ConstantConditions")
public class DetailReviewFragment extends Fragment {

    private final static String TAG = "Detail Review";

    private View view;
    private RecyclerView peerReviews;
    private RecyclerView clientReviews;
    private JSONObject lawyerDb;
    private JSONArray currPeerReviews;
    private JSONArray currClientReviews;
    private JSONArray peerReviewMore;
    private JSONArray clientReviewMore;
    private ArrayList<ImageView> detailClientRatingStars;
    private ArrayList<ImageView> detailPeerRatingStars;
    private TextView detailClientRating;
    private TextView detailPeerRating;
    private TextView detailClientRatingCount;
    private TextView detailPeerRatingCount;
    private TextView detailPeerReviewsMore;
    private TextView detailClientReviewsMore;
    private TextView detailPriceRating;
    private TextView detailPeerReviewHeader;
    private TextView detailClientReviewHeader;
    private ImageView div1;
    private ImageView div2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_review_fragment, container, false);
        peerReviews = view.findViewById(R.id.DetailPeerReviews);
        clientReviews = view.findViewById(R.id.DetailClientReviews);
        peerReviews.setNestedScrollingEnabled(false);
        clientReviews.setNestedScrollingEnabled(false);
        detailClientRating = view.findViewById(R.id.detail_client_rating);
        detailPeerRating = view.findViewById(R.id.detail_peer_rating);
        detailClientRatingCount = view.findViewById(R.id.detail_client_rating_count2);
        detailPeerRatingCount = view.findViewById(R.id.detail_peer_rating_count2);
        detailPeerReviewsMore = view.findViewById(R.id.detail_peer_rating_viewmore);
        detailClientReviewsMore = view.findViewById(R.id.detail_client_rating_viewmore);
        detailPriceRating = view.findViewById(R.id.detail_price_rating2);
        detailPeerReviewHeader = view.findViewById(R.id.detail_peer_review_header);
        detailClientReviewHeader = view.findViewById(R.id.detail_client_review_header2);
        div1 = view.findViewById(R.id.detail_review_break3);
        div2 = view.findViewById(R.id.detail_review_break4);
        detailClientRatingStars = new ArrayList<ImageView>() {{
            add((ImageView) view.findViewById(R.id.detail_client_rating_star1));
            add((ImageView) view.findViewById(R.id.detail_client_rating_star2));
            add((ImageView) view.findViewById(R.id.detail_client_rating_star3));
            add((ImageView) view.findViewById(R.id.detail_client_rating_star4));
            add((ImageView) view.findViewById(R.id.detail_client_rating_star5));
        }};
        detailPeerRatingStars = new ArrayList<ImageView>() {{
            add((ImageView) view.findViewById(R.id.detail_peer_rating_star1));
            add((ImageView) view.findViewById(R.id.detail_peer_rating_star2));
            add((ImageView) view.findViewById(R.id.detail_peer_rating_star3));
            add((ImageView) view.findViewById(R.id.detail_peer_rating_star4));
            add((ImageView) view.findViewById(R.id.detail_peer_rating_star5));
        }};
        try {
            lawyerDb = ((DetailScreen) getActivity()).getLawyer()
                    .getJSONObject("DATABASE_CONTENTS");
            setupReviewHeader();
            setupPeerReviews();
            setupClientReviews();
            setupPriceEstimate();
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return view;
    }

    private double getAvgReview(JSONArray jsonArray) throws JSONException {
        double sum = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            sum += jsonArray.getJSONObject(i).getInt("rating");
        }
        return jsonArray.length() != 0 ? sum / jsonArray.length() : 0;
    }

    private void setStars(ArrayList<ImageView> stars, float rating) {
        Drawable emptyStar = getActivity().getResources().getDrawable(R.drawable.ic_rating_star_empty);
        Drawable halfStar = getActivity().getResources().getDrawable(R.drawable.ic_rating_star_half);
        Drawable fullStar = getActivity().getResources().getDrawable(R.drawable.ic_rating_star_full);
        for (int i = 0; i < stars.size(); i++) {
            ImageView star = stars.get(i);
            if (rating - (i + 1) >= 0) {
                star.setImageDrawable(fullStar);
            } else if (rating - (i + 1) >= -0.75) {
                star.setImageDrawable(halfStar);
            } else {
                star.setImageDrawable(emptyStar);
            }
        }
    }

    // TODO: more sorting options & leave date of review at the bottom.
    //       This will require some reworking of both recycler view's cards.
    //       Basic: sort by rating and sort by date (if it's implemented)
    // TODO: Have review submission immediate appear as a review. This will need
    //       some logic to insert and resort (if we have that feature).

    private JSONArray sortVerifiedReviewFirst(JSONArray jsonArray) throws JSONException {
        JSONArray unverified = new JSONArray();
        JSONArray verified = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject review = jsonArray.getJSONObject(i);
            if (review.getBoolean("VERIFIED")) {
                verified.put(review);
            } else {
                unverified.put(review);
            }
        }
        for (int i = 0; i < unverified.length(); i++) {
            verified.put(unverified.getJSONObject(i));
        }
        return verified;
    }

    @SuppressLint("SetTextI18n")
    private void setupReviewHeader() throws JSONException {
        clientReviewMore = sortVerifiedReviewFirst(lawyerDb.getJSONArray("USER_REVIEWS"));
        float clientAvgRating = (float) getAvgReview(clientReviewMore);
        peerReviewMore = lawyerDb.getJSONArray("PEER_REVIEWS");
        float peerAvgRating = (float) getAvgReview(peerReviewMore);
        setStars(detailClientRatingStars, clientAvgRating);
        setStars(detailPeerRatingStars, peerAvgRating);
        detailClientRating.setText(String.format("%.1f", clientAvgRating));
        detailClientRatingCount.setText("(" + clientReviewMore.length() + ")");
        detailPeerRating.setText(String.format("%.1f", peerAvgRating));
        detailPeerRatingCount.setText("(" + peerReviewMore.length() + ")");
    }

    private void setupPeerReviews() throws JSONException {
        currPeerReviews = new JSONArray();
        int initCount = Math.min(2, peerReviewMore.length());
        for (int i = 0; i < initCount; i++) {
            currPeerReviews.put(peerReviewMore.getJSONObject(0));
            peerReviewMore.remove(0);
        }
        if (peerReviewMore.length() == 0) {
            detailPeerReviewsMore.setClickable(false);
            detailPeerReviewsMore.setHeight(0);
            detailPeerReviewsMore.setAlpha(0.0f);
        }
        if (currPeerReviews.length() == 0) {
            div1.setAlpha(0.0f);
            div1.setMaxHeight(0);
            detailPeerReviewHeader.setAlpha(0.0f);
            detailPeerReviewHeader.setHeight(0);
        }
        final DetailPeerViewAdapter adapter = new DetailPeerViewAdapter(getContext(), currPeerReviews);
        peerReviews.setAdapter(adapter);
        peerReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        detailPeerReviewsMore.setClickable(true);
        detailPeerReviewsMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Math.min(3, peerReviewMore.length());
                for (int i = 0; i < count; i++) {
                    try {
                        currPeerReviews.put(peerReviewMore.getJSONObject(0));
                        peerReviewMore.remove(0);
                        adapter.notifyItemInserted(currPeerReviews.length() - 1);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (peerReviewMore.length() == 0) {
                    detailPeerReviewsMore.setClickable(false);
                    detailPeerReviewsMore.setHeight(0);
                    detailPeerReviewsMore.setAlpha(0.0f);
                }
            }
        });
    }

    private void setupClientReviews() throws JSONException {
        currClientReviews = new JSONArray();
        int initCount = Math.min(2, clientReviewMore.length());
        for (int i = 0; i < initCount; i++) {
            currClientReviews.put(clientReviewMore.getJSONObject(0));
            clientReviewMore.remove(0);
        }
        if (clientReviewMore.length() == 0) {
            detailClientReviewsMore.setClickable(false);
            detailClientReviewsMore.setHeight(0);
            detailClientReviewsMore.setAlpha(0.0f);
        }
        if (currClientReviews.length() == 0) {
            div2.setAlpha(0.0f);
            div2.setMaxHeight(0);
            detailClientReviewHeader.setAlpha(0.0f);
            detailClientReviewHeader.setHeight(0);
        }
        final DetailClientViewAdapter adapter = new DetailClientViewAdapter(getContext(), currClientReviews);
        clientReviews.setAdapter(adapter);
        clientReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        detailClientReviewsMore.setClickable(true);
        detailClientReviewsMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Math.min(3, clientReviewMore.length());
                for (int i = 0; i < count; i++) {
                    try {
                        currClientReviews.put(clientReviewMore.getJSONObject(0));
                        clientReviewMore.remove(0);
                        adapter.notifyItemInserted(currClientReviews.length() - 1);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                if (clientReviewMore.length() == 0) {
                    detailClientReviewsMore.setClickable(false);
                    detailClientReviewsMore.setHeight(0);
                    detailClientReviewsMore.setAlpha(0.0f);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupPriceEstimate() throws JSONException {
        switch (lawyerDb.getInt("PRICE")) {
            case 1:
                detailPriceRating.setTextColor(Color.parseColor("#007000"));
                detailPriceRating.setText("Below Average");
                break;
            case 2:
                detailPriceRating.setTextColor(Color.parseColor("#238823"));
                detailPriceRating.setText("Average");
                break;
            case 3:
                detailPriceRating.setTextColor(Color.parseColor("#eb8d00"));
                detailPriceRating.setText("Above Average");
                break;
            default:
                detailPriceRating.setTextColor(Color.parseColor("#D2222D"));
                detailPriceRating.setText("Expensive");
        }
    }
}
