package common.gui;

import common.Topic;
import common.TestResult;
import common.Concept;

import java.util.List;

public class LessonTestScreen {
    private AppLauncher launcher;
    private Topic topic;

    public LessonTestScreen(AppLauncher launcher, Topic topic) {
        this.launcher = launcher;
        this.topic = topic;
    }

    public void show() {
        QuestionTestScreen screen = new QuestionTestScreen(
                topic.getLessonTestForDisplay(),
                "Submit Lesson Test",
                this::handleAnswers
        );
        launcher.getStage().setScene(screen.buildScene("Lesson Test"));
    }

    private void handleAnswers(List<Integer> answers) {
        TestResult result = topic.submitLessonTest(answers);

        if (result.meetsPassingThreshold(75.0)) {
            launcher.showReport();
        } else {
            List<Concept> weakConcepts = result.getWeakConcepts();
            launcher.showRetest(weakConcepts);
        }
    }
}