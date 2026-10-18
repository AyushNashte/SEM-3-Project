package class8;

import common.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class Factorisation extends Topic {

    // Lesson concepts
    private static final Concept WHAT_IS_FACTORISATION = new Concept("FA1", "What is Factorisation");
    private static final Concept COMMON_FACTOR = new Concept("FA2", "Taking Out the Common Factor");
    private static final Concept GROUPING = new Concept("FA3", "Factorising by Grouping");
    private static final Concept IDENTITIES = new Concept("FA4", "Factorising Using Identities");
    private static final Concept QUADRATIC_TRINOMIAL = new Concept("FA5", "Factorising x² + bx + c");

    // Prerequisite concepts
    private static final Concept HCF_BASICS = new Concept("FA6", "HCF of Numbers");
    private static final Concept EXPANDING_BRACKETS = new Concept("FA7", "Expanding Brackets");
    private static final Concept ALGEBRA_TERMS = new Concept("FA8", "Terms and Like Terms");
    private static final Concept SQUARES = new Concept("FA9", "Perfect Squares");

    public Factorisation() {
        super("Class 8", "Factorisation");
    }

    // ==============================================================================
    // 1. PREREQUISITE TEST
    // ==============================================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question("What is the HCF of 24 and 36?", Arrays.asList("6", "12", "24", "72"), 1, HCF_BASICS),
                new Question("What is the HCF of 15 and 25?", Arrays.asList("5", "3", "15", "75"), 0, HCF_BASICS),
                new Question("Expand: 3(x + 4)", Arrays.asList("3x + 4", "x + 12", "3x + 12", "7x"), 2, EXPANDING_BRACKETS),
                new Question("Expand: 2x(x - 5)", Arrays.asList("2x² - 10x", "2x² - 5", "2x - 10x", "x² - 10x"), 0, EXPANDING_BRACKETS),
                new Question("Expand: (x + 2)(x + 3)", Arrays.asList("x² + 5", "x² + 6", "x² + 5x + 6", "2x + 5"), 2, EXPANDING_BRACKETS),
                new Question("Which pair are like terms?", Arrays.asList("3x and 5x²", "2x and 2y", "3x and 5x", "x and x²"), 2, ALGEBRA_TERMS),
                new Question("What is x multiplied by x?", Arrays.asList("2x", "x²", "x + x", "x³"), 1, ALGEBRA_TERMS),
                new Question("Which of these is a perfect square?", Arrays.asList("40", "36", "50", "60"), 1, SQUARES)
        );
        return new Test(questions);
    }

    // ==============================================================================
    // 2. LESSON (+ GAME CONSOLE HOOK)
    // ==============================================================================
    @Override
    protected void teachLesson() {
        System.out.println("=== STEP 2: LESSON CONTENT ===");
        getLessonContentBank().forEach(LessonContent::display);

        // Never block the JavaFX thread (it would freeze the whole app)
        if (isJavaFxThread() || GraphicsEnvironment.isHeadless()) {
            return;
        }

        System.out.println("\n=======================================================");
        System.out.println("   LESSON COMPLETE. LAUNCHING FACTOR BREAKER GAME...   ");
        System.out.println("   (Please check for a new window to play the game)    ");
        System.out.println("=======================================================\n");

        CountDownLatch done = new CountDownLatch(1);
        launchGame(done::countDown);

        try {
            done.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("=======================================================");
        System.out.println("   GAME FINISHED. PROCEEDING TO MAIN TEST...           ");
        System.out.println("=======================================================\n");
    }

    private boolean isJavaFxThread() {
        return Thread.currentThread().getName().startsWith("JavaFX");
    }

    // ==============================================================================
    // 3. JAVAFX UI GAME HOOKS (used by the LessonScreen "Play Game" button)
    // ==============================================================================
    @Override
    public Optional<String> getGameId() {
        return Optional.of("factor-breaker");
    }

    @Override
    public void launchGame(Runnable onFinished) {
        final Runnable callback = (onFinished != null) ? onFinished : () -> { };

        if (GraphicsEnvironment.isHeadless()) {
            callback.run();
            return;
        }

        Platform.runLater(() -> {
            try {
                FactorBreakerGame game = new FactorBreakerGame(callback);
                game.show();
            } catch (Throwable t) {
                t.printStackTrace();
                callback.run(); // never leave the caller waiting
            }
        });
    }

    // ==============================================================================
    // 4. MAIN (LESSON) TEST
    // ==============================================================================
    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question("Writing an expression as a product of its factors is called:", Arrays.asList("Expansion", "Factorisation", "Simplification", "Substitution"), 1, WHAT_IS_FACTORISATION),
                new Question("Which of the following is a factor of 12x?", Arrays.asList("5", "3x", "x + 12", "12 + x"), 1, WHAT_IS_FACTORISATION),
                new Question("The factors of 5xy are:", Arrays.asList("5 + x + y", "5x + y", "5, x and y", "only xy"), 2, WHAT_IS_FACTORISATION),
                new Question("Factorise: 4x + 8", Arrays.asList("4(x + 8)", "2(x + 4)", "4(x + 2)", "8(x + 4)"), 2, COMMON_FACTOR),
                new Question("Factorise completely: 6x² + 9x", Arrays.asList("3x(2x + 3)", "3(2x² + 3x)", "x(6x + 9)", "9x(x + 1)"), 0, COMMON_FACTOR),
                new Question("What is the greatest common factor of 12x²y and 18xy²?", Arrays.asList("3xy", "6x²y²", "12xy", "6xy"), 3, COMMON_FACTOR),
                new Question("Factorise: ax + ay + bx + by", Arrays.asList("(a + x)(b + y)", "ab(x + y)", "(a + b)(x + y)", "(a + y)(b + x)"), 2, GROUPING),
                new Question("Factorise: x² + xy + 3x + 3y", Arrays.asList("(x + 3)(x - y)", "(x - 3)(x + y)", "x(x + y + 3)", "(x + 3)(x + y)"), 3, GROUPING),
                new Question("Grouping works when, after taking out a common factor from each group, the groups share:", Arrays.asList("the same coefficient", "a common bracket (binomial factor)", "the same sign", "no terms at all"), 1, GROUPING),
                new Question("Factorise: x² - 49", Arrays.asList("(x - 7)²", "(x + 7)²", "(x + 7)(x - 7)", "(x - 49)(x + 1)"), 2, IDENTITIES),
                new Question("Factorise: x² + 6x + 9", Arrays.asList("(x + 9)(x + 1)", "(x + 3)²", "(x - 3)²", "(x + 6)(x + 3)"), 1, IDENTITIES),
                new Question("Factorise: 25x² - 16", Arrays.asList("(5x + 4)(5x - 4)", "(5x - 4)²", "(25x + 16)(x - 1)", "5(5x - 16)"), 0, IDENTITIES),
                new Question("To factorise x² + bx + c, find two numbers whose product is c and whose sum is:", Arrays.asList("c", "b", "b + c", "b x c"), 1, QUADRATIC_TRINOMIAL),
                new Question("Factorise: x² - 7x + 12", Arrays.asList("(x - 3)(x - 4)", "(x + 3)(x + 4)", "(x - 2)(x - 6)", "(x - 1)(x - 12)"), 0, QUADRATIC_TRINOMIAL),
                new Question("Factorise: x² + 2x - 15", Arrays.asList("(x + 3)(x - 5)", "(x - 3)(x - 5)", "(x + 5)(x - 3)", "(x + 15)(x - 1)"), 2, QUADRATIC_TRINOMIAL)
        );
    }

    @Override
    protected Test getRetest(List<Concept> weakConcepts) {
        List<Question> filtered = getLessonQuestionBank().stream()
                .filter(q -> weakConcepts.contains(q.getConcept()))
                .collect(Collectors.toList());
        return new Test(filtered.isEmpty() ? getLessonQuestionBank() : filtered);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(WHAT_IS_FACTORISATION, "Factorisation means writing an expression as a product of its factors. It is the reverse of expanding, where you multiply out brackets.", "Expanding: a(b + c) = ab + ac     Factorising: ab + ac = a(b + c)", Arrays.asList("12x = 3 x 4x, so 3 and 4x are factors of 12x", "The factors of 5xy are 5, x and y")),
                new LessonContent(COMMON_FACTOR, "Find the greatest common factor (GCF) of all the terms, then take it outside a bracket. Check your answer by expanding it again.", "ab + ac = a(b + c)", Arrays.asList("4x + 8 = 4(x + 2)", "6x² + 9x = 3x(2x + 3)", "The GCF of 12x²y and 18xy² is 6xy")),
                new LessonContent(GROUPING, "When no factor is common to every term, group the terms in pairs, take a common factor out of each pair, and then look for a common bracket.", null, Arrays.asList("ax + ay + bx + by = a(x + y) + b(x + y) = (a + b)(x + y)", "x² + xy + 3x + 3y = x(x + y) + 3(x + y) = (x + 3)(x + y)")),
                new LessonContent(IDENTITIES, "Some expressions match a standard identity. Spotting the pattern lets you factorise straight away: a difference of two squares, or a perfect square.", "a² - b² = (a + b)(a - b)     a² + 2ab + b² = (a + b)²     a² - 2ab + b² = (a - b)²", Arrays.asList("x² - 49 = x² - 7² = (x + 7)(x - 7)", "x² + 6x + 9 = (x + 3)²", "25x² - 16 = (5x)² - 4² = (5x + 4)(5x - 4)")),
                new LessonContent(QUADRATIC_TRINOMIAL, "To factorise x² + bx + c, find two numbers p and q whose product is c and whose sum is b. Then write the expression as (x + p)(x + q).", "x² + bx + c = (x + p)(x + q), where p x q = c and p + q = b", Arrays.asList("x² - 7x + 12: the numbers are -3 and -4 (product 12, sum -7), so (x - 3)(x - 4)", "x² + 2x - 15: the numbers are 5 and -3 (product -15, sum 2), so (x + 5)(x - 3)"))
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }

    // ==============================================================================
    // INNER CLASS: THE GAME (JavaFX)
    // ==============================================================================
    public static class FactorBreakerGame {

        // ---------- Game state ----------
        private static final int MAX_LEVELS = 10;
        private int currentLevel = 1;
        private int score = 0;
        private int expectedP, expectedQ, expectedA;
        private int levelType;
        private boolean inputLocked = false;
        private boolean finishedNotified = false;
        private final Random random = new Random();
        private final Runnable onFinished;

        // ---------- Style ----------
        private static final Color BG = Color.rgb(10, 20, 10);
        private static final Color FG = Color.rgb(0, 255, 65);
        private static final Color ERR = Color.rgb(255, 50, 50);
        private static final String FG_HEX = "#00ff41";
        private static final String ERR_HEX = "#ff3232";
        private static final Font TERMINAL = Font.font("Monospaced", FontWeight.BOLD, 16);
        private static final Font TITLE = Font.font("Monospaced", FontWeight.BOLD, 40);

        // ---------- UI ----------
        private Stage stage;
        private Label lblStatus, lblEquation, lblInstructions, lblFloat;
        private TextField txtGcf, txtP, txtQ;
        private HBox gcfBox, inputPanel;
        private Button btnHack, btnFinish;
        private VBox consoleBox;
        private ScrollPane consoleScroll;
        private final Rectangle[] segments = new Rectangle[MAX_LEVELS];
        private AnimationTimer rainTimer;

        public FactorBreakerGame(Runnable onFinished) {
            this.onFinished = onFinished;
            buildUi();
            printToConsole("Initializing Factor Breaker v2.0...", FG_HEX);
            printToConsole("Agent, crack the polynomial passwords to bypass the firewalls.", FG_HEX);
            printToConsole("Use negative numbers if needed (e.g., -4).", FG_HEX);
            generateLevel();
        }

        public void show() {
            stage.show();
            stage.toFront();
        }

        // =====================================================================
        // UI CONSTRUCTION
        // =====================================================================
        private void buildUi() {
            stage = new Stage();
            stage.setTitle("Factor Breaker: Cyber-Heist");

            // --- Matrix rain background ---
            Canvas rain = new Canvas(860, 660);
            StackPane root = new StackPane();
            root.setStyle("-fx-background-color: rgb(10,20,10);");
            rain.widthProperty().bind(root.widthProperty());
            rain.heightProperty().bind(root.heightProperty());
            startRain(rain);

            // --- Top: status, title, progress ---
            lblStatus = styledLabel("LEVEL: 1   |   SCORE: 0", TERMINAL, FG);
            Label lblTitle = styledLabel("SYSTEM MAINFRAME FIREWALL",
                    Font.font("Monospaced", FontWeight.BOLD, 24), FG);
            lblTitle.setEffect(new DropShadow(12, FG));

            BorderPane topRow = new BorderPane();
            topRow.setLeft(lblStatus);
            topRow.setCenter(lblTitle);

            HBox progress = new HBox(6);
            progress.setAlignment(Pos.CENTER);
            for (int i = 0; i < MAX_LEVELS; i++) {
                Rectangle r = new Rectangle(52, 10);
                r.setArcWidth(4);
                r.setArcHeight(4);
                r.setFill(Color.rgb(25, 45, 25));
                r.setStroke(FG);
                r.setStrokeWidth(0.8);
                segments[i] = r;
                progress.getChildren().add(r);
            }

            VBox top = new VBox(14, topRow, progress);
            top.setPadding(new Insets(20));

            // --- Center: equation + instructions + floating score ---
            lblEquation = styledLabel("", TITLE, Color.WHITE);
            lblEquation.setEffect(new DropShadow(10, Color.rgb(0, 255, 65, 0.6)));
            lblInstructions = styledLabel("", TERMINAL, Color.LIGHTGRAY);
            lblInstructions.setWrapText(true);
            lblInstructions.setAlignment(Pos.CENTER);

            VBox centerBox = new VBox(24, lblEquation, lblInstructions);
            centerBox.setAlignment(Pos.CENTER);

            lblFloat = styledLabel("+100", Font.font("Monospaced", FontWeight.BOLD, 34), FG);
            lblFloat.setEffect(new DropShadow(14, FG));
            lblFloat.setOpacity(0);
            lblFloat.setMouseTransparent(true);

            StackPane center = new StackPane(centerBox, lblFloat);

            // --- Bottom: inputs + console ---
            txtGcf = createTextField();
            gcfBox = new HBox(6, styledLabel("GCF:", TERMINAL, FG), txtGcf,
                    styledLabel(" * ", TITLE, FG));
            gcfBox.setAlignment(Pos.CENTER);

            txtP = createTextField();
            txtQ = createTextField();

            btnHack = new Button("INITIATE HACK");
            styleButton(btnHack);
            btnHack.setOnAction(e -> onHack());

            inputPanel = new HBox(10, gcfBox,
                    styledLabel("( x + ", TITLE, FG), txtP,
                    styledLabel(" ) ( x + ", TITLE, FG), txtQ,
                    styledLabel(" )", TITLE, FG), btnHack);
            inputPanel.setAlignment(Pos.CENTER);

            btnFinish = new Button("RETURN TO LESSON");
            styleButton(btnFinish);
            btnFinish.setVisible(false);
            btnFinish.setManaged(false);
            btnFinish.setOnAction(e -> stage.close());

            consoleBox = new VBox(2);
            consoleBox.setPadding(new Insets(6));
            consoleBox.setStyle("-fx-background-color: rgb(5,10,5);");
            consoleScroll = new ScrollPane(consoleBox);
            consoleScroll.setFitToWidth(true);
            consoleScroll.setPrefHeight(170);
            consoleScroll.setStyle("-fx-background: rgb(5,10,5); -fx-background-color: rgb(5,10,5);"
                    + "-fx-border-color: " + FG_HEX + ";");
            consoleBox.heightProperty().addListener((o, a, b) -> consoleScroll.setVvalue(1.0));

            VBox bottom = new VBox(14, inputPanel, btnFinish, consoleScroll);
            bottom.setAlignment(Pos.CENTER);
            bottom.setPadding(new Insets(10, 20, 20, 20));

            BorderPane ui = new BorderPane();
            ui.setTop(top);
            ui.setCenter(center);
            ui.setBottom(bottom);

            root.getChildren().addAll(rain, ui);

            Scene scene = new Scene(root, 860, 660);
            stage.setScene(scene);
            stage.setMinWidth(760);
            stage.setMinHeight(600);
            stage.centerOnScreen();

            // Intro fade-in
            root.setOpacity(0);
            FadeTransition fade = new FadeTransition(Duration.millis(700), root);
            fade.setToValue(1);
            stage.setOnShown(e -> fade.play());

            // Resume the main app when the window closes
            stage.setOnHidden(e -> {
                if (rainTimer != null) rainTimer.stop();
                notifyFinished();
            });
        }

        private Label styledLabel(String text, Font font, Color color) {
            Label l = new Label(text);
            l.setFont(font);
            l.setTextFill(color);
            return l;
        }

        private TextField createTextField() {
            TextField tf = new TextField();
            tf.setPrefWidth(64);
            tf.setAlignment(Pos.CENTER);
            tf.setFont(TERMINAL);
            tf.setStyle(fieldStyle(FG_HEX));
            tf.setOnAction(e -> btnHack.fire());
            tf.focusedProperty().addListener((o, was, is) ->
                    tf.setStyle(fieldStyle(is ? "#ffffff" : FG_HEX)));
            return tf;
        }

        private String fieldStyle(String border) {
            return "-fx-background-color: black; -fx-text-fill: white; -fx-highlight-fill: #006622;"
                    + "-fx-border-color: " + border + "; -fx-border-width: 1.5;";
        }

        private void styleButton(Button b) {
            b.setFont(TERMINAL);
            b.setTextFill(FG);
            b.setStyle("-fx-background-color: #333333; -fx-border-color: " + FG_HEX + "; -fx-cursor: hand;");
            b.setFocusTraversable(false);
            b.setOnMouseEntered(e -> scaleTo(b, 1.08));
            b.setOnMouseExited(e -> scaleTo(b, 1.0));
        }

        private void scaleTo(javafx.scene.Node n, double target) {
            ScaleTransition st = new ScaleTransition(Duration.millis(120), n);
            st.setToX(target);
            st.setToY(target);
            st.play();
        }

        // =====================================================================
        // MATRIX RAIN BACKGROUND
        // =====================================================================
        private void startRain(Canvas canvas) {
            final String glyphs = "01ABCDEFXYZ+-=<>[]{}#$%";
            final int size = 16;
            final int[][] drops = {new int[0]};

            rainTimer = new AnimationTimer() {
                private long last = 0;

                @Override
                public void handle(long now) {
                    if (now - last < 70_000_000L) return; // ~14 fps
                    last = now;

                    double w = canvas.getWidth(), h = canvas.getHeight();
                    if (w <= 0 || h <= 0) return;
                    int cols = (int) (w / size) + 1;
                    if (drops[0].length != cols) {
                        drops[0] = new int[cols];
                        for (int i = 0; i < cols; i++) drops[0][i] = random.nextInt((int) (h / size) + 1);
                    }

                    GraphicsContext g = canvas.getGraphicsContext2D();
                    g.setFill(Color.rgb(10, 20, 10, 0.18));
                    g.fillRect(0, 0, w, h);
                    g.setFill(Color.rgb(0, 255, 65, 0.35));
                    g.setFont(Font.font("Monospaced", size));
                    for (int i = 0; i < cols; i++) {
                        char c = glyphs.charAt(random.nextInt(glyphs.length()));
                        g.fillText(String.valueOf(c), i * size, drops[0][i] * size);
                        if (drops[0][i] * size > h && random.nextDouble() > 0.96) drops[0][i] = 0;
                        drops[0][i]++;
                    }
                }
            };
            rainTimer.start();
        }

        // =====================================================================
        // CONSOLE (typewriter effect)
        // =====================================================================
        private void printToConsole(String text, String colorHex) {
            Label line = new Label("> ");
            line.setFont(TERMINAL);
            line.setWrapText(true);
            line.setStyle("-fx-text-fill: " + colorHex + ";");
            consoleBox.getChildren().add(line);
            if (consoleBox.getChildren().size() > 60) consoleBox.getChildren().remove(0);

            final int[] idx = {0};
            Timeline typing = new Timeline(new KeyFrame(Duration.millis(10), e -> {
                idx[0]++;
                line.setText("> " + text.substring(0, idx[0]));
            }));
            typing.setCycleCount(text.length());
            typing.play();
        }

        // =====================================================================
        // LEVEL GENERATION
        // =====================================================================
        private void generateLevel() {
            lblStatus.setText("LEVEL: " + currentLevel + " / " + MAX_LEVELS + "   |   SCORE: " + score);

            if (currentLevel <= 3) levelType = 1;
            else if (currentLevel <= 6) levelType = 2;
            else levelType = 3;

            expectedA = 1;
            boolean showGcf = false;
            String hint = "";

            switch (levelType) {
                case 1:
                    expectedP = random.nextInt(9) - 4;
                    expectedQ = random.nextInt(9) - 4;
                    if (expectedP == 0) expectedP = 2;
                    if (expectedQ == 0) expectedQ = 3;
                    hint = "Hint: What multiplies to C and adds to B?";
                    break;
                case 2:
                    expectedP = random.nextInt(6) + 3;
                    expectedQ = -expectedP;
                    hint = "Hint: Difference of Squares! The middle term is 0x.";
                    break;
                default:
                    expectedP = random.nextInt(7) - 3;
                    expectedQ = random.nextInt(7) - 3;
                    if (expectedP == 0) expectedP = 1;
                    if (expectedQ == 0) expectedQ = -2;
                    expectedA = random.nextInt(3) + 2;
                    showGcf = true;
                    hint = "Hint: Factor out the Greatest Common Factor (GCF) FIRST!";
                    break;
            }

            int b = expectedA * (expectedP + expectedQ);
            int c = expectedA * (expectedP * expectedQ);

            gcfBox.setVisible(showGcf);
            gcfBox.setManaged(showGcf);
            lblInstructions.setText(hint);

            txtGcf.clear();
            txtP.clear();
            txtQ.clear();

            playEquationIntro(formatQuadratic(expectedA, b, c));
            (showGcf ? txtGcf : txtP).requestFocus();
            inputLocked = false;
        }

        private String formatQuadratic(int a, int b, int c) {
            StringBuilder eq = new StringBuilder();
            if (a == 1) eq.append("x² ");
            else if (a == -1) eq.append("-x² ");
            else eq.append(a).append("x² ");

            if (b > 0) eq.append("+ ").append(b == 1 ? "x " : b + "x ");
            else if (b < 0) eq.append("- ").append(b == -1 ? "x " : Math.abs(b) + "x ");

            if (c > 0) eq.append("+ ").append(c);
            else if (c < 0) eq.append("- ").append(Math.abs(c));

            return eq.toString().trim();
        }

        /** Scramble/"decrypt" effect plus a pop-in scale. */
        private void playEquationIntro(String target) {
            final String noise = "01#$%&@!?<>[]{}";
            final int frames = 18;

            Timeline decrypt = new Timeline();
            for (int f = 0; f <= frames; f++) {
                final int frame = f;
                decrypt.getKeyFrames().add(new KeyFrame(Duration.millis(f * 35), e -> {
                    int reveal = target.length() * frame / frames;
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < target.length(); j++) {
                        char ch = target.charAt(j);
                        if (j < reveal || ch == ' ') sb.append(ch);
                        else sb.append(noise.charAt(random.nextInt(noise.length())));
                    }
                    lblEquation.setText(sb.toString());
                }));
            }
            decrypt.setOnFinished(e -> lblEquation.setText(target));

            lblEquation.setTextFill(Color.WHITE);
            lblEquation.setScaleX(0.6);
            lblEquation.setScaleY(0.6);
            lblEquation.setOpacity(0);

            FadeTransition fade = new FadeTransition(Duration.millis(350), lblEquation);
            fade.setToValue(1);
            ScaleTransition pop = new ScaleTransition(Duration.millis(450), lblEquation);
            pop.setToX(1);
            pop.setToY(1);
            pop.setInterpolator(Interpolator.EASE_OUT);

            new ParallelTransition(decrypt, fade, pop).play();
        }

        // =====================================================================
        // GAME LOGIC
        // =====================================================================
        private void onHack() {
            if (inputLocked) return;

            int userP, userQ, userA = 1;
            try {
                userP = Integer.parseInt(txtP.getText().trim());
                userQ = Integer.parseInt(txtQ.getText().trim());
                if (levelType == 3) userA = Integer.parseInt(txtGcf.getText().trim());
            } catch (NumberFormatException ex) {
                printToConsole("[SYNTAX ERROR] Please enter numbers only! Use '-' for negative numbers.", ERR_HEX);
                shake();
                return;
            }

            if (levelType == 3 && userA != expectedA) {
                printToConsole("[ERROR] Incorrect GCF. Look at the numbers in the equation again.", ERR_HEX);
                shake();
                return;
            }

            boolean correct = (userP == expectedP && userQ == expectedQ)
                    || (userP == expectedQ && userQ == expectedP);

            if (correct) {
                onCorrect();
            } else {
                printToConsole("[ACCESS DENIED] Incorrect factors. Remember: Multiply to get the last number, add to get the middle.", ERR_HEX);
                shake();
            }
        }

        private void onCorrect() {
            inputLocked = true;
            printToConsole("[SUCCESS] Firewall level " + currentLevel + " breached! +100 points.", FG_HEX);
            score += 100;
            lblStatus.setText("LEVEL: " + currentLevel + " / " + MAX_LEVELS + "   |   SCORE: " + score);

            lightSegment(currentLevel - 1);
            floatScore();
            flashEquation(FG);

            currentLevel++;
            PauseTransition wait = new PauseTransition(Duration.millis(900));
            wait.setOnFinished(e -> {
                if (currentLevel > MAX_LEVELS) showVictory();
                else generateLevel();
            });
            wait.play();
        }

        // =====================================================================
        // ANIMATIONS
        // =====================================================================
        /** Horizontal shake of the input row + red flash on the equation. */
        private void shake() {
            TranslateTransition t = new TranslateTransition(Duration.millis(55), inputPanel);
            t.setByX(12);
            t.setCycleCount(6);
            t.setAutoReverse(true);
            t.setOnFinished(e -> inputPanel.setTranslateX(0));
            t.play();
            flashEquation(ERR);
        }

        /** Briefly tints the equation and pulses its glow. */
        private void flashEquation(Color c) {
            DropShadow glow = new DropShadow(10, Color.rgb(0, 255, 65, 0.6));
            lblEquation.setEffect(glow);
            Timeline tl = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(glow.radiusProperty(), 10),
                            new KeyValue(glow.colorProperty(), c)),
                    new KeyFrame(Duration.millis(150),
                            new KeyValue(glow.radiusProperty(), 40),
                            new KeyValue(glow.colorProperty(), c)),
                    new KeyFrame(Duration.millis(600),
                            new KeyValue(glow.radiusProperty(), 10),
                            new KeyValue(glow.colorProperty(), Color.rgb(0, 255, 65, 0.6))));
            lblEquation.setTextFill(c == ERR ? ERR : Color.WHITE);
            tl.setOnFinished(e -> lblEquation.setTextFill(Color.WHITE));
            tl.play();
        }

        /** Fills a progress segment with a green fade and a little bounce. */
        private void lightSegment(int index) {
            if (index < 0 || index >= MAX_LEVELS) return;
            Rectangle r = segments[index];
            FillTransition fill = new FillTransition(Duration.millis(400), r,
                    Color.rgb(25, 45, 25), FG);
            ScaleTransition bounce = new ScaleTransition(Duration.millis(200), r);
            bounce.setToX(1.25);
            bounce.setToY(2.0);
            bounce.setAutoReverse(true);
            bounce.setCycleCount(2);
            r.setEffect(new DropShadow(10, FG));
            new ParallelTransition(fill, bounce).play();
        }

        /** "+100" rises and fades. */
        private void floatScore() {
            lblFloat.setTranslateY(40);
            lblFloat.setOpacity(1);
            TranslateTransition rise = new TranslateTransition(Duration.millis(900), lblFloat);
            rise.setToY(-70);
            FadeTransition fade = new FadeTransition(Duration.millis(900), lblFloat);
            fade.setFromValue(1);
            fade.setToValue(0);
            new ParallelTransition(rise, fade).play();
        }

        private void showVictory() {
            lblStatus.setText("MISSION COMPLETE   |   FINAL SCORE: " + score);
            lblInstructions.setText("Mainframe secured. Close this window to return to your lesson!");
            inputPanel.setVisible(false);
            inputPanel.setManaged(false);
            btnFinish.setVisible(true);
            btnFinish.setManaged(true);
            printToConsole("*** MISSION COMPLETE. YOU CAN CLOSE THIS WINDOW NOW. ***", FG_HEX);

            lblEquation.setText("ACCESS GRANTED");
            lblEquation.setTextFill(FG);
            lblEquation.setScaleX(0.2);
            lblEquation.setScaleY(0.2);
            lblEquation.setOpacity(0);

            FadeTransition fade = new FadeTransition(Duration.millis(500), lblEquation);
            fade.setToValue(1);
            ScaleTransition pop = new ScaleTransition(Duration.millis(700), lblEquation);
            pop.setToX(1);
            pop.setToY(1);
            pop.setInterpolator(Interpolator.EASE_OUT);
            new ParallelTransition(fade, pop).play();

            // Endless gentle pulse on the glow
            DropShadow glow = new DropShadow(10, FG);
            lblEquation.setEffect(glow);
            Timeline pulse = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(glow.radiusProperty(), 10)),
                    new KeyFrame(Duration.millis(800), new KeyValue(glow.radiusProperty(), 45)));
            pulse.setAutoReverse(true);
            pulse.setCycleCount(Animation.INDEFINITE);
            pulse.play();
            stage.setOnHidden(e -> {
                pulse.stop();
                if (rainTimer != null) rainTimer.stop();
                notifyFinished();
            });
        }

        // =====================================================================
        // FINISH CALLBACK
        // =====================================================================
        private void notifyFinished() {
            if (finishedNotified) return;
            finishedNotified = true;
            if (onFinished != null) {
                try {
                    onFinished.run();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
}