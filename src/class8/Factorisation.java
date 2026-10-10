package class8;

import common.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) { }

            try {
                FactorBreakerGame game = new FactorBreakerGame(callback);
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
    // INNER CLASS: THE GAME
    // ==============================================================================
    public static class FactorBreakerGame extends JFrame {

        private int currentLevel = 1;
        private int score = 0;
        private final int MAX_LEVELS = 10;
        private int expectedP, expectedQ, expectedA;
        private int levelType;
        private final Random random = new Random();

        // JavaFX / Core hooks
        private final Runnable onFinished;
        private boolean finishedNotified = false;

        private JLabel lblStatus, lblEquation, lblInstructions;
        private JTextField txtGcf, txtFactor1, txtFactor2;
        private JTextArea txtConsole;
        private JPanel inputPanel, gcfPanel;
        private JButton btnHack;

        private final Color BG_COLOR = new Color(10, 20, 10);
        private final Color FG_COLOR = new Color(0, 255, 65);
        private final Color ERROR_COLOR = new Color(255, 50, 50);
        private final Font TERMINAL_FONT = new Font("Monospaced", Font.BOLD, 16);
        private final Font TITLE_FONT = new Font("Monospaced", Font.BOLD, 36);

        public FactorBreakerGame(Runnable onFinished) {
            this.onFinished = onFinished;

            setTitle("Factor Breaker: Cyber-Heist");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Critical listener to resume the main application when window is closed
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    notifyFinished();
                }
            });

            setLayout(new BorderLayout());
            getContentPane().setBackground(BG_COLOR);
            setLocationRelativeTo(null);

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(BG_COLOR);
            topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            lblStatus = new JLabel("LEVEL: 1   |   SCORE: 0");
            lblStatus.setFont(TERMINAL_FONT);
            lblStatus.setForeground(FG_COLOR);

            JLabel lblTitle = new JLabel("SYSTEM MAINFRAME FIREWALL", SwingConstants.CENTER);
            lblTitle.setFont(new Font("Monospaced", Font.BOLD, 24));
            lblTitle.setForeground(FG_COLOR);

            topPanel.add(lblStatus, BorderLayout.WEST);
            topPanel.add(lblTitle, BorderLayout.CENTER);
            add(topPanel, BorderLayout.NORTH);

            JPanel centerPanel = new JPanel(new GridLayout(2, 1));
            centerPanel.setBackground(BG_COLOR);

            lblEquation = new JLabel("", SwingConstants.CENTER);
            lblEquation.setFont(TITLE_FONT);
            lblEquation.setForeground(Color.WHITE);

            lblInstructions = new JLabel("", SwingConstants.CENTER);
            lblInstructions.setFont(TERMINAL_FONT);
            lblInstructions.setForeground(Color.LIGHT_GRAY);

            centerPanel.add(lblEquation);
            centerPanel.add(lblInstructions);
            add(centerPanel, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.setBackground(BG_COLOR);
            bottomPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

            inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
            inputPanel.setBackground(BG_COLOR);

            gcfPanel = new JPanel();
            gcfPanel.setBackground(BG_COLOR);
            JLabel lblGcf = new JLabel("GCF:");
            lblGcf.setForeground(FG_COLOR);
            lblGcf.setFont(TERMINAL_FONT);
            txtGcf = createTextField();
            gcfPanel.add(lblGcf);
            gcfPanel.add(txtGcf);
            gcfPanel.add(createGreenLabel(" * "));

            inputPanel.add(gcfPanel);
            inputPanel.add(createGreenLabel("( x + "));
            txtFactor1 = createTextField();
            inputPanel.add(txtFactor1);
            inputPanel.add(createGreenLabel(" ) ( x + "));
            txtFactor2 = createTextField();
            inputPanel.add(txtFactor2);
            inputPanel.add(createGreenLabel(" )"));

            btnHack = new JButton("INITIATE HACK");
            btnHack.setFont(TERMINAL_FONT);
            btnHack.setBackground(Color.DARK_GRAY);
            btnHack.setForeground(FG_COLOR);
            btnHack.setFocusPainted(false);
            btnHack.addActionListener(new HackAction());
            inputPanel.add(btnHack);

            bottomPanel.add(inputPanel, BorderLayout.NORTH);

            txtConsole = new JTextArea(8, 50);
            txtConsole.setBackground(new Color(5, 10, 5));
            txtConsole.setForeground(FG_COLOR);
            txtConsole.setFont(TERMINAL_FONT);
            txtConsole.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(txtConsole);
            scrollPane.setBorder(BorderFactory.createLineBorder(FG_COLOR));
            bottomPanel.add(scrollPane, BorderLayout.SOUTH);

            add(bottomPanel, BorderLayout.SOUTH);

            printToConsole("Initializing Factor Breaker v2.0...");
            printToConsole("Agent, crack the polynomial passwords to bypass the firewalls.");
            printToConsole("Use negative numbers if needed (e.g., -4).\n");
            generateLevel();
        }

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

        private JTextField createTextField() {
            JTextField tf = new JTextField(3);
            tf.setFont(TERMINAL_FONT);
            tf.setBackground(Color.BLACK);
            tf.setForeground(Color.WHITE);
            tf.setCaretColor(FG_COLOR);
            tf.setHorizontalAlignment(JTextField.CENTER);
            tf.addActionListener(e -> btnHack.doClick());
            return tf;
        }

        private JLabel createGreenLabel(String text) {
            JLabel lbl = new JLabel(text);
            lbl.setForeground(FG_COLOR);
            lbl.setFont(TITLE_FONT);
            return lbl;
        }

        private void printToConsole(String text) {
            txtConsole.append("> " + text + "\n");
            txtConsole.setCaretPosition(txtConsole.getDocument().getLength());
        }

        private void generateLevel() {
            lblStatus.setText("LEVEL: " + currentLevel + " / " + MAX_LEVELS + "   |   SCORE: " + score);

            if (currentLevel <= 3) levelType = 1;
            else if (currentLevel <= 6) levelType = 2;
            else levelType = 3;

            expectedA = 1;

            switch (levelType) {
                case 1:
                    expectedP = random.nextInt(9) - 4;
                    expectedQ = random.nextInt(9) - 4;
                    if (expectedP == 0) expectedP = 2;
                    if (expectedQ == 0) expectedQ = 3;
                    gcfPanel.setVisible(false);
                    lblInstructions.setText("Hint: What multiplies to C and adds to B?");
                    break;
                case 2:
                    expectedP = random.nextInt(6) + 3;
                    expectedQ = -expectedP;
                    gcfPanel.setVisible(false);
                    lblInstructions.setText("Hint: Difference of Squares! The middle term is 0x.");
                    break;
                case 3:
                    expectedP = random.nextInt(7) - 3;
                    expectedQ = random.nextInt(7) - 3;
                    if (expectedP == 0) expectedP = 1;
                    if (expectedQ == 0) expectedQ = -2;
                    expectedA = random.nextInt(3) + 2;
                    gcfPanel.setVisible(true);
                    lblInstructions.setText("Hint: Factor out the Greatest Common Factor (GCF) FIRST!");
                    break;
            }

            int b = expectedA * (expectedP + expectedQ);
            int c = expectedA * (expectedP * expectedQ);

            lblEquation.setText(formatQuadratic(expectedA, b, c));

            txtGcf.setText("");
            txtFactor1.setText("");
            txtFactor2.setText("");
            txtFactor1.requestFocus();
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

            return eq.toString();
        }

        private class HackAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int userP = Integer.parseInt(txtFactor1.getText().trim());
                    int userQ = Integer.parseInt(txtFactor2.getText().trim());
                    int userA = 1;

                    if (levelType == 3) userA = Integer.parseInt(txtGcf.getText().trim());

                    if (levelType == 3 && userA != expectedA) {
                        printToConsole("[ERROR] Incorrect GCF. Look at the numbers in the equation again.");
                        txtConsole.setForeground(ERROR_COLOR);
                        return;
                    }

                    boolean factorsCorrect = (userP == expectedP && userQ == expectedQ) ||
                            (userP == expectedQ && userQ == expectedP);

                    if (factorsCorrect) {
                        txtConsole.setForeground(FG_COLOR);
                        printToConsole("[SUCCESS] Firewall level " + currentLevel + " breached! +100 points.");
                        score += 100;
                        currentLevel++;

                        if (currentLevel > MAX_LEVELS) {
                            lblEquation.setText("ACCESS GRANTED");
                            lblEquation.setForeground(FG_COLOR);
                            lblInstructions.setText("Mainframe secured. Close this window to return to your lesson!");
                            btnHack.setEnabled(false);
                            inputPanel.setVisible(false);
                            lblStatus.setText("MISSION COMPLETE   |   FINAL SCORE: " + score);
                            printToConsole("\n*** MISSION COMPLETE. YOU CAN CLOSE THIS WINDOW NOW. ***");
                        } else {
                            generateLevel();
                        }
                    } else {
                        txtConsole.setForeground(ERROR_COLOR);
                        printToConsole("[ACCESS DENIED] Incorrect factors. Remember: Multiply to get the last number, add to get the middle.");
                    }
                } catch (NumberFormatException ex) {
                    txtConsole.setForeground(ERROR_COLOR);
                    printToConsole("[SYNTAX ERROR] Please enter numbers only! Use '-' for negative numbers.");
                }
            }
        }
    }
}