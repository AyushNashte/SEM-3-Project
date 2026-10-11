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

public class PercentageAndFinancialMaths extends Topic {

    // Lesson concepts
    private static final Concept PERCENT_BASICS = new Concept("PF1", "Percentage as Fraction and Decimal");
    private static final Concept PERCENT_CHANGE = new Concept("PF2", "Percentage Increase and Decrease");
    private static final Concept PROFIT_LOSS = new Concept("PF3", "Profit and Loss");
    private static final Concept DISCOUNT = new Concept("PF4", "Discount and Selling Price");
    private static final Concept INTEREST = new Concept("PF5", "Simple and Compound Interest");

    // Prerequisite concepts
    private static final Concept FRACTION_DECIMAL = new Concept("PF6", "Converting Fractions and Decimals");
    private static final Concept MULTIPLY_DIVIDE = new Concept("PF7", "Multiplication and Division");
    private static final Concept RATIO_BASICS = new Concept("PF8", "Ratios");
    private static final Concept DECIMAL_OPERATIONS = new Concept("PF9", "Operations on Decimals");

    public PercentageAndFinancialMaths() {
        super("Class 8", "Percentage & Financial Maths");
    }

    // ==============================================================================
    // 1. PREREQUISITE TEST
    // ==============================================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question("Write 3/4 as a decimal.", Arrays.asList("0.34", "0.25", "0.75", "1.25"), 2, FRACTION_DECIMAL),
                new Question("Write 0.4 as a fraction in simplest form.", Arrays.asList("4/100", "1/4", "4/5", "2/5"), 3, FRACTION_DECIMAL),
                new Question("What is 250 x 4?", Arrays.asList("800", "1000", "1200", "254"), 1, MULTIPLY_DIVIDE),
                new Question("What is 1200 ÷ 100?", Arrays.asList("120", "1.2", "1200", "12"), 3, MULTIPLY_DIVIDE),
                new Question("Simplify the ratio 20:50.", Arrays.asList("2:5", "1:2", "4:5", "2:3"), 0, RATIO_BASICS),
                new Question("If two quantities are in the ratio 1:4, the first is what fraction of the total?", Arrays.asList("1/4", "4/5", "1/5", "1/3"), 2, RATIO_BASICS),
                new Question("What is 0.5 x 80?", Arrays.asList("4", "40", "400", "0.4"), 1, DECIMAL_OPERATIONS),
                new Question("What is 12.5 + 7.5?", Arrays.asList("19", "21", "18", "20"), 3, DECIMAL_OPERATIONS)
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
        System.out.println("   LESSON COMPLETE. LAUNCHING MARKET TYCOON GAME...    ");
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
        return Optional.of("market-tycoon");
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
                MarketTycoonGame game = new MarketTycoonGame(callback);
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
                new Question("The words 'per cent' mean:", Arrays.asList("per ten", "per hundred", "per thousand", "per one"), 1, PERCENT_BASICS),
                new Question("Write 35% as a fraction in simplest form.", Arrays.asList("35/10", "3/5", "7/20", "7/10"), 2, PERCENT_BASICS),
                new Question("Find 20% of 150.", Arrays.asList("20", "30", "75", "300"), 1, PERCENT_BASICS),
                new Question("A price rises from Rs. 200 to Rs. 250. What is the percentage increase?", Arrays.asList("20%", "25%", "50%", "5%"), 1, PERCENT_CHANGE),
                new Question("A quantity falls from 80 to 60. What is the percentage decrease?", Arrays.asList("20%", "33%", "25%", "75%"), 2, PERCENT_CHANGE),
                new Question("Percentage increase or decrease is always calculated on the:", Arrays.asList("larger value", "new value", "difference only", "original value"), 3, PERCENT_CHANGE),
                new Question("An article bought for Rs. 400 is sold for Rs. 460. What is the profit?", Arrays.asList("Rs. 40", "Rs. 60", "Rs. 460", "Rs. 860"), 1, PROFIT_LOSS),
                new Question("Which formula gives the profit percentage?", Arrays.asList("(Profit / Selling price) x 100", "(Profit / Cost price) x 100", "(Cost price / Profit) x 100", "(Loss / Cost price) x 100"), 1, PROFIT_LOSS),
                new Question("A shirt bought for Rs. 500 is sold for Rs. 450. What is the loss percentage?", Arrays.asList("5%", "11%", "10%", "50%"), 2, PROFIT_LOSS),
                new Question("A bag marked at Rs. 800 is sold at a 10% discount. What is the selling price?", Arrays.asList("Rs. 80", "Rs. 790", "Rs. 720", "Rs. 880"), 2, DISCOUNT),
                new Question("Discount is calculated on the:", Arrays.asList("cost price", "marked price", "selling price", "profit"), 1, DISCOUNT),
                new Question("A toy marked at Rs. 500 is sold for Rs. 450. What is the discount percentage?", Arrays.asList("10%", "5%", "45%", "50%"), 0, DISCOUNT),
                new Question("Which formula gives simple interest?", Arrays.asList("SI = P x R x T / 100", "SI = P + R + T", "SI = P x R / T", "SI = (P + R) x T / 100"), 0, INTEREST),
                new Question("Find the simple interest on Rs. 2000 at 5% per annum for 3 years.", Arrays.asList("Rs. 100", "Rs. 150", "Rs. 300", "Rs. 600"), 2, INTEREST),
                new Question("What is the amount on Rs. 1000 at 10% per annum compounded annually for 2 years?", Arrays.asList("Rs. 1200", "Rs. 1100", "Rs. 1210", "Rs. 1220"), 2, INTEREST)
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
                new LessonContent(PERCENT_BASICS, "Per cent means 'per hundred', so x% is the same as x/100. To find a percentage of a quantity, multiply the quantity by x/100.", "x% = x/100     x% of N = (x/100) x N", Arrays.asList("35% = 35/100 = 7/20", "20% of 150 = (20/100) x 150 = 30")),
                new LessonContent(PERCENT_CHANGE, "A percentage change compares the change with the ORIGINAL value. It is an increase if the value grows and a decrease if it falls.", "Percentage change = (Change / Original value) x 100", Arrays.asList("Rs. 200 to Rs. 250: change 50, so (50/200) x 100 = 25% increase", "80 to 60: change 20, so (20/80) x 100 = 25% decrease")),
                new LessonContent(PROFIT_LOSS, "Cost price (CP) is what you pay and selling price (SP) is what you get. If SP is more than CP there is a profit, and if SP is less there is a loss. Both percentages are worked out on the cost price.", "Profit = SP - CP     Loss = CP - SP     Profit% = (Profit/CP) x 100     Loss% = (Loss/CP) x 100", Arrays.asList("CP Rs. 400, SP Rs. 460: profit = 60, Profit% = (60/400) x 100 = 15%", "CP Rs. 500, SP Rs. 450: loss = 50, Loss% = (50/500) x 100 = 10%")),
                new LessonContent(DISCOUNT, "A discount is a reduction on the marked price (MP), the price printed on the article. The discount percentage is worked out on the marked price.", "Discount = MP - SP     Discount% = (Discount/MP) x 100     SP = MP - Discount", Arrays.asList("MP Rs. 800 with 10% discount: discount = 80, SP = 800 - 80 = Rs. 720", "MP Rs. 500, SP Rs. 450: discount = 50, Discount% = (50/500) x 100 = 10%")),
                new LessonContent(INTEREST, "Interest is the extra money paid for borrowing or earning on savings. Simple interest is always worked out on the original principal. In compound interest, each year's interest is added to the principal before the next year's interest is calculated.", "SI = (P x R x T)/100     Amount A = P + SI     Compound: A = P (1 + R/100)^n,  CI = A - P", Arrays.asList("SI on Rs. 2000 at 5% for 3 years = (2000 x 5 x 3)/100 = Rs. 300", "Rs. 1000 at 10% compounded for 2 years: A = 1000 x 1.1 x 1.1 = Rs. 1210, so CI = Rs. 210"))
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
    public static class MarketTycoonGame extends JFrame {

        private enum GamePhase { BUY_PHASE, SELL_PHASE, BANK_PHASE, GAME_OVER }

        // Financial Variables
        private int currentCash = 2000;
        private int currentLoan = 1000;
        private int currentCost = 0;
        private int day = 1;
        private final int MAX_DAYS = 5;

        private String currentItemName = "";
        private String currentItemEmoji = "";

        private GamePhase phase;
        private int expectedAnswer;
        private final Random random = new Random();

        // JavaFX / Core hooks
        private final Runnable onFinished;
        private boolean finishedNotified = false;

        // UI Components
        private JLabel lblDay, lblCash, lblLoan;
        private JTextArea newsTicker;

        // Deal Desk Components
        private JPanel dealCard;
        private JLabel lblPhaseTitle, lblItemEmoji, lblDealDetails, lblQuestion;
        private JTextField txtInput;
        private JButton btnSubmit;

        // Modern Tycoon Color Palette
        private final Color COLOR_TOP_BAR = new Color(44, 62, 80);    // Navy Blue
        private final Color COLOR_BG = new Color(236, 240, 241);      // Light Gray
        private final Color COLOR_CARD = new Color(255, 255, 255);    // White
        private final Color COLOR_PROFIT = new Color(39, 174, 96);    // Emerald Green
        private final Color COLOR_DEBT = new Color(231, 76, 60);      // Alizarin Red
        private final Color COLOR_ACCENT = new Color(41, 128, 185);   // Bright Blue

        private final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 20);
        private final Font FONT_TEXT = new Font("SansSerif", Font.PLAIN, 16);
        private final Font FONT_EMOJI = new Font("SansSerif", Font.PLAIN, 72);

        // Inventory Options
        private final String[][] INVENTORY = {
                {"📱", "Smartphones"}, {"👟", "Designer Sneakers"},
                {"🎮", "Gaming Consoles"}, {"💻", "Laptops"},
                {"🎸", "Electric Guitars"}, {"🛹", "Hoverboards"}
        };

        public MarketTycoonGame(Runnable onFinished) {
            this.onFinished = onFinished;
            setTitle("Market Tycoon: Wall Street Empire");
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

            // ==========================================
            // 1. TOP DASHBOARD (NAVBAR)
            // ==========================================
            JPanel topPanel = new JPanel(new GridLayout(1, 3));
            topPanel.setBackground(COLOR_TOP_BAR);
            topPanel.setBorder(new EmptyBorder(15, 30, 15, 30));

            lblDay = new JLabel("📅 DAY 1 / 5", SwingConstants.LEFT);
            lblDay.setFont(FONT_HEADER);
            lblDay.setForeground(Color.WHITE);

            lblCash = new JLabel("💵 CASH: $2000", SwingConstants.CENTER);
            lblCash.setFont(new Font("SansSerif", Font.BOLD, 26));
            lblCash.setForeground(COLOR_PROFIT);

            lblLoan = new JLabel("🏦 BANK LOAN: $1000", SwingConstants.RIGHT);
            lblLoan.setFont(FONT_HEADER);
            lblLoan.setForeground(new Color(255, 184, 77)); // Soft Orange

            topPanel.add(lblDay);
            topPanel.add(lblCash);
            topPanel.add(lblLoan);
            add(topPanel, BorderLayout.NORTH);

            // ==========================================
            // 2. MAIN SPLIT AREA
            // ==========================================
            JPanel mainArea = new JPanel(new BorderLayout(20, 0));
            mainArea.setBackground(COLOR_BG);
            mainArea.setBorder(new EmptyBorder(20, 20, 20, 20));

            // --- LEFT: NEWS TICKER ---
            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.setBackground(COLOR_BG);
            leftPanel.setPreferredSize(new Dimension(300, 0));

            JLabel lblNewsHeader = new JLabel("📊 MARKET NEWS");
            lblNewsHeader.setFont(FONT_HEADER);
            lblNewsHeader.setForeground(COLOR_TOP_BAR);
            lblNewsHeader.setBorder(new EmptyBorder(0, 0, 10, 0));

            newsTicker = new JTextArea();
            newsTicker.setEditable(false);
            newsTicker.setLineWrap(true);
            newsTicker.setWrapStyleWord(true);
            newsTicker.setFont(new Font("SansSerif", Font.PLAIN, 14));
            newsTicker.setBackground(new Color(253, 250, 240));
            newsTicker.setBorder(new EmptyBorder(10, 10, 10, 10));

            JScrollPane scrollNews = new JScrollPane(newsTicker);
            scrollNews.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));

            leftPanel.add(lblNewsHeader, BorderLayout.NORTH);
            leftPanel.add(scrollNews, BorderLayout.CENTER);
            mainArea.add(leftPanel, BorderLayout.WEST);

            // --- RIGHT/CENTER: THE DEAL DESK ---
            dealCard = new JPanel(new BorderLayout());
            dealCard.setBackground(COLOR_CARD);
            dealCard.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200), 2, true),
                    new EmptyBorder(30, 30, 30, 30)
            ));

            // Deal Header
            lblPhaseTitle = new JLabel("PROCUREMENT PHASE", SwingConstants.CENTER);
            lblPhaseTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
            lblPhaseTitle.setForeground(COLOR_ACCENT);
            dealCard.add(lblPhaseTitle, BorderLayout.NORTH);

            // Deal Details (Center of the card)
            JPanel dealCenter = new JPanel(new GridLayout(3, 1));
            dealCenter.setBackground(COLOR_CARD);

            lblItemEmoji = new JLabel("📦", SwingConstants.CENTER);
            lblItemEmoji.setFont(FONT_EMOJI);

            lblDealDetails = new JLabel("Base Price: $0 | Discount: 0%", SwingConstants.CENTER);
            lblDealDetails.setFont(new Font("SansSerif", Font.BOLD, 22));
            lblDealDetails.setForeground(COLOR_TOP_BAR);

            lblQuestion = new JLabel("What is your final cost?", SwingConstants.CENTER);
            lblQuestion.setFont(FONT_TEXT);
            lblQuestion.setForeground(Color.DARK_GRAY);

            dealCenter.add(lblItemEmoji);
            dealCenter.add(lblDealDetails);
            dealCenter.add(lblQuestion);
            dealCard.add(dealCenter, BorderLayout.CENTER);

            // Input Area (Bottom of the card)
            JPanel dealBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
            dealBottom.setBackground(COLOR_CARD);

            JLabel lblSign = new JLabel("Amount: $");
            lblSign.setFont(new Font("SansSerif", Font.BOLD, 22));

            txtInput = new JTextField(7);
            txtInput.setFont(new Font("SansSerif", Font.BOLD, 22));
            txtInput.setHorizontalAlignment(JTextField.CENTER);
            txtInput.addActionListener(e -> processTurn()); // Enter key to submit

            btnSubmit = new JButton("SIGN DEAL");
            btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 18));
            btnSubmit.setBackground(COLOR_ACCENT);
            btnSubmit.setForeground(Color.WHITE);
            btnSubmit.setFocusPainted(false);
            btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSubmit.setPreferredSize(new Dimension(160, 45));
            btnSubmit.addActionListener(e -> processTurn());

            dealBottom.add(lblSign);
            dealBottom.add(txtInput);
            dealBottom.add(btnSubmit);
            dealCard.add(dealBottom, BorderLayout.SOUTH);

            mainArea.add(dealCard, BorderLayout.CENTER);
            add(mainArea, BorderLayout.CENTER);

            // Start Game
            logNews("🏢 Welcome to Market Tycoon!");
            logNews("Turn $2000 into a business empire in 5 days.");
            logNews("------------------------");
            startBuyPhase();
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

        private void logNews(String message) {
            newsTicker.append("▶ " + message + "\n\n");
            newsTicker.setCaretPosition(newsTicker.getDocument().getLength());
        }

        private void updateDashboard() {
            lblDay.setText("📅 DAY " + day + " / " + MAX_DAYS);
            lblCash.setText("💵 CASH: $" + currentCash);
        }

        private void setRandomItem() {
            int idx = random.nextInt(INVENTORY.length);
            currentItemEmoji = INVENTORY[idx][0];
            currentItemName = INVENTORY[idx][1];
        }

        // ==========================================
        // GAME PHASES
        // ==========================================

        private void startBuyPhase() {
            phase = GamePhase.BUY_PHASE;
            setRandomItem();

            lblPhaseTitle.setText("📦 PROCUREMENT PHASE");
            lblPhaseTitle.setForeground(COLOR_ACCENT);
            btnSubmit.setText("BUY STOCK");
            btnSubmit.setBackground(COLOR_ACCENT);
            lblItemEmoji.setText(currentItemEmoji);

            int basePrice = (random.nextInt(5) + 1) * 1000; // 1000 to 5000
            int discountPercent = (random.nextInt(4) + 1) * 10; // 10, 20, 30, 40%

            currentCost = basePrice - (basePrice * discountPercent / 100);
            expectedAnswer = currentCost;

            lblDealDetails.setText("<html><center>Supplier offers <b>" + currentItemName + "</b><br>Base Price: $" + basePrice + " | Wholesale Discount: " + discountPercent + "%</center></html>");
            lblQuestion.setText("Calculate the Net Purchase Cost:");

            txtInput.setText("");
            txtInput.requestFocus();
        }

        private void startSellPhase() {
            phase = GamePhase.SELL_PHASE;
            lblPhaseTitle.setText("📈 RETAIL PHASE");
            lblPhaseTitle.setForeground(COLOR_PROFIT);
            btnSubmit.setText("SELL ITEMS");
            btnSubmit.setBackground(COLOR_PROFIT);

            int[] markups = {15, 20, 25, 30, 40, 50};
            int markupPercent = markups[random.nextInt(markups.length)];

            int profit = (currentCost * markupPercent) / 100;
            expectedAnswer = currentCost + profit;

            lblDealDetails.setText("<html><center>Selling your <b>" + currentItemName + "</b><br>Purchased for: $" + currentCost + " | Market Markup: " + markupPercent + "%</center></html>");
            lblQuestion.setText("Calculate your Total Sales Revenue (Cost + Markup):");

            txtInput.setText("");
            txtInput.requestFocus();
        }

        private void startBankPhase() {
            phase = GamePhase.BANK_PHASE;
            lblPhaseTitle.setText("🏦 BANK PHASE");
            lblPhaseTitle.setForeground(COLOR_DEBT);
            btnSubmit.setText("PAY BANK");
            btnSubmit.setBackground(COLOR_DEBT);
            lblItemEmoji.setText("🏦");

            int rate = random.nextInt(4) + 2; // 2% to 5%
            expectedAnswer = (currentLoan * rate) / 100;

            lblDealDetails.setText("<html><center>Daily Bank Settlement<br>Current Loan: $" + currentLoan + " | Simple Interest Rate: " + rate + "%</center></html>");
            lblQuestion.setText("How much interest must you pay today?");

            txtInput.setText("");
            txtInput.requestFocus();
        }

        // ==========================================
        // SUBMISSION LOGIC
        // ==========================================

        private void processTurn() {
            if (phase == GamePhase.GAME_OVER) return;

            String input = txtInput.getText().trim();
            if (input.isEmpty()) return;

            try {
                int playerAns = Integer.parseInt(input);

                if (phase == GamePhase.BUY_PHASE) {
                    resolveBuy(playerAns == expectedAnswer);
                } else if (phase == GamePhase.SELL_PHASE) {
                    resolveSell(playerAns == expectedAnswer);
                } else if (phase == GamePhase.BANK_PHASE) {
                    resolveBank(playerAns == expectedAnswer);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid whole number!", "Accounting Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void resolveBuy(boolean isCorrect) {
            if (isCorrect) {
                if (currentCash >= currentCost) {
                    currentCash -= currentCost;
                    logNews("✅ Bought " + currentItemName + " for $" + currentCost + ".");
                    updateDashboard();
                    startSellPhase();
                } else {
                    logNews("❌ Insufficient funds for $" + currentCost + "! Deal cancelled. You skip selling today.");
                    startBankPhase();
                }
            } else {
                logNews("❌ Math error! You offered $" + txtInput.getText() + " (Correct was $" + expectedAnswer + "). The supplier walked away.");
                startBankPhase();
            }
        }

        private void resolveSell(boolean isCorrect) {
            if (isCorrect) {
                currentCash += expectedAnswer;
                logNews("✅ Sold out! You generated $" + expectedAnswer + " in revenue.");
            } else {
                currentCash += currentCost; // Sold at cost
                logNews("❌ Pricing error! Correct revenue was $" + expectedAnswer + ". You panicked and sold at cost ($" + currentCost + "). Zero profit!");
            }
            updateDashboard();
            startBankPhase();
        }

        private void resolveBank(boolean isCorrect) {
            if (isCorrect) {
                currentCash -= expectedAnswer;
                logNews("✅ Paid the bank $" + expectedAnswer + " in interest.");
            } else {
                int penalty = expectedAnswer + 100;
                currentCash -= penalty;
                logNews("❌ Incorrect payment! Interest was $" + expectedAnswer + ". The bank fined you $100. Total paid: $" + penalty + ".");
            }

            updateDashboard();
            checkGameState();
        }

        private void checkGameState() {
            logNews("------------------------");
            if (currentCash < 0) {
                triggerGameOver("BANKRUPT!", COLOR_DEBT, "You ran out of money. Wall Street is ruthless.");
                return;
            }

            day++;
            if (day > MAX_DAYS) {
                logNews("📊 TRADING WEEK COMPLETE 📊");
                logNews("Starting Cash: $2000 | Final Cash: $" + currentCash);

                if (currentCash >= 6000) {
                    triggerGameOver("WALL STREET TYCOON!", COLOR_PROFIT, "Incredible! You mastered percentage math and built an empire!");
                } else if (currentCash > 2000) {
                    triggerGameOver("SUCCESSFUL MANAGER", COLOR_ACCENT, "Good job! You made a solid profit.");
                } else {
                    triggerGameOver("BUSINESS FAILURE", COLOR_DEBT, "You lost money. Time to review your math notes!");
                }
            } else {
                updateDashboard();
                startBuyPhase();
            }
        }

        private void triggerGameOver(String title, Color color, String message) {
            phase = GamePhase.GAME_OVER;
            txtInput.setEnabled(false);
            btnSubmit.setEnabled(false);

            lblPhaseTitle.setText(title);
            lblPhaseTitle.setForeground(color);
            lblItemEmoji.setText("🏆");
            lblDealDetails.setText("<html><center>Final Cash: <b>$" + currentCash + "</b><br>" + message + "</center></html>");
            lblQuestion.setText("Game Over! Close this window to take your Lesson Test.");
        }
    }
}