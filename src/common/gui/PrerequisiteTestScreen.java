package common.gui;

import common.TestResult;
import common.Topic;
import javafx.scene.Scene;

import java.util.List;

/** Pre-requisite test: now just reuses the shared QuestionTestScreen so it looks like the others. */
public class PrerequisiteTestScreen {
    private final AppLauncher launcher;
    private final Topic topic;

    public PrerequisiteTestScreen(AppLauncher launcher, Topic topic) {
        this.launcher = launcher;
        this.topic = topic;
    }

    public Scene buildScene() {
        QuestionTestScreen screen = new QuestionTestScreen(
                launcher,
                topic.getPrerequisiteTestForDisplay(),
                "Submit Test",
                this::submitTest
        );
        return screen.buildScene("Pre-requisite Test");
    }

    private void submitTest(List<Integer> answers) {
        TestResult result = topic.submitPrerequisiteTest(answers);
        if (result.isPerfectScore()) {
            launcher.showReport();
        } else {
            launcher.showLesson();
        }
    }
}