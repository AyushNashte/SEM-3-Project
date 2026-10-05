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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.Random;

public class CommonDifferenceGame {

    private static final int UNIT_PX = 24;
    private static final int MIN_VAL = -5;
    private static final int MAX_VAL = 30;
    private static final int TERMS_SHOWN = 4;

    private final Random random = new Random();

    private int firstTerm;
    private int targetDifference;
    private int guessDifference;
    private int score = 0;
    private int attemptsThisRound = 0;

    private Pane linePane;
    private Label targetLabel;
    private Label guessLabel;
    private Label feedbackLabel;
    private Label scoreLabel;
    private Button checkButton;
    private Button newChallengeButton;

    public Node build() {
        VBox container = new VBox(12);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("🎯 Challenge: match the target sequence!");
        title.setStyle("-fx-font-weight: bold;");

        scoreLabel = new Label();
        scoreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        targetLabel = new Label();
        targetLabel.setStyle("-fx-font-size: 14px;");

        linePane = new Pane();
        double width = (MAX_VAL - MIN_VAL) * UNIT_PX + 40;
        linePane.setPrefSize(width, 80);

        guessLabel = new Label();
        guessLabel.setStyle("-fx-font-weight: bold;");

        Button decrease = new Button("d - 1");
        Button increase = new Button("d + 1");
        decrease.setOnAction(e -> {
            guessDifference--;
            guessLabel.setText("Your guess: d = " + guessDifference);
            redraw();
        });
        increase.setOnAction(e -> {
            guessDifference++;
            guessLabel.setText("Your guess: d = " + guessDifference);
            redraw();
        });

        checkButton = new Button("✅ Check Answer");
        checkButton.setOnAction(e -> checkAnswer());

        newChallengeButton = new Button("🔄 New Challenge");
        newChallengeButton.setOnAction(e -> startNewChallenge());

        HBox controls = new HBox(10, decrease, guessLabel, increase);
        controls.setAlignment(Pos.CENTER);

        HBox actionButtons = new HBox(10, checkButton, newChallengeButton);
        actionButtons.setAlignment(Pos.CENTER);

        feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-weight: bold;");

        startNewChallenge();

        container.getChildren().addAll(title, scoreLabel, targetLabel, linePane, controls, actionButtons, feedbackLabel);
        return container;
    }

    private void startNewChallenge() {
        firstTerm = 1 + random.nextInt(5);
        targetDifference = 2 + random.nextInt(6); // difference between 2 and 7
        guessDifference = 1;
        attemptsThisRound = 0;
        feedbackLabel.setText("");
        guessLabel.setText("Your guess: d = " + guessDifference);

        StringBuilder targetSequence = new StringBuilder();
        int term = firstTerm;
        for (int i = 0; i < TERMS_SHOWN; i++) {
            targetSequence.append(term);
            if (i < TERMS_SHOWN - 1) targetSequence.append(", ");
            term += targetDifference;
        }
        targetLabel.setText("Target sequence: " + targetSequence + ", ...  — find d!");

        redraw();
    }

    private void checkAnswer() {
        attemptsThisRound++;
        if (guessDifference == targetDifference) {
            int points = Math.max(10 - (attemptsThisRound - 1) * 3, 2);
            score += points;
            scoreLabel.setText("⭐ Score: " + score);
            feedbackLabel.setText("✅ Correct! d = " + targetDifference + ". +" + points + " points!");
            feedbackLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        } else {
            feedbackLabel.setText("❌ Not quite — try adjusting d and check again.");
            feedbackLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #c62828;");
        }
    }

    private void redraw() {
        linePane.getChildren().clear();

        double lineY = 40;
        Line baseLine = new Line(20, lineY, linePane.getPrefWidth() - 20, lineY);
        linePane.getChildren().add(baseLine);

        int term = firstTerm;
        for (int i = 0; i < TERMS_SHOWN; i++) {
            double x = valueToX(term);
            if (term >= MIN_VAL && term <= MAX_VAL) {
                Circle dot = new Circle(x, lineY, 6, Color.web("#1565c0"));
                Text label = new Text(x - 8, lineY - 12, String.valueOf(term));
                linePane.getChildren().addAll(dot, label);
            }
            term += guessDifference;
        }
    }

    private double valueToX(int value) {
        return 20 + (value - MIN_VAL) * UNIT_PX;
    }
}