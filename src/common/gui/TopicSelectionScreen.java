package common.gui;

import common.Topic;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TopicSelectionScreen {
    private final AppLauncher launcher;

    public TopicSelectionScreen(AppLauncher launcher) {
        this.launcher = launcher;
    }

    public Scene buildScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label title = new Label("Choose a Topic");
        title.setStyle(UiStyle.TITLE);

        Button class9Button = new Button("Class 9 — Real Numbers");
        class9Button.setStyle(UiStyle.BUTTON);
        class9Button.setOnAction(e -> launcher.startTopic(new class9.RealNumbers()));

        Button class10Button = new Button("Class 10 — Arithmetic Progression");
        class10Button.setStyle(UiStyle.BUTTON);
        class10Button.setOnAction(e -> launcher.startTopic(new class10.ArithmeticProgression()));

        root.getChildren().addAll(title, class9Button, class10Button);
        return new Scene(root, 500, 400);
    }
}
