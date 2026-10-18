package class7.games;

import common.gui.games.ArcadeGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SmoothieRushGame extends ArcadeGame {

    // ------------------------------------------------------------------
    // Layout
    // ------------------------------------------------------------------
    private static final double JAR_CX = 470;
    private static final double JAR_BOTTOM = 450;
    private static final double JAR_TOP = 210;
    private static final double JAR_HW_BOTTOM = 55;
    private static final double JAR_HW_TOP = 80;
    private static final int MAX_TOTAL = 30;
    private static final double CUP_PX = (JAR_BOTTOM - JAR_TOP) / MAX_TOTAL;

    private static final double[] MILK_M5   = {640, 150, 40, 50};
    private static final double[] MILK_M1   = {684, 150, 40, 50};
    private static final double[] MILK_P1   = {786, 150, 40, 50};
    private static final double[] MILK_P5   = {830, 150, 40, 50};
    private static final double[] FRUIT_M5  = {640, 270, 40, 50};
    private static final double[] FRUIT_M1  = {684, 270, 40, 50};
    private static final double[] FRUIT_P1  = {786, 270, 40, 50};
    private static final double[] FRUIT_P5  = {830, 270, 40, 50};
    private static final double[] SERVE     = {650, 375, 210, 70};

    private static final Color MILK_COLOR = Color.web("#f1f5f9");
    private static final Color FRUIT_COLOR = Color.web("#fb923c");
    private static final Color[] CUSTOMER_COLORS = {
            Color.web("#60a5fa"), Color.web("#f472b6"), Color.web("#a78bfa"),
            Color.web("#34d399"), Color.web("#fbbf24")
    };
    private static final Color[] CONFETTI = {
            Color.GOLD, Color.HOTPINK, Color.CYAN, Color.LIMEGREEN, Color.ORANGE
    };

    private static final int[][] EASY = {{1, 2}, {1, 3}, {2, 1}, {3, 1}};
    private static final int[][] HARD = {{2, 3}, {3, 2}, {3, 4}, {4, 3}, {2, 5}, {5, 2}};

    // ------------------------------------------------------------------
    // Particles (confetti and falling drops)
    // ------------------------------------------------------------------
    private static class Particle {
        double x, y, vx, vy, life, maxLife, size, gravity;
        Color color;

        Particle(double x, double y, double vx, double vy, double life, double size, double gravity, Color color) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.life = life;
            this.maxLife = life;
            this.size = size;
            this.gravity = gravity;
            this.color = color;
        }
    }

    private final List<Particle> particles = new ArrayList<>();

    // ------------------------------------------------------------------
    // Game state
    // ------------------------------------------------------------------
    private boolean firstRound = true;
    private boolean lastRoundWon = false;
    private boolean outOfLives = false;
    private int lives = 3;
    private int level = 1;
    private int served = 0;
    private int combo = 0;

    private boolean typeB;                 // false: milk is given. true: set both amounts
    private int ratioA, ratioB, k;
    private int targetMilk, targetFruit;
    private int milk, fruit;
    private double shownMilk, shownFruit;  // eased values used for drawing
    private final String[] orderLines = new String[3];
    private String workingText = "";
    private String hintText = "";
    private boolean hintShown;
    private Color customerColor = CUSTOMER_COLORS[0];
    private double pulse;
    private double hop;
    private double hoverX = -1, hoverY = -1;

    public SmoothieRushGame() {
        roundTime = 45;
    }

    @Override
    protected String title() {
        return "SMOOTHIE RUSH";
    }

    @Override
    protected String wonMessage() {
        return "YUM! CUSTOMER HAPPY! CLICK TO CONTINUE";
    }

    @Override
    protected String lostMessage() {
        return outOfLives ? "OUT OF LIVES! CLICK TO RETRY" : "TOO SLOW! CLICK TO RETRY";
    }

    // ------------------------------------------------------------------
    // Round setup
    // ------------------------------------------------------------------
    @Override
    protected void startRound() {
        // the previous round was lost: start a fresh run
        if (!firstRound && !lastRoundWon) {
            lives = 3;
            level = 1;
            served = 0;
            combo = 0;
            outOfLives = false;
        }
        firstRound = false;
        lastRoundWon = false;
        hintShown = false;
        hop = 0;
        particles.clear();

        roundTime = Math.max(25, 45 - 3 * (level - 1));
        customerColor = CUSTOMER_COLORS[random.nextInt(CUSTOMER_COLORS.length)];

        int[] r;
        if (level <= 2) {
            r = EASY[random.nextInt(EASY.length)];
        } else if (level <= 4) {
            r = random.nextBoolean() ? EASY[random.nextInt(EASY.length)] : HARD[random.nextInt(HARD.length)];
        } else {
            r = HARD[random.nextInt(HARD.length)];
        }
        ratioA = r[0];
        ratioB = r[1];

        int maxK = Math.min(28 / (ratioA + ratioB), level <= 2 ? 3 : 5);
        k = 2 + random.nextInt(maxK - 1);          // 2..maxK
        targetMilk = k * ratioA;
        targetFruit = k * ratioB;
        int parts = ratioA + ratioB;
        int total = k * parts;

        typeB = level >= 3 && random.nextBoolean();

        if (typeB) {
            milk = 0;
            fruit = 0;
            orderLines[0] = "Make " + total + " cups in total,";
            orderLines[1] = "milk : fruit = " + ratioA + " : " + ratioB + ".";
            orderLines[2] = "Set BOTH amounts!";
            workingText = ratioA + " + " + ratioB + " = " + parts + " parts, "
                    + total + " / " + parts + " = " + k + " per part -> milk "
                    + targetMilk + ", fruit " + targetFruit;
            hintText = "Hint: total parts = " + ratioA + " + " + ratioB + " = " + parts
                    + ". One part = " + total + " / " + parts + ".";
        } else {
            milk = targetMilk;
            fruit = 0;
            orderLines[0] = "Recipe is  " + ratioA + " milk : " + ratioB + " fruit";
            orderLines[1] = "I want " + targetMilk + " cups of milk.";
            orderLines[2] = "How much fruit to add?";
            workingText = targetMilk + " / " + ratioA + " = " + k + " batches -> fruit = "
                    + k + " x " + ratioB + " = " + targetFruit;
            hintText = "Hint: how many times does " + ratioA + " fit into " + targetMilk
                    + "? Multiply fruit by that.";
        }
        shownMilk = milk;
        shownFruit = fruit;
    }

    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            return workingText;
        }
        if (hintShown) {
            return "Mix it right, then press SERVE!\n" + hintText;
        }
        return "Mix it right, then press SERVE!";
    }

    // ------------------------------------------------------------------
    // Input
    // ------------------------------------------------------------------
    private boolean hit(double x, double y, double[] r) {
        return x >= r[0] && x <= r[0] + r[2] && y >= r[1] && y <= r[1] + r[3];
    }

    @Override
    protected void onMouseMove(double x, double y) {
        hoverX = x;
        hoverY = y;
    }

    @Override
    protected void onPlayClick(double x, double y) {
        if (hit(x, y, SERVE)) serve();
        else if (hit(x, y, MILK_M5)) adjust(true, -5);
        else if (hit(x, y, MILK_M1)) adjust(true, -1);
        else if (hit(x, y, MILK_P1)) adjust(true, 1);
        else if (hit(x, y, MILK_P5)) adjust(true, 5);
        else if (hit(x, y, FRUIT_M5)) adjust(false, -5);
        else if (hit(x, y, FRUIT_M1)) adjust(false, -1);
        else if (hit(x, y, FRUIT_P1)) adjust(false, 1);
        else if (hit(x, y, FRUIT_P5)) adjust(false, 5);
    }

    private void adjust(boolean forMilk, int delta) {
        if (forMilk && !typeB) return;              // milk is fixed in the first order type

        if (delta > 0) {
            int room = MAX_TOTAL - (milk + fruit);
            delta = Math.min(delta, room);
        } else {
            int current = forMilk ? milk : fruit;
            delta = Math.max(delta, -current);
        }
        if (delta == 0) return;

        if (forMilk) milk += delta; else fruit += delta;
        if (delta > 0) spawnDrops(forMilk ? MILK_COLOR : FRUIT_COLOR);
    }

    private void serve() {
        if (milk == targetMilk && fruit == targetFruit) {
            lastRoundWon = true;
            combo++;
            served++;
            int newLevel = 1 + served / 3;
            int points = 100 + timeBonus() + 25 * combo;
            roundWon(points);
            hop = 1;
            spawnConfetti();
            floatingText("+" + points, JAR_CX, JAR_TOP - 20, Color.LIMEGREEN);
            if (combo >= 2) {
                floatingText("COMBO x" + combo + "!", JAR_CX, JAR_TOP - 50, Color.GOLD);
            }
            if (newLevel > level) {
                level = newLevel;
                floatingText("LEVEL UP!", WIDTH / 2, HUD_TOP + 130, Color.CYAN);
            }
            return;
        }

        String msg;
        if (milk == 0 && fruit == 0) {
            msg = "ADD SOMETHING!";
        } else if ((long) fruit * targetMilk == (long) milk * targetFruit) {
            msg = "RIGHT TASTE, WRONG AMOUNT!";
        } else if ((long) fruit * targetMilk > (long) milk * targetFruit) {
            msg = "TOO FRUITY!";
        } else {
            msg = "TOO MILKY!";
        }

        combo = 0;
        lives--;
        hintShown = true;
        wrongAnswer(4, msg, JAR_CX, JAR_TOP - 20);

        if (lives <= 0) {
            outOfLives = true;
            state = State.GAME_OVER;
        }
    }

    private void spawnDrops(Color c) {
        for (int i = 0; i < 6; i++) {
            particles.add(new Particle(
                    JAR_CX + (random.nextDouble() - 0.5) * 60, JAR_TOP - 25,
                    (random.nextDouble() - 0.5) * 40, 60 + random.nextDouble() * 60,
                    0.45, 5, 600, c));
        }
    }

    private void spawnConfetti() {
        for (int i = 0; i < 50; i++) {
            particles.add(new Particle(
                    JAR_CX, JAR_TOP + 40,
                    (random.nextDouble() - 0.5) * 500, -100 - random.nextDouble() * 250,
                    1.4, 7, 450, CONFETTI[random.nextInt(CONFETTI.length)]));
        }
    }

    // ------------------------------------------------------------------
    // Update
    // ------------------------------------------------------------------
    @Override
    protected void onUpdate(double dt) {
        pulse += dt;
        hop = Math.max(0, hop - dt * 0.8);

        double ease = Math.min(1, 8 * dt);
        shownMilk += (milk - shownMilk) * ease;
        shownFruit += (fruit - shownFruit) * ease;

        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.vy += p.gravity * dt;
            p.x += p.vx * dt;
            p.y += p.vy * dt;
            p.life -= dt;
            if (p.life <= 0) it.remove();
        }
    }

    // ------------------------------------------------------------------
    // Drawing
    // ------------------------------------------------------------------
    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        drawWall(gc);
        drawCustomer(gc);
        drawCounter(gc);
        drawBubble(gc);
        drawJar(gc);
        drawPanel(gc);
        drawStatus(gc);
        drawParticles(gc);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawWall(GraphicsContext gc) {
        gc.setFill(Color.web("#3b0764"));
        gc.fillRect(0, HUD_TOP, WIDTH, HEIGHT - HUD_TOP - HUD_BOTTOM);
        gc.setFill(Color.rgb(255, 255, 255, 0.04));
        for (double x = 0; x < WIDTH; x += 60) {
            gc.fillRect(x, HUD_TOP, 30, HEIGHT - HUD_TOP - HUD_BOTTOM);
        }
    }

    private void drawCounter(GraphicsContext gc) {
        gc.setFill(Color.web("#92400e"));
        gc.fillRect(0, JAR_BOTTOM, WIDTH, HEIGHT - HUD_BOTTOM - JAR_BOTTOM);
        gc.setFill(Color.web("#b45309"));
        gc.fillRect(0, JAR_BOTTOM, WIDTH, 8);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.GOLD);
        gc.fillText("SMOOTHIE SHOP", 160, JAR_BOTTOM + 50);
    }

    private void drawCustomer(GraphicsContext gc) {
        double cx = 130;
        double yOff = -Math.abs(Math.sin(pulse * 6)) * 14 * hop;

        gc.setFill(customerColor);
        gc.fillRoundRect(cx - 48, 345 + yOff, 96, 110, 30, 30);
        gc.setFill(Color.web("#fde68a"));
        gc.fillOval(cx - 40, 262 + yOff, 80, 80);

        gc.setFill(Color.BLACK);
        gc.fillOval(cx - 18, 292 + yOff, 8, 10);
        gc.fillOval(cx + 10, 292 + yOff, 8, 10);

        double patience = Math.max(0, timeLeft / roundTime);
        boolean happy = state == State.ROUND_WON || (state == State.PLAYING && patience > 0.5);
        boolean neutral = state == State.PLAYING && patience <= 0.5 && patience > 0.25;
        double my = 318 + yOff;

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        if (happy) {
            gc.strokeArc(cx - 16, my - 10, 32, 22, 180, 180, ArcType.OPEN);
        } else if (neutral) {
            gc.strokeLine(cx - 12, my + 4, cx + 12, my + 4);
        } else {
            gc.strokeArc(cx - 16, my + 2, 32, 22, 0, 180, ArcType.OPEN);
            gc.strokeLine(cx - 24, 284 + yOff, cx - 8, 290 + yOff);
            gc.strokeLine(cx + 8, 290 + yOff, cx + 24, 284 + yOff);
        }
    }

    private void drawBubble(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRoundRect(20, 108, 335, 95, 20, 20);
        gc.fillPolygon(new double[]{95, 135, 115}, new double[]{201, 201, 240}, 3);

        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        gc.setFill(Color.web("#1e1b4b"));
        for (int i = 0; i < orderLines.length; i++) {
            gc.fillText(orderLines[i], 34, 134 + i * 24);
        }
    }

    private double halfWidth(double y) {
        return JAR_HW_BOTTOM + (JAR_BOTTOM - y) / (JAR_BOTTOM - JAR_TOP) * (JAR_HW_TOP - JAR_HW_BOTTOM);
    }

    private void fillLayer(GraphicsContext gc, double yLow, double yHigh, Color color) {
        if (yLow - yHigh < 0.5) return;
        gc.setFill(color);
        gc.fillPolygon(
                new double[]{JAR_CX - halfWidth(yLow), JAR_CX + halfWidth(yLow),
                        JAR_CX + halfWidth(yHigh), JAR_CX - halfWidth(yHigh)},
                new double[]{yLow, yLow, yHigh, yHigh}, 4);
    }

    private void drawJar(GraphicsContext gc) {
        // blender base and lid
        gc.setFill(Color.web("#334155"));
        gc.fillRoundRect(JAR_CX - 75, JAR_BOTTOM, 150, 28, 10, 10);
        gc.setFill(Color.web("#64748b"));
        gc.fillRoundRect(JAR_CX - JAR_HW_TOP - 8, JAR_TOP - 14, 2 * JAR_HW_TOP + 16, 14, 8, 8);

        // liquids
        double milkH = shownMilk * CUP_PX;
        double fruitH = shownFruit * CUP_PX;
        fillLayer(gc, JAR_BOTTOM, JAR_BOTTOM - milkH, MILK_COLOR);
        fillLayer(gc, JAR_BOTTOM - milkH, JAR_BOTTOM - milkH - fruitH, FRUIT_COLOR);

        // bubbles
        double liquid = milkH + fruitH;
        if (liquid > 12) {
            gc.setFill(Color.rgb(255, 255, 255, 0.45));
            for (int i = 0; i < 5; i++) {
                double by = JAR_BOTTOM - ((pulse * 25 + i * 41) % liquid);
                double bx = JAR_CX + Math.sin(pulse * 1.3 + i * 1.9) * halfWidth(by) * 0.6;
                gc.fillOval(bx - 3, by - 3, 6, 6);
            }
        }

        // glass outline
        gc.setStroke(Color.web("#bae6fd"));
        gc.setLineWidth(4);
        gc.strokePolygon(
                new double[]{JAR_CX - JAR_HW_TOP, JAR_CX + JAR_HW_TOP, JAR_CX + JAR_HW_BOTTOM, JAR_CX - JAR_HW_BOTTOM},
                new double[]{JAR_TOP, JAR_TOP, JAR_BOTTOM, JAR_BOTTOM}, 4);

        // cup markers
        gc.setLineWidth(2);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 11));
        gc.setTextAlign(TextAlignment.LEFT);
        for (int c = 5; c <= MAX_TOTAL; c += 5) {
            double y = JAR_BOTTOM - c * CUP_PX;
            double edge = JAR_CX + halfWidth(y);
            gc.setStroke(Color.web("#bae6fd"));
            gc.strokeLine(edge, y, edge + 10, y);
            gc.setFill(Color.web("#bae6fd"));
            gc.fillText(String.valueOf(c), edge + 14, y + 4);
        }
    }

    private void drawPanel(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0, 0.28));
        gc.fillRoundRect(630, 105, 250, 350, 20, 20);

        drawStepper(gc, typeB ? "MILK" : "MILK (fixed)", MILK_COLOR, 142, milk, typeB,
                MILK_M5, MILK_M1, MILK_P1, MILK_P5);
        drawStepper(gc, "FRUIT", FRUIT_COLOR, 262, fruit, true,
                FRUIT_M5, FRUIT_M1, FRUIT_P1, FRUIT_P5);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        gc.setFill(Color.web("#c4b5fd"));
        gc.fillText("Your mix:  " + milk + " : " + fruit, 755, 352);

        drawButton(gc, SERVE, "SERVE!", true, Color.web("#16a34a"));
    }

    private void drawStepper(GraphicsContext gc, String label, Color labelColor, double labelY,
                             int value, boolean enabled,
                             double[] m5, double[] m1, double[] p1, double[] p5) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        gc.setFill(labelColor);
        gc.fillText(label, 755, labelY);

        Color base = Color.web("#6366f1");
        drawButton(gc, m5, "-5", enabled, base);
        drawButton(gc, m1, "-1", enabled, base);
        drawButton(gc, p1, "+1", enabled, base);
        drawButton(gc, p5, "+5", enabled, base);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 34));
        gc.setFill(Color.WHITE);
        gc.fillText(String.valueOf(value), 755, m1[1] + 38);
    }

    private void drawButton(GraphicsContext gc, double[] r, String label, boolean enabled, Color base) {
        boolean hovered = enabled && hit(hoverX, hoverY, r);
        Color fill = !enabled ? Color.web("#475569") : (hovered ? base.brighter() : base);
        gc.setFill(fill);
        gc.fillRoundRect(r[0], r[1], r[2], r[3], 14, 14);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, r[3] > 60 ? 28 : 20));
        gc.setFill(enabled ? Color.WHITE : Color.web("#94a3b8"));
        gc.fillText(label, r[0] + r[2] / 2, r[1] + r[3] / 2 + 8);
    }

    private void drawStatus(GraphicsContext gc) {
        for (int i = 0; i < 3; i++) {
            drawHeart(gc, 30 + i * 34, 64, i < lives);
        }
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        gc.setFill(Color.WHITE);
        gc.fillText("LEVEL " + level + "   SERVED " + served + "   COMBO x" + combo, WIDTH / 2, 70);
    }

    private void drawHeart(GraphicsContext gc, double cx, double cy, boolean full) {
        gc.setFill(full ? Color.web("#ef4444") : Color.web("#475569"));
        gc.fillOval(cx - 11, cy - 10, 12, 12);
        gc.fillOval(cx - 1, cy - 10, 12, 12);
        gc.fillPolygon(new double[]{cx - 11, cx + 11, cx}, new double[]{cy - 3, cy - 3, cy + 12}, 3);
    }

    private void drawParticles(GraphicsContext gc) {
        for (Particle p : particles) {
            gc.setGlobalAlpha(Math.max(0, Math.min(1, p.life / p.maxLife)));
            gc.setFill(p.color);
            gc.fillOval(p.x, p.y, p.size, p.size);
        }
        gc.setGlobalAlpha(1.0);
    }
}