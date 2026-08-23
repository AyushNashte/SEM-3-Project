package common;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private List<Question> questions;

    public Test(List<Question> questions) {
        this.questions = questions;
    }

    public TestResult evaluate(List<Integer> chosenAnswers) {
        int correct = 0;
        List<Concept> weak = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int chosen = chosenAnswers.get(i);

            if (q.isCorrect(chosen)) {
                correct++;
            } else if (!weak.contains(q.getConcept())) {
                weak.add(q.getConcept());
            }
        }

        return new TestResult(questions.size(), correct, weak);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}