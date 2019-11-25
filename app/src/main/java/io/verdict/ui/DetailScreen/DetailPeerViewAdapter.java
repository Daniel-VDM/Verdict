package io.verdict.ui.DetailScreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.verdict.R;

public class DetailPeerViewAdapter extends RecyclerView.Adapter<DetailPeerViewAdapter.ViewHolder>{
    private static final String TAG = "DetailPeerViewAdapter";

    private JSONArray peerReviews;
    private Context context;

    public DetailPeerViewAdapter(Context context, JSONArray peerReviews) {
        this.context = context;
        this.peerReviews = peerReviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_review_peer_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject peerReview = peerReviews.getJSONObject(position);
            holder.setupPeerReview(context, peerReview);
        } catch (JSONException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public int getItemCount() {
        return peerReviews.length();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView detailPeerReviewPic;
        private TextView detailPeerReviewName;
        private TextView detailPeerReviewText;
        private RatingBar detailPeerReviewRating;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailPeerReviewPic = itemView.findViewById(R.id.DetailPeerReviewPic);
            detailPeerReviewName = itemView.findViewById(R.id.DetailPeerReviewName);
            detailPeerReviewText = itemView.findViewById(R.id.DetailPeerReviewText);
            detailPeerReviewRating = itemView.findViewById(R.id.DetailPeerReviewRating);
        }

        void setupPeerReview(Context context, JSONObject peerReview) throws JSONException {
            detailPeerReviewRating.setNumStars(peerReview.getInt("rating"));
            detailPeerReviewName.setText(peerReview.getJSONObject("user").getString("name"));
            detailPeerReviewText.setText(peerReview.getString("text"));
            // TODO: setup the pic...
        }
    }
}
