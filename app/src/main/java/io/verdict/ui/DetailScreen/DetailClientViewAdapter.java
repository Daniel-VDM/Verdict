package io.verdict.ui.DetailScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import io.verdict.R;
import io.verdict.backend.Backend;
import io.verdict.backend.DatabaseListener;

public class DetailClientViewAdapter extends RecyclerView.Adapter<DetailClientViewAdapter.ViewHolder>{
    private static final String TAG = "detailClientViewAdapter";

    private JSONArray peerReviews;
    private Context context;

    public DetailClientViewAdapter(Context context, JSONArray peerReviews) {
        this.context = context;
        this.peerReviews = peerReviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_review_client_listitem, parent, false);
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

        private ImageView detailClientReviewPic;
        private ImageView detailClientReviewBg;
        private ImageView detailClientVerifiedIcon;
        private TextView detailClientVerifiedText;
        private TextView detailClientReviewName;
        private TextView detailClientReviewText;
        private TextView detailClientRatingNumber;
        private ArrayList<ImageView> detailRating;
        private Backend backend;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            backend = new Backend();
            detailClientReviewPic = itemView.findViewById(R.id.DetailClientReviewPic);
            detailClientReviewName = itemView.findViewById(R.id.DetailClientReviewName);
            detailClientReviewText = itemView.findViewById(R.id.DetailClientReviewText);
            detailClientReviewBg = itemView.findViewById(R.id.DetailItemReviewBg);
            detailClientRatingNumber = itemView.findViewById(R.id.detail_client_rating_number);
            detailClientVerifiedIcon = itemView.findViewById(R.id.DetailVerifiedIcon);
            detailClientVerifiedText = itemView.findViewById(R.id.detail_client_verified2);
            detailRating = new ArrayList<ImageView>(){{
               add((ImageView) itemView.findViewById(R.id.detail_client_rating_star1));
               add((ImageView) itemView.findViewById(R.id.detail_client_rating_star2));
               add((ImageView) itemView.findViewById(R.id.detail_client_rating_star3));
               add((ImageView) itemView.findViewById(R.id.detail_client_rating_star4));
               add((ImageView) itemView.findViewById(R.id.detail_client_rating_star5));
            }};
        }

        @SuppressLint("SetTextI18n")
        void setupPeerReview(final Context context, final JSONObject clientReview) throws JSONException {
            detailClientReviewName.setText(clientReview.getJSONObject("user").getString("name"));
            detailClientReviewText.setText(clientReview.getString("text"));
            float rating = (float)clientReview.getLong("rating");
            detailClientRatingNumber.setText(rating + "");

            fetchAndLoadPic(context, clientReview);

            Drawable emptyStar = context.getResources().getDrawable(R.drawable.ic_rating_star_empty);
            Drawable halfStar = context.getResources().getDrawable(R.drawable.ic_rating_star_half);
            Drawable fullStar = context.getResources().getDrawable(R.drawable.ic_rating_star_full);
            for (int i=0; i<detailRating.size(); i++) {
                ImageView star = detailRating.get(i);
                if (rating - (i + 1) >= 0) {
                    star.setImageDrawable(fullStar);
                } else if (rating - (i + 1) >= -0.75) {
                    star.setImageDrawable(halfStar);
                } else {
                    star.setImageDrawable(emptyStar);
                }
            }

            if (!clientReview.getBoolean("VERIFIED")){
                detailClientVerifiedIcon.setAlpha(0.0f);
                detailClientVerifiedText.setAlpha(0.0f);
            }

            detailClientReviewBg.setClickable(true);
            detailClientReviewBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Would you like to open a web browser?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        String url = clientReview.getString("url");
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(url));
                                        context.startActivity(intent);
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

        void fetchAndLoadPic(final Context context, JSONObject peerReview) throws JSONException {
            String reviewerKey = peerReview.getJSONObject("user").getString("KEY");
            backend.databaseGet("PIC_" + reviewerKey, new DatabaseListener() {
                @Override
                public void onStart(String key) {
                     Log.d(TAG, "Looking up image for " + key);
                }

                @Override
                public void onSuccess(String key, final String value) {
                    if (value != null && !value.equals("") && !value.equals("null")) {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    InputStream inputStream = (InputStream) new URL(value)
                                            .getContent();
                                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    ((DetailScreen)context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            detailClientReviewPic.setImageBitmap(bitmap);
                                        }
                                    });
                                } catch (IOException e) {
                                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                                }
                            }
                        }.start();
                    }
                }

                @Override
                public void onFailed(DatabaseError databaseError) {
                    Log.e(TAG, databaseError.getMessage());
                }
            });
        }

    }
}
