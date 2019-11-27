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
public class TemporaryAnswersAdapter extends ArrayAdapter<Answer> {

    private Context aContext;
    private List<Answer> answersList = new ArrayList<>();

    public TemporaryAnswersAdapter(Context context, ArrayList<Answer> list) {
        super(context, 0 , list);
        aContext = context;
        answersList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(aContext).inflate(R.layout.thread_answer,parent,false);

        Answer currentAnswer = answersList.get(position);

        TextView author = (TextView)listItem.findViewById(R.id.thread_answer_name);
        author.setText(String.valueOf(currentAnswer.getaAuthor()));

        TextView aRating = (TextView) listItem.findViewById(R.id.thread_answer_rating);
        aRating.setText('+'+ String.valueOf(currentAnswer.getaRating()));

        TextView aText = (TextView) listItem.findViewById(R.id.thread_answer_text);
        aText.setText(String.valueOf(currentAnswer.getanswer_text()));


        return listItem;
    }
}
