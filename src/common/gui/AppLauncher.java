package common.gui;

import common.Concept;
import common.Topic;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.List;

public class AppLauncher {

    private final Stage stage;
    private Topic topic;
    private String selectedClass;

    public AppLauncher(Stage stage) {
        this.stage = stage;
    }

    public void launch() {
        stage.setTitle("EduSnap");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setWidth(1150);
        stage.setHeight(720);
        stage.centerOnScreen();
        showHome();
        stage.show();
    }

    // HOME / CLASS / TOPIC SELECTION

    public void showHome() {
        selectedClass = null;
        stage.setScene(new HomeScreen(this).buildScene());
    }

    public void showClass(String className) {
        selectedClass = className;
        stage.setScene(new ClassTopicsScreen(this, className).buildScene());
    }

    // Back to Topics buttons land on the class you were in (or Home if none)
    public void showTopics() {
        if (selectedClass == null) {
            showHome();
        } else {
            showClass(selectedClass);
        }
    }

    // Main branch compatibility
    public void showTopicSelection() {
        showTopics();
    }

    public void selectTopic(Topic selectedTopic) {
        this.topic = selectedTopic;
        showPrerequisiteTest();
    }

    // Main branch compatibility
    public void startTopic(Topic selectedTopic) {
        selectTopic(selectedTopic);
    }

    // PREREQUISITE TEST

    public void showPrerequisiteTest() {
        PrerequisiteTestScreen screen =
                new PrerequisiteTestScreen(this, topic);

        stage.setScene(screen.buildScene());
    }

    // GETTERS

    public Topic getTopic() {
        return topic;
    }

    public Stage getStage() {
        return stage;
    }

    // REPORT

    public void showReport() {
        ReportScreen screen =
                new ReportScreen(this, topic.getReport());

        stage.setScene(screen.buildScene());
    }

    // LESSON

    public void showLesson() {
        LessonScreen screen =
                new LessonScreen(
                        this,
                        topic,
                        topic.getLessonContentForDisplay()
                );

        stage.setScene(screen.buildScene());
    }

    // LESSON TEST

    public void showLessonTest() {
        new LessonTestScreen(this, topic).show();
    }

    // RETEST

    public void showRetest(List<Concept> weakConcepts) {
        new RetestScreen(this, topic, weakConcepts).show();
    }

    public Button createBackButton(String warning) {
        Button back = new Button("← Back to Topics");
        back.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, warning, ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    showTopics();
                }
            });
        });
        return back;
    }
}