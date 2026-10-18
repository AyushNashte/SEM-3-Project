package common;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;
    private Concept concept;

    public Question(String questionText, List<String> options, int correctOptionIndex, Concept concept) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.concept = concept;
    }

    public boolean isCorrect(int chosenOptionIndex) {
        return chosenOptionIndex == correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public Concept getConcept() {
        return concept;
    }
}