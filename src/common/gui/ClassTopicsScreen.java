package common.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ClassTopicsScreen {
    private final AppLauncher launcher;
    private final String className;

    public ClassTopicsScreen(AppLauncher launcher, String className) {
        this.launcher = launcher;
        this.className = className;
    }

    public Scene buildScene() {
        Button back = Theme.pillButton("← All Classes");
        back.setOnAction(e -> launcher.showHome());

        Label title = Theme.title(className + " — choose a topic", 34);

        HBox top = new HBox(20, back, title);
        top.setAlignment(Pos.CENTER_LEFT);

        FlowPane grid = new FlowPane(22, 22);
        grid.setAlignment(Pos.TOP_LEFT);
        for (ClassCatalog.Entry entry : ClassCatalog.all().get(className)) {
            grid.getChildren().add(buildTopicCard(entry));
        }

        VBox content = new VBox(28, top, grid);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(30, 40, 30, 40));

        StackPane background = new StackPane(content);
        StackPane.setAlignment(content, Pos.TOP_LEFT);
        Theme.applyBackground(background);

        ScrollPane scroll = new ScrollPane(background);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        return new Scene(scroll, 1150, 720);
    }

    private VBox buildTopicCard(ClassCatalog.Entry entry) {
        Label emoji = new Label(entry.emoji);
        emoji.setStyle("-fx-font-size: 40px;");

        Label name = new Label(entry.name);
        name.setWrapText(true);
        name.setAlignment(Pos.CENTER);
        name.setTextAlignment(TextAlignment.CENTER);
        name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2b0b5e;");

        VBox card = new VBox(8, emoji, name);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(230, 140);
        card.setPadding(new Insets(14));
        Theme.styleCard(card);
        card.setStyle(card.getStyle() + " -fx-border-color: " + ClassCatalog.accent(className)
                + "; -fx-border-width: 0 0 5 0; -fx-border-radius: 18;");
        card.setOnMouseClicked(e -> entry.open(launcher));
        return card;
    }
}