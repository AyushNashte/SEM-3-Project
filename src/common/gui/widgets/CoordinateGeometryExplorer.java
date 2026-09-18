package common.gui.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class CoordinateGeometryExplorer {

    private static final int UNIT_PX = 20;
    private static final int RANGE = 7;
    private static final int CANVAS_SIZE = (2 * RANGE) * UNIT_PX;

    private final int ax = 1, ay = 1; // Point A fixed
    private int bx = 5, by = 4;       // Point B movable

    private Pane canvas;
    private Label infoLabel;

    public Node build() {
        VBox container = new VBox(12);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("Try it: move Point B and watch distance & midpoint update");
        title.setStyle("-fx-font-weight: bold;");

        canvas = new Pane();
        canvas.setPrefSize(CANVAS_SIZE, CANVAS_SIZE);

        GridPane controls = new GridPane();
        controls.setHgap(8);
        controls.setVgap(6);
        controls.setAlignment(Pos.CENTER);

        Button bxMinus = new Button("B: x -1");
        Button bxPlus = new Button("B: x +1");
        Button byMinus = new Button("B: y -1");
        Button byPlus = new Button("B: y +1");

        bxMinus.setOnAction(e -> { bx--; redraw(); });
        bxPlus.setOnAction(e -> { bx++; redraw(); });
        byMinus.setOnAction(e -> { by--; redraw(); });
        byPlus.setOnAction(e -> { by++; redraw(); });

        controls.add(bxMinus, 0, 0);
        controls.add(bxPlus, 1, 0);
        controls.add(byMinus, 0, 1);
        controls.add(byPlus, 1, 1);

        infoLabel = new Label();
        infoLabel.setWrapText(true);

        redraw();

        container.getChildren().addAll(title, canvas, controls, infoLabel);
        return container;
    }

    private void redraw() {
        canvas.getChildren().clear();

        double originX = CANVAS_SIZE / 2.0;
        double originY = CANVAS_SIZE / 2.0;

        Line xAxis = new Line(0, originY, CANVAS_SIZE, originY);
        Line yAxis = new Line(originX, 0, originX, CANVAS_SIZE);
        xAxis.setStroke(Color.LIGHTGRAY);
        yAxis.setStroke(Color.LIGHTGRAY);
        canvas.getChildren().addAll(xAxis, yAxis);

        double axPx = originX + ax * UNIT_PX;
        double ayPx = originY - ay * UNIT_PX;
        double bxPx = originX + bx * UNIT_PX;
        double byPx = originY - by * UNIT_PX;

        Line segment = new Line(axPx, ayPx, bxPx, byPx);
        segment.setStroke(Color.web("#616161"));
        canvas.getChildren().add(segment);

        Circle pointA = new Circle(axPx, ayPx, 6, Color.web("#1565c0"));
        Circle pointB = new Circle(bxPx, byPx, 6, Color.web("#c62828"));
        Text labelA = new Text(axPx + 8, ayPx - 8, "A(" + ax + "," + ay + ")");
        Text labelB = new Text(bxPx + 8, byPx - 8, "B(" + bx + "," + by + ")");

        double mx = (ax + bx) / 2.0;
        double my = (ay + by) / 2.0;
        double midPx = originX + mx * UNIT_PX;
        double midPy = originY - my * UNIT_PX;
        Circle midpoint = new Circle(midPx, midPy, 4, Color.web("#2e7d32"));

        canvas.getChildren().addAll(pointA, pointB, labelA, labelB, midpoint);

        double distance = Math.sqrt(Math.pow(bx - ax, 2) + Math.pow(by - ay, 2));
        infoLabel.setText(String.format(
                "Distance AB = √((%d-%d)² + (%d-%d)²) = %.2f    |    Midpoint = (%.1f, %.1f)",
                bx, ax, by, ay, distance, mx, my
        ));
    }
}