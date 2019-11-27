package io.verdict.ui.Forum;

public class Topic {
    // Store the id of the topic image
    private int tImageDrawable;
    // Store the topic
    private String tName;
    // Store all threads under this topic
    private Question[] questions;

    // Constructor that is used to create an instance of the Topic object
    public Topic(int tImageDrawable, String tName) {
        this.tImageDrawable = tImageDrawable;
        this.tName = tName;
    }

    public int gettImageDrawable() {
        return tImageDrawable;
    }

    public void settImageDrawable(int tImageDrawable) {
        this.tImageDrawable = tImageDrawable;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String mName) {
        this.tName = tName;
    }

    public Question[] getquestions() {
        return questions;
    }

    public void setquestions(Question[] questions) {
        this.questions = questions;
    }

}
