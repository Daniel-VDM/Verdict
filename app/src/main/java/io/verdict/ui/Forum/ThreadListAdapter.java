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
    private List<Question> originalThreadsList;
    private List<Question> threadsList;

    public ThreadListAdapter(Context context, ArrayList<Question> list) {
        super(context, 0 , list);
        ttContext = context;
        threadsList = list;
        originalThreadsList = new ArrayList<>(list);
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


    private boolean match(String[] input, String reference){
        reference = reference.toLowerCase();
        for (String s: input){
            if (!reference.contains(s)){
                return false;
            }
        }
        return true;
    }

    /**
     * Naive implementation of search.
     *
     * @param s filter string
     */
    void filter(String s){
        String[] inputToks = s.replaceAll("[^a-zA-Z ]", "")
                .toLowerCase().split("\\s+");
        List<Question> newThreadsList = new ArrayList<>();
        if (inputToks.length == 0){
            newThreadsList = new ArrayList<>(originalThreadsList);
        } else {
            for (Question question: originalThreadsList){
                if (match(inputToks, question.getquestion())){
                    newThreadsList.add(question);
                }
            }
        }
        clear();
        addAll(newThreadsList);
        notifyDataSetChanged();
    }

}
