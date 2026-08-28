package class7;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Integers extends Application {

    private static final int[][] ROUNDS = {
            {-3, 5, 1},
            {4, 9, 0},
            {-6, -4, 0},
            {2, -7, 1},
            {-8, 6, 1}
    };

    private static final int UNIT_PX = 28;
    private static final int MIN_VAL = -12;
    private static final int MAX_VAL = 12;

    @Override
    public void start(Stage primaryStage) {
        MainApp app = new MainApp();
        Scene scene = create(app);
        primaryStage.setTitle("Number Line Jump");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Scene create(MainApp app) {
        VBox root = new VBox(16);
        root.setPadding(new Insets(24));
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("root-pane");

        Label title = new Label("Step 3: Number Line Jump!");
        title.getStyleClass().add("title-label");

        Label roundLabel = new Label();
        roundLabel.getStyleClass().add("subtitle-label");

        Label equationLabel = new Label();
        equationLabel.getStyleClass().add("question-label");

        Label feedbackLabel = new Label();
        feedbackLabel.getStyleClass().add("feedback-label");

        int lineWidthUnits = MAX_VAL - MIN_VAL;
        double laneWidth = lineWidthUnits * UNIT_PX + 40;
        Pane linePane = new Pane();
        linePane.setPrefSize(laneWidth, 90);

        Line line = new Line(20, 55, laneWidth - 20, 55);
        line.setStrokeWidth(3);
        linePane.getChildren().add(line);

        for (int v = MIN_VAL; v <= MAX_VAL; v++) {
            double x = valueToX(v);
            Line tick = new Line(x, 45, x, 65);
            linePane.getChildren().add(tick);
            if (v % 2 == 0) {
                Text label = new Text(x - 6, 80, String.valueOf(v));
                linePane.getChildren().add(label);
            }
        }

        Circle frog = new Circle(10, Color.web("#2e7d32"));
        frog.setStroke(Color.web("#1b5e20"));
        frog.setStrokeWidth(2);
        linePane.getChildren().add(frog);

        Circle targetMarker = new Circle(6, Color.web("#c62828"));
        targetMarker.setVisible(false);
        linePane.getChildren().add(targetMarker);

        Button jumpRight = new Button("Jump Right ▶ (+1)");
        Button jumpLeft = new Button("◀ Jump Left (-1)");
        jumpRight.getStyleClass().add("secondary-button");
        jumpLeft.getStyleClass().add("secondary-button");
        HBox jumpControls = new HBox(15, jumpLeft, jumpRight);
        jumpControls.setAlignment(Pos.CENTER);

        Button nextBtn = new Button("Next Round ▶");
        nextBtn.getStyleClass().add("primary-button");
        nextBtn.setDisable(true);

        int[] roundIndex = {0};
        double[] frogValue = {0};
        int[] target = {0};

        Runnable[] loadRound = new Runnable[1];

        loadRound[0] = () -> {
            feedbackLabel.setText("");
            nextBtn.setDisable(true);
            targetMarker.setVisible(false);

            int[] r = ROUNDS[roundIndex[0]];
            int start = r[0];
            int operand = r[1];
            boolean isPlus = r[2] == 1;

            int effectiveDelta = isPlus ? operand : -operand;
            target[0] = start + effectiveDelta;
            frogValue[0] = start;

            roundLabel.setText("Round " + (roundIndex[0] + 1) + " of " + ROUNDS.length);
            String opSymbol = isPlus ? "+" : "-";
            String operandDisplay = operand < 0 ? "(" + operand + ")" : String.valueOf(operand);
            equationLabel.setText(start + " " + opSymbol + " " + operandDisplay + " = ?  "
                    + "(Move the frog to find out!)");

            double x = valueToX(start);
            frog.setCenterX(x);
            frog.setCenterY(55);
            frog.setTranslateX(0);
        };

        Runnable checkAnswer = () -> {
            if ((int) frogValue[0] == target[0]) {
                boolean isPlus = ROUNDS[roundIndex[0]][2] == 1;
                int start = ROUNDS[roundIndex[0]][0];
                int operand = ROUNDS[roundIndex[0]][1];
                String opSymbol = isPlus ? "+" : "-";
                String operandDisplay = operand < 0 ? "(" + operand + ")" : String.valueOf(operand);
                feedbackLabel.setText("Correct! " + start + " " + opSymbol + " " + operandDisplay
                        + " = " + target[0] + " 🎉");
                feedbackLabel.setTextFill(Color.web("#2e7d32"));
                nextBtn.setDisable(false);
                jumpRight.setDisable(true);
                jumpLeft.setDisable(true);

                double tx = valueToX(target[0]);
                targetMarker.setCenterX(tx);
                targetMarker.setCenterY(55);
                targetMarker.setVisible(true);
            }
        };

        jumpRight.setOnAction(e -> {
            if (frogValue[0] >= MAX_VAL) return;
            frogValue[0]++;
            double x = valueToX((int) frogValue[0]);
            frog.setCenterX(x);
            checkAnswer.run();
        });

        jumpLeft.setOnAction(e -> {
            if (frogValue[0] <= MIN_VAL) return;
            frogValue[0]--;
            double x = valueToX((int) frogValue[0]);
            frog.setCenterX(x);
            checkAnswer.run();
        });

        nextBtn.setOnAction(e -> {
            jumpRight.setDisable(false);
            jumpLeft.setDisable(false);
            roundIndex[0]++;
            if (roundIndex[0] < ROUNDS.length) {
                loadRound[0].run();
            } else {
                app.onTeachingComplete(root);
            }
        });

        loadRound[0].run();

        root.getChildren().addAll(title, roundLabel, equationLabel, linePane,
                jumpControls, feedbackLabel, nextBtn);
        return new Scene(root, 750, 560);
    }

    private static double valueToX(int value) {
        return 20 + (value - MIN_VAL) * UNIT_PX;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class MainApp {
        public void onTeachingComplete(VBox root) {
            root.getChildren().clear();
            Label endLabel = new Label("🎉 Great Job! You completed the Number Line Jump lesson!");
            endLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
            root.setAlignment(Pos.CENTER);
            root.getChildren().add(endLabel);
        }
    }
}