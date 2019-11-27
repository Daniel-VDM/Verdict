package io.verdict.ui.forum;

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



}
