package io.verdict.ui.Forum;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Question {
    // Store the topic this question is under
    private String qTopic;
    // Store the date posted
    private String date;
    // Store the name of person who asked the question (could be anonymous)
    private String qAuthor;
    // Store the number of thumbs up on the question
    private int qRating;
    // Store the question
    private String question;
    // Store all answers under this question
    private ArrayList<Answer> answers;

    // Constructor that is used to create an instance of the Question object
    public Question(String qTopic, String date, String qAuthor, String question) {
        this.qTopic = qTopic;
        this.date = date;
        this.qAuthor = qAuthor;
        this.question = question;
        this.answers = Answer.createDummyAnswers(6);
        this.qRating = 3;
    }

    public Question(JSONObject jsonObject) throws JSONException {
        this.qTopic = jsonObject.getString("qTopic");
        this.date = jsonObject.getString("date");
        this.qAuthor = jsonObject.getString("qAuthor");
        this.question = jsonObject.getString("question");
        this.answers = new ArrayList<>();
        JSONArray jsonAnswers = jsonObject.getJSONArray("answers");
        for (int i = 0; i < jsonAnswers.length(); i++) {
            this.answers.add(new Answer((JSONObject) jsonAnswers.get(i)));
        }
        this.qRating = jsonObject.getInt("qRating");
    }

    public String getqTopic() {
        return qTopic;
    }

    public void setqTopic(String qTopic) {
        this.qTopic = qTopic;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getqAuthor() {
        return qAuthor;
    }

    public void setqAuthor(String qAuthor) {
        this.qAuthor = qAuthor;
    }

    public int getqRating() {
        return qRating;
    }

    public void setqRating(int qRating) {
        this.qRating = qRating;
    }

    public String getquestion() {
        return question;
    }

    public void setquestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getanswers() {
        return answers;
    }

    public void setanswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public JSONObject jsonSerialize() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("qTopic", this.qTopic);
        jsonObject.put("date", this.date);
        jsonObject.put("qAuthor", this.qAuthor);
        jsonObject.put("question", this.question);
        jsonObject.put("qRating", this.qRating);

        JSONArray jsonArray = new JSONArray();
        for (Answer answer : answers) {
            jsonArray.put(answer.jsonSerialize());
        }
        jsonObject.put("answers", jsonArray);

        return jsonObject;
    }

    @NonNull
    @Override
    public String toString() {
        try {
            return jsonSerialize().toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return super.toString();
        }
    }
}
