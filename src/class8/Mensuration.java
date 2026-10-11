package class8;

import common.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

public class Mensuration extends Topic {

    // Lesson concepts
    private static final Concept TRAPEZIUM_RHOMBUS = new Concept("ME1", "Area of a Trapezium and Rhombus");
    private static final Concept CUBOID_SURFACE = new Concept("ME2", "Surface Area of Cube and Cuboid");
    private static final Concept CUBOID_VOLUME = new Concept("ME3", "Volume of Cube and Cuboid");
    private static final Concept CYLINDER = new Concept("ME4", "Surface Area and Volume of a Cylinder");
    private static final Concept VOLUME_CAPACITY = new Concept("ME5", "Volume and Capacity Units");

    // Prerequisite concepts
    private static final Concept AREA_BASICS = new Concept("ME6", "Area of Rectangle and Triangle");
    private static final Concept SQUARES_CUBES = new Concept("ME7", "Squares and Cubes of Numbers");
    private static final Concept UNIT_CONVERSION = new Concept("ME8", "Converting Units of Length");
    private static final Concept CIRCLE_BASICS = new Concept("ME9", "Circumference and Area of a Circle");

    public Mensuration() {
        super("Class 8", "Mensuration");
    }

    // ==============================================================================
    // 1. PREREQUISITE TEST
    // ==============================================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What is the area of a rectangle with length 8 cm and breadth 5 cm?",
                        Arrays.asList("13 cm²", "40 cm²", "26 cm²", "80 cm²"),
                        1,
                        AREA_BASICS
                ),
                new Question(
                        "What is the area of a triangle with base 10 cm and height 6 cm?",
                        Arrays.asList("60 cm²", "16 cm²", "30 cm²", "15 cm²"),
                        2,
                        AREA_BASICS
                ),
                new Question(
                        "What is 4 x 4 x 4?",
                        Arrays.asList("12", "16", "48", "64"),
                        3,
                        SQUARES_CUBES
                ),
                new Question(
                        "What is 12 x 12?",
                        Arrays.asList("24", "122", "144", "124"),
                        2,
                        SQUARES_CUBES
                ),
                new Question(
                        "Convert 3 metres into centimetres.",
                        Arrays.asList("30 cm", "300 cm", "3000 cm", "0.03 cm"),
                        1,
                        UNIT_CONVERSION
                ),
                new Question(
                        "Convert 2 kilometres into metres.",
                        Arrays.asList("20 m", "200 m", "2000 m", "20000 m"),
                        2,
                        UNIT_CONVERSION
                ),
                new Question(
                        "Using π = 22/7, what is the circumference of a circle of radius 7 cm?",
                        Arrays.asList("22 cm", "44 cm", "154 cm", "49 cm"),
                        1,
                        CIRCLE_BASICS
                ),
                new Question(
                        "Using π = 22/7, what is the area of a circle of radius 7 cm?",
                        Arrays.asList("44 cm²", "49 cm²", "154 cm²", "308 cm²"),
                        2,
                        CIRCLE_BASICS
                )
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
        System.out.println("   LESSON COMPLETE. LAUNCHING AREA DEFENDER GAME...    ");
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
        return Optional.of("area-defender");
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
                AreaDefenderGame game = new AreaDefenderGame(callback);
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
                        "Which formula gives the area of a trapezium?",
                        Arrays.asList("½ x (sum of parallel sides) x height", "base x height", "½ x base x height", "(a + b) x h"),
                        0,
                        TRAPEZIUM_RHOMBUS
                ),
                new Question(
                        "Find the area of a trapezium whose parallel sides are 8 cm and 12 cm and whose height is 5 cm.",
                        Arrays.asList("40 cm²", "50 cm²", "100 cm²", "60 cm²"),
                        1,
                        TRAPEZIUM_RHOMBUS
                ),
                new Question(
                        "Find the area of a rhombus whose diagonals are 10 cm and 8 cm.",
                        Arrays.asList("80 cm²", "40 cm²", "18 cm²", "36 cm²"),
                        1,
                        TRAPEZIUM_RHOMBUS
                ),
                new Question(
                        "What is the total surface area of a cube of side 5 cm?",
                        Arrays.asList("100 cm²", "125 cm²", "150 cm²", "30 cm²"),
                        2,
                        CUBOID_SURFACE
                ),
                new Question(
                        "Find the total surface area of a cuboid with l = 6 cm, b = 4 cm and h = 3 cm.",
                        Arrays.asList("72 cm²", "108 cm²", "54 cm²", "144 cm²"),
                        1,
                        CUBOID_SURFACE
                ),
                new Question(
                        "Which formula gives the lateral surface area of a cuboid?",
                        Arrays.asList("2(lb + bh + hl)", "2h(l + b)", "lbh", "6a²"),
                        1,
                        CUBOID_SURFACE
                ),
                new Question(
                        "What is the volume of a cube of side 4 cm?",
                        Arrays.asList("16 cm³", "48 cm³", "64 cm³", "96 cm³"),
                        2,
                        CUBOID_VOLUME
                ),
                new Question(
                        "Find the volume of a cuboid measuring 10 cm x 5 cm x 3 cm.",
                        Arrays.asList("150 cm³", "30 cm³", "300 cm³", "18 cm³"),
                        0,
                        CUBOID_VOLUME
                ),
                new Question(
                        "A cube has volume 125 cm³. What is the length of its side?",
                        Arrays.asList("25 cm", "5 cm", "15 cm", "12.5 cm"),
                        1,
                        CUBOID_VOLUME
                ),
                new Question(
                        "Find the curved surface area of a cylinder of radius 7 cm and height 10 cm. (π = 22/7)",
                        Arrays.asList("220 cm²", "440 cm²", "154 cm²", "1540 cm²"),
                        1,
                        CYLINDER
                ),
                new Question(
                        "Find the volume of a cylinder of radius 7 cm and height 10 cm. (π = 22/7)",
                        Arrays.asList("440 cm³", "154 cm³", "1540 cm³", "3080 cm³"),
                        2,
                        CYLINDER
                ),
                new Question(
                        "Which formula gives the total surface area of a closed cylinder?",
                        Arrays.asList("2πrh", "πr²h", "2πr(r + h)", "πr²"),
                        2,
                        CYLINDER
                ),
                new Question(
                        "How many cm³ are there in 1 litre?",
                        Arrays.asList("10 cm³", "100 cm³", "1000 cm³", "10000 cm³"),
                        2,
                        VOLUME_CAPACITY
                ),
                new Question(
                        "How many litres can a tank of volume 2 m³ hold?",
                        Arrays.asList("20 L", "200 L", "2000 L", "20000 L"),
                        2,
                        VOLUME_CAPACITY
                ),
                new Question(
                        "A tank measures 50 cm x 40 cm x 30 cm. How many litres of water can it hold?",
                        Arrays.asList("6 L", "60 L", "600 L", "6000 L"),
                        1,
                        VOLUME_CAPACITY
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
                        TRAPEZIUM_RHOMBUS,
                        "A trapezium has one pair of parallel sides. Its area is half the sum of the parallel sides "
                                + "multiplied by the distance between them. A rhombus is found from its two diagonals.",
                        "Trapezium: A = ½ x (a + b) x h     Rhombus: A = ½ x d1 x d2",
                        Arrays.asList(
                                "Trapezium with a = 8, b = 12, h = 5: A = ½ x 20 x 5 = 50 cm²",
                                "Rhombus with diagonals 10 and 8: A = ½ x 10 x 8 = 40 cm²"
                        )
                ),
                new LessonContent(
                        CUBOID_SURFACE,
                        "The total surface area (TSA) is the area of all the faces. The lateral surface area (LSA) "
                                + "counts only the four side faces, leaving out the top and bottom.",
                        "Cube: TSA = 6a²     Cuboid: TSA = 2(lb + bh + hl),  LSA = 2h(l + b)",
                        Arrays.asList(
                                "Cube of side 5 cm: TSA = 6 x 25 = 150 cm²",
                                "Cuboid 6 x 4 x 3: TSA = 2(24 + 12 + 18) = 108 cm²"
                        )
                ),
                new LessonContent(
                        CUBOID_VOLUME,
                        "Volume is the space a solid occupies, measured in cubic units. For a cuboid, multiply "
                                + "length, breadth and height. A cube is a cuboid whose three edges are equal.",
                        "Cuboid: V = l x b x h     Cube: V = a³",
                        Arrays.asList(
                                "Cube of side 4 cm: V = 4³ = 64 cm³",
                                "Cuboid 10 x 5 x 3: V = 150 cm³",
                                "A cube of volume 125 cm³ has side 5 cm, since 5³ = 125"
                        )
                ),
                new LessonContent(
                        CYLINDER,
                        "A cylinder has a curved surface and two circular ends. The curved surface opens out into a "
                                + "rectangle of width 2πr and height h. We use π = 22/7 in these problems.",
                        "CSA = 2πrh     TSA = 2πr(r + h)     V = πr²h",
                        Arrays.asList(
                                "r = 7, h = 10: CSA = 2 x 22/7 x 7 x 10 = 440 cm²",
                                "r = 7, h = 10: V = 22/7 x 7 x 7 x 10 = 1540 cm³"
                        )
                ),
                new LessonContent(
                        VOLUME_CAPACITY,
                        "Volume is the space a solid takes up, and capacity is how much liquid a container can hold. "
                                + "The two are linked by simple conversions.",
                        "1 cm³ = 1 mL     1000 cm³ = 1 L     1 m³ = 1000 L",
                        Arrays.asList(
                                "A tank of 2 m³ holds 2 x 1000 = 2000 L",
                                "A tank 50 x 40 x 30 cm has volume 60000 cm³ = 60 L"
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

    // ==============================================================================
    // INNER CLASS: THE GAME
    // ==============================================================================
    public static class AreaDefenderGame extends JFrame {

        private int baseIntegrity = 100;
        private int energy = 0;
        private int level = 1;
        private final int MAX_LEVELS = 5;

        private int expectedAnswer;
        private int shapeType; // 1=Rect, 2=Triangle, 3=Circle, 4=Cuboid, 5=Cylinder
        private int p1, p2, p3; // parameters for shapes

        private final Random random = new Random();

        // Callback hook to resume main app
        private final Runnable onFinished;
        private boolean finishedNotified = false;

        // UI Colors - Sci-Fi Blueprint Theme
        private final Color COLOR_BG = new Color(10, 25, 47);      // Deep Space Blue
        private final Color COLOR_GRID = new Color(23, 42, 69);    // Lighter Blue Grid
        private final Color COLOR_ACCENT = new Color(100, 255, 218);// Neon Cyan
        private final Color COLOR_TEXT = new Color(230, 241, 255); // Off White
        private final Color COLOR_WARNING = new Color(255, 107, 107); // Coral Red

        private JProgressBar healthBar;
        private JLabel lblLevel, lblEnergy;
        private JTextArea consoleLog;
        private BlueprintPanel blueprintPanel;

        private JLabel lblMissionTitle, lblMissionDesc, lblFormulaHint;
        private JTextField txtInput;
        private JButton btnDeploy;

        public AreaDefenderGame(Runnable onFinished) {
            this.onFinished = onFinished;

            setTitle("Area Defender: Engineering Command");
            setSize(950, 650);

            // Critical listener to resume the main application when window is closed
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    notifyFinished();
                }
            });

            setLayout(new BorderLayout());
            getContentPane().setBackground(COLOR_BG);
            setLocationRelativeTo(null);

            // --- TOP STATUS BAR ---
            JPanel topPanel = new JPanel(new BorderLayout(20, 0));
            topPanel.setBackground(COLOR_BG);
            topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

            lblLevel = new JLabel("DEFENSE GRID: SECTOR 1");
            lblLevel.setFont(new Font("Monospaced", Font.BOLD, 22));
            lblLevel.setForeground(COLOR_ACCENT);

            JPanel statsPanel = new JPanel(new GridLayout(2, 1, 0, 5));
            statsPanel.setBackground(COLOR_BG);

            lblEnergy = new JLabel("🔋 SAVED ENERGY: 0 Units");
            lblEnergy.setFont(new Font("Monospaced", Font.BOLD, 16));
            lblEnergy.setForeground(Color.YELLOW);

            healthBar = new JProgressBar(0, 100);
            healthBar.setValue(baseIntegrity);
            healthBar.setString("Base Integrity: 100%");
            healthBar.setStringPainted(true);
            healthBar.setForeground(COLOR_ACCENT);
            healthBar.setBackground(COLOR_GRID);

            statsPanel.add(lblEnergy);
            statsPanel.add(healthBar);

            topPanel.add(lblLevel, BorderLayout.WEST);
            topPanel.add(statsPanel, BorderLayout.EAST);
            add(topPanel, BorderLayout.NORTH);

            // --- CENTER SPLIT (BLUEPRINT & CONSOLE) ---
            JPanel centerArea = new JPanel(new BorderLayout(15, 0));
            centerArea.setBackground(COLOR_BG);
            centerArea.setBorder(new EmptyBorder(10, 20, 10, 20));

            // Left: The Custom Blueprint Drawing Panel
            blueprintPanel = new BlueprintPanel();
            blueprintPanel.setPreferredSize(new Dimension(500, 400));
            centerArea.add(blueprintPanel, BorderLayout.CENTER);

            // Right: The Command Console
            JPanel commandPanel = new JPanel(new BorderLayout());
            commandPanel.setBackground(COLOR_BG);
            commandPanel.setPreferredSize(new Dimension(380, 0));
            commandPanel.setBorder(new LineBorder(COLOR_ACCENT, 1, true));

            JPanel missionPanel = new JPanel(new GridLayout(3, 1));
            missionPanel.setBackground(COLOR_BG);
            missionPanel.setBorder(new EmptyBorder(15, 15, 10, 15));

            lblMissionTitle = new JLabel("MISSION DIRECTIVE", SwingConstants.CENTER);
            lblMissionTitle.setFont(new Font("Monospaced", Font.BOLD, 18));
            lblMissionTitle.setForeground(COLOR_ACCENT);

            lblMissionDesc = new JLabel("<html>Calculate the parameters to deploy.</html>");
            lblMissionDesc.setFont(new Font("SansSerif", Font.PLAIN, 16));
            lblMissionDesc.setForeground(COLOR_TEXT);

            lblFormulaHint = new JLabel("Hint: Check your manual.");
            lblFormulaHint.setFont(new Font("SansSerif", Font.ITALIC, 14));
            lblFormulaHint.setForeground(Color.GRAY);

            missionPanel.add(lblMissionTitle);
            missionPanel.add(lblMissionDesc);
            missionPanel.add(lblFormulaHint);

            commandPanel.add(missionPanel, BorderLayout.NORTH);

            // Console Log
            consoleLog = new JTextArea();
            consoleLog.setBackground(new Color(5, 12, 24));
            consoleLog.setForeground(COLOR_ACCENT);
            consoleLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
            consoleLog.setEditable(false);
            consoleLog.setMargin(new Insets(10, 10, 10, 10));
            JScrollPane scrollLog = new JScrollPane(consoleLog);
            scrollLog.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, COLOR_ACCENT));
            commandPanel.add(scrollLog, BorderLayout.CENTER);

            // Input Area
            JPanel inputContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
            inputContainer.setBackground(COLOR_BG);

            JLabel lblEq = new JLabel("Value:");
            lblEq.setFont(new Font("Monospaced", Font.BOLD, 16));
            lblEq.setForeground(COLOR_TEXT);

            txtInput = new JTextField(6);
            txtInput.setFont(new Font("Monospaced", Font.BOLD, 18));
            txtInput.setHorizontalAlignment(JTextField.CENTER);
            txtInput.setBackground(COLOR_GRID);
            txtInput.setForeground(COLOR_ACCENT);
            txtInput.setCaretColor(COLOR_ACCENT);
            txtInput.addActionListener(e -> processCommand());

            btnDeploy = new JButton("DEPLOY");
            btnDeploy.setFont(new Font("Monospaced", Font.BOLD, 16));
            btnDeploy.setBackground(COLOR_ACCENT);
            btnDeploy.setForeground(COLOR_BG);
            btnDeploy.setFocusPainted(false);
            btnDeploy.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnDeploy.addActionListener(e -> processCommand());

            inputContainer.add(lblEq);
            inputContainer.add(txtInput);
            inputContainer.add(btnDeploy);
            commandPanel.add(inputContainer, BorderLayout.SOUTH);

            centerArea.add(commandPanel, BorderLayout.EAST);
            add(centerArea, BorderLayout.CENTER);

            log("SYSTEM BOOT: MENSURATION PROTOCOLS ONLINE.");
            log("Defend the base using geometric calculations.");
            log("Use π = 22/7 for circular calculations.");
            log("-----------------------------------------");
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

        private void log(String message) {
            consoleLog.append("> " + message + "\n");
            consoleLog.setCaretPosition(consoleLog.getDocument().getLength());
        }

        // ==========================================
        // LEVEL GENERATION
        // ==========================================
        private void generateLevel() {
            lblLevel.setText("DEFENSE GRID: SECTOR " + level);
            shapeType = level; // 1 to 5

            switch(shapeType) {
                case 1: // Perimeter of Rectangle
                    p1 = random.nextInt(15) + 10; // Length
                    p2 = random.nextInt(10) + 5;  // Width
                    expectedAnswer = 2 * (p1 + p2);
                    lblMissionTitle.setText("PERIMETER FENCING");
                    lblMissionDesc.setText("<html>Base requires a rectangular laser fence. <br>Length = " + p1 + "m, Width = " + p2 + "m.</html>");
                    lblFormulaHint.setText("Hint: Perimeter = 2 * (L + W)");
                    break;
                case 2: // Area of Triangle
                    p1 = (random.nextInt(8) + 2) * 2; // Base (Even number for clean half)
                    p2 = random.nextInt(10) + 5;      // Height
                    expectedAnswer = (p1 * p2) / 2;
                    lblMissionTitle.setText("ARTILLERY COVERAGE");
                    lblMissionDesc.setText("<html>Deploy a triangular defense zone. <br>Base = " + p1 + "m, Height = " + p2 + "m.</html>");
                    lblFormulaHint.setText("Hint: Area = 1/2 * Base * Height");
                    break;
                case 3: // Area of Circle
                    p1 = (random.nextInt(3) + 1) * 7; // Radius (Multiple of 7)
                    expectedAnswer = (22 * p1 * p1) / 7;
                    lblMissionTitle.setText("ENERGY SHIELD");
                    lblMissionDesc.setText("<html>Project a circular energy shield. <br>Radius = " + p1 + "m. (Use π = 22/7)</html>");
                    lblFormulaHint.setText("Hint: Area = π * r²");
                    break;
                case 4: // Volume of Cuboid
                    p1 = random.nextInt(5) + 3; // L
                    p2 = random.nextInt(4) + 2; // W
                    p3 = random.nextInt(5) + 4; // H
                    expectedAnswer = p1 * p2 * p3;
                    lblMissionTitle.setText("STORAGE SILO (CUBOID)");
                    lblMissionDesc.setText("<html>Build a rectangular silo for batteries. <br>L=" + p1 + "m, W=" + p2 + "m, H=" + p3 + "m.</html>");
                    lblFormulaHint.setText("Hint: Volume = L * W * H");
                    break;
                case 5: // Volume of Cylinder
                    p1 = (random.nextInt(2) + 1) * 7; // Radius (Multiple of 7)
                    p2 = random.nextInt(5) + 5;       // Height
                    expectedAnswer = (22 * p1 * p1 * p2) / 7;
                    lblMissionTitle.setText("PLASMA GENERATOR");
                    lblMissionDesc.setText("<html>Construct a cylindrical plasma tank. <br>Radius = " + p1 + "m, Height = " + p2 + "m.</html>");
                    lblFormulaHint.setText("Hint: Volume = π * r² * h");
                    break;
            }

            txtInput.setText("");
            txtInput.requestFocus();
            blueprintPanel.updateShape(shapeType, p1, p2, p3);
        }

        // ==========================================
        // INPUT PROCESSING
        // ==========================================
        private void processCommand() {
            if (baseIntegrity <= 0 || level > MAX_LEVELS) return;

            String input = txtInput.getText().trim();
            if (input.isEmpty()) return;

            try {
                int playerAns = Integer.parseInt(input);

                if (playerAns == expectedAnswer) {
                    log("SUCCESS: Calculated exactly. Deployment successful!");
                    energy += 50;
                    lblEnergy.setText("🔋 SAVED ENERGY: " + energy + " Units");
                    level++;

                    if (level > MAX_LEVELS) {
                        endGame(true);
                    } else {
                        generateLevel();
                    }
                } else {
                    log("ERROR: Incorrect math! Expected: " + expectedAnswer);
                    log("WARNING: Hull breached! Base takes damage.");
                    baseIntegrity -= 30;

                    if (baseIntegrity < 0) baseIntegrity = 0;
                    healthBar.setValue(baseIntegrity);
                    healthBar.setString("Base Integrity: " + baseIntegrity + "%");

                    if (baseIntegrity == 0) {
                        healthBar.setForeground(COLOR_WARNING);
                        endGame(false);
                    } else {
                        txtInput.setText("");
                        txtInput.requestFocus();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid whole number!", "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void endGame(boolean victory) {
            txtInput.setEnabled(false);
            btnDeploy.setEnabled(false);

            if (victory) {
                lblMissionTitle.setText("BASE SECURED");
                lblMissionTitle.setForeground(Color.GREEN);
                lblMissionDesc.setText("<html>All defenses active.<br>Great job, Engineer!</html>");
                log("\n*** MISSION COMPLETE! ***");
                log("You mastered perimeter, area, and volume.");
            } else {
                lblMissionTitle.setText("BASE DESTROYED");
                lblMissionTitle.setForeground(COLOR_WARNING);
                lblMissionDesc.setText("<html>Math errors led to system failure.</html>");
                log("\n*** MISSION FAILED! ***");
                log("Review your formulas and try again.");
            }
            lblFormulaHint.setText("Game Over. Close this window to take your Lesson Test.");
        }

        // ==========================================
        // CUSTOM BLUEPRINT DRAWING PANEL
        // ==========================================
        class BlueprintPanel extends JPanel {
            private int type;
            private int d1, d2, d3;

            public BlueprintPanel() {
                setBackground(COLOR_BG);
            }

            public void updateShape(int type, int d1, int d2, int d3) {
                this.type = type;
                this.d1 = d1;
                this.d2 = d2;
                this.d3 = d3;
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // Draw Background Grid
                g2d.setColor(COLOR_GRID);
                for (int i = 0; i < w; i += 30) g2d.drawLine(i, 0, i, h);
                for (int i = 0; i < h; i += 30) g2d.drawLine(0, i, w, i);

                // Draw Shape based on Type
                g2d.setColor(COLOR_ACCENT);
                g2d.setStroke(new BasicStroke(3));
                g2d.setFont(new Font("Monospaced", Font.BOLD, 18));

                int centerX = w / 2;
                int centerY = h / 2;

                if (type == 1) { // Rectangle
                    int rw = 250, rh = 150;
                    g2d.drawRect(centerX - rw/2, centerY - rh/2, rw, rh);
                    g2d.drawString("L=" + d1 + "m", centerX - 20, centerY - rh/2 - 10);
                    g2d.drawString("W=" + d2 + "m", centerX + rw/2 + 10, centerY);
                }
                else if (type == 2) { // Triangle
                    int tw = 200, th = 200;
                    int[] xPoints = {centerX, centerX - tw/2, centerX + tw/2};
                    int[] yPoints = {centerY - th/2, centerY + th/2, centerY + th/2};
                    g2d.drawPolygon(xPoints, yPoints, 3);
                    // Draw height line
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
                    g2d.drawLine(centerX, centerY - th/2, centerX, centerY + th/2);
                    g2d.drawString("H=" + d2 + "m", centerX + 10, centerY);
                    g2d.drawString("B=" + d1 + "m", centerX - 20, centerY + th/2 + 25);
                }
                else if (type == 3) { // Circle
                    int cr = 100;
                    g2d.drawOval(centerX - cr, centerY - cr, cr*2, cr*2);
                    g2d.drawLine(centerX, centerY, centerX + cr, centerY); // Radius
                    g2d.drawString("r=" + d1 + "m", centerX + 20, centerY - 10);
                }
                else if (type == 4) { // Cuboid (Wireframe)
                    int cw = 200, ch = 120, offset = 50;
                    // Back face
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
                    g2d.drawRect(centerX - cw/2 + offset, centerY - ch/2 - offset, cw, ch);
                    // Front face
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawRect(centerX - cw/2, centerY - ch/2, cw, ch);
                    // Connecting lines
                    g2d.drawLine(centerX - cw/2, centerY - ch/2, centerX - cw/2 + offset, centerY - ch/2 - offset);
                    g2d.drawLine(centerX + cw/2, centerY - ch/2, centerX + cw/2 + offset, centerY - ch/2 - offset);
                    g2d.drawLine(centerX - cw/2, centerY + ch/2, centerX - cw/2 + offset, centerY + ch/2 - offset);
                    g2d.drawLine(centerX + cw/2, centerY + ch/2, centerX + cw/2 + offset, centerY + ch/2 - offset);

                    g2d.drawString("L=" + d1 + "m", centerX - 20, centerY + ch/2 + 25);
                    g2d.drawString("H=" + d3 + "m", centerX - cw/2 - 60, centerY);
                    g2d.drawString("W=" + d2 + "m", centerX + cw/2 + 10, centerY + ch/2 - 20);
                }
                else if (type == 5) { // Cylinder (Wireframe)
                    int cyw = 140, cyh = 220;
                    // Top oval
                    g2d.drawOval(centerX - cyw/2, centerY - cyh/2, cyw, 40);
                    // Bottom oval
                    g2d.drawOval(centerX - cyw/2, centerY + cyh/2 - 40, cyw, 40);
                    // Sides
                    g2d.drawLine(centerX - cyw/2, centerY - cyh/2 + 20, centerX - cyw/2, centerY + cyh/2 - 20);
                    g2d.drawLine(centerX + cyw/2, centerY - cyh/2 + 20, centerX + cyw/2, centerY + cyh/2 - 20);
                    // Radius line on top
                    g2d.drawLine(centerX, centerY - cyh/2 + 20, centerX + cyw/2, centerY - cyh/2 + 20);

                    g2d.drawString("r=" + d1 + "m", centerX + 10, centerY - cyh/2 + 15);
                    g2d.drawString("H=" + d2 + "m", centerX + cyw/2 + 15, centerY);
                }
            }
        }
    }
}