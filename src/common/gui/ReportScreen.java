package common.gui;

import common.StudentReport;
import common.TopicStatus;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ReportScreen {
    private AppLauncher launcher;
    private StudentReport report;

    public ReportScreen(AppLauncher launcher, StudentReport report) {
        this.launcher = launcher;
        this.report = report;
    }

    public Scene buildScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Student Report — " + report.getTopicName());
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label status = new Label("Status: " + formatStatus(report.getStatus()));
        status.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label prereq = new Label(String.format("Pre-requisite Score: %.1f%%", report.getPrerequisiteScore()));
        Label lesson = new Label(String.format("Lesson Test Score: %.1f%%", report.getLessonTestScore()));
        Label retest = new Label(String.format("Retest Score: %.1f%%", report.getRetestScore()));
        Label attempts = new Label("Attempts: " + report.getAttempts());

        Label weakLabel = new Label("Weak Concepts: " + formatWeakConcepts(report));
        weakLabel.setWrapText(true);

        Button doneButton = new Button("Close");
        doneButton.setOnAction(e -> launcher.getStage().close());

        root.getChildren().addAll(title, status, prereq, lesson, retest, attempts, weakLabel, doneButton);
        return new Scene(root, 500, 380);
    }

    private String formatStatus(TopicStatus status) {
        switch (status) {
            case ALREADY_MASTERED: return "Already Mastered";
            case COMPLETED: return "Completed";
            case RETEST_REQUIRED: return "Retest Required";
            case IN_PROGRESS: return "In Progress";
            default: return status.toString();
        }
    }

    private String formatWeakConcepts(StudentReport report) {
        if (report.getWeakConcepts() == null || report.getWeakConcepts().isEmpty()) {
            return "None";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < report.getWeakConcepts().size(); i++) {
            sb.append(report.getWeakConcepts().get(i).getName());
            if (i < report.getWeakConcepts().size() - 1) sb.append(", ");
        }
        return sb.toString();
    }
}