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
import javafx.scene.shape.Polyline;

import java.util.Random;

public class ParabolaGame {

    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 300;
    private static final double X_RANGE = 10;
    private static final double Y_RANGE = 10;
    private static final int A = 1; // fixed for this game

    private final Random random = new Random();

    private int root1, root2;
    private int b = 0;
    private int c = 0;
    private int score = 0;
    private int attemptsThisRound = 0;

    private Pane canvas;
    private Label targetLabel;
    private Label equationLabel;
    private Label scoreLabel;
    private Label feedbackLabel;

    public Node build() {
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("🎯 Challenge: shape the parabola to hit the target roots!");
        title.setStyle("-fx-font-weight: bold;");

        scoreLabel = new Label();
        scoreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32;");

        targetLabel = new Label();
        targetLabel.setStyle("-fx-font-size: 14px;");

        equationLabel = new Label();
        equationLabel.setStyle("-fx-font-size: 13px;");

        canvas = new Pane();
        canvas.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setStyle("-fx-border-color: #999999;");

        HBox controls = new HBox(15,
                buildControl("b", () -> b, v -> b = v),
                buildControl("c", () -> c, v -> c = v)
        );
        controls.setAlignment(Pos.CENTER);

        Button checkButton = new Button("✅ Check Answer");
        checkButton.setOnAction(e -> checkAnswer());

        Button newChallengeButton = new Button("🔄 New Challenge");
        newChallengeButton.setOnAction(e -> startNewChallenge());

        HBox actionButtons = new HBox(10, checkButton, newChallengeButton);
        actionButtons.setAlignment(Pos.CENTER);

        feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-weight: bold;");
        feedbackLabel.setWrapText(true);

        startNewChallenge();

        container.getChildren().addAll(title, scoreLabel, targetLabel, canvas, controls, equationLabel, actionButtons, feedbackLabel);
        return container;
    }

    private interface IntGetter { int get(); }
    private interface IntSetter { void set(int value); }

    private HBox buildControl(String label, IntGetter getter, IntSetter setter) {
        Label valueLabel = new Label(label + " = " + getter.get());
        valueLabel.setStyle("-fx-font-weight: bold;");

        Button decrease = new Button(label + " - 1");
        Button increase = new Button(label + " + 1");

        decrease.setOnAction(e -> {
            setter.set(getter.get() - 1);
            valueLabel.setText(label + " = " + getter.get());
            redraw();
        });
        increase.setOnAction(e -> {
            setter.set(getter.get() + 1);
            valueLabel.setText(label + " = " + getter.get());
            redraw();
        });

        return new HBox(6, decrease, valueLabel, increase);
    }

    private void startNewChallenge() {
        int r1 = -5 + random.nextInt(6); // -5 to 0
        int r2 = 1 + random.nextInt(5);  // 1 to 5
        root1 = Math.min(r1, r2);
        root2 = Math.max(r1, r2);

        b = 0;
        c = 0;
        attemptsThisRound = 0;
        feedbackLabel.setText("");

        targetLabel.setText("Target: make the parabola cross the x-axis at x = " + root1 + " and x = " + root2);
        redraw();
    }

    private void checkAnswer() {
        attemptsThisRound++;
        int discriminant = b * b - 4 * A * c;

        if (discriminant >= 0) {
            double sqrtD = Math.sqrt(discriminant);
            double studentRoot1 = (-b - sqrtD) / (2.0 * A);
            double studentRoot2 = (-b + sqrtD) / (2.0 * A);

            boolean matches = Math.abs(studentRoot1 - root1) < 0.01 && Math.abs(studentRoot2 - root2) < 0.01;

            if (matches) {
                int points = Math.max(10 - (attemptsThisRound - 1) * 3, 2);
                score += points;
                scoreLabel.setText("⭐ Score: " + score);
                feedbackLabel.setText("✅ Correct! b=" + b + ", c=" + c + " gives exactly those roots. +" + points + " points!");
                feedbackLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2e7d32;");
                return;
            }
        }

        feedbackLabel.setText("❌ Not quite yet — adjust b and c and check again.");
        feedbackLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #c62828;");
    }

    private void redraw() {
        canvas.getChildren().clear();

        double originX = CANVAS_WIDTH / 2.0;
        double originY = CANVAS_HEIGHT / 2.0;
        double xScale = CANVAS_WIDTH / (2 * X_RANGE);
        double yScale = CANVAS_HEIGHT / (2 * Y_RANGE);

        Line xAxis = new Line(0, originY, CANVAS_WIDTH, originY);
        Line yAxis = new Line(originX, 0, originX, CANVAS_HEIGHT);
        xAxis.setStroke(Color.LIGHTGRAY);
        yAxis.setStroke(Color.LIGHTGRAY);
        canvas.getChildren().addAll(xAxis, yAxis);

        Polyline curve = new Polyline();
        for (double x = -X_RANGE; x <= X_RANGE; x += 0.2) {
            double y = A * x * x + b * x + c;
            double px = originX + x * xScale;
            double py = originY - y * yScale;
            if (py >= -50 && py <= CANVAS_HEIGHT + 50) {
                curve.getPoints().addAll(px, py);
            }
        }
        curve.setStroke(Color.web("#1565c0"));
        curve.setStrokeWidth(2);
        canvas.getChildren().add(curve);

        // Mark target roots on the x-axis as hollow circles (the "goal")
        markTargetRoot(root1, originX, originY, xScale);
        markTargetRoot(root2, originX, originY, xScale);

        equationLabel.setText(String.format("Your equation: y = x² + %dx + %d", b, c));
    }

    private void markTargetRoot(int rootX, double originX, double originY, double xScale) {
        double px = originX + rootX * xScale;
        if (px >= 0 && px <= CANVAS_WIDTH) {
            Circle target = new Circle(px, originY, 7, Color.TRANSPARENT);
            target.setStroke(Color.web("#c62828"));
            target.setStrokeWidth(2);
            canvas.getChildren().add(target);
        }
    }
}