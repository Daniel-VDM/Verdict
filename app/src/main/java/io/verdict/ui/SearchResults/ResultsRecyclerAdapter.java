package io.verdict.ui.SearchResults;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.verdict.R;

public class ResultsRecyclerAdapter extends RecyclerView.Adapter<ResultsRecyclerAdapter.ResultsViewHolder>{

    private JSONArray resultsJsonArray;

    public class ResultsViewHolder extends RecyclerView.ViewHolder {
        public TextView resultsTextView1;
        public TextView resultsTextView2;
        public TextView resultsTextView3;

        public ResultsViewHolder(View view) {
            super(view);
            resultsTextView1 = view.findViewById(R.id.result_text_view1);
            resultsTextView2 = view.findViewById(R.id.result_text_view2);
            resultsTextView3 = view.findViewById(R.id.result_text_view3);
        }
    }

    public ResultsRecyclerAdapter(JSONArray jsonArray) {
        resultsJsonArray = jsonArray;
    }

    @Override
    public ResultsRecyclerAdapter.ResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_results_card, parent, false);
        return new ResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultsViewHolder holder, int position) {
        try {
            JSONObject result = resultsJsonArray.getJSONObject(position);
            String name = result.getString("name");
            String city = result.getJSONObject("location").getString("city");
            String reviewCount = result.getString("review_count");
            holder.resultsTextView1.setText(name);
            holder.resultsTextView2.setText(city);
            holder.resultsTextView3.setText(reviewCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return resultsJsonArray.length();
    }

}
