package common.gui;

import common.LessonContent;
import common.Topic;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class LessonScreen {
    private AppLauncher launcher;
    private Topic topic;
    private List<LessonContent> contentBank;
    private int currentIndex;
    private VBox root;

    public LessonScreen(AppLauncher launcher, Topic topic, List<LessonContent> contentBank) {
        this.launcher = launcher;
        this.topic = topic;
        this.contentBank = contentBank;
        this.currentIndex = 0;
    }

    public Scene buildScene() {
        root = new VBox(12);
        root.setPadding(new Insets(20));
        showConcept();

        javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        return new Scene(scrollPane, 700, 550);
    }

    private void showConcept() {
        root.getChildren().clear();
        LessonContent content = contentBank.get(currentIndex);

        Label header = new Label("Lesson — " + (currentIndex + 1) + " of " + contentBank.size());
        header.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label conceptName = new Label(content.getConcept().getName());
        conceptName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label explanation = new Label(content.getExplanation());
        explanation.setWrapText(true);

        root.getChildren().addAll(header, conceptName, explanation);

        if (content.getFormula() != null) {
            Label formula = new Label("Formula: " + content.getFormula());
            formula.setStyle("-fx-font-style: italic;");
            root.getChildren().add(formula);
        }

        if (content.getWorkedExamples() != null && !content.getWorkedExamples().isEmpty()) {
            Label examplesHeader = new Label("Worked Examples:");
            examplesHeader.setStyle("-fx-font-weight: bold;");
            root.getChildren().add(examplesHeader);

            for (String example : content.getWorkedExamples()) {
                Label exampleLabel = new Label("• " + example);
                exampleLabel.setWrapText(true);
                root.getChildren().add(exampleLabel);
            }
        }

        java.util.Optional<String> visualId = topic.getVisualComponentId(content.getConcept());
        if (visualId.isPresent()) {
            javafx.scene.Node visual = resolveVisual(visualId.get());
            if (visual != null) {
                root.getChildren().add(visual);
            }
        }

        Button previousButton = new Button("Previous");
        previousButton.setDisable(currentIndex == 0);
        previousButton.setOnAction(e -> handlePrevious());

        Button nextButton = new Button(
                currentIndex == contentBank.size() - 1 ? "Start Lesson Test" : "Next"
        );
        nextButton.setOnAction(e -> handleNext());

        javafx.scene.layout.HBox navigationBox = new javafx.scene.layout.HBox(10, previousButton, nextButton);
        root.getChildren().add(navigationBox);
    }

    private void handlePrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            showConcept();
        }
    }

    private void handleNext() {
        if (currentIndex < contentBank.size() - 1) {
            currentIndex++;
            showConcept();
        } else {
            launcher.showLessonTest();
        }
    }

    private javafx.scene.Node resolveVisual(String visualId) {
        switch (visualId) {
            case "ap-common-difference-explorer":
                return new common.gui.widgets.CommonDifferenceExplorer().build();
            default:
                return null;
        }
    }
}