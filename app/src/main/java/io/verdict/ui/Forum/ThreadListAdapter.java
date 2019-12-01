package io.verdict.ui.Forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import io.verdict.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ThreadListAdapter extends ArrayAdapter<Question>{
    private Context ttContext;
    private List<Question> originalThreadsList;
    private List<Question> currThreadsList;

    public ThreadListAdapter(Context context, ArrayList<Question> list) {
        super(context, 0 , list);
        ttContext = context;
        currThreadsList = list;
        originalThreadsList = new ArrayList<>(list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(ttContext).inflate(R.layout.forum_threads_card,parent,false);

        Question currentThread = currThreadsList.get(position);

        TextView tq = listItem.findViewById(R.id.thread_question);
        tq.setText(String.valueOf(currentThread.getquestion()));

        TextView rating = listItem.findViewById(R.id.thread_rating);
        rating.setText('+'+ String.valueOf(currentThread.getqRating()));

        TextView date = listItem.findViewById(R.id.thread_date);
        date.setText("\t" + currentThread.getdate());

        return listItem;
    }

    public boolean isCurrentEmpty(){
        return currThreadsList.size() == 0;
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

    void sortByDate(){
        Collections.sort(currThreadsList, new Comparator<Question>() {
            @Override
            public int compare(Question question, Question t1) {
                try {
                    Date date1 = Question.dateFormat.parse(question.getdate());
                    Date date2 = Question.dateFormat.parse(t1.getdate());
                    return date2.compareTo(date1);
                } catch (java.text.ParseException ignore){
                    return 0;
                }
            }
        });
        notifyDataSetChanged();
    }

    void sortByRating(){
        Collections.sort(currThreadsList, new Comparator<Question>() {
            @Override
            public int compare(Question question, Question t1) {
                if (question.getqRating() == t1.getqRating()){
                    return 0;
                }
                return question.getqRating() < t1.getqRating() ? 1 : -1;
            }
        });
        notifyDataSetChanged();
    }

}
