package common.gui;

import common.Topic;
import javafx.stage.Stage;

public class AppLauncher {
    private Stage stage;
    private Topic topic;

    public AppLauncher(Stage stage) {
        this.stage = stage;
    }

    public void launch() {
        stage.setTitle("EduSnap");

        // Class 10 -> Arithmetic Progression hardcoded for now.
        // Once class/topic selection screens exist, this will be chosen by the student instead.
        topic = new class10.ArithmeticProgression();

        showPrerequisiteTest();
        stage.show();
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
}