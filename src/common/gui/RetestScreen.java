package common.gui;

import common.Concept;
import common.Topic;
import common.TestResult;

import java.util.List;

public class RetestScreen {
    private AppLauncher launcher;
    private Topic topic;
    private List<Concept> weakConcepts;

    public RetestScreen(AppLauncher launcher, Topic topic, List<Concept> weakConcepts) {
        this.launcher = launcher;
        this.topic = topic;
        this.weakConcepts = weakConcepts;
    }

    public void show() {
        QuestionTestScreen screen = new QuestionTestScreen(
                topic.getRetestForDisplay(weakConcepts),
                "Submit Retest",
                this::handleAnswers
        );
        launcher.getStage().setScene(screen.buildScene("Retest"));
    }

    private void handleAnswers(List<Integer> answers) {
        TestResult result = topic.submitRetest(weakConcepts, answers);

        if (result.meetsPassingThreshold(75.0)) {
            launcher.showReport();
        } else {
            // Still weak — loop back into another retest on the remaining weak concepts
            launcher.showRetest(result.getWeakConcepts());
        }
    }
}