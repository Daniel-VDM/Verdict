package io.verdict.forum;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
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
