package io.verdict.ui.Forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.verdict.R;

public class SingleThreadAnswersAdapter extends ArrayAdapter<Answer> {

    private Context aContext;
    private Question question;
    private List<Answer> answersList;

    public SingleThreadAnswersAdapter(Context context, ArrayList<Answer> list, Question question) {
        super(context, 0, list);
        this.aContext = context;
        this.answersList = list;
        this.question = question;
        sortAnswers();
    }

    private void sortAnswers() {
        Collections.sort(answersList, new Comparator<Answer>() {
            @Override
            public int compare(Answer answer, Answer t1) {
                if (answer.getaRating() == t1.getaRating()) {
                    return 0;
                }
                return answer.getaRating() < t1.getaRating() ? 1 : -1;
            }
        });
    }

    public boolean isEmpty() {
        return answersList.size() == 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(aContext).inflate(R.layout.forum_thread_answer, parent, false);

        final Answer currentAnswer = answersList.get(position);

        TextView author = listItem.findViewById(R.id.response_name);
        if (currentAnswer.isAnonymous()) {
            author.setText("Anonymous");
        } else {
            author.setText(currentAnswer.getaAuthor());
        }

        final TextView aRating = listItem.findViewById(R.id.response_rating);
        aRating.setText('+' + String.valueOf(currentAnswer.getaRating()));

        TextView aText = listItem.findViewById(R.id.response_body);
        aText.setText(String.valueOf(currentAnswer.getanswer_text()));

        ImageView legalExpertIco = listItem.findViewById(R.id.response_verified_lawyer_ico);
        TextView legalExpertTxt = listItem.findViewById(R.id.response_verified_lawyer_txt);
        if (!currentAnswer.getUserType().equals("lawyer")) {
            legalExpertIco.setAlpha(0.0f);
            legalExpertTxt.setAlpha(0.0f);
        }

        final ImageView likeButton = listItem.findViewById(R.id.response_like);
        likeButton.setClickable(true);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeButton.setClickable(false);
                likeButton.setMaxHeight(0);
                likeButton.setAlpha(0.0f);
                currentAnswer.setaRating(currentAnswer.getaRating() + 1);
                notifyDataSetChanged();
                Question toChange = question; // TODO: hook this up to the backend and save the question
            }
        });
        return listItem;
    }
}
