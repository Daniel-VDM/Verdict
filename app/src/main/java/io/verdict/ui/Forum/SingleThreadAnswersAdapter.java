package io.verdict.ui.Forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.verdict.R;

public class SingleThreadAnswersAdapter extends ArrayAdapter<Answer> {

    private Context aContext;
    private List<Answer> answersList = new ArrayList<>();

    public SingleThreadAnswersAdapter(Context context, ArrayList<Answer> list) {
        super(context, 0, list);
        aContext = context;
        answersList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(aContext).inflate(R.layout.forum_thread_answer, parent, false);

        Answer currentAnswer = answersList.get(position);

        TextView author = listItem.findViewById(R.id.response_name);
        author.setText(String.valueOf(currentAnswer.getaAuthor()));

        TextView aRating = listItem.findViewById(R.id.response_rating);
        aRating.setText('+' + String.valueOf(currentAnswer.getaRating()));

        TextView aText = listItem.findViewById(R.id.response_body);
        aText.setText(String.valueOf(currentAnswer.getanswer_text()));


        return listItem;
    }
}