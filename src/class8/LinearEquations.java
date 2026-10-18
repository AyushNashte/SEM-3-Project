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

        // Never block the JavaFX thread
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
    // 3. JAVAFX UI GAME HOOKS (used by the LessonScreen "Play Game" button)
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

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) { }

            try {
                EquationDuelGame game = new EquationDuelGame(callback);
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
                new LessonContent(WHAT_IS_LINEAR, "A linear equation in one variable is an equation where the variable has power 1 and appears only once as a letter (no x², no 1/x, no second variable). The solution, or root, is the value of the variable that makes both sides equal.", "ax + b = c, where a ≠ 0", Arrays.asList("2x + 5 = 11 is linear. Putting x = 3 gives 2(3) + 5 = 11, so x = 3 is the solution", "x² + 3 = 7 is not linear because the power of x is 2")),
                new LessonContent(SOLVE_BASIC, "To solve an equation, undo what has been done to x using inverse operations, and do the same thing to both sides so the equation stays balanced. Undo addition or subtraction first, then multiplication or division.", "Whatever you do to one side, do to the other", Arrays.asList("x + 7 = 12 -> x = 12 - 7 = 5", "2x + 3 = 11 -> 2x = 8 -> x = 4")),
                new LessonContent(BOTH_SIDES, "When x appears on both sides, move all the x terms to one side and all the numbers to the other. Moving a term across the equals sign (transposition) changes its sign.", "Transposition: + becomes -, and - becomes +", Arrays.asList("5x - 3 = 2x + 9 -> 5x - 2x = 9 + 3 -> 3x = 12 -> x = 4", "6x + 2 = 2x + 22 -> 4x = 20 -> x = 5")),
                new LessonContent(BRACKETS_FRACTIONS, "Remove brackets first by multiplying them out. For fractions, multiply every term by the LCM of the denominators to clear them, then solve as usual.", "a(b + c) = ab + ac", Arrays.asList("3(x + 2) = 15 -> 3x + 6 = 15 -> 3x = 9 -> x = 3", "x/2 + 1 = 4 -> x/2 = 3 -> x = 6")),
                new LessonContent(WORD_PROBLEMS, "To solve a word problem, let the unknown be x, translate each sentence into an equation, solve it, and check the answer against the story.", "Let the unknown = x, form the equation, solve, then verify", Arrays.asList("A number increased by 9 gives 25: x + 9 = 25, so x = 16", "The sum of two consecutive numbers is 31: x + (x + 1) = 31 -> 2x = 30 -> x = 15, so the numbers are 15 and 16"))
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
    public static class EquationDuelGame extends JFrame {

        private enum GameState {
            PLAYER_TURN, MONSTER_TURN, GAME_OVER
        }

        private int playerHP = 100;
        private int monsterHP = 100;
        private int expectedX;
        private GameState currentState;
        private final Random random = new Random();

        private Timer defenseTimer;
        private int timeLeft = 15;

        // JavaFX / Core hooks
        private final Runnable onFinished;
        private boolean finishedNotified = false;

        private JProgressBar playerHealthBar, monsterHealthBar;
        private JTextArea battleLog;
        private JLabel lblEquation, lblPhase, lblTimer, lblHero, lblMonster;
        private JTextField txtInput;
        private JButton btnAction;
        private JPanel inputPanel;

        private final Color BG_COLOR = new Color(20, 20, 25);
        private final Color LOG_BG = new Color(10, 10, 15);
        private final Color TEXT_COLOR = new Color(220, 220, 220);
        private final Color ACCENT_COLOR = new Color(255, 165, 0);
        private final Font MAIN_FONT = new Font("SansSerif", Font.BOLD, 18);
        private final Font EQ_FONT = new Font("Monospaced", Font.BOLD, 32);

        public EquationDuelGame(Runnable onFinished) {
            this.onFinished = onFinished;

            setTitle("Equation Duel: Wizard vs. Dragon");
            setSize(800, 650);
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

            JPanel topPanel = new JPanel(new GridLayout(1, 2, 20, 0));
            topPanel.setBackground(BG_COLOR);
            topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            JPanel playerPanel = new JPanel(new BorderLayout(0, 10));
            playerPanel.setBackground(BG_COLOR);
            lblHero = new JLabel("🧙‍♂️ WIZARD (You)", SwingConstants.LEFT);
            lblHero.setFont(MAIN_FONT);
            lblHero.setForeground(Color.CYAN);
            playerHealthBar = new JProgressBar(0, 100);
            playerHealthBar.setValue(playerHP);
            playerHealthBar.setStringPainted(true);
            playerHealthBar.setForeground(new Color(50, 205, 50));
            playerPanel.add(lblHero, BorderLayout.NORTH);
            playerPanel.add(playerHealthBar, BorderLayout.CENTER);

            JPanel monsterPanel = new JPanel(new BorderLayout(0, 10));
            monsterPanel.setBackground(BG_COLOR);
            lblMonster = new JLabel("🐉 ANCIENT DRAGON", SwingConstants.RIGHT);
            lblMonster.setFont(MAIN_FONT);
            lblMonster.setForeground(Color.RED);
            monsterHealthBar = new JProgressBar(0, 100);
            monsterHealthBar.setValue(monsterHP);
            monsterHealthBar.setStringPainted(true);
            monsterHealthBar.setForeground(Color.RED);
            monsterPanel.add(lblMonster, BorderLayout.NORTH);
            monsterPanel.add(monsterHealthBar, BorderLayout.CENTER);

            topPanel.add(playerPanel);
            topPanel.add(monsterPanel);
            add(topPanel, BorderLayout.NORTH);

            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBackground(BG_COLOR);

            battleLog = new JTextArea();
            battleLog.setBackground(LOG_BG);
            battleLog.setForeground(TEXT_COLOR);
            battleLog.setFont(new Font("Monospaced", Font.PLAIN, 16));
            battleLog.setEditable(false);
            battleLog.setMargin(new Insets(10, 10, 10, 10));
            JScrollPane scrollLog = new JScrollPane(battleLog);
            scrollLog.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 2));
            centerPanel.add(scrollLog, BorderLayout.CENTER);

            JPanel eqPanel = new JPanel(new GridLayout(3, 1));
            eqPanel.setBackground(BG_COLOR);
            eqPanel.setBorder(new EmptyBorder(20, 0, 10, 0));

            lblPhase = new JLabel("PHASE", SwingConstants.CENTER);
            lblPhase.setFont(MAIN_FONT);
            lblPhase.setForeground(ACCENT_COLOR);

            lblEquation = new JLabel("2x + 5 = 15", SwingConstants.CENTER);
            lblEquation.setFont(EQ_FONT);
            lblEquation.setForeground(Color.WHITE);

            lblTimer = new JLabel("", SwingConstants.CENTER);
            lblTimer.setFont(MAIN_FONT);
            lblTimer.setForeground(Color.RED);

            eqPanel.add(lblPhase);
            eqPanel.add(lblEquation);
            eqPanel.add(lblTimer);
            centerPanel.add(eqPanel, BorderLayout.SOUTH);

            add(centerPanel, BorderLayout.CENTER);

            inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
            inputPanel.setBackground(BG_COLOR);

            JLabel lblX = new JLabel("x = ");
            lblX.setFont(EQ_FONT);
            lblX.setForeground(Color.WHITE);

            txtInput = new JTextField(5);
            txtInput.setFont(EQ_FONT);
            txtInput.setHorizontalAlignment(JTextField.CENTER);
            txtInput.setBackground(Color.DARK_GRAY);
            txtInput.setForeground(Color.WHITE);
            txtInput.setCaretColor(Color.WHITE);
            txtInput.addActionListener(e -> processTurn());

            btnAction = new JButton("CAST SPELL");
            btnAction.setFont(MAIN_FONT);
            btnAction.setBackground(ACCENT_COLOR);
            btnAction.setForeground(Color.BLACK);
            btnAction.setFocusPainted(false);
            btnAction.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnAction.addActionListener(e -> processTurn());

            inputPanel.add(lblX);
            inputPanel.add(txtInput);
            inputPanel.add(btnAction);
            add(inputPanel, BorderLayout.SOUTH);

            defenseTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeLeft--;
                    lblTimer.setText("⏳ Time remaining: " + timeLeft + "s");
                    if (timeLeft <= 0) {
                        defenseTimer.stop();
                        log("⏳ TIME'S UP! You failed to raise your shield!");
                        txtInput.setText("");
                        resolveDefense(false);
                    }
                }
            });

            log("⚔️ A wild ANCIENT DRAGON appears!");
            log("Solve the linear equations to attack and defend.");
            startPlayerTurn();
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

        private void log(String message) {
            battleLog.append(message + "\n");
            battleLog.setCaretPosition(battleLog.getDocument().getLength());
        }

        private void startPlayerTurn() {
            currentState = GameState.PLAYER_TURN;
            lblPhase.setText("⚔️ ATTACK PHASE: Solve for x to cast Fireball!");
            lblPhase.setForeground(Color.CYAN);
            lblTimer.setText("");
            btnAction.setText("CAST FIREBALL");
            btnAction.setBackground(Color.CYAN);

            int x = random.nextInt(10) + 2;
            int a = random.nextInt(6) + 2;
            int b = random.nextInt(20) - 10;
            int c = (a * x) + b;

            expectedX = x;
            String sign = (b >= 0) ? "+ " + b : "- " + Math.abs(b);
            lblEquation.setText(a + "x " + sign + " = " + c);

            txtInput.setText("");
            txtInput.requestFocus();
        }

        private void startMonsterTurn() {
            currentState = GameState.MONSTER_TURN;
            lblPhase.setText("🛡️ DEFENSE PHASE: Quick! Solve to raise your Shield!");
            lblPhase.setForeground(Color.ORANGE);
            btnAction.setText("RAISE SHIELD");
            btnAction.setBackground(Color.ORANGE);

            int x = random.nextInt(10) + 2;
            int a = random.nextInt(4) + 2;
            int b = random.nextInt(8) + 1;
            int c = a * (x + b);

            expectedX = x;
            lblEquation.setText(a + "(x + " + b + ") = " + c);

            txtInput.setText("");
            txtInput.requestFocus();

            timeLeft = 15;
            lblTimer.setText("⏳ Time remaining: " + timeLeft + "s");
            defenseTimer.start();
        }

        private void processTurn() {
            if (currentState == GameState.GAME_OVER) return;

            String input = txtInput.getText().trim();
            if (input.isEmpty()) return;

            try {
                int playerAns = Integer.parseInt(input);

                if (currentState == GameState.PLAYER_TURN) {
                    resolveAttack(playerAns == expectedX);
                } else if (currentState == GameState.MONSTER_TURN) {
                    defenseTimer.stop();
                    resolveDefense(playerAns == expectedX);
                }
            } catch (NumberFormatException ex) {
                log("⚠️ System Error: Enter a valid number for x!");
            }
        }

        private void resolveAttack(boolean isCorrect) {
            if (isCorrect) {
                int damage = 20 + random.nextInt(10);
                monsterHP -= damage;
                log("🔥 CRITICAL HIT! Your Fireball deals " + damage + " damage to the Dragon!");
            } else {
                log("💨 MISFIRE! Your math was wrong (" + expectedX + "). The spell fizzles out.");
            }

            updateHealthBars();
            checkGameOver();

            if (currentState != GameState.GAME_OVER) {
                startMonsterTurn();
            }
        }

        private void resolveDefense(boolean isCorrect) {
            if (isCorrect) {
                int damage = random.nextInt(5) + 1;
                playerHP -= damage;
                log("🛡️ SHIELD UP! You blocked the Dragon's breath! You only take " + damage + " splash damage.");
            } else {
                int damage = 25 + random.nextInt(15);
                playerHP -= damage;
                log("💥 SHIELD SHATTERED! The Dragon's breath scorches you for " + damage + " damage! (Correct x was " + expectedX + ")");
            }

            updateHealthBars();
            checkGameOver();

            if (currentState != GameState.GAME_OVER) {
                startPlayerTurn();
            }
        }

        private void updateHealthBars() {
            if (playerHP < 0) playerHP = 0;
            if (monsterHP < 0) monsterHP = 0;

            playerHealthBar.setValue(playerHP);
            playerHealthBar.setString(playerHP + " / 100");

            monsterHealthBar.setValue(monsterHP);
            monsterHealthBar.setString(monsterHP + " / 100");
        }

        private void checkGameOver() {
            if (playerHP <= 0 || monsterHP <= 0) {
                currentState = GameState.GAME_OVER;
                defenseTimer.stop();
                txtInput.setEnabled(false);
                btnAction.setEnabled(false);
                lblTimer.setText("");

                if (playerHP <= 0 && monsterHP <= 0) {
                    log("💀 MUTUAL DESTRUCTION! Both you and the Dragon fall.");
                    lblPhase.setText("DRAW! Close window to return.");
                } else if (monsterHP <= 0) {
                    log("🏆 VICTORY! You have slain the Ancient Dragon!");
                    log("You may close this window to take your Lesson Test.");
                    lblPhase.setText("VICTORY! Close window to continue.");
                    lblPhase.setForeground(Color.GREEN);
                } else {
                    log("☠️ DEFEAT. The Dragon overwhelms you.");
                    lblPhase.setText("GAME OVER! Close window to continue.");
                    lblPhase.setForeground(Color.RED);
                }
            }
        }
    }
}