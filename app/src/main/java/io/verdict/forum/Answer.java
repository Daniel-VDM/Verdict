package io.verdict.forum;

import java.util.ArrayList;

public class Answer {
    // Store the question this answer is under
    private String aQuestion;
    // Store the date answer is posted
    private String aDate;
    // Store the name of person who wrote answer (could be anonymous)
    private String aAuthor;
    // Store the number of thumbs up on the answer
    private int aRating;
    // Store the answer
    private String answer_text;


    // Constructor that is used to create an instance of the Answer object
    public Answer(String aQuestion, String aDate, String aAuthor, String answer_text) {
        this.aQuestion = aQuestion;
        this.aDate = aDate;
        this.aAuthor = aAuthor;
        this.answer_text = answer_text;
        this.aRating = 0;
    }



}
