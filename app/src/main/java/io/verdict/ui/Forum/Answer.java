package io.verdict.ui.Forum;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Answer {
    private String aQuestion;
    private String aDate;
    private String aAuthor;
    private String aAuthorId;
    private int aRating;
    private String answer_text;
    private String userType;
    private Boolean isAnonymous;

    public Answer(String aQuestion, String aDate, String aAuthor, String answer_text, String userType, int aRating, boolean isAnonymous) {
        this.aQuestion = aQuestion;
        this.aDate = aDate;
        this.aAuthor = aAuthor;
        this.aAuthorId = "";
        this.answer_text = answer_text;
        this.aRating = aRating;
        this.userType = userType;
        this.isAnonymous = isAnonymous;
    }

    public Answer(JSONObject jsonObject) throws JSONException {
        this.aQuestion = jsonObject.getString("aQuestion");
        this.aDate = jsonObject.getString("aDate");
        this.aAuthor = jsonObject.getString("aAuthor");
        this.aAuthorId = jsonObject.getString("aAuthorId");
        this.answer_text = jsonObject.getString("answer_text");
        this.aRating = jsonObject.getInt("aRating");
        this.userType = jsonObject.getString("userType");
        this.isAnonymous = jsonObject.getBoolean("isAnonymous");
    }

    public static ArrayList<Answer> createDummyAnswers(int size) {
        ArrayList<Answer> result = new ArrayList<>();
        List<String> dummy_answers = new ArrayList<String>();
        dummy_answers.add("No you should not.");
        dummy_answers.add("This depends on law 1001.");
        dummy_answers.add("I think you can try to settle out of court.");
        dummy_answers.add("You are entitled to a lot of money.");
        dummy_answers.add("No.");
        dummy_answers.add("Absolutely");

        for (int i = 0; i < size; i++) {
            String aQuestion = "Should I take my case to court?";
            String aDate = "01-11-19";
            String aAuthor = "TEST_ACC";
            String answer_text = dummy_answers.get(i);
            Answer a = new Answer(aQuestion, aDate, aAuthor, answer_text, "user",
                    new Random().nextInt(99), new Random().nextBoolean());
            result.add(a);

        }
        return result;
    }

    public String getaQuestion() {
        return aQuestion;
    }

    public void setaQuestion(String aQuestion) {
        this.aQuestion = aQuestion;
    }

    public String getaDate() {
        return aDate;
    }

    public void setaDate(String aDate) {
        this.aDate = aDate;
    }

    public String getaAuthor() {
        return aAuthor;
    }

    public void setaAuthor(String aAuthor) {
        this.aAuthor = aAuthor;
    }

    public int getaRating() {
        return aRating;
    }

    public void setaRating(int aRating) {
        this.aRating = aRating;
    }

    public String getanswer_text() {
        return answer_text;
    }

    public void setanswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public JSONObject jsonSerialize() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aQuestion", this.aQuestion);
        jsonObject.put("aDate", this.aDate);
        jsonObject.put("aAuthor", this.aAuthor);
        jsonObject.put("aAuthorId", this.aAuthorId);
        jsonObject.put("answer_text", this.answer_text);
        jsonObject.put("aRating", this.aRating);
        jsonObject.put("userType", this.userType);
        jsonObject.put("isAnonymous", this.isAnonymous);
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

    public String getUserType() {
        return userType;
    }
}
