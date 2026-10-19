package common.gui;

import common.Question;
import common.Test;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Shared question screen used by the Pre-requisite test, Lesson test and Retest.
 * Flow per question: pick an option -> "Check Answer" (shows right/wrong) -> "Next".
 */
public class QuestionTestScreen {
    private static final double MAX_WIDTH = 820;

    private final Test test;
    private final AppLauncher launcher;
    private final String submitButtonLabel;
    private final Consumer<List<Integer>> onSubmit;

    private final List<Integer> answers = new ArrayList<>();
    private int currentIndex = 0;
    private int score = 0;
    private int streak = 0;

    // state of the question currently on screen
    private int selected = -1;
    private boolean checked = false;
    private final List<HBox> optionRows = new ArrayList<>();

    private VBox root;

    public QuestionTestScreen(AppLauncher launcher, Test test, String submitButtonLabel, Consumer<List<Integer>> onSubmit) {
        this.launcher = launcher;
        this.test = test;
        this.submitButtonLabel = submitButtonLabel;
        this.onSubmit = onSubmit;
    }

    public Scene buildScene(String headerPrefix) {
        root = new VBox(18);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30, 40, 30, 40));
        showQuestion(headerPrefix);
        return Theme.pageScene(root);
    }

    private void showQuestion(String headerPrefix) {
        root.getChildren().clear();
        optionRows.clear();
        selected = -1;
        checked = false;

        int total = test.getQuestions().size();
        Question q = test.getQuestions().get(currentIndex);

        // ---- top bar: back button + score ----
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label scoreChip = Theme.chip("Score: " + score + "    |    Streak: " + streak);
        HBox topBar = new HBox(12,
                launcher.createBackButton("Your progress on this test will be lost. Go back to Topics?"),
                spacer, scoreChip);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setMaxWidth(MAX_WIDTH);

        // ---- title + progress ----
        Label header = Theme.title(headerPrefix + " — Question " + (currentIndex + 1) + " of " + total, 26);
        ProgressBar progress = new ProgressBar((double) currentIndex / total);
        progress.setMaxWidth(Double.MAX_VALUE);
        progress.setPrefHeight(12);
        progress.setStyle("-fx-accent: #fbbf24;");
        VBox headerBox = new VBox(10, header, progress);
        headerBox.setMaxWidth(MAX_WIDTH);

        // ---- question card ----
        Label questionLabel = new Label(q.getQuestionText());
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2b0b5e;");

        VBox optionsBox = new VBox(12);
        List<String> options = q.getOptions();
        for (int i = 0; i < options.size(); i++) {
            HBox row = buildOptionRow(i, options.get(i));
            optionRows.add(row);
            optionsBox.getChildren().add(row);
        }

        Label feedback = new Label();
        feedback.setWrapText(true);

        boolean isLast = currentIndex == total - 1;
        Button actionButton = Theme.primaryButton("Check Answer");
        actionButton.setOnAction(e -> handleAction(q, feedback, actionButton, isLast, headerPrefix));

        HBox buttonBar = new HBox(actionButton);
        buttonBar.setAlignment(Pos.CENTER_RIGHT);

        VBox card = new VBox(20, questionLabel, optionsBox, feedback, buttonBar);
        Theme.styleContentCard(card);
        card.setMaxWidth(MAX_WIDTH);

        root.getChildren().addAll(topBar, headerBox, card);
    }

    // ---------------- options ----------------

    private HBox buildOptionRow(int index, String text) {
        Label badge = new Label(String.valueOf((char) ('A' + index)));
        badge.setMinSize(34, 34);
        badge.setAlignment(Pos.CENTER);
        badge.setStyle("-fx-background-color: #7b2fd0; -fx-background-radius: 17; "
                + "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 15px;");

        Label optionText = new Label(text);
        optionText.setWrapText(true);
        optionText.setStyle("-fx-font-size: 17px; -fx-text-fill: #1f2937;");

        HBox row = new HBox(14, badge, optionText);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        row.setStyle(rowStyle("white", "#d9c7f5"));

        row.setOnMouseEntered(e -> {
            if (!checked && selected != optionRows.indexOf(row)) row.setStyle(rowStyle("#faf5ff", "#b794f4"));
        });
        row.setOnMouseExited(e -> {
            if (!checked && selected != optionRows.indexOf(row)) row.setStyle(rowStyle("white", "#d9c7f5"));
        });
        row.setOnMouseClicked(e -> {
            if (checked) return;
            selected = optionRows.indexOf(row);
            for (int i = 0; i < optionRows.size(); i++) {
                optionRows.get(i).setStyle(i == selected
                        ? rowStyle("#f3e8ff", "#7b2fd0")
                        : rowStyle("white", "#d9c7f5"));
            }
        });
        return row;
    }

    private String rowStyle(String background, String border) {
        return "-fx-background-color: " + background + "; -fx-background-radius: 14; "
                + "-fx-border-color: " + border + "; -fx-border-width: 2; -fx-border-radius: 14; -fx-cursor: hand;";
    }

    // ---------------- check / next ----------------

    private void handleAction(Question q, Label feedback, Button actionButton, boolean isLast, String headerPrefix) {
        if (!checked) {
            if (selected == -1) {
                feedback.setStyle(UiStyle.STATUS_WARNING);
                feedback.setText("Please select an answer first.");
                return;
            }

            checked = true;
            answers.add(selected);

            int correctIndex = -1;
            for (int i = 0; i < q.getOptions().size(); i++) {
                if (q.isCorrect(i)) {
                    correctIndex = i;
                    break;
                }
            }

            if (q.isCorrect(selected)) {
                streak++;
                int gained = 10 + (streak > 1 ? (streak - 1) * 2 : 0);
                score += gained;
                optionRows.get(selected).setStyle(rowStyle("#dcfce7", "#16a34a"));
                feedback.setStyle(UiStyle.FEEDBACK_CORRECT);
                feedback.setText("Correct!  +" + gained + " points");
            } else {
                streak = 0;
                optionRows.get(selected).setStyle(rowStyle("#fee2e2", "#dc2626"));
                if (correctIndex >= 0) {
                    optionRows.get(correctIndex).setStyle(rowStyle("#dcfce7", "#16a34a"));
                }
                feedback.setStyle(UiStyle.FEEDBACK_INCORRECT);
                feedback.setText("Not quite. The correct answer is: "
                        + (correctIndex >= 0 ? q.getOptions().get(correctIndex) : "?"));
            }

            actionButton.setText(isLast ? submitButtonLabel : "Next");
        } else {
            if (isLast) {
                onSubmit.accept(answers);
            } else {
                currentIndex++;
                showQuestion(headerPrefix);
            }
        }
    }
}