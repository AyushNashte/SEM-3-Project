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

public class ParabolaExplorer {

    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 300;
    private static final double X_RANGE = 10;
    private static final double Y_RANGE = 10;

    private int a = 1;
    private int b = -3;
    private int c = 2;

    private Pane canvas;
    private Label equationLabel;
    private Label discriminantLabel;
    private Label rootsLabel;

    public Node build() {
        VBox container = new VBox(12);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("Try it: change a, b, c and watch the parabola");
        title.setStyle("-fx-font-weight: bold;");

        equationLabel = new Label();
        equationLabel.setStyle("-fx-font-size: 14px;");

        canvas = new Pane();
        canvas.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setStyle("-fx-border-color: #999999;");

        HBox controls = new HBox(15,
                buildControl("a", () -> a, v -> a = v),
                buildControl("b", () -> b, v -> b = v),
                buildControl("c", () -> c, v -> c = v)
        );
        controls.setAlignment(Pos.CENTER);

        discriminantLabel = new Label();
        rootsLabel = new Label();
        rootsLabel.setWrapText(true);

        redraw();

        container.getChildren().addAll(title, equationLabel, canvas, controls, discriminantLabel, rootsLabel);
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
            int newValue = getter.get() - 1;
            if (label.equals("a") && newValue == 0) newValue = -1; // a can never be 0
            setter.set(newValue);
            valueLabel.setText(label + " = " + newValue);
            redraw();
        });
        increase.setOnAction(e -> {
            int newValue = getter.get() + 1;
            if (label.equals("a") && newValue == 0) newValue = 1; // a can never be 0
            setter.set(newValue);
            valueLabel.setText(label + " = " + newValue);
            redraw();
        });

        return new HBox(6, decrease, valueLabel, increase);
    }

    private void redraw() {
        canvas.getChildren().clear();

        double originX = CANVAS_WIDTH / 2.0;
        double originY = CANVAS_HEIGHT / 2.0;
        double xScale = CANVAS_WIDTH / (2 * X_RANGE);
        double yScale = CANVAS_HEIGHT / (2 * Y_RANGE);

        // Axes
        Line xAxis = new Line(0, originY, CANVAS_WIDTH, originY);
        Line yAxis = new Line(originX, 0, originX, CANVAS_HEIGHT);
        xAxis.setStroke(Color.LIGHTGRAY);
        yAxis.setStroke(Color.LIGHTGRAY);
        canvas.getChildren().addAll(xAxis, yAxis);

        // Curve
        Polyline curve = new Polyline();
        for (double x = -X_RANGE; x <= X_RANGE; x += 0.2) {
            double y = a * x * x + b * x + c;
            double px = originX + x * xScale;
            double py = originY - y * yScale;
            if (py >= -50 && py <= CANVAS_HEIGHT + 50) {
                curve.getPoints().addAll(px, py);
            }
        }
        curve.setStroke(Color.web("#1565c0"));
        curve.setStrokeWidth(2);
        canvas.getChildren().add(curve);

        // Roots (discriminant)
        int discriminant = b * b - 4 * a * c;
        if (discriminant >= 0) {
            double sqrtD = Math.sqrt(discriminant);
            double root1 = (-b + sqrtD) / (2.0 * a);
            double root2 = (-b - sqrtD) / (2.0 * a);

            markRoot(root1, originX, originY, xScale);
            if (discriminant > 0) {
                markRoot(root2, originX, originY, xScale);
            }
        }

        equationLabel.setText(String.format("y = %dx² + %dx + %d", a, b, c));
        discriminantLabel.setText("Discriminant (b² - 4ac) = " + discriminant);

        if (discriminant > 0) {
            double sqrtD = Math.sqrt(discriminant);
            double root1 = (-b + sqrtD) / (2.0 * a);
            double root2 = (-b - sqrtD) / (2.0 * a);
            rootsLabel.setText(String.format("Two real roots: x = %.2f, x = %.2f", root1, root2));
        } else if (discriminant == 0) {
            double root = -b / (2.0 * a);
            rootsLabel.setText(String.format("One repeated real root: x = %.2f", root));
        } else {
            rootsLabel.setText("No real roots (the curve never touches the x-axis)");
        }
    }

    private void markRoot(double rootX, double originX, double originY, double xScale) {
        double px = originX + rootX * xScale;
        if (px >= 0 && px <= CANVAS_WIDTH) {
            Circle dot = new Circle(px, originY, 5, Color.web("#c62828"));
            canvas.getChildren().add(dot);
        }
    }
}