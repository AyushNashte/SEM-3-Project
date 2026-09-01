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

public class CommonDifferenceExplorer {

    private static final int UNIT_PX = 24;
    private static final int MIN_VAL = -10;
    private static final int MAX_VAL = 30;
    private static final int FIRST_TERM = 2;
    private static final int TERMS_SHOWN = 5;

    private int commonDifference = 3;
    private Pane linePane;
    private Label sequenceLabel;

    public Node build() {
        VBox container = new VBox(12);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));

        Label title = new Label("Try it: change the common difference (d) and watch the AP");
        title.setStyle("-fx-font-weight: bold;");

        sequenceLabel = new Label();
        sequenceLabel.setStyle("-fx-font-size: 14px;");

        linePane = new Pane();
        double width = (MAX_VAL - MIN_VAL) * UNIT_PX + 40;
        linePane.setPrefSize(width, 80);

        Label dLabel = new Label("d = " + commonDifference);
        dLabel.setStyle("-fx-font-weight: bold;");

        Button decreaseD = new Button("d - 1");
        Button increaseD = new Button("d + 1");
        decreaseD.setOnAction(e -> {
            commonDifference--;
            dLabel.setText("d = " + commonDifference);
            redraw();
        });
        increaseD.setOnAction(e -> {
            commonDifference++;
            dLabel.setText("d = " + commonDifference);
            redraw();
        });

        HBox controls = new HBox(10, decreaseD, dLabel, increaseD);
        controls.setAlignment(Pos.CENTER);

        redraw();

        container.getChildren().addAll(title, linePane, controls, sequenceLabel);
        return container;
    }

    private void redraw() {
        linePane.getChildren().clear();

        double lineY = 40;
        Line baseLine = new Line(20, lineY, linePane.getPrefWidth() - 20, lineY);
        linePane.getChildren().add(baseLine);

        StringBuilder sequence = new StringBuilder();
        int term = FIRST_TERM;
        for (int i = 0; i < TERMS_SHOWN; i++) {
            double x = valueToX(term);
            if (term >= MIN_VAL && term <= MAX_VAL) {
                Circle dot = new Circle(x, lineY, 6, Color.web("#1565c0"));
                Text label = new Text(x - 8, lineY - 12, String.valueOf(term));
                linePane.getChildren().addAll(dot, label);
            }
            sequence.append(term);
            if (i < TERMS_SHOWN - 1) sequence.append(", ");
            term += commonDifference;
        }

        sequenceLabel.setText("Sequence: " + sequence + ", ...");
    }

    private double valueToX(int value) {
        return 20 + (value - MIN_VAL) * UNIT_PX;
    }
}