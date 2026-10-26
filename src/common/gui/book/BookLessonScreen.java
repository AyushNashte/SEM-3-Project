package common.gui.book;

import common.LessonContent;
import common.Topic;
import common.gui.AppLauncher;
import common.gui.LessonScreen;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class BookLessonScreen {

    private static final String COVER_FILE = "class7_cover.jpg";

    private final AppLauncher launcher;
    private final Topic topic;
    private final List<LessonContent> contentBank;
    private int page;                       // 0 = cover, 1..n = concepts

    private Scene scene;
    private BorderPane root;
    private ScrollPane scroll;
    private VBox pageBox;

    public BookLessonScreen(AppLauncher launcher, Topic topic, List<LessonContent> contentBank) {
        this.launcher = launcher;
        this.topic = topic;
        this.contentBank = contentBank;
        this.page = 0;
    }

    public Scene buildScene() {
        root = new BorderPane();
        BookTheme.applyDesk(root);

        Button contents = new Button("Contents");
        contents.setCursor(javafx.scene.Cursor.HAND);
        contents.setStyle(BookTheme.font(15, true) + " -fx-text-fill: " + BookTheme.ACCENT
                + "; -fx-background-color: white; -fx-background-radius: 16; -fx-border-radius: 16; "
                + "-fx-border-color: " + BookTheme.ACCENT + "; -fx-padding: 4 16 4 16;");
        contents.setOnAction(e -> launcher.showTopics());
        HBox topBar = new HBox(contents);
        topBar.setPadding(new Insets(12, 24, 4, 24));
        root.setTop(topBar);

        pageBox = new VBox();
        pageBox.setStyle("-fx-background-color: " + BookTheme.PAGE + ";");
        pageBox.setEffect(BookTheme.pageShadow());
        pageBox.setMinHeight(Region.USE_PREF_SIZE);

        StackPane deskHolder = new StackPane(pageBox);
        deskHolder.setAlignment(Pos.TOP_CENTER);
        deskHolder.setPadding(new Insets(10, 20, 24, 20));
        BookTheme.applyDesk(deskHolder);

        scroll = new ScrollPane(deskHolder);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background: " + BookTheme.DESK + "; -fx-background-color: " + BookTheme.DESK + ";");
        root.setCenter(scroll);

        scene = new Scene(root, 1150, 720);

        pageBox.prefWidthProperty().bind(Bindings.createDoubleBinding(
                () -> BookTheme.pageWidth(scene.getWidth()), scene.widthProperty()));
        pageBox.maxWidthProperty().bind(pageBox.prefWidthProperty());

        showPage();
        return scene;
    }

    private void showPage() {
        pageBox.getChildren().clear();
        if (page == 0) {
            pageBox.getChildren().add(buildCoverPage());
            root.setBottom(null);
        } else {
            pageBox.getChildren().add(buildConceptPage(contentBank.get(page - 1)));
            root.setBottom(buildFooter());
        }
        scroll.setVvalue(0);
    }

    private String topicName() {
        return topic.getReport().getTopicName();
    }

    private String chapterNumber() {
        if ("Integers".equals(topicName())) {
            return "2";
        }
        return null;
    }

    // ---------- cover page (page 0) ----------

    private Node buildCoverPage() {
        Node cover = loadCover();

        Label title = new Label(topicName());
        title.setWrapText(true);
        title.setStyle(BookTheme.font(40, true) + " -fx-text-fill: " + BookTheme.TEXT + ";");
        Label sub = new Label("Standard Seven - Mathematics");
        sub.setStyle(BookTheme.font(20, false) + " -fx-text-fill: " + BookTheme.ACCENT + ";");
        Label count = new Label(contentBank.size() + " parts to learn, then a Practice Set");
        count.setStyle(BookTheme.font(16, false) + " -fx-text-fill: #444444;");

        Button open = new Button("Open");
        open.setCursor(javafx.scene.Cursor.HAND);
        open.setStyle(BookTheme.font(20, true) + " -fx-text-fill: white; -fx-background-color: " + BookTheme.ACCENT
                + "; -fx-background-radius: 24; -fx-padding: 8 40 8 40;");
        open.setOnAction(e -> {
            page = 1;
            showPage();
        });

        VBox right = new VBox(14, title, sub, count, open);
        right.setAlignment(Pos.CENTER_LEFT);
        right.setPadding(new Insets(0, 0, 0, 20));
        HBox.setHgrow(right, Priority.ALWAYS);

        HBox row = new HBox(36, cover, right);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(40, 50, 24, 50));

        Label credit = new Label("Styled after the Balbharati textbooks - Maharashtra State Bureau of "
                + "Textbook Production and Curriculum Research, Pune.");
        credit.setWrapText(true);
        credit.setStyle(BookTheme.font(12, false) + " -fx-text-fill: #666666;");
        VBox creditBox = new VBox(credit);
        creditBox.setPadding(new Insets(6, 50, 18, 50));

        return new VBox(row, creditBox);
    }

    private Node loadCover() {
        try (InputStream in = BookLessonScreen.class.getResourceAsStream(COVER_FILE)) {
            if (in != null) {
                Image img = new Image(in);
                if (!img.isError()) {
                    ImageView view = new ImageView(img);
                    view.setPreserveRatio(true);
                    view.setFitHeight(430);
                    return view;
                }
            }
        } catch (Exception ignored) {
            // fall through to the drawn cover
        }
        Rectangle bg = new Rectangle(310, 430, Color.web(BookTheme.PALE));
        bg.setStroke(Color.web(BookTheme.ACCENT));
        bg.setStrokeWidth(3);
        Label m = new Label("Mathematics");
        m.setStyle(BookTheme.font(34, true) + " -fx-text-fill: white; -fx-background-color: " + BookTheme.ORANGE
                + "; -fx-padding: 10 22 10 22;");
        Label std = new Label("Standard Seven");
        std.setStyle(BookTheme.font(22, true) + " -fx-text-fill: " + BookTheme.ACCENT + ";");
        VBox stack = new VBox(16, m, std);
        stack.setAlignment(Pos.CENTER);
        return new StackPane(bg, stack);
    }

    // ---------- concept pages ----------

    private Node buildConceptPage(LessonContent c) {
        VBox body = new VBox(14);
        body.setPadding(new Insets(20, 36, 28, 36));

        Node tag = BookParts.cloudTag("Let's learn.");
        Node heading = BookParts.sectionHeading(c.getConcept().getName());
        HBox head = new HBox(16, tag, heading);
        head.setAlignment(Pos.CENTER_LEFT);
        body.getChildren().add(head);

        body.getChildren().add(BookParts.body(c.getExplanation()));

        if (c.getFormula() != null) {
            body.getChildren().add(BookParts.rememberBox(c.getFormula()));
        }

        List<String> examples = c.getWorkedExamples();
        if (examples != null) {
            int n = 1;
            for (String ex : examples) {
                String label = examples.size() == 1 ? "Example" : "Example " + n;
                body.getChildren().add(BookParts.exampleBlock(label, ex));
                n++;
            }
        }

        Optional<String> gameId = topic.getGameId();
        if (page == contentBank.size() && gameId.isPresent()) {
            body.getChildren().add(BookParts.activityBlock(
                    "Play the game to practise what you have learnt.", () -> openGame(gameId.get())));
        }

        return new VBox(BookParts.chapterBanner(chapterNumber(), topicName()), body);
    }

    // ---------- footer strip ----------

    private Node buildFooter() {
        Button prev = arrow("<");
        prev.setDisable(page <= 1);
        prev.setOnAction(e -> {
            page--;
            showPage();
        });

        Label num = new Label(String.valueOf(page));
        num.setMinWidth(40);
        num.setAlignment(Pos.CENTER);
        num.setStyle(BookTheme.font(18, true) + " -fx-text-fill: " + BookTheme.TEXT
                + "; -fx-background-color: white; -fx-border-color: " + BookTheme.ACCENT
                + "; -fx-border-width: 1.5; -fx-padding: 2 8 2 8;");

        Node next;
        if (page == contentBank.size()) {
            Button practice = new Button("Practice Set");
            practice.setCursor(javafx.scene.Cursor.HAND);
            practice.setStyle(BookTheme.font(16, true) + " -fx-text-fill: white; -fx-background-color: "
                    + BookTheme.ORANGE + "; -fx-background-radius: 20; -fx-padding: 5 22 5 22;");
            practice.setOnAction(e -> launcher.showLessonTest());
            next = practice;
        } else {
            Button b = arrow(">");
            b.setOnAction(e -> {
                page++;
                showPage();
            });
            next = b;
        }

        Label of = new Label("Part " + page + " of " + contentBank.size());
        of.setStyle(BookTheme.font(14, false) + " -fx-text-fill: #335566;");
        Region left = new Region();
        Region right = new Region();
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);

        HBox strip = new HBox(14, of, left, prev, num, next, right);
        strip.setAlignment(Pos.CENTER);
        strip.setPadding(new Insets(8, 28, 8, 28));
        strip.setStyle("-fx-background-color: " + BookTheme.PALE + "; -fx-border-color: " + BookTheme.ACCENT
                + "; -fx-border-width: 3 0 0 0;");
        of.setMinWidth(110);
        right.setMinWidth(110);
        return strip;
    }

    private Button arrow(String symbol) {
        Button b = new Button(symbol);
        b.setCursor(javafx.scene.Cursor.HAND);
        b.setMinWidth(40);
        b.setStyle(BookTheme.font(18, true) + " -fx-text-fill: white; -fx-background-color: " + BookTheme.ACCENT
                + "; -fx-background-radius: 20; -fx-padding: 2 14 2 14;");
        return b;
    }

    // ---------- game (Activity) ----------

    private void openGame(String gameId) {
        common.gui.games.ArcadeGame game = LessonScreen.resolveGame(gameId);
        if (game == null) {
            topic.launchGame(() -> { });        // Class 8 Swing games
            return;
        }
        Scene lessonScene = scene;              // coming back lands on the same page
        launcher.getStage().setScene(game.buildScene(() -> launcher.getStage().setScene(lessonScene)));
    }
}