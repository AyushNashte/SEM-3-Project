package common.gui;

import common.LessonContent;
import common.Topic;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class LessonScreen {
    private AppLauncher launcher;
    private Topic topic;
    private List<LessonContent> contentBank;
    private int currentIndex;
    private VBox root;
    private Scene scene;
    private ScrollPane scrollPane;

    // Play Game button state (the topic's own game window, e.g. Exponent Tower)
    private Button playButton;
    private boolean gameRunning = false;

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

        scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        scene = new Scene(scrollPane, 700, 550);
        return scene;
    }

    private void showConcept() {
        root.getChildren().clear();

        if (contentBank == null || contentBank.isEmpty()) {
            root.getChildren().add(new Label("No lesson content available for this topic."));
            Button testButton = new Button("Start Lesson Test");
            testButton.setOnAction(e -> launcher.showLessonTest());
            root.getChildren().add(testButton);
            return;
        }

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

        playButton = null;
        java.util.Optional<String> gameId = topic.getGameId();
        if (gameId.isPresent()) {
            playButton = new Button("🎮 Play Game");
            playButton.setOnAction(e -> openGame(gameId.get()));
            updatePlayButton();
            navigationBox.getChildren().add(playButton);
        }
        root.getChildren().add(navigationBox);

        // Start each concept page from the top
        if (scrollPane != null) {
            scrollPane.setVvalue(0);
        }
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
                return new common.gui.widgets.CommonDifferenceGame().build();
            case "qe-parabola-explorer":
                return new common.gui.widgets.ParabolaGame().build();
            case "similarity-scale-explorer":
                return new common.gui.widgets.SimilarityShapeMatchGame().build();
            case "coordinate-geometry-explorer":
                return new common.gui.widgets.CoordinateGeometryExplorer().build();
            case "trig-ratio-explorer":
                return new common.gui.widgets.TrigRatioExplorer().build();
            case "probability-simulator":
                return new common.gui.widgets.ProbabilitySimulator().build();
            default:
                return null;
        }
    }

    private void openGame(String gameId) {
        // 1) JavaFX arcade games that swap the scene inside this window
        common.gui.games.ArcadeGame game = resolveGame(gameId);
        if (game != null) {
            Scene lessonScene = scene;
            launcher.getStage().setScene(game.buildScene(() -> launcher.getStage().setScene(lessonScene)));
            return;
        }

        // 2) Games that open in their own window (e.g. Class 8 "exponent-tower").
        //    The topic launches it; we re-enable the button when the game window closes.
        if (gameRunning) return; // don't open two game windows

        gameRunning = true;
        updatePlayButton();
        try {
            topic.launchGame(() -> Platform.runLater(() -> {
                gameRunning = false;
                updatePlayButton();
            }));
        } catch (Throwable t) {
            t.printStackTrace();
            gameRunning = false;
            updatePlayButton();
        }
    }

    private void updatePlayButton() {
        if (playButton == null) return;
        playButton.setDisable(gameRunning);
        playButton.setText(gameRunning ? "🎮 Game running..." : "🎮 Play Game");
    }

    private common.gui.games.ArcadeGame resolveGame(String gameId) {
        switch (gameId) {
            case "coordinate-radar":
                return new common.gui.games.CoordinateRadarGame();
            case "root-radar":
                return new common.gui.games.RootRadarGame();
            default:
                return null; // not a scene-swap game; handled by topic.launchGame(...)
        }
    }
}