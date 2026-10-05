package common.gui.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimilarityShapeMatchGame {

    private static final double PX_PER_UNIT = 8;

    private final Random random = new Random();

    private int refW, refH;
    private List<int[]> candidates; // each: {width, height}
    private int correctIndex;
    private int score = 0;
    private boolean roundOver;

    private Pane referencePane;
    private HBox candidatesBox;
    private Label feedbackLabel;
    private Label scoreLabel;

    public Node build() {
        VBox container = new VBox(12);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("🔍 Challenge: click the triangle SIMILAR to the reference!");
        title.setStyle("-fx-font-weight: bold;");

        scoreLabel = new Label();
        scoreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        Label refTitle = new Label("Reference:");
        referencePane = new Pane();
        referencePane.setPrefSize(120, 100);

        candidatesBox = new HBox(15);
        candidatesBox.setAlignment(Pos.CENTER);

        feedbackLabel = new Label();
        feedbackLabel.setWrapText(true);
        feedbackLabel.setStyle("-fx-font-weight: bold;");

        Button newRoundButton = new Button("🔄 New Round");
        newRoundButton.setOnAction(e -> startNewRound());

        startNewRound();

        container.getChildren().addAll(title, scoreLabel, refTitle, referencePane, candidatesBox, feedbackLabel, newRoundButton);
        return container;
    }

    private void startNewRound() {
        refW = 3 + random.nextInt(3); // 3-5
        refH = 4 + random.nextInt(3); // 4-6
        int k = 2 + random.nextInt(2); // scale factor 2 or 3

        candidates = new ArrayList<>();
        candidates.add(new int[]{refW * k, refH * k});          // correct: proportional
        candidates.add(new int[]{refH * k, refW * k});          // swapped ratio
        candidates.add(new int[]{refW * k + 3, refH * k});      // broken proportion
        candidates.add(new int[]{5 + random.nextInt(4), 3 + random.nextInt(4)}); // unrelated

        Collections.shuffle(candidates);
        correctIndex = 0;
        for (int i = 0; i < candidates.size(); i++) {
            if (candidates.get(i)[0] == refW * k && candidates.get(i)[1] == refH * k) {
                correctIndex = i;
                break;
            }
        }

        roundOver = false;
        feedbackLabel.setText("");
        drawReference();
        drawCandidates();
    }

    private void drawReference() {
        referencePane.getChildren().clear();
        double w = refW * PX_PER_UNIT;
        double h = refH * PX_PER_UNIT;

        Polygon triangle = new Polygon(10, 90, 10 + w, 90, 10, 90 - h);
        triangle.setFill(Color.web("#1565c0", 0.4));
        triangle.setStroke(Color.web("#1565c0"));
        triangle.setStrokeWidth(2);

        Text label = new Text(10, 100, refW + " x " + refH);

        referencePane.getChildren().addAll(triangle, label);
    }

    private void drawCandidates() {
        candidatesBox.getChildren().clear();
        String[] letters = {"A", "B", "C", "D"};

        for (int i = 0; i < candidates.size(); i++) {
            int[] dims = candidates.get(i);
            final int index = i;

            VBox card = new VBox(4);
            card.setAlignment(Pos.CENTER);

            Pane shapePane = new Pane();
            shapePane.setPrefSize(90, 90);

            double w = Math.min(dims[0] * PX_PER_UNIT, 75);
            double h = Math.min(dims[1] * PX_PER_UNIT, 75);

            Polygon triangle = new Polygon(5, 80, 5 + w, 80, 5, 80 - h);
            triangle.setFill(Color.web("#c62828", 0.35));
            triangle.setStroke(Color.web("#c62828"));
            triangle.setStrokeWidth(2);
            triangle.setOnMouseClicked(e -> handleGuess(index));
            triangle.setStyle("-fx-cursor: hand;");

            shapePane.getChildren().add(triangle);

            Label letterLabel = new Label(letters[i] + ": " + dims[0] + " x " + dims[1]);

            card.getChildren().addAll(shapePane, letterLabel);
            candidatesBox.getChildren().add(card);
        }
    }

    private void handleGuess(int index) {
        if (roundOver) return;
        roundOver = true;

        if (index == correctIndex) {
            score += 10;
            scoreLabel.setText("⭐ Score: " + score);
            int[] correct = candidates.get(correctIndex);
            feedbackLabel.setText("✅ Correct! " + correct[0] + ":" + correct[1]
                    + " is proportional to the reference " + refW + ":" + refH + ". +10 points!");
            feedbackLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        } else {
            int[] correct = candidates.get(correctIndex);
            feedbackLabel.setText("❌ Not quite. The similar one had sides " + correct[0] + ":" + correct[1]
                    + ", proportional to the reference " + refW + ":" + refH + ".");
            feedbackLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #c62828;");
        }
    }
}