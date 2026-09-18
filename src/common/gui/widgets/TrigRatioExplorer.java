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
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class TrigRatioExplorer {

    private static final double HYPOTENUSE_PX = 140;
    private int angleDegrees = 30;

    private Pane canvas;
    private Label ratiosLabel;

    public Node build() {
        VBox container = new VBox(12);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("Try it: change the angle θ and watch the ratios");
        title.setStyle("-fx-font-weight: bold;");

        canvas = new Pane();
        canvas.setPrefSize(220, 180);

        Label angleLabel = new Label();
        angleLabel.setStyle("-fx-font-weight: bold;");

        Button decrease = new Button("θ - 15°");
        Button increase = new Button("θ + 15°");

        decrease.setOnAction(e -> {
            if (angleDegrees > 0) angleDegrees -= 15;
            angleLabel.setText("θ = " + angleDegrees + "°");
            redraw();
        });
        increase.setOnAction(e -> {
            if (angleDegrees < 90) angleDegrees += 15;
            angleLabel.setText("θ = " + angleDegrees + "°");
            redraw();
        });

        angleLabel.setText("θ = " + angleDegrees + "°");

        HBox controls = new HBox(10, decrease, angleLabel, increase);
        controls.setAlignment(Pos.CENTER);

        ratiosLabel = new Label();
        ratiosLabel.setWrapText(true);

        redraw();

        container.getChildren().addAll(title, canvas, controls, ratiosLabel);
        return container;
    }

    private void redraw() {
        canvas.getChildren().clear();

        double radians = Math.toRadians(angleDegrees);
        double adjacent = HYPOTENUSE_PX * Math.cos(radians);
        double opposite = HYPOTENUSE_PX * Math.sin(radians);

        double baseX = 20, baseY = 160;

        Polygon triangle = new Polygon(
                baseX, baseY,
                baseX + adjacent, baseY,
                baseX, baseY - opposite
        );
        triangle.setFill(Color.web("#1565c0", 0.3));
        triangle.setStroke(Color.web("#1565c0"));
        triangle.setStrokeWidth(2);

        canvas.getChildren().add(triangle);

        double sinValue = Math.sin(radians);
        double cosValue = Math.cos(radians);
        String tanText = angleDegrees == 90 ? "undefined" : String.format("%.2f", Math.tan(radians));

        ratiosLabel.setText(String.format(
                "sin(%d°) = %.2f     cos(%d°) = %.2f     tan(%d°) = %s",
                angleDegrees, sinValue, angleDegrees, cosValue, angleDegrees, tanText
        ));
    }
}