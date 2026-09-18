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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Random;

public class ProbabilitySimulator {

    private final int[] counts = new int[6];
    private int totalRolls = 0;
    private final Random random = new Random();

    private Pane barsPane;
    private Label summaryLabel;

    public Node build() {
        VBox container = new VBox(12);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("Try it: roll a virtual die and watch experimental probability approach 1/6");
        title.setStyle("-fx-font-weight: bold;");

        barsPane = new Pane();
        barsPane.setPrefSize(300, 160);

        Button rollTen = new Button("Roll Die x10");
        Button reset = new Button("Reset");

        rollTen.setOnAction(e -> {
            for (int i = 0; i < 10; i++) {
                int face = random.nextInt(6);
                counts[face]++;
                totalRolls++;
            }
            redraw();
        });

        reset.setOnAction(e -> {
            for (int i = 0; i < 6; i++) counts[i] = 0;
            totalRolls = 0;
            redraw();
        });

        HBox controls = new HBox(10, rollTen, reset);
        controls.setAlignment(Pos.CENTER);

        summaryLabel = new Label();
        summaryLabel.setWrapText(true);

        redraw();

        container.getChildren().addAll(title, barsPane, controls, summaryLabel);
        return container;
    }

    private void redraw() {
        barsPane.getChildren().clear();

        int maxCount = 1;
        for (int c : counts) maxCount = Math.max(maxCount, c);

        double barWidth = 35;
        double gap = 12;
        double maxBarHeight = 110;

        for (int face = 0; face < 6; face++) {
            double barHeight = (counts[face] / (double) maxCount) * maxBarHeight;
            if (barHeight < 2) barHeight = 2;

            double x = 15 + face * (barWidth + gap);
            double y = 140 - barHeight;

            Rectangle bar = new Rectangle(x, y, barWidth, barHeight);
            bar.setFill(Color.web("#1565c0"));

            Text faceLabel = new Text(x + 10, 155, String.valueOf(face + 1));
            Text countLabel = new Text(x + 5, y - 5, String.valueOf(counts[face]));

            barsPane.getChildren().addAll(bar, faceLabel, countLabel);
        }

        if (totalRolls == 0) {
            summaryLabel.setText("No rolls yet. Theoretical probability of any face = 1/6 ≈ 0.17");
        } else {
            double experimentalFace1 = counts[0] / (double) totalRolls;
            summaryLabel.setText(String.format(
                    "Total rolls: %d   |   Experimental P(rolling a 1) = %d/%d ≈ %.2f   |   Theoretical = 1/6 ≈ 0.17",
                    totalRolls, counts[0], totalRolls, experimentalFace1
            ));
        }
    }
}