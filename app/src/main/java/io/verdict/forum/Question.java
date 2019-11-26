package io.verdict.forum;

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
    private ArrayList answers;

    // Constructor that is used to create an instance of the Question object
    public Question(String qTopic, String date, String qAuthor, String question) {
        this.qTopic = qTopic;
        this.date = date;
        this.qAuthor = qAuthor;
        this.question = question;
        this.answers = new ArrayList<Answer>();
        this.qRating = 0;
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

    public ArrayList getanswers() {
        return answers;
    }

    public void setanswers(ArrayList answers) {
        this.answers = answers;
    }


}
