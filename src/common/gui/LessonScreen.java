package common.gui;

import common.LessonContent;
import common.Topic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.List;
import java.util.Optional;

public class LessonScreen {
    private static final double MAX_WIDTH = 900;

    private final AppLauncher launcher;
    private final Topic topic;
    private final List<LessonContent> contentBank;
    private int currentIndex;
    private VBox root;
    private Scene scene;

    public LessonScreen(AppLauncher launcher, Topic topic, List<LessonContent> contentBank) {
        this.launcher = launcher;
        this.topic = topic;
        this.contentBank = contentBank;
        this.currentIndex = 0;
    }

    public Scene buildScene() {
        root = new VBox(18);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30, 40, 30, 40));
        showConcept();
        scene = Theme.pageScene(root);
        return scene;
    }

    private void showConcept() {
        root.getChildren().clear();
        LessonContent content = contentBank.get(currentIndex);
        int total = contentBank.size();

        // ---- top bar ----
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox topBar = new HBox(12,
                launcher.createBackButton("Your lesson progress will be lost. Go back to Topics?"),
                spacer,
                Theme.chip("Concept " + (currentIndex + 1) + " of " + total));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setMaxWidth(MAX_WIDTH);

        // ---- title + progress ----
        Label title = Theme.title(topic.getReport().getTopicName(), 32);
        Label sub = Theme.subtitle("Lesson");
        ProgressBar progress = new ProgressBar((currentIndex + 1.0) / total);
        progress.setMaxWidth(Double.MAX_VALUE);
        progress.setPrefHeight(12);
        progress.setStyle("-fx-accent: #fbbf24;");
        VBox headerBox = new VBox(6, title, sub, progress);
        headerBox.setMaxWidth(MAX_WIDTH);

        // ---- content card ----
        VBox card = new VBox(18);
        Theme.styleContentCard(card);
        card.setMaxWidth(MAX_WIDTH);

        Label conceptName = new Label(content.getConcept().getName());
        conceptName.setWrapText(true);
        conceptName.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2b0b5e;");

        Label explanation = new Label(content.getExplanation());
        explanation.setWrapText(true);
        explanation.setStyle("-fx-font-size: 17px; -fx-text-fill: #1f2937; -fx-line-spacing: 4;");

        card.getChildren().addAll(conceptName, explanation);

        if (content.getFormula() != null) {
            Label formulaTag = new Label("FORMULA");
            formulaTag.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #b45309;");
            Label formula = new Label(content.getFormula());
            formula.setWrapText(true);
            formula.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-style: italic; -fx-text-fill: #2b0b5e;");
            VBox formulaBox = new VBox(4, formulaTag, formula);
            formulaBox.setPadding(new Insets(14, 18, 14, 18));
            formulaBox.setStyle("-fx-background-color: #fff7d6; -fx-background-radius: 14; "
                    + "-fx-border-color: #fbbf24; -fx-border-width: 0 0 0 5; -fx-border-radius: 14;");
            card.getChildren().add(formulaBox);
        }

        if (content.getWorkedExamples() != null && !content.getWorkedExamples().isEmpty()) {
            Label examplesHeader = new Label("Worked Examples");
            examplesHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2b0b5e;");
            card.getChildren().add(examplesHeader);

            int n = 1;
            for (String example : content.getWorkedExamples()) {
                Label tag = new Label("Example " + n++);
                tag.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #7b2fd0;");
                Label text = new Label(example);
                text.setWrapText(true);
                text.setStyle("-fx-font-size: 16px; -fx-text-fill: #1f2937;");
                VBox exampleBox = new VBox(4, tag, text);
                exampleBox.setPadding(new Insets(12, 16, 12, 16));
                exampleBox.setStyle("-fx-background-color: #f5f0ff; -fx-background-radius: 12;");
                card.getChildren().add(exampleBox);
            }
        }

        // ---- navigation ----
        Button previousButton = Theme.pillButton("← Previous");
        previousButton.setDisable(currentIndex == 0);
        previousButton.setOnAction(e -> handlePrevious());

        Button nextButton = Theme.primaryButton(
                currentIndex == total - 1 ? "Start Lesson Test →" : "Next →");
        nextButton.setOnAction(e -> handleNext());

        Region navSpacer = new Region();
        HBox.setHgrow(navSpacer, Priority.ALWAYS);
        HBox navBar = new HBox(12, previousButton);

        Optional<String> gameId = topic.getGameId();
        if (gameId.isPresent()) {
            Button playButton = Theme.pillButton("Play Game");
            Polygon playIcon = new Polygon(0, 0, 0, 14, 12, 7);
            playIcon.setFill(Color.web("#16a34a"));
            playButton.setGraphic(playIcon);
            playButton.setOnAction(e -> openGame(gameId.get()));
            navBar.getChildren().add(playButton);
        }
        navBar.getChildren().addAll(navSpacer, nextButton);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setMaxWidth(MAX_WIDTH);

        root.getChildren().addAll(topBar, headerBox, card, navBar);
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

    private void openGame(String gameId) {
        common.gui.games.ArcadeGame game = resolveGame(gameId);
        if (game == null) {
            // Not a JavaFX arcade game: Class 8 topics open their own game window
            topic.launchGame(() -> { });
            return;
        }
        Scene lessonScene = scene;
        launcher.getStage().setScene(game.buildScene(() -> launcher.getStage().setScene(lessonScene)));
    }

    private common.gui.games.ArcadeGame resolveGame(String gameId) {
        switch (gameId) {
            case "ap-express":
                return new class10.games.HillClimbGame();
            case "similarity-lab":
                return new class10.games.SimilarityDefenceGame();
            case "coordinate-radar":
                return new class10.games.CoordinateRadarGame();
            case "root-radar":
                return new class10.games.RootRadarGame();
            case "integer-hopper":
                return new class7.games.IntegerHopperGame();
            case "fraction-fisher":
                return new class7.games.FractionFisherGame();
            case "term-catcher":
                return new class7.games.TermCatcherGame();
            case "smoothie-rush":
                return new class7.games.SmoothieRushGame();
            case "upside-down-lights":
                return new class9.games.UpsideDownLightsGame();
            case "borderland-games":
                return new class9.games.BorderlandGamesGame();
            default:
                return null;
        }
    }
}