package io.verdict.forum;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.verdict.R;

public class ThreadsAdapter extends RecyclerView.Adapter<ThreadsViewHolder>{
    private List<Question> questionList;

    public ThreadsAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    @Override
    public void onBindViewHolder(ThreadsViewHolder threadsViewHolder, int i) {
        Question currentQuestion = questionList.get(i);
        threadsViewHolder.vThreadQuestion.setText(currentQuestion.getquestion());
        threadsViewHolder.vThreadRating.setText(currentQuestion.getqRating());
    }

    @Override
    public ThreadsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.threads_card, viewGroup, false);

        return new ThreadsViewHolder(itemView);
    }


}
