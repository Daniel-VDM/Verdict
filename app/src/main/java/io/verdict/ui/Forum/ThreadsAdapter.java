package io.verdict.ui.Forum;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.verdict.R;

public class ThreadsAdapter extends RecyclerView.Adapter<ThreadsAdapter.ThreadsViewHolder>{
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
        threadsViewHolder.vThreadQuestion.setText(String.valueOf(currentQuestion.getquestion()));
        threadsViewHolder.vThreadRating.setText(String.valueOf(currentQuestion.getqRating()));
    }

    @Override
    public ThreadsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.threads_card, viewGroup, false);

        return new ThreadsViewHolder(itemView);
    }

    public class ThreadsViewHolder extends RecyclerView.ViewHolder {

        public TextView vThreadQuestion;
        public TextView vThreadRating;


        public ThreadsViewHolder(View v) {
            super(v);
            this.vThreadQuestion =  (TextView) v.findViewById(R.id.thread_question);
            this.vThreadRating = (TextView)  v.findViewById(R.id.thread_rating);

        }



    }


}
