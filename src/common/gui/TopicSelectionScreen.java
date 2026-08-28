package common.gui;

import common.Topic;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Supplier;

public class TopicSelectionScreen {
    private final AppLauncher launcher;
    private final VBox root;

    public TopicSelectionScreen(AppLauncher launcher) {
        this.launcher = launcher;
        this.root = new VBox(15);
    }

    public Scene buildScene() {
        root.setPadding(new Insets(20));

        Label title = new Label("EduSnap — Select a Topic");
        title.setStyle(UiStyle.TITLE);

        root.getChildren().add(title);

        // Class 7 Section
        addClassSection("Class 7", gameButton("Integers"));

        // Class 9 Section
        addClassSection("Class 9", topicButton("Real Numbers", class9.RealNumbers::new));

        // Class 10 Section
        addClassSection("Class 10", topicButton("Arithmetic Progression", class10.ArithmeticProgression::new));

        return new Scene(root, 500, 450);
    }

    private void addClassSection(String className, Button topicButton) {
        Label header = new Label(className);
        header.setStyle(UiStyle.HEADER);

        VBox section = new VBox(8);
        section.getChildren().addAll(header, topicButton);
        root.getChildren().add(section);
    }

    private Button gameButton(String label) {
        Button button = new Button(label);
        button.setStyle(UiStyle.BUTTON);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> {
            class7.Integers.MainApp app = new class7.Integers.MainApp();
            launcher.getStage().setScene(class7.Integers.create(app));
        });
        return button;
    }

    private Button topicButton(String label, Supplier<Topic> topicSupplier) {
        Button button = new Button(label);
        button.setStyle(UiStyle.BUTTON);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> launcher.selectTopic(topicSupplier.get()));
        return button;
    }
}