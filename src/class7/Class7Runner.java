package class7;

import common.*;
import java.util.List;
import java.util.Scanner;

/**
 * Class7Runner
 * ------------
 * A simple CONSOLE menu so you can run and test your Class 7 topics on
 * your own machine, without needing the JavaFX SDK set up (only teammates
 * wiring the GUI need JavaFX -- see README.md).
 *
 * This is a temporary personal test harness, not part of the final app.
 * The final app will use AppLauncher (JavaFX) once class/topic selection
 * screens are built.
 *
 * NOTE: This is a TRIMMED version listing only the 3 topics added so far
 * (Integers, FractionsAndDecimals, RatioAndProportion). Once you add
 * AlgebraicExpressions, Geometry, and HcfAndLcm, ask for the full 6-topic
 * version and swap it in.
 *
 * Run this class directly to try out the full flow for any of your topics:
 * Prerequisite Test -> Lesson -> Lesson Test -> Retest (if needed) -> Report.
 */
public class Class7Runner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println(" EduSnap - Class 7 Topic Tester (Console)");
        System.out.println("=========================================");
        System.out.println("1. Integers");
        System.out.println("2. Fractions & Decimals");
        System.out.println("3. Ratio & Proportion");
        System.out.print("Choose a topic to test (1-3): ");
        int choice = scanner.nextInt();

        Topic topic;
        switch (choice) {
            case 1: topic = new Integers(); break;
            case 2: topic = new FractionsAndDecimals(); break;
            case 3: topic = new RatioAndProportion(); break;
            default:
                System.out.println("Invalid choice. Exiting.");
                return;
        }

        runTopic(topic, scanner);
    }

    private static void runTopic(Topic topic, Scanner scanner) {
        System.out.println("\n--- Prerequisite Test ---");
        List<Integer> preAnswers = ConsoleRunner.administerTest(topic.getPrerequisiteTestForDisplay(), scanner);
        TestResult preResult = topic.submitPrerequisiteTest(preAnswers);
        System.out.printf("Prerequisite score: %.1f%%\n", preResult.getPercentage());

        if (preResult.isPerfectScore()) {
            System.out.println("Perfect score! This topic is already mastered.");
            printReport(topic);
            return;
        }

        System.out.println("\n--- Lesson ---");
        topic.teach();

        System.out.println("\n--- Lesson Test ---");
        List<Integer> lessonAnswers = ConsoleRunner.administerTest(topic.getLessonTestForDisplay(), scanner);
        TestResult lessonResult = topic.submitLessonTest(lessonAnswers);
        System.out.printf("Lesson test score: %.1f%%\n", lessonResult.getPercentage());

        List<Concept> weak = lessonResult.getWeakConcepts();
        while (!lessonResult.meetsPassingThreshold(75.0)) {
            System.out.println("\nSome concepts need review: " + weak);
            System.out.println("\n--- Retest ---");
            List<Integer> retestAnswers = ConsoleRunner.administerTest(topic.getRetestForDisplay(weak), scanner);
            lessonResult = topic.submitRetest(weak, retestAnswers);
            System.out.printf("Retest score: %.1f%%\n", lessonResult.getPercentage());
            weak = lessonResult.getWeakConcepts();
        }

        System.out.println("\nTopic completed!");
        printReport(topic);
    }

    private static void printReport(Topic topic) {
        StudentReport report = topic.getReport();
        System.out.println("\n--- Final Report ---");
        System.out.println(report);
    }
}
