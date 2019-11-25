package io.verdict.ui.SearchResults;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        public CardView resultCard;

        public ResultsViewHolder(View view) {
            super(view);
            resultLawyerName = view.findViewById(R.id.result_lawyer_name);
            resultLawyerTitle = view.findViewById(R.id.result_laywer_title);
            resultLawyerLocation = view.findViewById(R.id.result_lawyer_location);
            resultLawyerImage = view.findViewById(R.id.result_lawyer_image);
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
            final String location = city + ", " + state;

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
            holder.resultLawyerLocation.setText(location);

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

    @Override
    public int getItemCount() {
        return resultsJsonArray.length();
    }

}
