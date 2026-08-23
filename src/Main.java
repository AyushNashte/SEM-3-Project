import class10.ArithmeticProgression;
import common.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Topic topic = new ArithmeticProgression();

        System.out.println("=== Pre-requisite Test ===");
        List<Integer> prereqAnswers = ConsoleRunner.administerTest(topic.getPrerequisiteTestForDisplay(), scanner);
        TestResult prereqResult = topic.submitPrerequisiteTest(prereqAnswers);
        System.out.println("Pre-requisite score: " + prereqResult.getPercentage() + "%");

        if (prereqResult.isPerfectScore()) {
            System.out.println("Already mastered — skipping lesson.");
            printReport(topic);
            return;
        }

        System.out.println("\n=== Main Teaching Program ===");
        topic.teach();

        System.out.println("\n=== Lesson Test ===");
        List<Integer> lessonAnswers = ConsoleRunner.administerTest(topic.getLessonTestForDisplay(), scanner);
        TestResult lessonResult = topic.submitLessonTest(lessonAnswers);
        System.out.println("Lesson test score: " + lessonResult.getPercentage() + "%");

        List<Concept> weakConcepts = lessonResult.getWeakConcepts();

        while (topic.getReport().getStatus() == TopicStatus.RETEST_REQUIRED) {
            System.out.println("\n=== Retest (weak concepts only) ===");
            List<Integer> retestAnswers = ConsoleRunner.administerTest(topic.getRetestForDisplay(weakConcepts), scanner);
            TestResult retestResult = topic.submitRetest(weakConcepts, retestAnswers);
            System.out.println("Retest score: " + retestResult.getPercentage() + "%");
            weakConcepts = retestResult.getWeakConcepts();
        }

        printReport(topic);
    }

    private static void printReport(Topic topic) {
        System.out.println("\n=== Student Report ===");
        System.out.println(topic.getReport());
    }
}