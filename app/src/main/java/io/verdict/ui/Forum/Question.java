package io.verdict.ui.Forum;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

@SuppressLint("SimpleDateFormat")
public class Question {

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

    private String qTopic;
    private String date;
    private int qRating;
    private String question;
    private String questionDetails;
    private ArrayList<Answer> answers;
    private UUID uuid;


    // Constructor that is used to create an instance of the Question object
    public Question(String qTopic, String date, String question, String questionDetails, int qRating) {
        this.qTopic = qTopic;
        this.date = date;
        this.question = question;
        this.questionDetails = questionDetails;
        this.answers = Answer.createDummyAnswers(6);
        this.qRating = qRating;
        this.uuid = UUID.randomUUID();
    }

    public Question(JSONObject jsonObject) throws JSONException {
        this.qTopic = jsonObject.getString("qTopic");
        this.date = jsonObject.getString("date");
        this.question = jsonObject.getString("question");
        this.questionDetails = jsonObject.getString("questionDetails");
        this.answers = new ArrayList<>();
        JSONArray jsonAnswers = jsonObject.getJSONArray("answers");
        for (int i = 0; i < jsonAnswers.length(); i++) {
            this.answers.add(new Answer((JSONObject) jsonAnswers.get(i)));
        }
        this.qRating = jsonObject.getInt("qRating");
        this.uuid = UUID.fromString(jsonObject.getString("uuid"));
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
        jsonObject.put("question", this.question);
        jsonObject.put("questionDetails", this.questionDetails);
        jsonObject.put("qRating", this.qRating);
        jsonObject.put("uuid", this.uuid.toString());

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

    public String getQuestionDetails() {
        return questionDetails;
    }

    public void setQuestionDetails(String questionDetails) {
        this.questionDetails = questionDetails;
    }

    public String getUUID() {
        return uuid.toString();
    }
}
