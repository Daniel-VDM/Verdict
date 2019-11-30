package io.verdict.ui.Forum;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.verdict.R;
import io.verdict.backend.Backend;

public class ForumDataGenerator {

    private static final String TAG = "ForumDataGenerator";

    private static Backend backend;
    private Context context;

    public ForumDataGenerator(Context context) {
        backend = new Backend();
        this.context = context;
    }

    private ArrayList<Question> createDebugQuestions(String lawField) {
        ArrayList<Question> result = new ArrayList<Question>();
        List<String> dummy_questions = new ArrayList<String>();
        dummy_questions.add("Should I take my case to court?");
        dummy_questions.add("Who is the best lawyer in my area?");
        dummy_questions.add("Who should I ask for help?");
        dummy_questions.add("What am I entitled to?");
        dummy_questions.add("What do I do now?");
        dummy_questions.add("How do I file a case?");

        for (int i = 0; i < 6; i++) {
            String date = "01-11-" + String.format("%02d", new Random().nextInt(31));
            String question = dummy_questions.get(i);
            String questionDetails = "This is a test";
            Question q = new Question(lawField, date, question, questionDetails, new Random().nextInt(20));
            result.add(q);

        }
        return result;
    }

    public void generateDebugData() {
        try {
            String[] forumCategories = context.getResources().getStringArray(R.array.law_topics);
            JSONObject jsonObject = new JSONObject();
            for (String s : forumCategories) {
                JSONObject questions = new JSONObject();
                for (Question q : createDebugQuestions(s)) {
                    questions.put(q.getUUID(), q.toString());
                }
                jsonObject.put(s, questions);
            }
            backend.initForumData(jsonObject);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
    }

    public void generateDemoData() {
    }
}
