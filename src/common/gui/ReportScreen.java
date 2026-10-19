package common.gui;

import common.Concept;
import common.StudentReport;
import common.TopicStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReportScreen {
    private static final double MAX_WIDTH = 760;

    private final AppLauncher launcher;
    private final StudentReport report;

    public ReportScreen(AppLauncher launcher, StudentReport report) {
        this.launcher = launcher;
        this.report = report;
    }

    public Scene buildScene() {
        Label title = Theme.title("Student Report", 36);
        Label topic = Theme.subtitle(report.getTopicName());

        // ---- status badge ----
        Label status = new Label(formatStatus(report.getStatus()));
        status.setStyle("-fx-background-color: " + statusColor(report.getStatus()) + "; -fx-background-radius: 20; "
                + "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px; -fx-padding: 8 22 8 22;");
        Label message = new Label(statusMessage(report.getStatus()));
        message.setWrapText(true);
        message.setStyle("-fx-font-size: 16px; -fx-text-fill: #4b5563;");

        // ---- scores ----
        VBox scores = new VBox(14);
        scores.getChildren().add(scoreRow("Pre-requisite Test", report.getPrerequisiteScore()));
        // attempts counts the lesson test + every retest
        if (report.getAttempts() >= 1) {
            scores.getChildren().add(scoreRow("Lesson Test", report.getLessonTestScore()));
        }
        if (report.getAttempts() >= 2) {
            scores.getChildren().add(scoreRow("Retest", report.getRetestScore()));
        }

        Label attempts = new Label("Attempts: " + report.getAttempts());
        attempts.setStyle("-fx-font-size: 15px; -fx-text-fill: #4b5563;");

        // ---- weak concepts ----
        Label weakHeader = new Label("Weak Concepts");
        weakHeader.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2b0b5e;");
        FlowPane weakBox = new FlowPane(10, 10);
        if (report.getWeakConcepts() == null || report.getWeakConcepts().isEmpty()) {
            Label none = new Label("None — great job!");
            none.setStyle("-fx-font-size: 15px; -fx-text-fill: #16a34a; -fx-font-weight: bold;");
            weakBox.getChildren().add(none);
        } else {
            for (Concept c : report.getWeakConcepts()) {
                Label chip = new Label(c.getName());
                chip.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-background-radius: 16; "
                        + "-fx-padding: 6 14 6 14; -fx-font-weight: bold; -fx-font-size: 14px;");
                weakBox.getChildren().add(chip);
            }
        }

        VBox card = new VBox(20, status, message, scores, attempts, weakHeader, weakBox);
        Theme.styleContentCard(card);
        card.setMaxWidth(MAX_WIDTH);

        // ---- buttons ----
        Button topicsButton = Theme.primaryButton("Back to Topics");
        topicsButton.setOnAction(e -> launcher.showTopics());
        Button doneButton = Theme.pillButton("Close");
        doneButton.setOnAction(e -> launcher.getStage().close());
        HBox buttons = new HBox(14, topicsButton, doneButton);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(18, title, topic, card, buttons);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40));
        return Theme.pageScene(root);
    }

    private HBox scoreRow(String label, double percent) {
        Label name = new Label(label);
        name.setMinWidth(170);
        name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1f2937;");

        ProgressBar bar = new ProgressBar(Math.max(0, Math.min(percent, 100)) / 100.0);
        bar.setPrefWidth(380);
        bar.setPrefHeight(16);
        bar.setStyle("-fx-accent: " + (percent >= 75 ? "#16a34a" : "#f59e0b") + ";");

        Label value = new Label(String.format("%.0f%%", percent));
        value.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2b0b5e;");

        HBox row = new HBox(16, name, bar, value);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private String statusColor(TopicStatus status) {
        if (status == null) return "#6b7280";
        switch (status) {
            case ALREADY_MASTERED:
            case COMPLETED:
                return "#16a34a";
            case RETEST_REQUIRED:
                return "#e65100";
            default:
                return "#7b2fd0";
        }
    }

    private String statusMessage(TopicStatus status) {
        if (status == null) return "";
        switch (status) {
            case ALREADY_MASTERED: return "You already know this topic — no lesson needed!";
            case COMPLETED: return "Well done! You've completed this topic.";
            case RETEST_REQUIRED: return "A little more practice on the weak concepts below will get you there.";
            default: return "Keep going!";
        }
    }

    private String formatStatus(TopicStatus status) {
        if (status == null) return "N/A";

        switch (status) {
            case ALREADY_MASTERED: return "Already Mastered";
            case COMPLETED: return "Completed";
            case RETEST_REQUIRED: return "Retest Required";
            case IN_PROGRESS: return "In Progress";
            default: return status.toString();
        }
    }
}