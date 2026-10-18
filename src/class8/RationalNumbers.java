package class8;

import common.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * Class 8 Mathematics - Rational Numbers
 * Prerequisite test -> Lesson (with playable game) -> Lesson test
 */
public class RationalNumbers extends Topic {

    // Lesson concepts
    private static final Concept WHAT_IS_RATIONAL = new Concept("RN1", "What is a Rational Number");
    private static final Concept STANDARD_FORM = new Concept("RN2", "Standard Form of a Rational Number");
    private static final Concept COMPARING = new Concept("RN3", "Comparing and Ordering Rational Numbers");
    private static final Concept ADD_SUBTRACT = new Concept("RN4", "Addition and Subtraction of Rational Numbers");
    private static final Concept MULTIPLY_DIVIDE = new Concept("RN5", "Multiplication and Division of Rational Numbers");

    // Prerequisite concepts
    private static final Concept FRACTION_BASICS = new Concept("RN6", "Fractions: Simplifying and Comparing");
    private static final Concept INTEGER_OPERATIONS = new Concept("RN7", "Operations on Integers");
    private static final Concept HCF_LCM = new Concept("RN8", "HCF and LCM");

    public RationalNumbers() {
        super("Class 8", "Rational Numbers");
    }

    // ==============================================================================
    // 1. PREREQUISITE TEST
    // ==============================================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "Simplify the fraction 12/18",
                        Arrays.asList("1/2", "2/3", "3/4", "4/6"),
                        1,
                        FRACTION_BASICS
                ),
                new Question(
                        "Which fraction is equal to 3/5?",
                        Arrays.asList("6/10", "6/15", "9/20", "5/3"),
                        0,
                        FRACTION_BASICS
                ),
                new Question(
                        "Which is greater: 3/4 or 2/3?",
                        Arrays.asList("3/4", "2/3", "They are equal", "Cannot be compared"),
                        0,
                        FRACTION_BASICS
                ),
                new Question(
                        "What is (-7) + 10?",
                        Arrays.asList("-17", "-3", "3", "17"),
                        2,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is (-6) x (-4)?",
                        Arrays.asList("-24", "-10", "10", "24"),
                        3,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is (-12) ÷ 3?",
                        Arrays.asList("4", "-4", "-9", "36"),
                        1,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is the HCF of 12 and 18?",
                        Arrays.asList("2", "3", "6", "36"),
                        2,
                        HCF_LCM
                ),
                new Question(
                        "What is the LCM of 4 and 6?",
                        Arrays.asList("10", "12", "24", "2"),
                        1,
                        HCF_LCM
                )
        );
        return new Test(questions);
    }

    // ==============================================================================
    // 2. LESSON (+ GAME)
    // ==============================================================================

    /**
     * Console flow only. The JavaFX screens do NOT call this; they use
     * getLessonContentForDisplay() and the Play Game button (getGameId / launchGame).
     */
    @Override
    protected void teachLesson() {
        System.out.println("=== STEP 2: LESSON CONTENT ===");
        getLessonContentBank().forEach(LessonContent::display);

        // Never block the JavaFX thread (it would freeze the whole app)
        if (isJavaFxThread() || GraphicsEnvironment.isHeadless()) {
            return;
        }

        System.out.println("\n=======================================================");
        System.out.println("   LESSON COMPLETE. LAUNCHING FRACTION RUNNER GAME...  ");
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
    // 3. GAME HOOKS (used by the JavaFX LessonScreen "Play Game" button)
    // ==============================================================================
    @Override
    public java.util.Optional<String> getGameId() {
        return java.util.Optional.of("fraction-runner");
    }

    /**
     * Opens the game window WITHOUT blocking the caller.
     * onFinished runs when the game window is closed (or if it fails to open).
     */
    @Override
    public void launchGame(Runnable onFinished) {
        final Runnable callback = (onFinished != null) ? onFinished : () -> { };

        if (GraphicsEnvironment.isHeadless()) {
            callback.run();
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) { }

            try {
                FractionRunnerGUI game = new FractionRunnerGUI(callback);
                game.setVisible(true);
                game.toFront();
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
                new Question(
                        "A rational number is a number that can be written as p/q where:",
                        Arrays.asList("p and q are integers and q ≠ 0", "p and q are integers and p ≠ 0", "p and q are any real numbers", "q is positive and p is negative"),
                        0,
                        WHAT_IS_RATIONAL
                ),
                new Question(
                        "Which of these is NOT a rational number?",
                        Arrays.asList("3/7", "-5", "0", "5/0"),
                        3,
                        WHAT_IS_RATIONAL
                ),
                new Question(
                        "Is every integer a rational number?",
                        Arrays.asList("Yes, because n = n/1", "No, only fractions are rational", "Only positive integers are", "Only zero is"),
                        0,
                        WHAT_IS_RATIONAL
                ),
                new Question(
                        "What is the standard form of 6/-8?",
                        Arrays.asList("6/-8", "-3/4", "3/-4", "-6/8"),
                        1,
                        STANDARD_FORM
                ),
                new Question(
                        "A rational number is in standard form when its denominator is positive and:",
                        Arrays.asList("the numerator and denominator have no common factor other than 1", "the numerator is also positive", "the numerator is larger than the denominator", "the denominator is 1"),
                        0,
                        STANDARD_FORM
                ),
                new Question(
                        "Write 15/-35 in standard form.",
                        Arrays.asList("-3/7", "3/7", "-5/7", "-1/7"),
                        0,
                        STANDARD_FORM
                ),
                new Question(
                        "Which is greater: -1/2 or -1/3?",
                        Arrays.asList("-1/2", "-1/3", "They are equal", "Cannot be compared"),
                        1,
                        COMPARING
                ),
                new Question(
                        "Arrange in ascending order: 1/2, -3/4, 0",
                        Arrays.asList("-3/4, 0, 1/2", "0, 1/2, -3/4", "1/2, 0, -3/4", "-3/4, 1/2, 0"),
                        0,
                        COMPARING
                ),
                new Question(
                        "On the number line, a negative rational number lies:",
                        Arrays.asList("to the left of zero", "to the right of zero", "at zero", "above zero"),
                        0,
                        COMPARING
                ),
                new Question(
                        "Find 1/3 + 1/6.",
                        Arrays.asList("2/9", "1/2", "1/3", "2/3"),
                        1,
                        ADD_SUBTRACT
                ),
                new Question(
                        "Find 3/4 - 5/6.",
                        Arrays.asList("-1/12", "1/12", "-2/10", "1/2"),
                        0,
                        ADD_SUBTRACT
                ),
                new Question(
                        "What is the additive inverse of -5/7?",
                        Arrays.asList("5/7", "-7/5", "7/5", "0"),
                        0,
                        ADD_SUBTRACT
                ),
                new Question(
                        "Find (2/3) x (-9/4).",
                        Arrays.asList("-3/2", "3/2", "-11/12", "-2/3"),
                        0,
                        MULTIPLY_DIVIDE
                ),
                new Question(
                        "What is the reciprocal (multiplicative inverse) of -3/8?",
                        Arrays.asList("-8/3", "8/3", "3/8", "-3/8"),
                        0,
                        MULTIPLY_DIVIDE
                ),
                new Question(
                        "Find (3/5) ÷ (9/10).",
                        Arrays.asList("2/3", "27/50", "3/2", "1/3"),
                        0,
                        MULTIPLY_DIVIDE
                )
        );
    }

    @Override
    protected Test getRetest(List<Concept> weakConcepts) {
        List<Question> filtered = getLessonQuestionBank().stream()
                .filter(q -> weakConcepts.contains(q.getConcept()))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return new Test(getLessonQuestionBank());
        }
        return new Test(filtered);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(
                        WHAT_IS_RATIONAL,
                        "A rational number is any number that can be written as a fraction p/q, where p and q "
                                + "are integers and q is not zero. Every integer and every fraction is a rational number.",
                        "Rational number = p/q, where q ≠ 0",
                        Arrays.asList(
                                "5 = 5/1, 0 = 0/1 and -3/4 are all rational numbers",
                                "5/0 is not defined, so it is not a rational number"
                        )
                ),
                new LessonContent(
                        STANDARD_FORM,
                        "A rational number is in standard form when its denominator is positive and its numerator "
                                + "and denominator have no common factor except 1.",
                        "Standard form: denominator > 0 and HCF(p, q) = 1",
                        Arrays.asList(
                                "6/-8 -> -6/8 -> divide by 2 -> -3/4",
                                "15/-35 -> -15/35 -> divide by 5 -> -3/7"
                        )
                ),
                new LessonContent(
                        COMPARING,
                        "To compare rational numbers, give them the same positive denominator (use the LCM) and "
                                + "compare the numerators.",
                        "a/b < c/d  when  a x d < c x b  (for b, d > 0)",
                        Arrays.asList(
                                "Compare -1/2 and -1/3: LCM = 6, so -3/6 and -2/6. Since -2 > -3, -1/3 > -1/2"
                        )
                ),
                new LessonContent(
                        ADD_SUBTRACT,
                        "To add or subtract rational numbers, first write them with a common denominator, then "
                                + "add or subtract the numerators.",
                        "a/b + c/d = (ad + bc) / bd",
                        Arrays.asList(
                                "1/3 + 1/6 = 2/6 + 1/6 = 3/6 = 1/2",
                                "3/4 - 5/6 = 9/12 - 10/12 = -1/12"
                        )
                ),
                new LessonContent(
                        MULTIPLY_DIVIDE,
                        "To multiply rational numbers, multiply the numerators and denominators. "
                                + "To divide, multiply by the reciprocal of the divisor.",
                        "a/b x c/d = ac/bd     a/b ÷ c/d = a/b x d/c",
                        Arrays.asList(
                                "(2/3) x (-9/4) = -18/12 = -3/2",
                                "(3/5) ÷ (9/10) = (3/5) x (10/9) = 2/3"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }

    // ==========================================
    // FRACTION RUNNER GAME IMPLEMENTATION (Swing)
    // ==========================================
    public static class FractionRunnerGUI extends JFrame {

        private final List<Level> levels;
        private int currentLevelIndex = 0;
        private Fraction currentPos;
        private int movesLeft;
        private int totalScore = 0;
        private String lastEquation = "Awaiting liftoff...";
        private boolean inputLocked = false; // blocks extra clicks while a dialog is pending

        private final Runnable onFinished;
        private boolean finishedNotified = false;

        private final GamePanel gamePanel;
        private final JPanel cardPanel;
        private final JLabel statusLabel;
        private final JLabel targetLabel;
        private final JLabel scoreLabel;

        public FractionRunnerGUI(Runnable onFinished) {
            this.onFinished = onFinished;

            setTitle("Fraction Runner: Space Hop Edition");
            setSize(900, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    notifyFinished();
                }
            });
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            this.levels = setupLevels();

            JPanel topPanel = new JPanel(new GridLayout(1, 3));
            topPanel.setBackground(new Color(11, 12, 16));

            statusLabel = createStyledLabel("Fuel (Moves): 0");
            targetLabel = createStyledLabel("Target: ?");
            scoreLabel = createStyledLabel("Score: 0");
            scoreLabel.setForeground(new Color(102, 252, 241));

            topPanel.add(statusLabel);
            topPanel.add(targetLabel);
            topPanel.add(scoreLabel);
            topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            add(topPanel, BorderLayout.NORTH);

            gamePanel = new GamePanel();
            add(gamePanel, BorderLayout.CENTER);

            JPanel bottomContainer = new JPanel(new BorderLayout());
            bottomContainer.setBackground(new Color(31, 40, 51));

            cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
            cardPanel.setBackground(new Color(31, 40, 51));

            JButton restartBtn = new JButton("Abort & Restart");
            styleButton(restartBtn, new Color(235, 87, 87));
            restartBtn.addActionListener(e -> {
                if (inputLocked) return;
                lastEquation = "Level restarted.";
                loadLevel(currentLevelIndex);
            });

            bottomContainer.add(cardPanel, BorderLayout.CENTER);
            bottomContainer.add(restartBtn, BorderLayout.EAST);
            add(bottomContainer, BorderLayout.SOUTH);

            loadLevel(0);
        }

        /** Makes sure the "game finished" callback runs only once. */
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

        private JLabel createStyledLabel(String text) {
            JLabel label = new JLabel(text, SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Monospaced", Font.BOLD, 18));
            return label;
        }

        private void styleButton(JButton btn, Color bg) {
            btn.setFont(new Font("Monospaced", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setBackground(bg);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        private void loadLevel(int index) {
            inputLocked = false;

            if (index >= levels.size()) {
                JOptionPane.showMessageDialog(this, "MISSION COMPLETED!\nFinal Game Score: " + totalScore, "Victory", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // windowClosed tells the app to continue
                return;
            }

            Level level = levels.get(index);
            currentPos = new Fraction(0, 1);
            movesLeft = level.getMoveLimit();

            updateTopLabels(level);
            buildCardButtons(level);

            gamePanel.setLevelData(level, currentPos);
            gamePanel.repaint();
        }

        private void updateTopLabels(Level level) {
            statusLabel.setText("Fuel (Moves): " + movesLeft);
            targetLabel.setText("Sector " + level.getLevelNumber() + " Target: " + level.getTarget().toString());
            scoreLabel.setText("Score: " + totalScore);
        }

        private void buildCardButtons(Level level) {
            cardPanel.removeAll();
            for (ActionCard card : level.getAvailableCards()) {
                JButton cardBtn = new JButton(card.toString());
                styleButton(cardBtn, new Color(69, 162, 158));
                cardBtn.setPreferredSize(new Dimension(140, 60));

                cardBtn.addActionListener(e -> processMove(card, level));
                cardPanel.add(cardBtn);
            }
            cardPanel.revalidate();
            cardPanel.repaint();
        }

        private void processMove(ActionCard card, Level level) {
            if (inputLocked || movesLeft <= 0) return;

            Fraction oldPos = currentPos;
            currentPos = card.apply(currentPos);
            movesLeft--;

            lastEquation = oldPos + " " + card.getOperation() + " " + card.getValue() + " = " + currentPos;

            updateTopLabels(level);
            gamePanel.setLevelData(level, currentPos);
            gamePanel.repaint();

            final boolean reached = currentPos.equals(level.getTarget());
            final boolean outOfFuel = !reached && movesLeft == 0;

            if (reached || outOfFuel) {
                inputLocked = true; // ignore extra clicks until the dialog is handled
                SwingUtilities.invokeLater(() -> {
                    if (reached) {
                        int levelScore = 100 + (movesLeft * 50);
                        totalScore += levelScore;
                        JOptionPane.showMessageDialog(this, "Target Reached!\nLevel Score: " + levelScore, "Sector Cleared", JOptionPane.INFORMATION_MESSAGE);
                        lastEquation = "Warping to next sector...";
                        currentLevelIndex++;
                        loadLevel(currentLevelIndex);
                    } else {
                        JOptionPane.showMessageDialog(this, "Out of fuel! You missed the target.\nEnded at: " + currentPos, "Mission Failed", JOptionPane.ERROR_MESSAGE);
                        lastEquation = "Awaiting liftoff...";
                        loadLevel(currentLevelIndex);
                    }
                });
            }
        }

        // ------------------------------------------------------------------
        // Drawing panel
        // ------------------------------------------------------------------
        class GamePanel extends JPanel {
            private Level level;
            private Fraction playerPos;
            private final List<Star> stars = new ArrayList<>();

            // Stars are stored as fractions of the panel size so they scale with the window
            class Star {
                final double rx, ry;
                final int size;
                Star(Random r) {
                    rx = r.nextDouble();
                    ry = r.nextDouble();
                    size = r.nextInt(3) + 1;
                }
            }

            public GamePanel() {
                setBackground(new Color(11, 12, 16));
                Random rand = new Random();
                for (int i = 0; i < 100; i++) {
                    stars.add(new Star(rand));
                }
            }

            public void setLevelData(Level level, Fraction playerPos) {
                this.level = level;
                this.playerPos = playerPos;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                g2d.setColor(Color.WHITE);
                for (Star star : stars) {
                    g2d.fillOval((int) (star.rx * width), (int) (star.ry * height), star.size, star.size);
                }

                if (level == null || playerPos == null) return;

                int padding = 80;
                int lineY = height / 2 + 50;

                double targetDouble = level.getTarget().toDouble();
                double playerDouble = playerPos.toDouble();

                // The number line always includes 0, the target AND the rocket, so nothing goes off-screen
                double lo = Math.min(0.0, Math.min(targetDouble, playerDouble));
                double hi = Math.max(1.0, Math.max(targetDouble, playerDouble));
                double minView = Math.min(-1.0, Math.floor(lo));
                double maxView = Math.max(2.0, Math.ceil(hi));
                double range = maxView - minView;

                g2d.setColor(new Color(102, 252, 241));
                g2d.setFont(new Font("Monospaced", Font.BOLD, 22));
                g2d.drawString("Math Log: " + lastEquation, padding, 40);

                g2d.setColor(new Color(197, 198, 199));
                g2d.setStroke(new BasicStroke(4));
                g2d.drawLine(padding, lineY, width - padding, lineY);

                g2d.setStroke(new BasicStroke(2));
                g2d.setFont(new Font("Monospaced", Font.BOLD, 16));
                for (int i = (int) minView; i <= (int) maxView; i++) {
                    int x = padding + (int) (((i - minView) / range) * (width - 2 * padding));
                    g2d.drawLine(x, lineY - 15, x, lineY + 15);
                    g2d.drawString(String.valueOf(i), x - 5, lineY + 35);
                }

                int targetX = padding + (int) (((targetDouble - minView) / range) * (width - 2 * padding));
                g2d.setColor(new Color(255, 215, 0));
                g2d.fillOval(targetX - 25, lineY - 25, 50, 50);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString(level.getTarget().toString(), targetX - 10, lineY - 35);

                int playerX = padding + (int) (((playerDouble - minView) / range) * (width - 2 * padding));

                g2d.setColor(Color.ORANGE);
                g2d.fillPolygon(new int[]{playerX - 10, playerX + 10, playerX}, new int[]{lineY - 15, lineY - 15, lineY}, 3);

                g2d.setColor(new Color(235, 87, 87));
                g2d.fillRoundRect(playerX - 15, lineY - 55, 30, 40, 15, 15);

                g2d.setColor(Color.CYAN);
                g2d.fillOval(playerX - 8, lineY - 45, 16, 16);

                g2d.setColor(Color.WHITE);
                g2d.drawString(playerPos.toString(), playerX - 10, lineY - 65);
            }
        }

        // ------------------------------------------------------------------
        // Game model
        // ------------------------------------------------------------------
        static class Fraction {
            private int numerator;
            private int denominator;

            public Fraction(int numerator, int denominator) {
                if (denominator == 0) throw new IllegalArgumentException("Zero denominator");
                this.numerator = numerator;
                this.denominator = denominator;
                simplify();
            }

            private void simplify() {
                int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
                if (gcd != 0) {
                    numerator /= gcd;
                    denominator /= gcd;
                }
                if (denominator < 0) {
                    numerator = -numerator;
                    denominator = -denominator;
                }
            }

            private int gcd(int a, int b) { return b == 0 ? a : gcd(b, a % b); }

            public Fraction add(Fraction o) { return new Fraction(numerator * o.denominator + o.numerator * denominator, denominator * o.denominator); }
            public Fraction subtract(Fraction o) { return new Fraction(numerator * o.denominator - o.numerator * denominator, denominator * o.denominator); }
            public Fraction multiply(Fraction o) { return new Fraction(numerator * o.numerator, denominator * o.denominator); }
            public double toDouble() { return (double) numerator / denominator; }

            // Proper equals/hashCode (the old equals(Fraction) only overloaded, it did not override)
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (!(obj instanceof Fraction)) return false;
                Fraction o = (Fraction) obj;
                return numerator == o.numerator && denominator == o.denominator;
            }

            @Override
            public int hashCode() {
                return Objects.hash(numerator, denominator);
            }

            @Override
            public String toString() {
                if (numerator == 0) return "0";
                if (denominator == 1) return String.valueOf(numerator);
                return numerator + "/" + denominator;
            }
        }

        static class ActionCard {
            private final String operation;
            private final Fraction value;

            public ActionCard(String op, Fraction val) {
                this.operation = op;
                this.value = val;
            }

            public Fraction apply(Fraction current) {
                switch (operation) {
                    case "+": return current.add(value);
                    case "-": return current.subtract(value);
                    case "*": return current.multiply(value);
                    default: return current;
                }
            }

            public String getOperation() { return operation; }
            public Fraction getValue() { return value; }

            @Override
            public String toString() { return operation + " " + value.toString(); }
        }

        static class Level {
            private final int levelNumber;
            private final int moveLimit;
            private final Fraction target;
            private final List<ActionCard> availableCards = new ArrayList<>();

            public Level(int num, Fraction target, int limit) {
                this.levelNumber = num;
                this.target = target;
                this.moveLimit = limit;
            }

            public void addCard(String op, int num, int den) {
                availableCards.add(new ActionCard(op, new Fraction(num, den)));
            }

            public int getLevelNumber() { return levelNumber; }
            public Fraction getTarget() { return target; }
            public int getMoveLimit() { return moveLimit; }
            public List<ActionCard> getAvailableCards() { return availableCards; }
        }

        private static List<Level> setupLevels() {
            List<Level> levels = new ArrayList<>();

            Level l1 = new Level(1, new Fraction(3, 4), 2);
            l1.addCard("+", 1, 2);
            l1.addCard("+", 1, 4);
            levels.add(l1);

            Level l2 = new Level(2, new Fraction(1, 3), 3);
            l2.addCard("+", 5, 6);
            l2.addCard("-", 1, 2);
            l2.addCard("+", 1, 6);
            levels.add(l2);

            Level l3 = new Level(3, new Fraction(5, 8), 3);
            l3.addCard("+", 1, 4);
            l3.addCard("*", 5, 2);
            l3.addCard("-", 1, 8);
            levels.add(l3);

            return levels;
        }
    }
}