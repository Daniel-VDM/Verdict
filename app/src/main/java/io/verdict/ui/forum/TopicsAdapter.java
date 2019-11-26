package io.verdict.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import io.verdict.R;

import java.util.ArrayList;
import java.util.List;

public class TopicsAdapter extends ArrayAdapter<Topic>{

    private Context tContext;
    private List<Topic> topicsList = new ArrayList<>();

    public TopicsAdapter(Context context, ArrayList<Topic> list) {
        super(context, 0 , list);
        tContext = context;
        topicsList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(tContext).inflate(R.layout.list_item,parent,false);

        Topic currentTopic = topicsList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.topic_imageview);
        image.setImageResource(currentTopic.gettImageDrawable());

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentTopic.gettName());


        return listItem;
    }

}
