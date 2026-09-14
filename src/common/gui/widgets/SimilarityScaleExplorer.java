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

public class SimilarityScaleExplorer {

    private static final double BASE_WIDTH = 60;
    private static final double BASE_HEIGHT = 45;
    private static final double BASE_SIDE_LABEL = 4.0; // the "real" length represented by BASE_WIDTH, in cm

    private double scaleFactor = 1.5;

    private Pane canvas;
    private Label sideLabel;
    private Label areaRatioLabel;

    public Node build() {
        VBox container = new VBox(12);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("Try it: change the scale factor and watch both triangle and area ratio");
        title.setStyle("-fx-font-weight: bold;");

        canvas = new Pane();
        canvas.setPrefSize(320, 160);

        Label scaleLabel = new Label();
        scaleLabel.setStyle("-fx-font-weight: bold;");
        scaleLabel.setText("Scale factor (k) = " + formatScale());

        Button decrease = new Button("k - 0.5");
        Button increase = new Button("k + 0.5");

        decrease.setOnAction(e -> {
            if (scaleFactor > 0.5) {
                scaleFactor -= 0.5;
                scaleLabel.setText("Scale factor (k) = " + formatScale());
                redraw();
            }
        });
        increase.setOnAction(e -> {
            scaleFactor += 0.5;
            scaleLabel.setText("Scale factor (k) = " + formatScale());
            redraw();
        });

        HBox controls = new HBox(10, decrease, scaleLabel, increase);
        controls.setAlignment(Pos.CENTER);

        sideLabel = new Label();
        areaRatioLabel = new Label();
        areaRatioLabel.setStyle("-fx-font-weight: bold;");

        redraw();

        container.getChildren().addAll(title, canvas, controls, sideLabel, areaRatioLabel);
        return container;
    }

    private void redraw() {
        canvas.getChildren().clear();

        // Base (reference) triangle, drawn on the left
        double baseX = 20;
        double baseY = 120;
        Polygon baseTriangle = new Polygon(
                baseX, baseY,
                baseX + BASE_WIDTH, baseY,
                baseX, baseY - BASE_HEIGHT
        );
        baseTriangle.setFill(Color.web("#1565c0", 0.5));
        baseTriangle.setStroke(Color.web("#1565c0"));
        baseTriangle.setStrokeWidth(2);

        Text baseLabel = new Text(baseX + 5, baseY + 20, "Original");

        // Scaled triangle, drawn on the right, scaled from the same corner
        double scaledX = 170;
        double scaledY = 120;
        double scaledWidth = BASE_WIDTH * scaleFactor;
        double scaledHeight = BASE_HEIGHT * scaleFactor;

        Polygon scaledTriangle = new Polygon(
                scaledX, scaledY,
                scaledX + scaledWidth, scaledY,
                scaledX, scaledY - scaledHeight
        );
        scaledTriangle.setFill(Color.web("#c62828", 0.4));
        scaledTriangle.setStroke(Color.web("#c62828"));
        scaledTriangle.setStrokeWidth(2);

        Text scaledLabel = new Text(scaledX + 5, scaledY + 20, "Scaled");

        canvas.getChildren().addAll(baseTriangle, baseLabel, scaledTriangle, scaledLabel);

        double scaledSide = BASE_SIDE_LABEL * scaleFactor;
        double areaRatioValue = scaleFactor * scaleFactor;

        sideLabel.setText(String.format(
                "If the original side is %.1f cm, the scaled side is %.1f x %.1f = %.1f cm",
                BASE_SIDE_LABEL, BASE_SIDE_LABEL, scaleFactor, scaledSide
        ));
        areaRatioLabel.setText(String.format(
                "Area ratio = %.1f² : 1 = %.2f : 1",
                scaleFactor, areaRatioValue
        ));
    }

    private String formatScale() {
        return String.format("%.1f", scaleFactor);
    }
}