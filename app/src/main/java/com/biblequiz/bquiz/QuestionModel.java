package com.biblequiz.bquiz;

public class QuestionModel {

    private String question, optionA, optionB, optionC, optionD, answerKey;

    public QuestionModel(String question, String optionA, String optionB, String optionC, String optionD, String answerKey) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answerKey = answerKey;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getAnswerKey() {
        return answerKey;
    }
}
