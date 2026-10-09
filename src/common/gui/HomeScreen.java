package common.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

public class HomeScreen {
    private final AppLauncher launcher;

    public HomeScreen(AppLauncher launcher) {
        this.launcher = launcher;
    }

    public Scene buildScene() {
        Label logo = new Label("📚");
        logo.setStyle("-fx-font-size: 72px;");

        Label title = Theme.title("EduSnap", 52);
        Label tagline = Theme.subtitle("Maths made simple for Maharashtra SSC  |  Classes 7 to 10");
        Label prompt = Theme.title("Choose your class", 22);

        FlowPane cards = new FlowPane(24, 24);
        cards.setAlignment(Pos.CENTER);
        cards.setMaxWidth(1000);
        for (Map.Entry<String, List<ClassCatalog.Entry>> entry : ClassCatalog.all().entrySet()) {
            cards.getChildren().add(buildClassCard(entry.getKey(), entry.getValue().size()));
        }

        VBox content = new VBox(14, logo, title, tagline, prompt, cards);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        VBox.setMargin(prompt, new Insets(26, 0, 4, 0));

        StackPane root = new StackPane(content);
        Theme.applyBackground(root);
        return new Scene(root, 1150, 720);
    }

    private VBox buildClassCard(String className, int topicCount) {
        String number = className.replace("Class ", "");

        Label badge = new Label(number);
        badge.setMinSize(70, 70);
        badge.setAlignment(Pos.CENTER);
        badge.setStyle("-fx-background-color: " + ClassCatalog.accent(className)
                + "; -fx-background-radius: 35; -fx-text-fill: white; "
                + "-fx-font-size: 30px; -fx-font-weight: bold;");

        Label name = new Label(className);
        name.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2b0b5e;");

        Label count = new Label(topicCount + (topicCount == 1 ? " topic" : " topics"));
        count.setStyle("-fx-font-size: 13px; -fx-text-fill: #6b7280;");

        VBox card = new VBox(10, badge, name, count);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(210, 190);
        card.setPadding(new Insets(20));
        Theme.styleCard(card);
        card.setOnMouseClicked(e -> launcher.showClass(className));
        return card;
    }
}