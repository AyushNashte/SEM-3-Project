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
        addClassSection("Class 7", topicButton("Integers", class7.Integers::new));

        // Class 8 Section
        addClassSection("Class 8",
                topicButton("Rational Numbers", class8.RationalNumbers::new),
                topicButton("Exponents", class8.Exponents::new),
                topicButton("Factorisation", class8.Factorisation::new),
                topicButton("Linear Equations", class8.LinearEquations::new),
                topicButton("Percentage & Financial Maths", class8.PercentageAndFinancialMaths::new),
                topicButton("Mensuration", class8.Mensuration::new)
        );

        // Class 9 Section
        addClassSection("Class 9", topicButton("Real Numbers", class9.RealNumbers::new));

        // Class 10 Section
        addClassSection("Class 10",
                topicButton("Arithmetic Progression", class10.ArithmeticProgression::new),
                topicButton("Quadratic Equations", () -> new class10.QuadraticEquations()),
                topicButton("Similarity", () -> new class10.Similarity()),
                topicButton("Coordinate Geometry", () -> new class10.CoordinateGeometry()),
                topicButton("Trigonometry", () -> new class10.Trigonometry()),
                topicButton("Probability", () -> new class10.Probability())
        );

        return new Scene(root, 500, 450);
    }

    private void addClassSection(String className, Button... topicButtons) {
        Label header = new Label(className);
        header.setStyle(UiStyle.HEADER);

        VBox section = new VBox(8);
        section.getChildren().add(header);
        section.getChildren().addAll(topicButtons);
        root.getChildren().add(section);
    }

    private Button topicButton(String label, Supplier<Topic> topicSupplier) {
        Button button = new Button(label);
        button.setStyle(UiStyle.BUTTON);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setOnAction(e -> launcher.selectTopic(topicSupplier.get()));
        return button;
    }
}