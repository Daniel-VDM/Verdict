package io.verdict.ui.forum;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import io.verdict.R;

public class ThreadsViewHolder extends RecyclerView.ViewHolder {

    public TextView vThreadQuestion;
    public TextView vThreadRating;


    public ThreadsViewHolder(View v) {
        super(v);
        this.vThreadQuestion =  (TextView) v.findViewById(R.id.thread_question);
        this.vThreadRating = (TextView)  v.findViewById(R.id.thread_rating);

    }



}
