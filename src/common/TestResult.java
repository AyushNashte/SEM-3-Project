package common;

import java.util.List;

public class TestResult {
    private int totalQuestions;
    private int correctAnswers;
    private List<Concept> weakConcepts;

    public TestResult(int totalQuestions, int correctAnswers, List<Concept> weakConcepts) {
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.weakConcepts = weakConcepts;
    }

    public double getPercentage() {
        if (totalQuestions == 0) return 0;
        return (correctAnswers * 100.0) / totalQuestions;
    }

    public boolean isPerfectScore() {
        return correctAnswers == totalQuestions;
    }

    public boolean meetsPassingThreshold(double thresholdPercent) {
        return getPercentage() >= thresholdPercent;
    }

    public List<Concept> getWeakConcepts() {
        return weakConcepts;
    }
}