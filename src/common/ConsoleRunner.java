package common;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleRunner {
    public static List<Integer> administerTest(Test test, Scanner scanner) {
        List<Integer> answers = new ArrayList<>();
        List<Question> questions = test.getQuestions();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println((i + 1) + ". " + q.getQuestionText());
            List<String> options = q.getOptions();
            for (int j = 0; j < options.size(); j++) {
                System.out.println("   " + (j + 1) + ". " + options.get(j));
            }
            System.out.print("Your answer (1-" + options.size() + "): ");
            int choice = scanner.nextInt() - 1;
            answers.add(choice);
        }
        return answers;
    }
}