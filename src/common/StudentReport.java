package common;

import java.util.List;

public class StudentReport {
    private String studentClass;
    private String topicName;
    private double prerequisiteScore;
    private double lessonTestScore;
    private List<Concept> weakConcepts;
    private double retestScore;
    private int attempts;
    private TopicStatus status;

    public StudentReport(String studentClass, String topicName) {
        this.studentClass = studentClass;
        this.topicName = topicName;
        this.attempts = 0;
    }

    public void recordPrerequisiteScore(double score) {
        this.prerequisiteScore = score;
    }

    public void recordLessonTestScore(double score, List<Concept> weakConcepts) {
        this.lessonTestScore = score;
        this.weakConcepts = weakConcepts;
        this.attempts++;
    }

    public void recordRetestScore(double score, List<Concept> stillWeak) {
        this.retestScore = score;
        this.weakConcepts = stillWeak;
        this.attempts++;
    }

    public void setStatus(TopicStatus status) {
        this.status = status;
    }

    public TopicStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Class: " + studentClass +
                ", Topic: " + topicName +
                ", Prerequisite: " + prerequisiteScore + "%" +
                ", Lesson Test: " + lessonTestScore + "%" +
                ", Retest: " + retestScore + "%" +
                ", Attempts: " + attempts +
                ", Status: " + status;
    }

    public double getPrerequisiteScore() {
        return prerequisiteScore;
    }

    public double getLessonTestScore() {
        return lessonTestScore;
    }

    public double getRetestScore() {
        return retestScore;
    }

    public int getAttempts() {
        return attempts;
    }

    public List<Concept> getWeakConcepts() {
        return weakConcepts;
    }

    public String getTopicName() {
        return topicName;
    }
}