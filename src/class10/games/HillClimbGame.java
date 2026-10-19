package class10.games;

import common.gui.games.ArcadeGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * HILL CLIMB - a car waits at the bottom of a hill. A flag is planted at each
 * checkpoint and its height is a term of an AP. One flag is missing (or you
 * must answer a question about the flags). Pick the right answer and the hill
 * rises into place, so the car can drive over it. A wrong answer costs a life.
 *
 * 7 levels, one per AP concept, 2 hills per level, 3 lives for the run.
 */
public class HillClimbGame extends ArcadeGame {

    private static final String[] LEVEL_NAMES = {
            "SEQUENCE", "IS IT AN AP?", "FIRST TERM", "COMMON DIFFERENCE", "nth TERM", "SUM", "MEAN"
    };
    private static final int LEVELS = LEVEL_NAMES.length;
    private static final int QUESTIONS_PER_LEVEL = 2;
    private static final int START_LIVES = 3;

    private static final double FLOOR = HEIGHT - HUD_BOTTOM;   // bottom of the playing area
    private static final double BASE_Y = 500;                  // height 0 of the hill
    private static final double START_Y = 330;                 // flat road at both ends
    private static final double MAX_HILL_H = 200;
    private static final double GAP_PIT = 60;                  // depth of the hole for a missing flag
    private static final double SPAN_LEFT = 130;               // flags live between the two flat roads
    private static final double SPAN_WIDTH = 640;
    private static final double CAR_START = 70;
    private static final double CAR_END = 830;
    private static final double CAR_SPEED = 300;               // pixels per second

    private static class Chip {
        final double x, y, w, h;
        final String label;
        final int value;
        boolean off;                                           // true after a wrong click

        Chip(double x, double y, double w, double h, String label, int value) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.label = label;
            this.value = value;
        }

        boolean contains(double px, double py) {
            return px >= x && px <= x + w && py >= y && py <= y + h;
        }
    }

    // ---- run state ----
    private int level = -1;
    private int qIndex;
    private int lives = START_LIVES;
    private boolean failed;

    // ---- current hill / question ----
    private int[] values = new int[0];     // flag heights (the missing flag holds its real value)
    private int gapIndex = -1;             // -1 = no missing flag
    private boolean gapFilled;
    private String gapLabel = "";
    private final List<Chip> chips = new ArrayList<>();
    private int correctValue;
    private int mistakes;
    private boolean hintShown;
    private String question = "";
    private String hint = "";
    private String solution = "";

    // ---- car / animation ----
    private boolean crossing;
    private double cartX = CAR_START;
    private double pulse;

    public HillClimbGame() {
        roundTime = 75;
    }

    @Override
    protected String title() {
        return "HILL CLIMB";
    }

    @Override
    protected String wonMessage() {
        return level == LEVELS - 1
                ? "ALL 7 HILLS CLIMBED! CLICK TO PLAY AGAIN"
                : "HILL CLEARED! CLICK FOR THE NEXT HILL";
    }

    @Override
    protected String lostMessage() {
        return lives <= 0
                ? "OUT OF LIVES! CLICK TO RESTART"
                : "TIME UP! CLICK TO RESTART";
    }

    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            return solution;
        }
        return hintShown ? question + "\n" + hint : question;
    }

    // =========================================================
    // Round and question setup
    // =========================================================

    @Override
    protected void startRound() {
        if (failed) {
            level = 0;
            lives = START_LIVES;
            failed = false;
        } else {
            level = (level + 1) % LEVELS;
            if (level == 0) {
                lives = START_LIVES;
            }
        }
        qIndex = 0;
        loadQuestion();
    }

    private void loadQuestion() {
        crossing = false;
        cartX = CAR_START;
        hintShown = false;
        mistakes = 0;
        gapIndex = -1;
        gapFilled = false;
        gapLabel = "";
        chips.clear();

        switch (level) {
            case 0: genNext(); break;
            case 1: genIsAp(); break;
            case 2: genFirst(); break;
            case 3: genDifference(); break;
            case 4: genNthTerm(); break;
            case 5: genSum(); break;
            default: genMean(); break;
        }
    }

    // Level 1 - next term: the last flag is missing
    private void genNext() {
        int d = 2 + random.nextInt(4);                    // 2..5
        int a = 2 + random.nextInt(8);                    // 2..9
        values = ap(a, d, 6);
        gapIndex = 5;
        int last = values[4];

        buildChips(values[5], false, last, last + 2 * d, last + d + 1);
        question = "SEQUENCE: what is the height of the next flag?";
        hint = "Each flag is higher by the same amount - add it to the last flag";
        solution = "d = " + (values[1] - values[0]) + ",  next = " + last + " + " + d + " = " + values[5];
    }

    // Level 2 - is it an AP: YES / NO
    private void genIsAp() {
        boolean isAp = random.nextBoolean();
        if (isAp) {
            int d = 2 + random.nextInt(4);
            int a = 2 + random.nextInt(7);
            values = ap(a, d, 5);
        } else if (random.nextBoolean()) {
            int a = 1 + random.nextInt(3);                // doubling: 3, 6, 12, 24, 48
            values = new int[5];
            for (int i = 0; i < 5; i++) values[i] = a << i;
        } else {
            int[] steps = new int[4];
            do {                                          // uneven gaps, not all equal
                for (int i = 0; i < 4; i++) steps[i] = 1 + random.nextInt(6);
            } while (steps[0] == steps[1] && steps[1] == steps[2] && steps[2] == steps[3]);
            values = new int[5];
            values[0] = 2 + random.nextInt(6);
            for (int i = 1; i < 5; i++) values[i] = values[i - 1] + steps[i - 1];
        }

        double w = 250, h = 58;
        double startX = (WIDTH - (2 * w + 40)) / 2;
        chips.add(new Chip(startX, 160, w, h, "YES - IT IS AN AP", 1));
        chips.add(new Chip(startX + w + 40, 160, w, h, "NO - NOT AN AP", 0));
        correctValue = isAp ? 1 : 0;

        question = "IS IT AN AP? are the steps between the flags all equal?";
        hint = "Subtract each flag from the next one and compare the answers";
        solution = "steps: " + gaps(values) + (isAp ? "  - all equal, so it IS an AP"
                : "  - not all equal, so it is NOT an AP");
    }

    // Level 3 - first term: the first flag is missing
    private void genFirst() {
        int d = 2 + random.nextInt(3);                    // 2..4
        int a = 5 + random.nextInt(8);                    // 5..12
        values = ap(a, d, 5);
        gapIndex = 0;

        buildChips(a, false, a + d, a + 2 * d, a - d);
        question = "FIRST TERM: what is the missing FIRST flag (a)?";
        hint = "Work backwards: first flag = second flag - d";
        solution = "d = " + d + ",  a = second - d = " + values[1] + " - " + d + " = " + a;
    }

    // Level 4 - common difference (can be negative: the hill goes downhill)
    private void genDifference() {
        int[] choices = {-4, -3, -2, 2, 3, 4};
        int d = choices[random.nextInt(choices.length)];
        int a = d > 0 ? 3 + random.nextInt(8) : 18 + random.nextInt(7);
        values = ap(a, d, 5);

        buildChips(d, true, -d, d + 1, d - 1);
        question = "COMMON DIFFERENCE: what is d for these flags?";
        hint = "d = a flag minus the one before it - watch the sign!";
        solution = "d = " + values[1] + " - " + values[0] + " = " + d;
    }

    // Level 5 - nth term: first 4 flags shown, flag n is missing
    private void genNthTerm() {
        int d = 2 + random.nextInt(4);                    // 2..5
        int a = 3 + random.nextInt(7);                    // 3..9
        int n = 7 + random.nextInt(4);                    // 7..10
        int answer = a + (n - 1) * d;

        values = new int[5];
        for (int i = 0; i < 4; i++) values[i] = a + i * d;
        values[4] = answer;
        gapIndex = 4;
        gapLabel = "flag " + n;

        buildChips(answer, false, answer + d, answer - d, n * d);
        question = "nth TERM: a = " + a + ", d = " + d + ". How high is flag " + n + "?";
        hint = "Flag 1 is the start, so flag n needs only n - 1 steps of d";
        solution = "a" + n + " = a + (n-1)d = " + a + " + " + (n - 1) + " x " + d + " = " + answer;
    }

    // Level 6 - sum of all flags
    private void genSum() {
        int n = 4 + 2 * random.nextInt(3);                // 4, 6 or 8 flags
        int a = 1 + random.nextInt(4);
        int d = 1 + random.nextInt(3);
        values = ap(a, d, n);

        int pairSum = values[0] + values[n - 1];
        int total = n * pairSum / 2;
        buildChips(total, false, n * pairSum, pairSum, total + a);
        question = "SUM: what is the total height of ALL " + n + " flags?";
        hint = "Pair first + last, 2nd + 2nd last... every pair has the same sum";
        solution = "S = n/2 x (first + last) = " + n + "/2 x (" + values[0] + " + "
                + values[n - 1] + ") = " + total;
    }

    // Level 7 - arithmetic mean: the middle flag is missing
    private void genMean() {
        int x = 3 + random.nextInt(8);
        int k = 1 + random.nextInt(4);
        int y = x + 2 * k;
        int mean = x + k;
        values = new int[]{x, mean, y};
        gapIndex = 1;

        buildChips(mean, false, mean + 1, mean - 1, x + y);
        question = "MEAN: what flag sits exactly between " + x + " and " + y + "?";
        hint = "Arithmetic mean = (first + last) / 2, exactly halfway";
        solution = "AM = (" + x + " + " + y + ") / 2 = " + mean;
    }

    // =========================================================
    // Helpers
    // =========================================================

    private int[] ap(int a, int d, int count) {
        int[] out = new int[count];
        for (int i = 0; i < count; i++) out[i] = a + i * d;
        return out;
    }

    private String gaps(int[] v) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < v.length; i++) {
            if (i > 1) sb.append(", ");
            sb.append(v[i] - v[i - 1]);
        }
        return sb.toString();
    }

    private String signed(int v) {
        return v > 0 ? "+" + v : String.valueOf(v);
    }

    // Four answer chips: the correct one plus three wrong ones, in random order
    private void buildChips(int correct, boolean withSign, int... extras) {
        Set<Integer> pool = new LinkedHashSet<>();
        pool.add(correct);
        for (int e : extras) pool.add(e);
        int offset = 1;
        while (pool.size() < 4) {
            pool.add(correct + offset);
            if (pool.size() < 4 && (correct <= 0 || correct - offset > 0)) {
                pool.add(correct - offset);
            }
            offset++;
        }

        List<Integer> others = new ArrayList<>(pool);
        others.remove(Integer.valueOf(correct));
        Collections.shuffle(others, random);
        List<Integer> four = new ArrayList<>(others.subList(0, 3));
        four.add(correct);
        Collections.shuffle(four, random);

        double w = 150, h = 58, gap = 24;
        double startX = (WIDTH - (4 * w + 3 * gap)) / 2;
        for (int i = 0; i < 4; i++) {
            int v = four.get(i);
            chips.add(new Chip(startX + i * (w + gap), 160, w, h,
                    withSign ? signed(v) : String.valueOf(v), v));
        }
        correctValue = correct;
    }

    private double slotCenter(int i) {
        double pitch = SPAN_WIDTH / values.length;
        return SPAN_LEFT + pitch * (i + 0.5);
    }

    private double unit() {
        int max = 1;
        for (int v : values) max = Math.max(max, v);
        return MAX_HILL_H / max;
    }

    private double hillHeight(int i) {
        return Math.max(14, values[i] * unit());
    }

    private boolean flagVisible(int i) {
        return i != gapIndex || gapFilled || state != State.PLAYING;
    }

    // Ground height at a flag. A missing flag is a hole of fixed depth, so it gives nothing away.
    private double groundY(int i) {
        return flagVisible(i) ? BASE_Y - hillHeight(i) : BASE_Y - GAP_PIT;
    }

    // Height of the road at a given x: flat start, the flags, flat finish.
    // Only used once the missing flag is filled in, so it uses the real heights.
    private double pathY(double x) {
        double prevX = CAR_START;
        double prevY = START_Y;
        for (int i = 0; i <= values.length; i++) {
            double nx;
            double ny;
            if (i < values.length) {
                nx = slotCenter(i);
                ny = BASE_Y - hillHeight(i);
            } else {
                nx = CAR_END;
                ny = START_Y;
            }
            if (x <= nx) {
                double t = (x - prevX) / (nx - prevX);
                return prevY + (ny - prevY) * Math.max(0, Math.min(1, t));
            }
            prevX = nx;
            prevY = ny;
        }
        return START_Y;
    }

    // =========================================================
    // Input and update
    // =========================================================

    @Override
    protected void onPlayClick(double x, double y) {
        if (crossing) return;
        for (Chip c : chips) {
            if (!c.contains(x, y) || c.off) continue;
            if (c.value == correctValue) {
                solved(x, y);
            } else {
                c.off = true;
                mistakes++;
                hintShown = true;
                lives--;
                wrongAnswer(0, "WRONG!  -1 LIFE", x, y);
                if (lives <= 0) {
                    state = State.GAME_OVER;
                }
            }
            return;
        }
    }

    private void solved(double x, double y) {
        int points = mistakes == 0 ? 100 : 40;
        score += points;
        floatingText("+" + points, x, y + 60, Color.LIMEGREEN);
        gapFilled = true;
        crossing = true;
    }

    @Override
    protected void onUpdate(double dt) {
        pulse += 3 * dt;
        if (state == State.GAME_OVER) {
            failed = true;                                // next click restarts at level 1
            return;
        }
        if (state == State.PLAYING && crossing) {
            cartX += CAR_SPEED * dt;
            if (cartX >= CAR_END) {
                crossing = false;
                nextQuestion();
            }
        }
    }

    private void nextQuestion() {
        qIndex++;
        if (qIndex >= QUESTIONS_PER_LEVEL) {
            int bonus = timeBonus();
            roundWon(bonus);
            floatingText("LEVEL BONUS +" + bonus, WIDTH / 2, 270, Color.GOLD);
        } else {
            loadQuestion();
        }
    }

    // =========================================================
    // Drawing
    // =========================================================

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        drawSky(gc);
        drawTerrain(gc);
        drawFlags(gc);
        drawStartFinish(gc);
        drawCar(gc);
        drawChips(gc);
        drawInfo(gc);
    }

    private void centerText(GraphicsContext gc, String text, double x, double y, double size, Color color) {
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, size));
        gc.setFill(color);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, x, y);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawSky(GraphicsContext gc) {
        gc.setFill(Color.web("#0f172a"));
        gc.fillRect(0, HUD_TOP, WIDTH, FLOOR - HUD_TOP);

        gc.setFill(Color.web("#fde68a"));
        gc.fillOval(780, 240, 44, 44);

        gc.setFill(Color.web("#1e293b"));
        gc.fillPolygon(
                new double[]{0, 120, 260, 380, 520, 660, 800, 900, 900, 0},
                new double[]{330, 262, 312, 240, 304, 250, 312, 270, 330, 330},
                10);
    }

    private void drawTerrain(GraphicsContext gc) {
        int n = values.length;
        double[] xs = new double[n + 6];
        double[] ys = new double[n + 6];

        xs[0] = 0;          ys[0] = START_Y;
        xs[1] = CAR_START;  ys[1] = START_Y;
        for (int i = 0; i < n; i++) {
            xs[i + 2] = slotCenter(i);
            ys[i + 2] = groundY(i);
        }
        xs[n + 2] = CAR_END; ys[n + 2] = START_Y;
        xs[n + 3] = WIDTH;   ys[n + 3] = START_Y;
        xs[n + 4] = WIDTH;   ys[n + 4] = FLOOR;
        xs[n + 5] = 0;       ys[n + 5] = FLOOR;

        gc.setFill(Color.web("#78350f"));
        gc.fillPolygon(xs, ys, n + 6);

        gc.setStroke(Color.web("#65a30d"));
        gc.setLineWidth(8);
        for (int k = 0; k <= n + 2; k++) {
            gc.strokeLine(xs[k], ys[k], xs[k + 1], ys[k + 1]);
        }
    }

    private void drawFlags(GraphicsContext gc) {
        for (int i = 0; i < values.length; i++) {
            double cx = slotCenter(i);
            double ty = groundY(i);

            if (!flagVisible(i)) {
                centerText(gc, "?", cx, ty - 14, 44, Color.ORANGE);
                if (!gapLabel.isEmpty()) {
                    centerText(gc, gapLabel, cx, FLOOR - 12, 16, Color.WHITE);
                }
                continue;
            }

            boolean revealed = i == gapIndex && !gapFilled;       // shown after a game over
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(3);
            gc.strokeLine(cx, ty, cx, ty - 44);

            double wave = 2 * Math.sin(pulse + i);
            gc.setFill(revealed ? Color.GOLD : Color.web("#ef4444"));
            gc.fillPolygon(new double[]{cx, cx + 24, cx},
                    new double[]{ty - 44, ty - 36 + wave, ty - 28}, 3);

            centerText(gc, String.valueOf(values[i]), cx, ty - 54, 20,
                    revealed ? Color.GOLD : Color.WHITE);
        }
    }

    private void drawStartFinish(GraphicsContext gc) {
        centerText(gc, "START", 55, START_Y + 34, 14, Color.WHITE);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeLine(WIDTH - 55, START_Y, WIDTH - 55, START_Y - 50);
        gc.setFill(Color.WHITE);
        gc.fillRect(WIDTH - 55, START_Y - 50, 24, 10);
        gc.setFill(Color.BLACK);
        gc.fillRect(WIDTH - 55, START_Y - 50, 12, 5);
        gc.fillRect(WIDTH - 43, START_Y - 45, 12, 5);
        centerText(gc, "FINISH", WIDTH - 55, START_Y + 34, 14, Color.WHITE);
    }

    private void drawCar(GraphicsContext gc) {
        double y = pathY(cartX);
        double slope = Math.atan2(pathY(cartX + 6) - pathY(cartX - 6), 12);

        gc.save();
        gc.translate(cartX, y);
        gc.rotate(Math.toDegrees(slope));

        gc.setFill(Color.web("#2563eb"));
        gc.fillRoundRect(-34, -36, 68, 26, 8, 8);
        gc.setFill(Color.web("#93c5fd"));
        gc.fillRoundRect(-8, -50, 32, 18, 6, 6);
        gc.setFill(Color.web("#0b1220"));
        gc.fillOval(-27, -18, 22, 22);
        gc.fillOval(6, -18, 22, 22);
        gc.setFill(Color.web("#94a3b8"));
        gc.fillOval(-20, -11, 8, 8);
        gc.fillOval(13, -11, 8, 8);

        gc.restore();
    }

    private void drawChips(GraphicsContext gc) {
        boolean reveal = state != State.PLAYING;
        for (Chip c : chips) {
            boolean gold = reveal && c.value == correctValue;
            Color fill = gold ? Color.GOLD : (c.off ? Color.web("#3f1d1d") : Color.web("#334155"));
            Color text = gold ? Color.web("#0f172a") : (c.off ? Color.web("#7f1d1d") : Color.WHITE);
            gc.setFill(fill);
            gc.fillRoundRect(c.x, c.y, c.w, c.h, 16, 16);
            centerText(gc, c.label, c.x + c.w / 2, c.y + c.h / 2 + 8, 24, text);
        }
    }

    private void drawInfo(GraphicsContext gc) {
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        gc.setFill(Color.web("#94a3b8"));
        gc.fillText("LEVEL " + (level + 1) + "/" + LEVELS + ": " + LEVEL_NAMES[level]
                + "   HILL " + Math.min(qIndex + 1, QUESTIONS_PER_LEVEL) + "/" + QUESTIONS_PER_LEVEL, 24, 138);

        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText("LIVES", WIDTH - 24 - START_LIVES * 26 - 4, 138);
        gc.setTextAlign(TextAlignment.LEFT);
        for (int i = 0; i < START_LIVES; i++) {
            gc.setFill(i < lives ? Color.web("#ef4444") : Color.web("#475569"));
            gc.fillOval(WIDTH - 24 - (START_LIVES - i) * 26 + 4, 124, 18, 18);
        }
    }
}