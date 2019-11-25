package io.verdict.ui.SearchResults;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.verdict.R;
import io.verdict.ui.DetailScreen.DetailScreen;

public class ResultsRecyclerAdapter extends RecyclerView.Adapter<ResultsRecyclerAdapter.ResultsViewHolder>{

    private JSONArray resultsJsonArray;
    private SearchResults searchResultsActivity;
    private String currentLawField;

    public class ResultsViewHolder extends RecyclerView.ViewHolder {
        public TextView resultLawyerName;
        public TextView resultLawyerTitle;
        public TextView resultLawyerLocation;
        public ImageView resultLawyerImage;
        public TextView resultClientRatingAvg;
        public TextView resultClientRatingCount;
        public ImageView[] resultClientRatingStars;
        public TextView resultPeerRatingAvg;
        public TextView resultPeerRatingCount;
        public ImageView[] resultPeerRatingStars;
        public TextView resultPriceRating;
        public CardView resultCard;

        public ResultsViewHolder(View view) {
            super(view);
            resultLawyerName = view.findViewById(R.id.result_lawyer_name);
            resultLawyerTitle = view.findViewById(R.id.result_laywer_title);
            resultLawyerLocation = view.findViewById(R.id.result_lawyer_location);
            resultLawyerImage = view.findViewById(R.id.result_lawyer_image);
            resultClientRatingAvg = view.findViewById(R.id.result_client_rating);
            resultClientRatingCount = view.findViewById(R.id.result_client_rating_count);
            resultClientRatingStars = new ImageView[]{
                    view.findViewById(R.id.result_client_rating_star1),
                    view.findViewById(R.id.result_client_rating_star2),
                    view.findViewById(R.id.result_client_rating_star3),
                    view.findViewById(R.id.result_client_rating_star4),
                    view.findViewById(R.id.result_client_rating_star5),
            };
            resultPeerRatingAvg = view.findViewById(R.id.result_peer_rating);
            resultPeerRatingCount = view.findViewById(R.id.result_peer_rating_count);
            resultPeerRatingStars = new ImageView[]{
                    view.findViewById(R.id.result_peer_rating_star1),
                    view.findViewById(R.id.result_peer_rating_star2),
                    view.findViewById(R.id.result_peer_rating_star3),
                    view.findViewById(R.id.result_peer_rating_star4),
                    view.findViewById(R.id.result_peer_rating_star5),
            };
            resultPriceRating = view.findViewById(R.id.result_price_rating);
            resultCard = view.findViewById(R.id.result_card);
        }
    }

    public ResultsRecyclerAdapter(JSONArray jsonArray, String legalField, SearchResults activity) {
        resultsJsonArray = jsonArray;
        currentLawField = legalField;
        searchResultsActivity = activity;
    }

    @Override
    public ResultsRecyclerAdapter.ResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_card, parent, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ResultsViewHolder holder, int position) {
        try {
            final JSONObject result = resultsJsonArray.getJSONObject(position);
            final String name = result.getString("name");
            final String title = result.getJSONArray("categories").getJSONObject(0).getString("title");
            final String city = result.getJSONObject("location").getString("city");
            final String state = result.getJSONObject("location").getString("state");
            final String imageUrl = result.getString("image_url");
            final int priceRating = result.getJSONObject("DATABASE_CONTENTS").getInt("PRICE");

            new Thread() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream = (InputStream) new URL(imageUrl).getContent();
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        searchResultsActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.resultLawyerImage.setImageBitmap(bitmap);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            holder.resultLawyerName.setText(name);
            holder.resultLawyerTitle.setText(title);

            final String location = city + ", " + state;
            holder.resultLawyerLocation.setText(location);

            StringBuilder priceRatingString = new StringBuilder();
            for (int i=0; i<priceRating; i++) {
                priceRatingString.append("$");
            }
            holder.resultPriceRating.setText(priceRatingString.toString());

            int clientRatingSum = 0;
            JSONArray clientRatingsArray = result.getJSONObject("DATABASE_CONTENTS").getJSONArray("USER_REVIEWS");
            for (int i=0; i<clientRatingsArray.length(); i++) {
                clientRatingSum += clientRatingsArray.getJSONObject(i).getInt("rating");
            }
            holder.resultClientRatingCount.setText(String.format("(%d)", clientRatingsArray.length()));
            if (clientRatingsArray.length() == 0) {
                holder.resultClientRatingAvg.setText("None");
                holder.resultClientRatingAvg.setTextColor(searchResultsActivity.getResources().getColor(R.color.colorPrimaryDark));
                Drawable emptyStar = searchResultsActivity.getResources().getDrawable(R.drawable.ic_rating_star_empty);
                setStarRatingImages(holder.resultClientRatingStars, 0);
            } else {
                double clientRatingAvg = ((double)clientRatingSum)/clientRatingsArray.length();
                holder.resultClientRatingAvg.setText(String.format("%.1f", clientRatingAvg));
                setStarRatingImages(holder.resultClientRatingStars, clientRatingAvg);
            }

            int peerRatingSum = 0;
            JSONArray peerRatingsArray = result.getJSONObject("DATABASE_CONTENTS").getJSONArray("PEER_REVIEWS");
            for (int i=0; i<peerRatingsArray.length(); i++) {
                peerRatingSum += peerRatingsArray.getJSONObject(i).getInt("rating");
            }
            holder.resultPeerRatingCount.setText(String.format("(%d)", peerRatingsArray.length()));
            if (peerRatingsArray.length() == 0) {
                holder.resultPeerRatingAvg.setText("None");
                holder.resultPeerRatingAvg.setTextColor(searchResultsActivity.getResources().getColor(R.color.colorPrimaryDark));
                Drawable emptyStar = searchResultsActivity.getResources().getDrawable(R.drawable.ic_rating_star_empty);
                setStarRatingImages(holder.resultPeerRatingStars, 0);
            } else {
                double peerRatingAvg = ((double)peerRatingSum)/peerRatingsArray.length();
                holder.resultPeerRatingAvg.setText(String.format("%.1f", peerRatingAvg));
                setStarRatingImages(holder.resultPeerRatingStars, peerRatingAvg);
            }

            holder.resultCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String lawField = currentLawField + " Law";

                    Intent detailScreenIntent = new Intent(searchResultsActivity, DetailScreen.class);
                    detailScreenIntent.putExtra("data", result.toString());
                    detailScreenIntent.putExtra("lawField", lawField);

                    searchResultsActivity.startActivity(detailScreenIntent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setStarRatingImages(ImageView[] stars, double ratingAvg) {
        Drawable emptyStar = searchResultsActivity.getResources().getDrawable(R.drawable.ic_rating_star_empty);
        Drawable halfStar = searchResultsActivity.getResources().getDrawable(R.drawable.ic_rating_star_half);
        Drawable fullStar = searchResultsActivity.getResources().getDrawable(R.drawable.ic_rating_star_full);
        for (int i=0; i<stars.length; i++) {
            ImageView star = stars[i];
            if (ratingAvg - (i + 1) >= 0) {
                star.setImageDrawable(fullStar);
            } else if (ratingAvg - (i + 1) >= -0.75) {
                star.setImageDrawable(halfStar);
            } else {
                star.setImageDrawable(emptyStar);
            }
        }
    }

    @Override
    public int getItemCount() {
        return resultsJsonArray.length();
    }

}
