package model;

import java.util.List;

public class QuestionData {
    public String questionText;
    public List<String> choices;

    public QuestionData(String questionText, List<String> choices) {
        this.questionText = questionText;
        this.choices = choices;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getChoices() {
        return choices;
    }
}
