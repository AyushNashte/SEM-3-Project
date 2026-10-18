package common.gui;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class Theme {

    public static void applyBackground(Region region) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#2b0b5e")),
                new Stop(1, Color.web("#7b2fd0")));
        region.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public static Label title(String text, double size) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: " + size + "px; -fx-font-weight: bold; -fx-text-fill: white;");
        label.setEffect(new DropShadow(6, 0, 2, Color.rgb(0, 0, 0, 0.4)));
        return label;
    }

    public static Label subtitle(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 15px; -fx-text-fill: #e9d8ff;");
        return label;
    }

    public static void styleCard(Region card) {
        card.setStyle("-fx-background-color: white; -fx-background-radius: 18; -fx-cursor: hand;");
        card.setEffect(new DropShadow(14, 0, 5, Color.rgb(0, 0, 0, 0.35)));
        addHoverPop(card);
    }

    public static Button pillButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: white; "
                + "-fx-text-fill: #46178f; -fx-background-radius: 20; -fx-padding: 8 20 8 20; -fx-cursor: hand;");
        addHoverPop(button);
        return button;
    }

    public static void addHoverPop(Node node) {
        ScaleTransition grow = new ScaleTransition(Duration.millis(120), node);
        grow.setToX(1.05);
        grow.setToY(1.05);

        ScaleTransition shrink = new ScaleTransition(Duration.millis(120), node);
        shrink.setToX(1.0);
        shrink.setToY(1.0);

        node.setOnMouseEntered(e -> {
            shrink.stop();
            grow.playFromStart();
        });
        node.setOnMouseExited(e -> {
            grow.stop();
            shrink.playFromStart();
        });
    }
}