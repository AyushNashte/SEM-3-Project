package common.gui;

import common.Topic;
import javafx.stage.Stage;
import java.util.List;
import common.Concept;

public class AppLauncher {
    private Stage stage;
    private Topic topic;

    public AppLauncher(Stage stage) {
        this.stage = stage;
    }

    public void launch() {
        stage.setTitle("EduSnap");
        showTopicSelection();
        stage.show();
    }

    public void showTopicSelection() {
        TopicSelectionScreen screen = new TopicSelectionScreen(this);
        stage.setScene(screen.buildScene());
    }

    public void startTopic(Topic selectedTopic) {
        this.topic = selectedTopic;
        showPrerequisiteTest();
    }

    public void showPrerequisiteTest() {
        PrerequisiteTestScreen screen = new PrerequisiteTestScreen(this, topic);
        stage.setScene(screen.buildScene());
    }

    public Topic getTopic() {
        return topic;
    }

    public Stage getStage() {
        return stage;
    }

    public void showReport() {
        ReportScreen screen = new ReportScreen(this, topic.getReport());
        stage.setScene(screen.buildScene());
    }

    public void showLesson() {
        LessonScreen screen = new LessonScreen(this, topic, topic.getLessonContentForDisplay());
        stage.setScene(screen.buildScene());
    }

    public void showLessonTest() {
        new LessonTestScreen(this, topic).show();
    }

    public void showRetest(List<Concept> weakConcepts) {
        new RetestScreen(this, topic, weakConcepts).show();
    }
}