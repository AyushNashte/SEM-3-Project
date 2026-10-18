package class8;

import common.*;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class LinearEquations extends Topic {

    // Lesson concepts
    private static final Concept WHAT_IS_LINEAR = new Concept("LE1", "What is a Linear Equation");
    private static final Concept SOLVE_BASIC = new Concept("LE2", "Solving One-Step and Two-Step Equations");
    private static final Concept BOTH_SIDES = new Concept("LE3", "Variable on Both Sides");
    private static final Concept BRACKETS_FRACTIONS = new Concept("LE4", "Equations with Brackets and Fractions");
    private static final Concept WORD_PROBLEMS = new Concept("LE5", "Forming Equations from Word Problems");

    // Prerequisite concepts
    private static final Concept SUBSTITUTION = new Concept("LE6", "Substituting Values into Expressions");
    private static final Concept INVERSE_OPERATIONS = new Concept("LE7", "Inverse (Opposite) Operations");
    private static final Concept INTEGER_OPERATIONS = new Concept("LE8", "Operations on Integers");
    private static final Concept FRACTION_BASICS = new Concept("LE9", "LCM and Adding Fractions");

    public LinearEquations() {
        super("Class 8", "Linear Equations");
    }

    // ==============================================================================
    // 1. PREREQUISITE TEST
    // ==============================================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question("If x = 4, what is the value of 3x + 2?", Arrays.asList("10", "12", "14", "20"), 2, SUBSTITUTION),
                new Question("If y = 5, what is the value of 2y - 3?", Arrays.asList("5", "7", "10", "13"), 1, SUBSTITUTION),
                new Question("What is the inverse (opposite) operation of addition?", Arrays.asList("Multiplication", "Subtraction", "Addition", "Division"), 1, INVERSE_OPERATIONS),
                new Question("What is the inverse operation of multiplying by 5?", Arrays.asList("Multiplying by 5", "Subtracting 5", "Dividing by 5", "Adding 5"), 2, INVERSE_OPERATIONS),
                new Question("What is (-8) + 3?", Arrays.asList("-11", "-5", "5", "11"), 1, INTEGER_OPERATIONS),
                new Question("What is (-2) x (-6)?", Arrays.asList("-12", "-8", "8", "12"), 3, INTEGER_OPERATIONS),
                new Question("What is the LCM of 2, 3 and 4?", Arrays.asList("6", "9", "12", "24"), 2, FRACTION_BASICS),
                new Question("What is 1/2 + 1/3?", Arrays.asList("2/5", "5/6", "1/6", "2/6"), 1, FRACTION_BASICS)
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

        if (isJavaFxThread() || GraphicsEnvironment.isHeadless()) {
            return;
        }

        System.out.println("\n=======================================================");
        System.out.println("   LESSON COMPLETE. LAUNCHING EQUATION DUEL GAME...    ");
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
    // 3. JAVAFX UI GAME HOOKS
    // ==============================================================================
    @Override
    public Optional<String> getGameId() {
        return Optional.of("equation-duel");
    }

    @Override
    public void launchGame(Runnable onFinished) {
        final Runnable callback = (onFinished != null) ? onFinished : () -> { };

        if (GraphicsEnvironment.isHeadless()) {
            callback.run();
            return;
        }

        javafx.application.Platform.runLater(() -> {
            try {
                EquationDuelFX game = new EquationDuelFX(callback);
                game.show();
            } catch (Throwable t) {
                t.printStackTrace();
                callback.run();
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
                new Question("Which of these is a linear equation in one variable?", Arrays.asList("x² + 3 = 7", "2x + 5 = 11", "x + y = 4", "1/x = 3"), 1, WHAT_IS_LINEAR),
                new Question("The solution (root) of an equation is the value of the variable that:", Arrays.asList("makes both sides equal", "makes the left side zero", "makes the equation longer", "is always positive"), 0, WHAT_IS_LINEAR),
                new Question("Is x = 3 a solution of 2x + 1 = 7?", Arrays.asList("Yes, because both sides equal 7", "No, because the left side is 6", "No, because the left side is 5", "Yes, because x is positive"), 0, WHAT_IS_LINEAR),
                new Question("Solve: x + 7 = 12", Arrays.asList("19", "5", "-5", "7"), 1, SOLVE_BASIC),
                new Question("Solve: 3x = 18", Arrays.asList("6", "15", "21", "54"), 0, SOLVE_BASIC),
                new Question("Solve: 2x + 3 = 11", Arrays.asList("7", "4", "8", "14"), 1, SOLVE_BASIC),
                new Question("Solve: 5x - 3 = 2x + 9", Arrays.asList("2", "3", "4", "6"), 2, BOTH_SIDES),
                new Question("Solve: 6x + 2 = 2x + 22", Arrays.asList("4", "5", "6", "10"), 1, BOTH_SIDES),
                new Question("When you move a term to the other side of an equation (transposition), its sign:", Arrays.asList("stays the same", "changes (+ becomes -, - becomes +)", "becomes zero", "is doubled"), 1, BOTH_SIDES),
                new Question("Solve: 3(x + 2) = 15", Arrays.asList("3", "5", "7", "13"), 0, BRACKETS_FRACTIONS),
                new Question("Solve: x/2 + 1 = 4", Arrays.asList("3", "6", "8", "10"), 1, BRACKETS_FRACTIONS),
                new Question("Solve: 2x/3 = 8", Arrays.asList("12", "16", "24", "11"), 0, BRACKETS_FRACTIONS),
                new Question("A number increased by 9 gives 25. Which equation represents this?", Arrays.asList("x - 9 = 25", "x + 9 = 25", "9x = 25", "x/9 = 25"), 1, WORD_PROBLEMS),
                new Question("The sum of two consecutive numbers is 41. If the smaller number is x, the equation is:", Arrays.asList("x + (x + 1) = 41", "x + (x + 2) = 41", "x(x + 1) = 41", "2x = 42"), 0, WORD_PROBLEMS),
                new Question("A father's age is 3 times his son's age, and together they are 48. How old is the son?", Arrays.asList("9", "12", "16", "36"), 1, WORD_PROBLEMS)
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
                new LessonContent(WHAT_IS_LINEAR, "A linear equation in one variable is an equation where the variable has power 1 and appears only once as a letter. The solution is the value of the variable that makes both sides equal.", "ax + b = c", Arrays.asList("2x + 5 = 11 is linear.", "x² + 3 = 7 is not linear.")),
                new LessonContent(SOLVE_BASIC, "Undo what has been done to x using inverse operations, and do the same thing to both sides so the equation stays balanced.", "Do the same to both sides", Arrays.asList("x + 7 = 12 -> x = 12 - 7 = 5")),
                new LessonContent(BOTH_SIDES, "Move all x terms to one side and numbers to the other. Transposition changes its sign.", "+ becomes -, and - becomes +", Arrays.asList("5x - 3 = 2x + 9 -> 3x = 12 -> x = 4")),
                new LessonContent(BRACKETS_FRACTIONS, "Remove brackets first. For fractions, multiply every term by the LCM to clear them.", "a(b + c) = ab + ac", Arrays.asList("3(x + 2) = 15 -> 3x + 6 = 15 -> 3x = 9 -> x = 3")),
                new LessonContent(WORD_PROBLEMS, "Let the unknown be x, translate each sentence into an equation, solve it.", "Let unknown = x", Arrays.asList("Number increased by 9 gives 25: x + 9 = 25, so x = 16"))
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
    // INNER CLASS: JAVAFX GAME IMPLEMENTATION
    // ==============================================================================
    public static class EquationDuelFX extends javafx.stage.Stage {

        private enum GameState {
            PLAYER_TURN, MONSTER_TURN, GAME_OVER
        }

        private int playerHP = 100;
        private int monsterHP = 100;
        private int expectedX;
        private GameState currentState;
        private final Random random = new Random();

        // Control Animations
        private javafx.animation.Timeline defenseTimer;
        private javafx.animation.TranslateTransition wizHover;
        private javafx.animation.TranslateTransition dragHover;
        private javafx.animation.ScaleTransition orbPulse;
        private javafx.animation.ScaleTransition wingFlap;

        private int timeLeft = 15;
        private boolean inputLocked = false;

        private final Runnable onFinished;
        private boolean finishedNotified = false;

        // UI Components
        private javafx.scene.control.ProgressBar playerHealthBar;
        private javafx.scene.control.ProgressBar monsterHealthBar;
        private javafx.scene.control.TextArea battleLog;
        private javafx.scene.control.Label lblEquation;
        private javafx.scene.control.Label lblPhase;
        private javafx.scene.control.Label lblTimer;
        private javafx.scene.control.TextField txtInput;
        private javafx.scene.control.Button btnAction;

        // Arena Animation Nodes
        private javafx.scene.layout.Pane arena;
        private javafx.scene.Group wizardNode;
        private javafx.scene.Group dragonNode;
        private javafx.scene.shape.Circle fireball;
        private javafx.scene.shape.Polygon dragonBreath;
        private javafx.scene.shape.Circle magicShield;

        private final String BG_HEX = "#111116";
        private final String PANEL_HEX = "#1e1e24";

        public EquationDuelFX(Runnable onFinished) {
            this.onFinished = onFinished;
            setTitle("Equation Duel: Wizard vs. Dragon");
            setResizable(false);
            setOnHidden(e -> notifyFinished());

            javafx.scene.layout.BorderPane root = new javafx.scene.layout.BorderPane();
            root.setStyle("-fx-background-color: " + BG_HEX + ";");

            // --- TOP PANEL (Health Bars) ---
            javafx.scene.layout.HBox topBox = new javafx.scene.layout.HBox(40);
            topBox.setAlignment(javafx.geometry.Pos.CENTER);
            topBox.setPadding(new javafx.geometry.Insets(20));
            topBox.setStyle("-fx-background-color: " + PANEL_HEX + "; -fx-border-color: #333; -fx-border-width: 0 0 2 0;");

            javafx.scene.layout.VBox pBox = createHealthBox("WIZARD (You)", "#00ffff", true);
            playerHealthBar = (javafx.scene.control.ProgressBar) pBox.getChildren().get(1);

            javafx.scene.layout.VBox mBox = createHealthBox("ANCIENT DRAGON", "#ff4444", false);
            monsterHealthBar = (javafx.scene.control.ProgressBar) mBox.getChildren().get(1);

            topBox.getChildren().addAll(pBox, mBox);
            root.setTop(topBox);

            // --- CENTER PANEL (Arena & Battle Log) ---
            javafx.scene.layout.VBox centerContent = new javafx.scene.layout.VBox();
            setupArena();

            battleLog = new javafx.scene.control.TextArea();
            battleLog.setEditable(false);
            battleLog.setWrapText(true);
            battleLog.setPrefHeight(120);
            battleLog.setStyle("-fx-control-inner-background: #0a0a0c; -fx-text-fill: #dddddd; -fx-font-family: 'Monospaced'; -fx-font-size: 14px; -fx-border-color: #ffaa00; -fx-border-width: 2;");

            centerContent.getChildren().addAll(arena, battleLog);
            root.setCenter(centerContent);

            // --- BOTTOM PANEL (Controls) ---
            javafx.scene.layout.VBox bottomBox = new javafx.scene.layout.VBox(15);
            bottomBox.setAlignment(javafx.geometry.Pos.CENTER);
            bottomBox.setPadding(new javafx.geometry.Insets(20));
            bottomBox.setStyle("-fx-background-color: " + PANEL_HEX + "; -fx-border-color: #333; -fx-border-width: 2 0 0 0;");

            lblPhase = createLabel("PHASE", "#ffaa00", 20, true);
            lblEquation = createLabel("2x + 5 = 15", "#ffffff", 36, true);
            lblTimer = createLabel("", "#ff4444", 18, true);

            javafx.scene.layout.HBox inputBox = new javafx.scene.layout.HBox(15);
            inputBox.setAlignment(javafx.geometry.Pos.CENTER);

            javafx.scene.control.Label lblX = createLabel("x = ", "#ffffff", 28, true);
            txtInput = new javafx.scene.control.TextField();
            txtInput.setPrefWidth(80);
            txtInput.setStyle("-fx-font-size: 24px; -fx-background-color: #333; -fx-text-fill: white; -fx-alignment: center;");
            txtInput.setOnAction(e -> processTurn()); // trigger on Enter

            btnAction = new javafx.scene.control.Button("CAST SPELL");
            btnAction.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-background-color: #ffaa00; -fx-text-fill: black; -fx-cursor: hand;");
            btnAction.setOnAction(e -> processTurn());

            inputBox.getChildren().addAll(lblX, txtInput, btnAction);
            bottomBox.getChildren().addAll(lblPhase, lblEquation, lblTimer, inputBox);
            root.setBottom(bottomBox);

            // Timer Setup
            defenseTimer = new javafx.animation.Timeline(
                    new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
                        timeLeft--;
                        lblTimer.setText("⏳ Time remaining: " + timeLeft + "s");
                        if (timeLeft <= 0) {
                            defenseTimer.stop();
                            log("⏳ TIME'S UP! You failed to raise your shield!");
                            txtInput.setText("");
                            handleMonsterAttack(false);
                        }
                    })
            );
            defenseTimer.setCycleCount(javafx.animation.Animation.INDEFINITE);

            javafx.scene.Scene scene = new javafx.scene.Scene(root, 800, 700);
            setScene(scene);

            log("⚔️ A wild ANCIENT DRAGON appears!");
            log("Solve the linear equations to attack and defend.");
            updateHealthBars();
            startPlayerTurn();
        }

        private void setupArena() {
            arena = new javafx.scene.layout.Pane();
            arena.setPrefSize(800, 250);
            arena.setStyle("-fx-background-color: " + BG_HEX + ";");

            // 1. HIGH-QUALITY WIZARD
            wizardNode = createWizard();
            wizardNode.setLayoutX(150);
            wizardNode.setLayoutY(140);

            // 2. HIGH-QUALITY DRAGON (Now flipped to face the Wizard)
            dragonNode = createDragon();
            dragonNode.setLayoutX(600);
            dragonNode.setLayoutY(140);

            // Hover Animations
            wizHover = new javafx.animation.TranslateTransition(javafx.util.Duration.seconds(1.5), wizardNode);
            wizHover.setByY(-10);
            wizHover.setAutoReverse(true);
            wizHover.setCycleCount(javafx.animation.Animation.INDEFINITE);
            wizHover.play();

            dragHover = new javafx.animation.TranslateTransition(javafx.util.Duration.seconds(1.2), dragonNode);
            dragHover.setByY(-15);
            dragHover.setAutoReverse(true);
            dragHover.setCycleCount(javafx.animation.Animation.INDEFINITE);
            dragHover.play();

            // 3. PROJECTILES
            fireball = new javafx.scene.shape.Circle(18);
            javafx.scene.paint.RadialGradient fbGrad = new javafx.scene.paint.RadialGradient(
                    0, 0, 0.5, 0.5, 0.5, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                    new javafx.scene.paint.Stop(0, javafx.scene.paint.Color.WHITE),
                    new javafx.scene.paint.Stop(0.3, javafx.scene.paint.Color.YELLOW),
                    new javafx.scene.paint.Stop(1, javafx.scene.paint.Color.RED)
            );
            fireball.setFill(fbGrad);
            fireball.setEffect(new javafx.scene.effect.DropShadow(15, javafx.scene.paint.Color.ORANGE));
            fireball.setVisible(false);

            // Dragon Breath (Redesigned to face and expand to the LEFT)
            dragonBreath = new javafx.scene.shape.Polygon(0, 0, -80, -35, -80, 35);
            javafx.scene.paint.LinearGradient breathGrad = new javafx.scene.paint.LinearGradient(
                    0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                    new javafx.scene.paint.Stop(0, javafx.scene.paint.Color.rgb(255, 255, 0, 0)), // Transparent yellow on left
                    new javafx.scene.paint.Stop(1, javafx.scene.paint.Color.RED)                   // Solid red on right (at mouth)
            );
            dragonBreath.setFill(breathGrad);
            dragonBreath.setVisible(false);

            magicShield = new javafx.scene.shape.Circle(75, javafx.scene.paint.Color.rgb(0, 255, 255, 0.2));
            magicShield.setStroke(javafx.scene.paint.Color.CYAN);
            magicShield.setStrokeWidth(5);
            magicShield.setEffect(new javafx.scene.effect.DropShadow(20, javafx.scene.paint.Color.CYAN));
            magicShield.setVisible(false);

            arena.getChildren().addAll(wizardNode, dragonNode, fireball, dragonBreath, magicShield);
        }

        // ==========================================
        // CUSTOM VECTOR GRAPHICS FOR WIZARD
        // ==========================================
        private javafx.scene.Group createWizard() {
            javafx.scene.Group w = new javafx.scene.Group();
            javafx.scene.effect.DropShadow glow = new javafx.scene.effect.DropShadow(20, javafx.scene.paint.Color.rgb(100, 100, 255, 0.6));

            // Cloak
            javafx.scene.shape.Polygon cloak = new javafx.scene.shape.Polygon(-35, 70, 35, 70, 20, -10, -20, -10);
            cloak.setFill(new javafx.scene.paint.LinearGradient(0, 0, 0, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                    new javafx.scene.paint.Stop(0, javafx.scene.paint.Color.web("#6a11cb")),
                    new javafx.scene.paint.Stop(1, javafx.scene.paint.Color.web("#2575fc"))));

            // Face
            javafx.scene.shape.Circle face = new javafx.scene.shape.Circle(0, -15, 18, javafx.scene.paint.Color.web("#ffccaa"));

            // Fluffy Beard
            javafx.scene.Group beard = new javafx.scene.Group(
                    new javafx.scene.shape.Circle(0, 5, 20, javafx.scene.paint.Color.WHITE),
                    new javafx.scene.shape.Circle(-15, 0, 15, javafx.scene.paint.Color.WHITE),
                    new javafx.scene.shape.Circle(15, 0, 15, javafx.scene.paint.Color.WHITE),
                    new javafx.scene.shape.Polygon(-25, 5, 25, 5, 0, 45) // Pointy bit
            );
            ((javafx.scene.shape.Polygon)beard.getChildren().get(3)).setFill(javafx.scene.paint.Color.WHITE);

            // Wizard Hat
            javafx.scene.shape.Polygon hatBrim = new javafx.scene.shape.Polygon(-40, -30, 40, -30, 0, -40);
            hatBrim.setFill(javafx.scene.paint.Color.web("#3a0ca3"));
            javafx.scene.shape.Polygon hatCone = new javafx.scene.shape.Polygon(-25, -35, 25, -35, 0, -90);
            hatCone.setFill(javafx.scene.paint.Color.web("#4361ee"));
            javafx.scene.shape.Polygon hatBend = new javafx.scene.shape.Polygon(0, -90, -30, -70, -10, -50);
            hatBend.setFill(javafx.scene.paint.Color.web("#4361ee"));

            // Staff
            javafx.scene.shape.Rectangle staff = new javafx.scene.shape.Rectangle(35, -10, 6, 90);
            staff.setFill(javafx.scene.paint.Color.web("#8b5a2b"));
            staff.setArcWidth(4); staff.setArcHeight(4);

            // Glowing Orb
            javafx.scene.shape.Circle orb = new javafx.scene.shape.Circle(38, -15, 12, javafx.scene.paint.Color.web("#00ffff"));
            orb.setEffect(new javafx.scene.effect.DropShadow(25, javafx.scene.paint.Color.CYAN));

            // Orb Pulse Animation (Saved in field)
            orbPulse = new javafx.animation.ScaleTransition(javafx.util.Duration.seconds(1), orb);
            orbPulse.setByX(0.2); orbPulse.setByY(0.2);
            orbPulse.setAutoReverse(true);
            orbPulse.setCycleCount(javafx.animation.Animation.INDEFINITE);
            orbPulse.play();

            w.getChildren().addAll(cloak, face, beard, hatBrim, hatCone, hatBend, staff, orb);
            w.setEffect(glow);
            return w;
        }

        // ==========================================
        // CUSTOM VECTOR GRAPHICS FOR DRAGON
        // ==========================================
        private javafx.scene.Group createDragon() {
            javafx.scene.Group d = new javafx.scene.Group();

            // Main Body
            javafx.scene.shape.Path body = new javafx.scene.shape.Path();
            body.getElements().add(new javafx.scene.shape.MoveTo(50, 60));
            body.getElements().add(new javafx.scene.shape.QuadCurveTo(-20, 60, -40, 20));
            body.getElements().add(new javafx.scene.shape.QuadCurveTo(-60, -20, -100, -30));
            body.getElements().add(new javafx.scene.shape.QuadCurveTo(-50, -10, -20, -10));
            body.getElements().add(new javafx.scene.shape.QuadCurveTo(20, -40, 40, -50));
            body.getElements().add(new javafx.scene.shape.QuadCurveTo(60, -30, 70, 0));
            body.getElements().add(new javafx.scene.shape.ClosePath());
            body.setFill(new javafx.scene.paint.LinearGradient(0, 0, 1, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                    new javafx.scene.paint.Stop(0, javafx.scene.paint.Color.web("#ff4b1f")),
                    new javafx.scene.paint.Stop(1, javafx.scene.paint.Color.web("#ff9068"))));

            // Head and Snout
            javafx.scene.shape.Polygon head = new javafx.scene.shape.Polygon(40, -45, 90, -40, 95, -20, 50, -15);
            head.setFill(javafx.scene.paint.Color.web("#ff4b1f"));
            javafx.scene.shape.Polygon jaw = new javafx.scene.shape.Polygon(50, -15, 90, -15, 80, 0, 55, 5);
            jaw.setFill(javafx.scene.paint.Color.web("#d83a15"));

            // Sharp Teeth
            javafx.scene.shape.Polygon tooth1 = new javafx.scene.shape.Polygon(70, -15, 75, -5, 80, -15);
            tooth1.setFill(javafx.scene.paint.Color.WHITE);
            javafx.scene.shape.Polygon tooth2 = new javafx.scene.shape.Polygon(85, -15, 88, -8, 90, -15);
            tooth2.setFill(javafx.scene.paint.Color.WHITE);

            // Glowing Eye
            javafx.scene.shape.Circle eye = new javafx.scene.shape.Circle(70, -30, 5, javafx.scene.paint.Color.web("#ffff00"));
            eye.setEffect(new javafx.scene.effect.DropShadow(10, javafx.scene.paint.Color.YELLOW));
            javafx.scene.shape.Circle pupil = new javafx.scene.shape.Circle(72, -30, 2, javafx.scene.paint.Color.BLACK);

            // Horns
            javafx.scene.shape.Polygon horn1 = new javafx.scene.shape.Polygon(45, -45, 30, -70, 55, -42);
            horn1.setFill(javafx.scene.paint.Color.web("#222222"));
            javafx.scene.shape.Polygon horn2 = new javafx.scene.shape.Polygon(55, -42, 45, -60, 65, -40);
            horn2.setFill(javafx.scene.paint.Color.web("#333333"));

            // Bat-like Wing
            javafx.scene.shape.Path wing = new javafx.scene.shape.Path();
            wing.getElements().add(new javafx.scene.shape.MoveTo(10, -20));
            wing.getElements().add(new javafx.scene.shape.QuadCurveTo(-30, -100, 0, -120));
            wing.getElements().add(new javafx.scene.shape.QuadCurveTo(20, -80, 40, -110));
            wing.getElements().add(new javafx.scene.shape.QuadCurveTo(40, -60, 60, -70));
            wing.getElements().add(new javafx.scene.shape.QuadCurveTo(30, -40, 30, -20));
            wing.setFill(javafx.scene.paint.Color.web("#7a0010"));
            wing.setOpacity(0.95);
            javafx.scene.Group wingGroup = new javafx.scene.Group(wing);

            // Wing Flap Animation
            wingFlap = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(400), wingGroup);
            wingFlap.setByY(-0.7);
            wingFlap.setAutoReverse(true);
            wingFlap.setCycleCount(javafx.animation.Animation.INDEFINITE);
            wingFlap.play();

            d.getChildren().addAll(wingGroup, body, jaw, head, tooth1, tooth2, horn1, horn2, eye, pupil);

            // Flip the entire dragon horizontally so it faces the Wizard (left)
            d.setScaleX(-1);

            // Wrap in an outer group so external animations (like faint rotation) don't conflict with the flipped scale
            javafx.scene.Group wrapper = new javafx.scene.Group(d);
            wrapper.setEffect(new javafx.scene.effect.DropShadow(20, javafx.scene.paint.Color.rgb(255, 50, 0, 0.4)));

            return wrapper;
        }

        private javafx.scene.layout.VBox createHealthBox(String title, String colorHex, boolean isLeft) {
            javafx.scene.layout.VBox box = new javafx.scene.layout.VBox(5);
            box.setAlignment(isLeft ? javafx.geometry.Pos.CENTER_LEFT : javafx.geometry.Pos.CENTER_RIGHT);

            javafx.scene.control.Label name = createLabel(title, colorHex, 18, true);

            javafx.scene.control.ProgressBar bar = new javafx.scene.control.ProgressBar(1.0);
            bar.setPrefSize(250, 25);
            bar.setStyle("-fx-accent: " + colorHex + "; -fx-control-inner-background: #333;");

            box.getChildren().addAll(name, bar);
            return box;
        }

        private javafx.scene.control.Label createLabel(String text, String hexColor, int size, boolean bold) {
            javafx.scene.control.Label lbl = new javafx.scene.control.Label(text);
            String weight = bold ? "bold" : "normal";
            lbl.setStyle("-fx-text-fill: " + hexColor + "; -fx-font-family: 'SansSerif'; -fx-font-weight: " + weight + "; -fx-font-size: " + size + "px;");
            return lbl;
        }

        private void showDamageText(String text, double x, double y, javafx.scene.paint.Color color) {
            javafx.scene.text.Text dmgText = new javafx.scene.text.Text(text);
            dmgText.setFont(javafx.scene.text.Font.font("Impact", 36));
            dmgText.setFill(color);
            dmgText.setStroke(javafx.scene.paint.Color.WHITE);
            dmgText.setStrokeWidth(1);
            dmgText.setX(x);
            dmgText.setY(y);
            arena.getChildren().add(dmgText);

            javafx.animation.TranslateTransition floatUp = new javafx.animation.TranslateTransition(javafx.util.Duration.seconds(1.2), dmgText);
            floatUp.setByY(-60);

            javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(javafx.util.Duration.seconds(1.2), dmgText);
            fade.setToValue(0);

            javafx.animation.ParallelTransition pt = new javafx.animation.ParallelTransition(floatUp, fade);
            pt.setOnFinished(e -> arena.getChildren().remove(dmgText));
            pt.play();
        }

        private void notifyFinished() {
            if (finishedNotified) return;
            finishedNotified = true;
            if (onFinished != null) onFinished.run();
        }

        private void log(String message) {
            battleLog.appendText(message + "\n");
        }

        private void startPlayerTurn() {
            currentState = GameState.PLAYER_TURN;
            inputLocked = false;
            lblPhase.setText("⚔️ ATTACK PHASE: Solve for x to cast Fireball!");
            lblPhase.setStyle(lblPhase.getStyle().replaceFirst("-fx-text-fill: #[a-zA-Z0-9]+;", "-fx-text-fill: #00ffff;"));
            lblTimer.setText("");
            btnAction.setText("CAST FIREBALL");
            btnAction.setStyle(btnAction.getStyle().replaceFirst("-fx-background-color: #[a-zA-Z0-9]+;", "-fx-background-color: #00ffff;"));

            int x = random.nextInt(10) + 2;
            int a = random.nextInt(6) + 2;
            int b = random.nextInt(20) - 10;
            int c = (a * x) + b;

            expectedX = x;
            String sign = (b >= 0) ? "+ " + b : "- " + Math.abs(b);
            lblEquation.setText(a + "x " + sign + " = " + c);

            txtInput.setText("");
            txtInput.setDisable(false);
            txtInput.requestFocus();
        }

        private void startMonsterTurn() {
            currentState = GameState.MONSTER_TURN;
            inputLocked = false;
            lblPhase.setText("🛡️ DEFENSE PHASE: Quick! Solve to raise your Shield!");
            lblPhase.setStyle(lblPhase.getStyle().replaceFirst("-fx-text-fill: #[a-zA-Z0-9]+;", "-fx-text-fill: #ffaa00;"));
            btnAction.setText("RAISE SHIELD");
            btnAction.setStyle(btnAction.getStyle().replaceFirst("-fx-background-color: #[a-zA-Z0-9]+;", "-fx-background-color: #ffaa00;"));

            int x = random.nextInt(10) + 2;
            int a = random.nextInt(4) + 2;
            int b = random.nextInt(8) + 1;
            int c = a * (x + b);

            expectedX = x;
            lblEquation.setText(a + "(x + " + b + ") = " + c);

            txtInput.setText("");
            txtInput.setDisable(false);
            txtInput.requestFocus();

            timeLeft = 15;
            lblTimer.setText("⏳ Time remaining: " + timeLeft + "s");
            defenseTimer.playFromStart();
        }

        private void processTurn() {
            if (currentState == GameState.GAME_OVER || inputLocked) return;

            String input = txtInput.getText().trim();
            if (input.isEmpty()) return;

            try {
                int playerAns = Integer.parseInt(input);
                inputLocked = true;
                txtInput.setDisable(true);

                if (currentState == GameState.PLAYER_TURN) {
                    handlePlayerAttack(playerAns == expectedX);
                } else if (currentState == GameState.MONSTER_TURN) {
                    defenseTimer.stop();
                    handleMonsterAttack(playerAns == expectedX);
                }
            } catch (NumberFormatException ex) {
                log("⚠️ System Error: Enter a valid number for x!");
                txtInput.setText("");
                txtInput.setDisable(false);
                inputLocked = false;
            }
        }

        private void handlePlayerAttack(boolean isCorrect) {
            fireball.setLayoutX(wizardNode.getLayoutX() + 45);
            fireball.setLayoutY(wizardNode.getLayoutY() - 15);
            fireball.setVisible(true);

            javafx.animation.TranslateTransition flight = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(500), fireball);
            flight.setFromX(0); flight.setFromY(0);

            if (isCorrect) {
                // Since the dragon is flipped, its body extends further to the left of its anchor
                flight.setToX(dragonNode.getLayoutX() - wizardNode.getLayoutX() - 100);
                flight.setToY(-10);
                flight.setOnFinished(e -> {
                    fireball.setVisible(false);

                    // Dragon Flinch
                    javafx.animation.ScaleTransition flinch = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(150), dragonNode);
                    flinch.setByX(-0.1); flinch.setByY(-0.1);
                    flinch.setAutoReverse(true);
                    flinch.setCycleCount(2);
                    flinch.play();

                    int damage = 20 + random.nextInt(10);
                    monsterHP -= damage;
                    showDamageText("-" + damage, dragonNode.getLayoutX() - 50, dragonNode.getLayoutY() - 50, javafx.scene.paint.Color.ORANGE);

                    log("🔥 CRITICAL HIT! Your Fireball deals " + damage + " damage to the Dragon!");
                    updateHealthBars();
                    checkGameOverAndProceed(GameState.PLAYER_TURN);
                });
            } else {
                flight.setToX(200);
                flight.setToY(120); // Drops down harmlessly
                flight.setOnFinished(e -> {
                    fireball.setVisible(false);
                    log("💨 MISFIRE! Your math was wrong (" + expectedX + "). The spell fizzles out.");
                    checkGameOverAndProceed(GameState.PLAYER_TURN);
                });
            }
            flight.play();
        }

        private void handleMonsterAttack(boolean isCorrect) {
            // Dragon's mouth is on its left side now
            dragonBreath.setLayoutX(dragonNode.getLayoutX() - 70);
            dragonBreath.setLayoutY(dragonNode.getLayoutY() - 25);
            dragonBreath.setVisible(true);

            if (isCorrect) {
                magicShield.setLayoutX(wizardNode.getLayoutX());
                magicShield.setLayoutY(wizardNode.getLayoutY());
                magicShield.setVisible(true);
            }

            javafx.animation.TranslateTransition blast = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(500), dragonBreath);
            blast.setFromX(0); blast.setFromY(0);
            // Move left towards the wizard
            blast.setToX(wizardNode.getLayoutX() - dragonNode.getLayoutX() + 70);
            blast.setToY(0);

            blast.setOnFinished(e -> {
                dragonBreath.setVisible(false);

                if (isCorrect) {
                    javafx.animation.FadeTransition fadeShield = new javafx.animation.FadeTransition(javafx.util.Duration.millis(300), magicShield);
                    fadeShield.setFromValue(1.0);
                    fadeShield.setToValue(0.0);
                    fadeShield.setOnFinished(ev -> magicShield.setVisible(false));
                    fadeShield.play();

                    int damage = random.nextInt(5) + 1;
                    playerHP -= damage;
                    showDamageText("BLOCKED -" + damage, wizardNode.getLayoutX() - 40, wizardNode.getLayoutY() - 70, javafx.scene.paint.Color.CYAN);
                    log("🛡️ SHIELD UP! You blocked the Dragon's breath! You only take " + damage + " splash damage.");
                } else {
                    // Wizard Flinch
                    javafx.animation.ScaleTransition flinch = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(150), wizardNode);
                    flinch.setByX(-0.1); flinch.setByY(-0.1);
                    flinch.setAutoReverse(true);
                    flinch.setCycleCount(2);
                    flinch.play();

                    int damage = 25 + random.nextInt(15);
                    playerHP -= damage;
                    showDamageText("-" + damage, wizardNode.getLayoutX() - 20, wizardNode.getLayoutY() - 70, javafx.scene.paint.Color.RED);
                    log("💥 SHIELD SHATTERED! The Dragon's breath scorches you for " + damage + " damage! (Correct x was " + expectedX + ")");
                }

                updateHealthBars();
                checkGameOverAndProceed(GameState.MONSTER_TURN);
            });
            blast.play();
        }

        // ==========================================
        // FAINT (DEATH) ANIMATION
        // ==========================================
        private void playFaintAnimation(javafx.scene.Node node, double rotationAngle) {
            javafx.animation.FadeTransition fade = new javafx.animation.FadeTransition(javafx.util.Duration.seconds(1.5), node);
            fade.setToValue(0.0);

            javafx.animation.RotateTransition rotate = new javafx.animation.RotateTransition(javafx.util.Duration.seconds(1.5), node);
            rotate.setByAngle(rotationAngle);

            javafx.animation.TranslateTransition sink = new javafx.animation.TranslateTransition(javafx.util.Duration.seconds(1.5), node);
            sink.setByY(100);

            javafx.animation.ParallelTransition pt = new javafx.animation.ParallelTransition(fade, rotate, sink);
            pt.play();
        }

        private void checkGameOverAndProceed(GameState justFinishedState) {
            if (playerHP <= 0 || monsterHP <= 0) {
                currentState = GameState.GAME_OVER;
                defenseTimer.stop();
                btnAction.setDisable(true);
                lblTimer.setText("");

                if (playerHP <= 0 && monsterHP <= 0) {
                    log("💀 MUTUAL DESTRUCTION! Both you and the Dragon fall.");
                    lblPhase.setText("DRAW! Close window to return.");
                    wizHover.stop(); dragHover.stop(); orbPulse.stop(); wingFlap.stop();
                    playFaintAnimation(wizardNode, -90);
                    playFaintAnimation(dragonNode, 90);

                } else if (monsterHP <= 0) {
                    log("🏆 VICTORY! You have slain the Ancient Dragon!");
                    lblPhase.setText("VICTORY! Close window to continue.");
                    lblPhase.setStyle(lblPhase.getStyle().replaceFirst("-fx-text-fill: #[a-zA-Z0-9]+;", "-fx-text-fill: #00ff00;"));
                    dragHover.stop(); wingFlap.stop();
                    playFaintAnimation(dragonNode, 90);

                } else {
                    log("☠️ DEFEAT. The Dragon overwhelms you.");
                    lblPhase.setText("GAME OVER! Close window to continue.");
                    lblPhase.setStyle(lblPhase.getStyle().replaceFirst("-fx-text-fill: #[a-zA-Z0-9]+;", "-fx-text-fill: #ff0000;"));
                    wizHover.stop(); orbPulse.stop();
                    playFaintAnimation(wizardNode, -90);
                }
            } else {
                if (justFinishedState == GameState.PLAYER_TURN) {
                    startMonsterTurn();
                } else {
                    startPlayerTurn();
                }
            }
        }

        private void updateHealthBars() {
            if (playerHP < 0) playerHP = 0;
            if (monsterHP < 0) monsterHP = 0;
            playerHealthBar.setProgress(playerHP / 100.0);
            monsterHealthBar.setProgress(monsterHP / 100.0);
        }
    }
}