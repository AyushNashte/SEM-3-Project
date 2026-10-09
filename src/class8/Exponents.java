package class8;

import common.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Educational Topic Class combined with Interactive Game
 * Semester 3 OOP Project
 */
public class Exponents extends Topic {

    // Lesson concepts
    private static final Concept EXPONENT_MEANING = new Concept("EX1", "Meaning of Base and Exponent");
    private static final Concept PRODUCT_QUOTIENT_LAW = new Concept("EX2", "Laws: Multiplying and Dividing Powers");
    private static final Concept POWER_LAWS = new Concept("EX3", "Laws: Power of a Power and Power of a Product");
    private static final Concept ZERO_NEGATIVE = new Concept("EX4", "Zero and Negative Exponents");
    private static final Concept STANDARD_FORM = new Concept("EX5", "Standard (Scientific) Form");

    // Prerequisite concepts
    private static final Concept MULTIPLICATION_BASICS = new Concept("EX6", "Repeated Multiplication");
    private static final Concept INTEGER_OPERATIONS = new Concept("EX7", "Operations on Integers");
    private static final Concept RECIPROCALS = new Concept("EX8", "Reciprocals");
    private static final Concept PLACE_VALUE = new Concept("EX9", "Powers of 10 and Place Value");

    public Exponents() {
        super("Class 8", "Exponents");
    }

    // ==============================================================================
    // 1. PREREQUISITE TEST
    // ==============================================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question("What is 2 x 2 x 2 x 2?", Arrays.asList("8", "16", "24", "32"), 1, MULTIPLICATION_BASICS),
                new Question("What is 5 x 5 x 5?", Arrays.asList("15", "75", "125", "25"), 2, MULTIPLICATION_BASICS),
                new Question("What is (-3) x (-3)?", Arrays.asList("-9", "9", "-6", "6"), 1, INTEGER_OPERATIONS),
                new Question("What is (-2) x (-2) x (-2)?", Arrays.asList("-8", "8", "-6", "6"), 0, INTEGER_OPERATIONS),
                new Question("What is the reciprocal of 5?", Arrays.asList("5", "-5", "1/5", "0"), 2, RECIPROCALS),
                new Question("What is the reciprocal of 2/3?", Arrays.asList("2/3", "3/2", "-3/2", "1/6"), 1, RECIPROCALS),
                new Question("What is 10 x 10 x 10?", Arrays.asList("30", "100", "1000", "10000"), 2, PLACE_VALUE),
                new Question("How many zeros are there in 10,000?", Arrays.asList("3", "4", "5", "10"), 1, PLACE_VALUE)
        );
        return new Test(questions);
    }

    // ==============================================================================
    // 2. LESSON
    // ==============================================================================
    @Override
    protected void teachLesson() {
        // Step 1: Display the lesson content
        getLessonContentBank().forEach(LessonContent::display);

        // Step 2: Transition into the Interactive Game
        System.out.println("\n=======================================================");
        System.out.println("   LESSON COMPLETE. LAUNCHING INTERACTIVE GAME...      ");
        System.out.println("   (Please check for a new window to play the game)    ");
        System.out.println("=======================================================\n");

        playGameAndWait();
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(EXPONENT_MEANING,
                        "An exponent tells you how many times to multiply a number by itself. In a^n, the number a is the base and n is the exponent (or power).",
                        "a^n = a x a x a ... (n times)",
                        Arrays.asList("2^5 = 2 x 2 x 2 x 2 x 2 = 32", "7 x 7 x 7 x 7 is written as 7^4")),
                new LessonContent(PRODUCT_QUOTIENT_LAW,
                        "When the bases are the same, add the exponents to multiply powers and subtract the exponents to divide powers.",
                        "a^m x a^n = a^(m+n)     a^m ÷ a^n = a^(m-n)",
                        Arrays.asList("2^3 x 2^4 = 2^7 = 128", "5^6 ÷ 5^2 = 5^4 = 625")),
                new LessonContent(POWER_LAWS,
                        "A power raised to another power multiplies the exponents. A power of a product gives each factor the same exponent.",
                        "(a^m)^n = a^(m x n)     (a x b)^m = a^m x b^m",
                        Arrays.asList("(3^2)^3 = 3^6 = 729", "(2 x 3)^2 = 2^2 x 3^2 = 4 x 9 = 36")),
                new LessonContent(ZERO_NEGATIVE,
                        "Any non-zero number raised to the power 0 equals 1. A negative exponent means take the reciprocal of the base raised to the positive exponent.",
                        "a^0 = 1 (a ≠ 0)     a^(-n) = 1 / a^n",
                        Arrays.asList("7^0 = 1", "2^(-3) = 1 / 2^3 = 1/8")),
                new LessonContent(STANDARD_FORM,
                        "Very large or very small numbers are written in standard (scientific) form as a number between 1 and 10 multiplied by a power of 10.",
                        "N = a x 10^n, where 1 ≤ a < 10",
                        Arrays.asList("4,500 = 4.5 x 10^3", "0.0072 = 7.2 x 10^-3"))
        );
    }

    // ==============================================================================
    // 3. GAME (Launch and Thread Blocking)
    // ==============================================================================
    private void playGameAndWait() {
        // A lock object to pause the console while the GUI runs
        final Object lock = new Object();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) { }

            // Pass the lock to the game window
            ExponentTowerGUI game = new ExponentTowerGUI(lock);
            game.setVisible(true);
        });

        // Block the console thread until the game window calls lock.notify()
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("=======================================================");
        System.out.println("   GAME FINISHED. PROCEEDING TO MAIN TEST...           ");
        System.out.println("=======================================================\n");
    }

    // ==============================================================================
    // 4. MAIN TEST
    // ==============================================================================
    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question("In 3^4, the number 3 is called the ___ and 4 is called the ___.", Arrays.asList("base, exponent", "exponent, base", "product, base", "base, product"), 0, EXPONENT_MEANING),
                new Question("What is the value of 2^5?", Arrays.asList("10", "25", "32", "16"), 2, EXPONENT_MEANING),
                new Question("How is 7 x 7 x 7 x 7 written using exponents?", Arrays.asList("7^3", "7^4", "4^7", "28"), 1, EXPONENT_MEANING),
                new Question("Simplify: 2^3 x 2^4", Arrays.asList("2^12", "2^7", "4^7", "2^1"), 1, PRODUCT_QUOTIENT_LAW),
                new Question("Simplify: 5^6 ÷ 5^2", Arrays.asList("5^3", "5^4", "5^8", "1^4"), 1, PRODUCT_QUOTIENT_LAW),
                new Question("Which law of exponents is correct?", Arrays.asList("a^m x a^n = a^(m+n)", "a^m x a^n = a^(m x n)", "a^m x a^n = (2a)^(m+n)", "a^m x a^n = a^(m-n)"), 0, PRODUCT_QUOTIENT_LAW),
                new Question("Simplify: (3^2)^3", Arrays.asList("3^5", "3^6", "3^8", "9^5"), 1, POWER_LAWS),
                new Question("Which expression is equal to (2 x 3)^2?", Arrays.asList("2^2 x 3^2", "2 x 3^2", "2^2 + 3^2", "2^3 x 3^3"), 0, POWER_LAWS),
                new Question("(a^m)^n is equal to:", Arrays.asList("a^(m+n)", "a^(m x n)", "a^(m-n)", "(a+n)^m"), 1, POWER_LAWS),
                new Question("What is the value of 7^0?", Arrays.asList("0", "7", "1", "undefined"), 2, ZERO_NEGATIVE),
                new Question("a^(-n) is equal to:", Arrays.asList("-a^n", "1/a^n", "a^n", "n/a"), 1, ZERO_NEGATIVE),
                new Question("What is the value of 2^(-3)?", Arrays.asList("-8", "1/8", "-6", "1/6"), 1, ZERO_NEGATIVE),
                new Question("Write 4,500 in standard form.", Arrays.asList("4.5 x 10^3", "45 x 10^2", "0.45 x 10^4", "4.5 x 10^2"), 0, STANDARD_FORM),
                new Question("Which is the standard form of 0.0072?", Arrays.asList("7.2 x 10^-3", "72 x 10^-4", "7.2 x 10^3", "0.72 x 10^-2"), 0, STANDARD_FORM),
                new Question("Which number is written in standard form?", Arrays.asList("12 x 10^4", "3.2 x 10^5", "0.5 x 10^3", "25.1 x 10^2"), 1, STANDARD_FORM)
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
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }
}

// ==============================================================================
// GAME GUI CLASS (Included in same file)
// ==============================================================================
class ExponentTowerGUI extends JFrame {

    private int currentFloor = 1;
    private int lives = 3;
    private final int maxLives = 3;
    private long totalPowerPoints = 0;
    private int combo = 1;
    private ExponentProblem currentProblem;

    private final int MAX_TIME_MS = 15000;
    private int timeRemainingMs = MAX_TIME_MS;
    private Timer gameLoopTimer;
    private final Object threadLock; // Reference to the lock

    private final TowerPanel towerPanel;
    private final JLabel statusLabel;
    private final JLabel questionLabel;
    private final JTextField answerField;
    private final JButton attackButton;
    private final JLabel feedbackLabel;

    public ExponentTowerGUI(Object lock) {
        this.threadLock = lock;

        setTitle("Class 8 Math Project: Exponent Tower OVERDRIVE");
        setSize(900, 700);

        // When user closes the game, notify the console thread to resume the Main Test
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (gameLoopTimer != null) gameLoopTimer.stop();
                synchronized (threadLock) {
                    threadLock.notify(); // Wake up the console
                }
            }
        });

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.setBackground(new Color(15, 15, 25));

        statusLabel = createStyledLabel("Floor: 1  |  Combo: x1  |  Power: 0", new Color(102, 252, 241), 18);
        topPanel.add(statusLabel);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(topPanel, BorderLayout.NORTH);

        towerPanel = new TowerPanel();
        add(towerPanel, BorderLayout.CENTER);

        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.setBackground(new Color(25, 25, 35));
        bottomContainer.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(102, 252, 241)));

        JPanel qPanel = new JPanel(new GridLayout(2, 1));
        qPanel.setOpaque(false);
        questionLabel = createStyledLabel("Loading Guardian...", new Color(255, 215, 0), 28);
        feedbackLabel = createStyledLabel("Enter the missing exponent (x) before time runs out!", Color.LIGHT_GRAY, 14);
        qPanel.add(questionLabel);
        qPanel.add(feedbackLabel);
        bottomContainer.add(qPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        inputPanel.setOpaque(false);

        JLabel xLabel = createStyledLabel("x = ", Color.WHITE, 24);
        answerField = new JTextField(5);
        answerField.setFont(new Font("Monospaced", Font.BOLD, 24));
        answerField.setHorizontalAlignment(JTextField.CENTER);
        answerField.setBackground(new Color(40, 40, 50));
        answerField.setForeground(Color.WHITE);
        answerField.setCaretColor(Color.WHITE);

        attackButton = new JButton("FIRE BEAM!");
        attackButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        attackButton.setBackground(new Color(235, 87, 87));
        attackButton.setForeground(Color.WHITE);
        attackButton.setFocusPainted(false);
        attackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ActionListener submitAction = e -> checkAnswer();
        attackButton.addActionListener(submitAction);
        answerField.addActionListener(submitAction);

        inputPanel.add(xLabel);
        inputPanel.add(answerField);
        inputPanel.add(attackButton);

        bottomContainer.add(inputPanel, BorderLayout.SOUTH);
        add(bottomContainer, BorderLayout.SOUTH);

        generateNextFloor();
        startGameLoop();
    }

    private JLabel createStyledLabel(String text, Color color, int size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(color);
        label.setFont(new Font("Monospaced", Font.BOLD, size));
        return label;
    }

    private void startGameLoop() {
        gameLoopTimer = new Timer(16, e -> {
            timeRemainingMs -= 16;
            if (timeRemainingMs <= 0 && lives > 0) {
                timeOutDamage();
            }
            towerPanel.updateAnimations();
            towerPanel.repaint();
        });
        gameLoopTimer.start();
    }

    private void timeOutDamage() {
        lives--;
        combo = 1;
        feedbackLabel.setText("TIME OUT! The Guardian struck you! " + currentProblem.getHint());
        feedbackLabel.setForeground(new Color(235, 87, 87));
        towerPanel.shakeAnimation();
        towerPanel.spawnFloatingText("TOO SLOW!", towerPanel.getWidth()/2, towerPanel.getHeight()/2, Color.RED);

        if (lives <= 0) {
            triggerGameOver();
        } else {
            generateNextFloor();
        }
    }

    private void generateNextFloor() {
        currentProblem = new ExponentProblem(currentFloor);
        questionLabel.setText("Solve: " + currentProblem.getQuestionString());
        answerField.setText("");
        answerField.requestFocus();

        timeRemainingMs = Math.max(MAX_TIME_MS - (currentFloor * 200), 5000);
        updateStatus();
        towerPanel.setGuardianColor(currentFloor);
    }

    private void checkAnswer() {
        if (lives <= 0) return;

        try {
            int playerAnswer = Integer.parseInt(answerField.getText().trim());

            if (playerAnswer == currentProblem.getCorrectAnswer()) {
                long basePower = (long) Math.pow(currentProblem.getBase(), Math.abs(playerAnswer));
                if(basePower == 0) basePower = 10;
                long powerGained = basePower * combo;

                totalPowerPoints += powerGained;
                feedbackLabel.setText("NICE! Base " + currentProblem.getBase() + "^" + Math.abs(playerAnswer) + " x Combo " + combo + " = +" + powerGained + " Power!");
                feedbackLabel.setForeground(new Color(102, 252, 241));

                towerPanel.fireLaserAnimation();
                towerPanel.spawnFloatingText("+" + powerGained, towerPanel.getWidth()/2, 100, new Color(102, 252, 241));

                combo++;
                currentFloor++;
                generateNextFloor();

            } else {
                lives--;
                combo = 1;
                feedbackLabel.setText("WRONG! Guardian blocked it! " + currentProblem.getHint());
                feedbackLabel.setForeground(new Color(235, 87, 87));
                towerPanel.shakeAnimation();
                towerPanel.spawnFloatingText("BLOCKED!", towerPanel.getWidth()/2, 100, Color.RED);
                updateStatus();

                if (lives <= 0) {
                    triggerGameOver();
                }
            }
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Invalid input! Please enter a valid integer for x.");
            feedbackLabel.setForeground(Color.ORANGE);
        }
        answerField.setText("");
        answerField.requestFocus();
    }

    private void triggerGameOver() {
        gameLoopTimer.stop();
        Toolkit.getDefaultToolkit().beep();

        int opt = JOptionPane.showConfirmDialog(this,
                "The Tower Guardians defeated you.\nFloors Cleared: " + (currentFloor - 1) +
                        "\nFinal Power: " + totalPowerPoints + "\n\nTry Again?",
                "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if (opt == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            dispose(); // Closes the GUI, automatically waking up the console for Step 4
        }
    }

    private void resetGame() {
        currentFloor = 1;
        lives = maxLives;
        totalPowerPoints = 0;
        combo = 1;
        feedbackLabel.setText("Enter the missing exponent (x) before time runs out!");
        feedbackLabel.setForeground(Color.LIGHT_GRAY);
        generateNextFloor();
        gameLoopTimer.start();
    }

    private void updateStatus() {
        statusLabel.setText(String.format("Floor: %d  |  Combo: x%d  |  Power: %d", currentFloor, combo, totalPowerPoints));
    }

    static class ExponentProblem {
        private final int base;
        private final int correctAnswer;
        private final String questionString;
        private final String hint;

        public ExponentProblem(int floorDifficulty) {
            Random rand = new Random();
            base = rand.nextInt(4) + 2;
            int exp1 = rand.nextInt(4) + 1 + (floorDifficulty / 4);
            int exp2 = rand.nextInt(4) + 1 + (floorDifficulty / 4);
            int ruleType = rand.nextInt(3);

            if (ruleType == 0) {
                correctAnswer = exp1 + exp2;
                questionString = base + "^" + exp1 + " * " + base + "^" + exp2 + " = " + base + "^x";
                hint = "Product Rule: MULTIPLYING same bases? ADD the exponents.";
            } else if (ruleType == 1) {
                if (rand.nextBoolean()) { int temp = exp1; exp1 = exp2; exp2 = temp; }
                correctAnswer = exp1 - exp2;
                questionString = base + "^" + exp1 + " / " + base + "^" + exp2 + " = " + base + "^x";
                hint = "Quotient Rule: DIVIDING same bases? SUBTRACT the exponents.";
            } else {
                correctAnswer = exp1 * exp2;
                questionString = "(" + base + "^" + exp1 + ")^" + exp2 + " = " + base + "^x";
                hint = "Power Rule: Power raised to a power? MULTIPLY the exponents.";
            }
        }
        public int getBase() { return base; }
        public int getCorrectAnswer() { return correctAnswer; }
        public String getQuestionString() { return questionString; }
        public String getHint() { return hint; }
    }


    class TowerPanel extends JPanel {
        private boolean isFiringLaser = false;
        private int laserFrames = 0;
        private int shakeOffset = 0;
        private double scrollY = 0;
        private Color guardianColor = new Color(138, 43, 226);

        private final List<Particle> particles = new ArrayList<>();
        private final List<FloatingText> floatingTexts = new ArrayList<>();

        public TowerPanel() {
            setBackground(new Color(10, 10, 15));
        }

        public void setGuardianColor(int floor) {
            Random r = new Random(floor);
            guardianColor = new Color(r.nextInt(155)+100, r.nextInt(155)+100, r.nextInt(155)+100);
        }

        public void fireLaserAnimation() {
            isFiringLaser = true;
            laserFrames = 15;

            int cx = getWidth() / 2;
            int ey = 100;
            for(int i=0; i<30; i++) {
                particles.add(new Particle(cx, ey, new Color(102, 252, 241)));
            }
        }

        public void shakeAnimation() {
            shakeOffset = 15;
            int cx = getWidth() / 2;
            int py = getHeight() - 80;
            for(int i=0; i<15; i++) {
                particles.add(new Particle(cx, py, Color.RED));
            }
        }

        public void spawnFloatingText(String text, int x, int y, Color c) {
            floatingTexts.add(new FloatingText(text, x, y, c));
        }

        public void updateAnimations() {
            scrollY += 1.5;
            if (scrollY > 60) scrollY = 0;

            if (shakeOffset != 0) {
                shakeOffset = (shakeOffset > 0) ? -shakeOffset + 2 : -shakeOffset - 2;
                if (Math.abs(shakeOffset) <= 2) shakeOffset = 0;
            }

            if (isFiringLaser) {
                laserFrames--;
                if (laserFrames <= 0) isFiringLaser = false;
            }

            Iterator<Particle> pIt = particles.iterator();
            while (pIt.hasNext()) {
                Particle p = pIt.next();
                p.update();
                if (p.life <= 0) pIt.remove();
            }

            Iterator<FloatingText> tIt = floatingTexts.iterator();
            while (tIt.hasNext()) {
                FloatingText ft = tIt.next();
                ft.update();
                if (ft.life <= 0) tIt.remove();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int centerX = w / 2;

            g2d.translate(shakeOffset, 0);

            g2d.setColor(new Color(25, 25, 40));
            g2d.setStroke(new BasicStroke(1));
            for (int i = (int)scrollY; i < h; i += 60) g2d.drawLine(0, i, w, i);
            for (int i = 0; i < w; i += 60) g2d.drawLine(i, 0, i, h);

            g2d.setColor(new Color(35, 35, 45));
            g2d.fillRect(centerX - 90, 0, 180, h);
            g2d.setColor(new Color(102, 252, 241, 80));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(centerX - 90, 0, centerX - 90, h);
            g2d.drawLine(centerX + 90, 0, centerX + 90, h);
            g2d.drawLine(centerX, 0, centerX, h);

            int hoverOffset = (int)(Math.sin(System.currentTimeMillis() * 0.005) * 10);
            int enemyY = 80 + hoverOffset;

            g2d.setColor(guardianColor);
            g2d.fillPolygon(
                    new int[]{centerX, centerX + 40, centerX, centerX - 40},
                    new int[]{enemyY - 30, enemyY, enemyY + 30, enemyY},
                    4
            );
            g2d.setColor(Color.WHITE);
            g2d.fillOval(centerX - 15, enemyY - 5, 10, 10);
            g2d.fillOval(centerX + 5, enemyY - 5, 10, 10);

            g2d.setColor(new Color(guardianColor.getRed(), guardianColor.getGreen(), guardianColor.getBlue(), 100));
            g2d.setStroke(new BasicStroke(4));
            g2d.drawArc(centerX - 55, enemyY - 45, 110, 90, 0, 180);

            int playerY = h - 100;
            g2d.setColor(new Color(69, 162, 158));
            g2d.fillRoundRect(centerX - 30, playerY, 60, 50, 15, 15);
            g2d.setColor(new Color(102, 252, 241));
            g2d.fillOval(centerX - 12, playerY + 12, 24, 24);

            if (isFiringLaser) {
                g2d.setColor(new Color(102, 252, 241, 180));
                g2d.setStroke(new BasicStroke(20));
                g2d.drawLine(centerX, playerY, centerX, enemyY + 30);
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(6));
                g2d.drawLine(centerX, playerY, centerX, enemyY + 30);
            }

            for (Particle p : particles) {
                g2d.setColor(new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), Math.max(0, p.life * 5)));
                g2d.fillRect((int)p.x, (int)p.y, p.size, p.size);
            }

            g2d.setFont(new Font("Monospaced", Font.BOLD, 22));
            for (FloatingText ft : floatingTexts) {
                g2d.setColor(new Color(ft.color.getRed(), ft.color.getGreen(), ft.color.getBlue(), Math.max(0, ft.life * 5)));
                g2d.drawString(ft.text, (int)ft.x - (g2d.getFontMetrics().stringWidth(ft.text) / 2), (int)ft.y);
            }

            g2d.translate(-shakeOffset, 0);

            g2d.setColor(new Color(235, 87, 87));
            for(int i=0; i<maxLives; i++) {
                if (i < lives) g2d.fillRect(20 + (i * 35), h - 40, 25, 25);
                else g2d.drawRect(20 + (i * 35), h - 40, 25, 25);
            }

            int barWidth = 300;
            int barHeight = 15;
            int barX = w / 2 - barWidth / 2;
            int barY = 20;

            g2d.setColor(new Color(50, 50, 50));
            g2d.fillRect(barX, barY, barWidth, barHeight);

            double timeRatio = (double)timeRemainingMs / MAX_TIME_MS;
            if(timeRatio > 0.5) g2d.setColor(new Color(76, 175, 80));
            else if (timeRatio > 0.25) g2d.setColor(new Color(255, 193, 7));
            else g2d.setColor(new Color(235, 87, 87));

            g2d.fillRect(barX, barY, (int)(barWidth * timeRatio), barHeight);
            g2d.setColor(Color.WHITE);
            g2d.drawRect(barX, barY, barWidth, barHeight);
        }
    }

    class Particle {
        double x, y, vx, vy;
        int life, size;
        Color color;

        public Particle(int x, int y, Color c) {
            this.x = x; this.y = y; this.color = c;
            this.vx = (Math.random() - 0.5) * 15;
            this.vy = (Math.random() - 0.5) * 15;
            this.life = 30 + (int)(Math.random() * 20);
            this.size = 4 + (int)(Math.random() * 6);
        }
        public void update() {
            x += vx; y += vy;
            vy += 0.5;
            life--;
        }
    }

    class FloatingText {
        String text;
        double x, y;
        int life;
        Color color;

        public FloatingText(String t, int x, int y, Color c) {
            this.text = t; this.x = x; this.y = y; this.color = c;
            this.life = 50;
        }
        public void update() {
            y -= 1.5;
            life--;
        }
    }
}