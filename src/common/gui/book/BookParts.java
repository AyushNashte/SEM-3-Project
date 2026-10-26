package common.gui.book;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public final class BookParts {

    private BookParts() { }

    private static Label text(String s, double size, boolean bold, String color) {
        Label l = new Label(s);
        l.setWrapText(true);
        l.setMinHeight(Region.USE_PREF_SIZE);
        l.setStyle(BookTheme.font(size, bold) + " -fx-text-fill: " + color + ";");
        return l;
    }

    private static Node cloud() {
        Shape c = new Circle(9, 11, 6);
        c = Shape.union(c, new Circle(17, 8, 8));
        c = Shape.union(c, new Circle(25, 12, 6));
        c = Shape.union(c, new Rectangle(9, 11, 16, 7));
        c.setFill(Color.web(BookTheme.PALE));
        c.setStroke(Color.web(BookTheme.ACCENT));
        c.setStrokeWidth(1.6);
        return new Group(c);
    }

    public static Node cloudTag(String label) {
        Label l = text(label, 17, true, BookTheme.TEXT);
        l.setWrapText(false);
        HBox pill = new HBox(8, cloud(), l);
        pill.setAlignment(Pos.CENTER_LEFT);
        pill.setPadding(new Insets(4, 14, 4, 10));
        pill.setMaxWidth(Region.USE_PREF_SIZE);
        pill.setStyle("-fx-background-color: white; -fx-background-radius: 18; "
                + "-fx-border-color: " + BookTheme.ACCENT + "; -fx-border-radius: 18; -fx-border-width: 1.5;");
        return pill;
    }

    public static Node sectionHeading(String s) {
        return text(s, 24, true, BookTheme.ACCENT);
    }

    public static Node body(String s) {
        Label l = text(s, BookTheme.BODY_SIZE, false, BookTheme.TEXT);
        l.setStyle(l.getStyle() + " -fx-line-spacing: 4;");
        return l;
    }

    public static Node bullet(String s) {
        Circle dot = new Circle(4, Color.web(BookTheme.ACCENT));
        StackPane dotBox = new StackPane(dot);
        dotBox.setPadding(new Insets(8, 0, 0, 0));
        dotBox.setAlignment(Pos.TOP_CENTER);
        dotBox.setMinWidth(12);
        Label l = text(s, BookTheme.BODY_SIZE, false, BookTheme.TEXT);
        HBox.setHgrow(l, Priority.ALWAYS);
        HBox row = new HBox(10, dotBox, l);
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    public static Node rememberBox(String formula) {
        Label head = text("Remember :", 19, true, BookTheme.ACCENT);
        head.setMaxWidth(Double.MAX_VALUE);
        head.setAlignment(Pos.CENTER);
        Label f = text(formula, 19, true, BookTheme.TEXT);
        f.setMaxWidth(Double.MAX_VALUE);
        f.setAlignment(Pos.CENTER);
        f.setStyle(f.getStyle() + " -fx-text-alignment: center;");
        VBox box = new VBox(8, head, f);
        box.setPadding(new Insets(14, 20, 16, 20));
        box.setStyle("-fx-background-color: " + BookTheme.PALE + "; -fx-background-radius: 14; "
                + "-fx-border-color: " + BookTheme.ACCENT + "; -fx-border-radius: 14; -fx-border-width: 1.6;");
        return box;
    }

    public static Node exampleBlock(String s) {
        return exampleBlock("Example", s);
    }

    /** Same as exampleBlock(text) but with a custom label such as "Example 2". */
    public static Node exampleBlock(String label, String s) {
        VBox box = new VBox(4);
        box.getChildren().add(text(label, BookTheme.BODY_SIZE, true, BookTheme.TEXT));
        int sol = s.indexOf("Solution");
        if (sol > 0) {
            box.getChildren().add(body(s.substring(0, sol).trim()));
            String rest = s.substring(sol + "Solution".length()).replaceFirst("^\\s*:?\\s*", "");
            HBox solRow = new HBox(8, text("Solution :", BookTheme.BODY_SIZE, true, BookTheme.ACCENT), body(rest));
            solRow.setAlignment(Pos.TOP_LEFT);
            box.getChildren().add(solRow);
        } else {
            box.getChildren().add(body(s));
        }
        box.setPadding(new Insets(4, 0, 4, 0));
        return box;
    }

    public static Node activityBlock(String s, Runnable onPlay) {
        Button play = new Button("Play");
        Polygon tri = new Polygon(0, 0, 0, 12, 10, 6);
        tri.setFill(Color.WHITE);
        play.setGraphic(tri);
        play.setCursor(javafx.scene.Cursor.HAND);
        play.setStyle(BookTheme.font(16, true) + " -fx-text-fill: white; -fx-background-color: " + BookTheme.ACCENT
                + "; -fx-background-radius: 20; -fx-padding: 6 22 6 18;");
        play.setOnAction(e -> onPlay.run());

        VBox left = new VBox(4, text("Activity", BookTheme.BODY_SIZE, true, BookTheme.TEXT), body(s));
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox row = new HBox(16, left, play);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 14, 10, 14));
        row.setStyle("-fx-border-color: " + BookTheme.ACCENT + "; -fx-border-style: segments(6, 5); "
                + "-fx-border-radius: 10; -fx-border-width: 1.4;");
        return row;
    }

    public static Node noteBox(String s) {
        Label l = text(s, 14, false, BookTheme.TEXT);
        VBox box = new VBox(l);
        box.setPadding(new Insets(10, 14, 10, 14));
        box.setStyle("-fx-background-color: " + BookTheme.PALE + "; -fx-background-radius: 12;");
        return box;
    }

    public static Node chapterBanner(String number, String title) {
        HBox row = new HBox(16);
        row.setAlignment(Pos.CENTER_LEFT);
        if (number != null) {
            Label n = text(number, 28, true, BookTheme.ACCENT);
            n.setWrapText(false);
            n.setMinWidth(54);
            n.setAlignment(Pos.CENTER);
            n.setStyle(n.getStyle() + " -fx-background-color: white; -fx-border-color: " + BookTheme.ACCENT
                    + "; -fx-border-width: 2; -fx-padding: 4 10 4 10;");
            row.getChildren().add(n);
        }
        Label t = text(title, 34, true, BookTheme.TEXT);
        row.getChildren().add(t);
        row.setPadding(new Insets(14, 24, 14, 24));
        row.setStyle("-fx-background-color: linear-gradient(to right, " + BookTheme.PALE + ", white);");
        return row;
    }
}