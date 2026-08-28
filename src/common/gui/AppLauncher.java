package common.gui;

import common.Concept;
import common.Topic;
import javafx.stage.Stage;

import java.util.List;

public class AppLauncher {

    private final Stage stage;
    private Topic topic;

    public AppLauncher(Stage stage) {
        this.stage = stage;
    }

    public void launch() {
        stage.setTitle("EduSnap");
        showTopics();
        stage.show();
    }

    // =========================
    // TOPIC SELECTION
    // =========================

    // Your version
    public void showTopics() {
        TopicSelectionScreen screen = new TopicSelectionScreen(this);
        stage.setScene(screen.buildScene());
    }

    // Main branch compatibility
    public void showTopicSelection() {
        showTopics();
    }

    // Your version
    public void selectTopic(Topic selectedTopic) {
        this.topic = selectedTopic;
        showPrerequisiteTest();
    }

    // Main branch compatibility
    public void startTopic(Topic selectedTopic) {
        selectTopic(selectedTopic);
    }

    // =========================
    // PREREQUISITE TEST
    // =========================

    public void showPrerequisiteTest() {
        PrerequisiteTestScreen screen =
                new PrerequisiteTestScreen(this, topic);

        stage.setScene(screen.buildScene());
    }

    // =========================
    // GETTERS
    // =========================

    public Topic getTopic() {
        return topic;
    }

    public Stage getStage() {
        return stage;
    }

    // =========================
    // REPORT
    // =========================

    public void showReport() {
        ReportScreen screen =
                new ReportScreen(this, topic.getReport());

        stage.setScene(screen.buildScene());
    }

    // =========================
    // LESSON
    // =========================

    public void showLesson() {
        LessonScreen screen =
                new LessonScreen(
                        this,
                        topic,
                        topic.getLessonContentForDisplay()
                );

        stage.setScene(screen.buildScene());
    }

    // =========================
    // LESSON TEST
    // =========================

    public void showLessonTest() {
        new LessonTestScreen(this, topic).show();
    }

    // =========================
    // RETEST
    // =========================

    public void showRetest(List<Concept> weakConcepts) {
        new RetestScreen(this, topic, weakConcepts).show();
    }
}