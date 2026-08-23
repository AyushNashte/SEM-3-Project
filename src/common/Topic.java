package common;

import java.util.List;

public abstract class Topic {
    protected String studentClass;
    protected String topicName;
    protected StudentReport report;

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

    public Test getPrerequisiteTestForDisplay() {
        return getPrerequisiteTest();
    }

    public TestResult submitPrerequisiteTest(List<Integer> answers) {
        TestResult result = getPrerequisiteTest().evaluate(answers);
        report.recordPrerequisiteScore(result.getPercentage());
        if (result.isPerfectScore()) {
            report.setStatus(TopicStatus.ALREADY_MASTERED);
        }
        return result;
    }

    public void teach() {
        teachLesson();
    }

    public Test getLessonTestForDisplay() {
        return getLessonTest();
    }

    public TestResult submitLessonTest(List<Integer> answers) {
        TestResult result = getLessonTest().evaluate(answers);
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
        return getRetest(weakConcepts);
    }

    public TestResult submitRetest(List<Concept> weakConcepts, List<Integer> answers) {
        TestResult result = getRetest(weakConcepts).evaluate(answers);
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