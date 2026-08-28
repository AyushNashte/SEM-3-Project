package common;

import java.util.List;

public abstract class Topic {
    protected String studentClass;
    protected String topicName;
    protected StudentReport report;

    private Test activePrerequisiteTest;
    private Test activeLessonTest;
    private Test activeRetest;

    private static final int QUESTIONS_PER_ATTEMPT = 5;

    public Topic(String studentClass, String topicName) {
        this.studentClass = studentClass;
        this.topicName = topicName;
        this.report = new StudentReport(studentClass, topicName);
    }

    protected abstract Test getPrerequisiteTest();
    protected abstract Test getLessonTest();
    protected abstract Test getRetest(List<Concept> weakConcepts);
    protected abstract void teachLesson();
    protected abstract void reviewConcepts(List<Concept> weakConcepts);
    protected abstract List<LessonContent> getLessonContentBank();

    public Test getPrerequisiteTestForDisplay() {
        activePrerequisiteTest = getPrerequisiteTest().pickRandom(QUESTIONS_PER_ATTEMPT);
        return activePrerequisiteTest;
    }

    public TestResult submitPrerequisiteTest(List<Integer> answers) {
        TestResult result = activePrerequisiteTest.evaluate(answers);
        report.recordPrerequisiteScore(result.getPercentage());
        if (result.isPerfectScore()) {
            report.setStatus(TopicStatus.ALREADY_MASTERED);
        }
        return result;
    }

    public void teach() {
        teachLesson();
    }

    public List<LessonContent> getLessonContentForDisplay() {
        return getLessonContentBank();
    }

    public Test getLessonTestForDisplay() {
        activeLessonTest = getLessonTest().pickRandom(QUESTIONS_PER_ATTEMPT);
        return activeLessonTest;
    }

    public TestResult submitLessonTest(List<Integer> answers) {
        TestResult result = activeLessonTest.evaluate(answers);
        report.recordLessonTestScore(result.getPercentage(), result.getWeakConcepts());
        if (result.meetsPassingThreshold(75.0)) {
            report.setStatus(TopicStatus.COMPLETED);
        } else {
            reviewConcepts(result.getWeakConcepts());
            report.setStatus(TopicStatus.RETEST_REQUIRED);
        }
        return result;
    }

    public Test getRetestForDisplay(List<Concept> weakConcepts) {
        activeRetest = getRetest(weakConcepts).pickRandom(QUESTIONS_PER_ATTEMPT);
        return activeRetest;
    }

    public TestResult submitRetest(List<Concept> weakConcepts, List<Integer> answers) {
        TestResult result = activeRetest.evaluate(answers);
        report.recordRetestScore(result.getPercentage(), result.getWeakConcepts());
        if (result.meetsPassingThreshold(75.0)) {
            report.setStatus(TopicStatus.COMPLETED);
        } else {
            reviewConcepts(result.getWeakConcepts());
            report.setStatus(TopicStatus.RETEST_REQUIRED);
        }
        return result;
    }

    public StudentReport getReport() {
        return report;
    }
}