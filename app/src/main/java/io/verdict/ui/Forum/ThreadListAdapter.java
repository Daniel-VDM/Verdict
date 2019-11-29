package io.verdict.ui.Forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import io.verdict.R;

import java.util.ArrayList;
import java.util.List;

public class ThreadListAdapter extends ArrayAdapter<Question>{
    private Context ttContext;
    private List<Question> threadsList = new ArrayList<>();

    public ThreadListAdapter(Context context, ArrayList<Question> list) {
        super(context, 0 , list);
        ttContext = context;
        threadsList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(ttContext).inflate(R.layout.forum_threads_card,parent,false);

        Question currentThread = threadsList.get(position);

        TextView tq = listItem.findViewById(R.id.thread_question);
        tq.setText(String.valueOf(currentThread.getquestion()));

        TextView rating = listItem.findViewById(R.id.thread_rating);
        rating.setText('+'+ String.valueOf(currentThread.getqRating()));

        TextView date = listItem.findViewById(R.id.thread_date);
        date.setText("\t" + currentThread.getdate());

        return listItem;
    }

}
