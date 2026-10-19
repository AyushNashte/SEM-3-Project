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

public class APExpressGame extends ArcadeGame {

    private static final String[] STATION_LABELS = {
            "SEQUENCE", "AP", "FIRST a", "DIFF d", "nth TERM", "SUM Sn", "MEAN"
    };
    private static final int STATIONS = STATION_LABELS.length;

    private static final double MEAN_LEFT = 100;
    private static final double MEAN_RIGHT = 800;
    private static final double MEAN_Y = 290;

    private static class Box {
        final double x, y, w, h;
        final String label;
        final int value;

        Box(double x, double y, double w, double h, String label, int value) {
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

    // ---- general state ----
    private int station = -1;
    private boolean failed;
    private boolean hintShown;
    private double pulse;

    private String mission = "";
    private String hint = "";
    private String solution = "";

    // ---- shared by stations 1, 3 (train + answer chips) and 2 (A/B boxes) ----
    private String[] train = new String[0];
    private final List<Box> options = new ArrayList<>();
    private int correctValue;

    // ---- station 2: AP or not ----
    private int[] apRowA = new int[0];
    private int[] apRowB = new int[0];
    private boolean aIsAp;

    // ---- station 4: common difference dial ----
    private int firstA;
    private int targetD;
    private int dialGuess;
    private Box minusBtn, plusBtn, fireBtn;

    // ---- station 5: nth term hop ----
    private int hopA, hopD, hopN, hops;
    private Box hopBtn, landBtn;

    // ---- station 6: sum ----
    private int[] sumTerms = new int[0];
    private boolean paired;
    private Box pairBtn;

    // ---- station 7: arithmetic mean ----
    private int meanX, meanY, meanAnswer, meanMin, meanMax;
    private Integer meanHover;

    public APExpressGame() {
        roundTime = 45;
    }

    @Override
    protected String title() {
        return "AP EXPRESS";
    }

    @Override
    protected String wonMessage() {
        return station == STATIONS - 1
                ? "ALL 7 STATIONS CLEARED! CLICK FOR A NEW LAP"
                : "STATION CLEARED! CLICK FOR THE NEXT";
    }

    @Override
    protected String lostMessage() {
        return "DERAILED! CLICK TO RESTART FROM STATION 1";
    }

    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            return solution;
        }
        return hintShown ? mission + "\n" + hint : mission;
    }

    // =========================================================
    // Round setup
    // =========================================================

    @Override
    protected void startRound() {
        if (failed) {
            station = 0;
            failed = false;
        } else {
            station = (station + 1) % STATIONS;
        }
        hintShown = false;
        options.clear();

        switch (station) {
            case 0: setupSequence(); break;
            case 1: setupAp(); break;
            case 2: setupFirstTerm(); break;
            case 3: setupDifference(); break;
            case 4: setupNthTerm(); break;
            case 5: setupSum(); break;
            default: setupMean(); break;
        }
    }

    private void setupSequence() {
        int[] v = new int[5];
        String rule;
        int type = random.nextInt(4);
        if (type == 0) {
            int a = 2 + random.nextInt(8);
            int k = 2 + random.nextInt(5);
            for (int i = 0; i < 5; i++) v[i] = a + i * k;
            rule = "+" + k + " each time";
        } else if (type == 1) {
            int a = 30 + random.nextInt(16);
            int k = 2 + random.nextInt(5);
            for (int i = 0; i < 5; i++) v[i] = a - i * k;
            rule = "-" + k + " each time";
        } else if (type == 2) {
            int a = 1 + random.nextInt(4);
            for (int i = 0; i < 5; i++) v[i] = a * (1 << i);
            rule = "x2 each time";
        } else {
            int a = 1 + random.nextInt(2);
            int p = 1;
            for (int i = 0; i < 5; i++) {
                v[i] = a * p;
                p *= 3;
            }
            rule = "x3 each time";
        }

        train = new String[]{
                String.valueOf(v[0]), String.valueOf(v[1]),
                String.valueOf(v[2]), String.valueOf(v[3]), "?"
        };
        buildChips(400, v[4], v[3] + (v[3] - v[2]));

        mission = "STATION 1 - SEQUENCE: click the number that comes next";
        hint = "Look at how each wagon changes - that change is the rule";
        solution = "Rule: " + rule + "  ->  next number = " + v[4];
    }

    private void setupAp() {
        int d = (random.nextBoolean() ? 1 : -1) * (2 + random.nextInt(4));
        int start = d > 0 ? 1 + random.nextInt(8) : 16 + random.nextInt(8);
        int[] ap = new int[4];
        for (int i = 0; i < 4; i++) ap[i] = start + i * d;

        int[] other = new int[4];
        if (random.nextBoolean()) {
            int b = 1 + random.nextInt(3);
            for (int i = 0; i < 4; i++) other[i] = b * (1 << i);
        } else {
            int s1, s2, s3;
            do {
                s1 = 1 + random.nextInt(6);
                s2 = 1 + random.nextInt(6);
                s3 = 1 + random.nextInt(6);
            } while (s1 == s2 && s2 == s3);
            other[0] = 2 + random.nextInt(6);
            other[1] = other[0] + s1;
            other[2] = other[1] + s2;
            other[3] = other[2] + s3;
        }

        aIsAp = random.nextBoolean();
        apRowA = aIsAp ? ap : other;
        apRowB = aIsAp ? other : ap;

        options.add(new Box(130, 180, 640, 95, "A", 0));
        options.add(new Box(130, 300, 640, 95, "B", 1));
        correctValue = aIsAp ? 0 : 1;

        mission = "STATION 2 - AP: click the train whose gaps are all EQUAL";
        hint = "Subtract each wagon from the next - an AP gives the same answer";
        solution = "A: gaps " + gaps(apRowA) + (aIsAp ? " = AP" : " = not AP")
                + "    B: gaps " + gaps(apRowB) + (aIsAp ? " = not AP" : " = AP");
    }

    private void setupFirstTerm() {
        int d = pickD();
        int a = startFor(d);
        train = new String[]{
                "?", String.valueOf(a + d), String.valueOf(a + 2 * d), String.valueOf(a + 3 * d)
        };
        buildChips(400, a, a + d, a - d, a + 1);

        mission = "STATION 3 - FIRST TERM: click the missing first wagon (a)";
        hint = "Work backwards: first term = second term - d";
        solution = "a = second term - d = " + (a + d) + " - " + paren(d) + " = " + a;
    }

    private void setupDifference() {
        targetD = pickD();
        firstA = startFor(targetD);
        dialGuess = 0;

        minusBtn = new Box(290, 430, 70, 60, "-", 0);
        plusBtn = new Box(540, 430, 70, 60, "+", 0);
        fireBtn = new Box(650, 430, 170, 60, "SET d", 0);

        mission = "STATION 4 - DIFFERENCE: match YOUR train to the target, then SET d";
        hint = "d = a wagon minus the one before it (watch the sign!)";
        solution = "d = " + (firstA + targetD) + " - " + firstA + " = " + targetD;
    }

    private void setupNthTerm() {
        hopA = 2 + random.nextInt(8);
        hopD = 2 + random.nextInt(5);
        hopN = 4 + random.nextInt(4);
        hops = 0;

        hopBtn = new Box(250, 440, 190, 60, "HOP", 0);
        landBtn = new Box(470, 440, 190, 60, "LAND HERE", 0);

        mission = String.format("STATION 5 - nth TERM: start on term 1 (a=%d, d=%d), LAND on term %d",
                hopA, hopD, hopN);
        hint = "Term 1 is the start, so reaching term n takes n - 1 hops";
        solution = String.format("a%d = a + (n-1)d = %d + %d x %d = %d",
                hopN, hopA, hopN - 1, hopD, hopA + (hopN - 1) * hopD);
    }

    private void setupSum() {
        int n = 4 + 2 * random.nextInt(3);   // 4, 6 or 8 terms
        int a = 1 + random.nextInt(5);
        int d = 1 + random.nextInt(4);
        sumTerms = new int[n];
        for (int i = 0; i < n; i++) sumTerms[i] = a + i * d;

        paired = false;
        pairBtn = new Box(350, 352, 200, 42, "PAIR UP", 0);

        int pairSum = sumTerms[0] + sumTerms[n - 1];
        int total = n * pairSum / 2;
        buildChips(415, total, pairSum, n * pairSum, total + sumTerms[0]);

        mission = "STATION 6 - SUM: click the total of ALL the bars (n = " + n + ")";
        hint = "Pair first+last, 2nd+2nd last... every pair has the same sum";
        solution = String.format("S = n/2 x (first + last) = %d/2 x (%d + %d) = %d",
                n, sumTerms[0], sumTerms[n - 1], total);
    }

    private void setupMean() {
        meanX = 3 + random.nextInt(8);
        int diff = 2 * (1 + random.nextInt(4));
        meanY = meanX + diff;
        meanAnswer = meanX + diff / 2;
        meanMin = meanX - 3;
        meanMax = meanY + 3;
        meanHover = null;

        mission = "STATION 7 - MEAN: click the ARITHMETIC MEAN of A and B";
        hint = "Arithmetic mean = (A + B) / 2, the number exactly halfway";
        solution = String.format("AM = (%d + %d)/2 = %d  (equal gaps of %d)",
                meanX, meanY, meanAnswer, diff / 2);
    }

    // =========================================================
    // Small helpers
    // =========================================================

    private int pickD() {
        int[] choices = {-4, -3, -2, 2, 3, 4, 5};
        return choices[random.nextInt(choices.length)];
    }

    private int startFor(int d) {
        return d > 0 ? 2 + random.nextInt(8) : 14 + random.nextInt(8);
    }

    private String[] apCells(int first, int d, int count) {
        String[] out = new String[count];
        for (int i = 0; i < count; i++) out[i] = String.valueOf(first + i * d);
        return out;
    }

    private String[] toCells(int[] values) {
        String[] out = new String[values.length];
        for (int i = 0; i < values.length; i++) out[i] = String.valueOf(values[i]);
        return out;
    }

    private String paren(int v) {
        return v < 0 ? "(" + v + ")" : String.valueOf(v);
    }

    private String gaps(int[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < values.length; i++) {
            if (i > 1) sb.append(", ");
            sb.append(values[i] - values[i - 1]);
        }
        return sb.toString();
    }

    private void buildChips(double y, int correct, int... extras) {
        Set<Integer> values = new LinkedHashSet<>();
        values.add(correct);
        for (int e : extras) values.add(e);
        int offset = 1;
        while (values.size() < 4) {
            values.add(correct + offset);
            if (values.size() < 4) values.add(correct - offset);
            offset++;
        }

        List<Integer> others = new ArrayList<>(values);
        others.remove(Integer.valueOf(correct));
        Collections.shuffle(others, random);
        List<Integer> four = new ArrayList<>(others.subList(0, 3));
        four.add(correct);
        Collections.shuffle(four, random);

        double w = 150, h = 64, gap = 24;
        double startX = (WIDTH - (4 * w + 3 * gap)) / 2;
        options.clear();
        for (int i = 0; i < 4; i++) {
            options.add(new Box(startX + i * (w + gap), y, w, h,
                    String.valueOf(four.get(i)), four.get(i)));
        }
        correctValue = correct;
    }

    private void win() {
        int points = 100 + timeBonus();
        roundWon(points);
        floatingText("+" + points, WIDTH / 2, 160, Color.LIMEGREEN);
    }

    private void wrong(String text, double x, double y) {
        hintShown = true;
        wrongAnswer(5, text, x, y);
    }

    // =========================================================
    // Input
    // =========================================================

    @Override
    protected void onMouseMove(double x, double y) {
        if (state != State.PLAYING || station != 6) return;
        meanHover = meanTickAt(x, y);
    }

    @Override
    protected void onPlayClick(double x, double y) {
        switch (station) {
            case 0:
            case 1:
            case 2:
                clickChips(x, y);
                break;
            case 3:
                clickDial(x, y);
                break;
            case 4:
                clickHop(x, y);
                break;
            case 5:
                if (pairBtn.contains(x, y)) {
                    paired = true;
                } else {
                    clickChips(x, y);
                }
                break;
            default:
                clickMean(x, y);
                break;
        }
    }

    private void clickChips(double x, double y) {
        for (Box b : options) {
            if (b.contains(x, y)) {
                if (b.value == correctValue) {
                    win();
                } else {
                    wrong("TRY AGAIN", x, y);
                }
                return;
            }
        }
    }

    private void clickDial(double x, double y) {
        if (minusBtn.contains(x, y)) {
            dialGuess = Math.max(-9, dialGuess - 1);
        } else if (plusBtn.contains(x, y)) {
            dialGuess = Math.min(9, dialGuess + 1);
        } else if (fireBtn.contains(x, y)) {
            if (dialGuess == targetD) {
                win();
            } else {
                wrong("d = " + dialGuess + " IS WRONG", x, y);
            }
        }
    }

    private void clickHop(double x, double y) {
        if (hopBtn.contains(x, y)) {
            if (hops < hopN + 1) {
                hops++;
            } else {
                floatingText("END OF TRACK", x, y, Color.ORANGE);
            }
        } else if (landBtn.contains(x, y)) {
            if (hops == hopN - 1) {
                win();
            } else {
                wrong("NOT TERM " + hopN + "!", x, y);
                hops = 0;
            }
        }
    }

    private void clickMean(double x, double y) {
        Integer tick = meanTickAt(x, y);
        if (tick == null) return;
        if (tick == meanAnswer) {
            win();
        } else {
            wrong(tick + " IS NOT THE MEAN", x, y);
        }
    }

    private double meanPx(int v) {
        return MEAN_LEFT + (v - meanMin) * ((MEAN_RIGHT - MEAN_LEFT) / (meanMax - meanMin));
    }

    private Integer meanTickAt(double x, double y) {
        double spacing = (MEAN_RIGHT - MEAN_LEFT) / (meanMax - meanMin);
        int v = meanMin + (int) Math.round((x - MEAN_LEFT) / spacing);
        if (v < meanMin || v > meanMax || Math.abs(y - MEAN_Y) > 45) return null;
        return v;
    }

    @Override
    protected void onUpdate(double dt) {
        pulse += 4 * dt;
        if (state == State.GAME_OVER) {
            failed = true;
        }
    }

    // =========================================================
    // Drawing
    // =========================================================

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        gc.setFill(Color.web("#0f172a"));
        gc.fillRect(0, HUD_TOP, WIDTH, HEIGHT - HUD_TOP - HUD_BOTTOM);

        drawProgress(gc);

        switch (station) {
            case 0:
            case 2:
                drawTrain(gc, train, 200, Color.web("#2563eb"));
                drawChips(gc);
                break;
            case 1:
                drawApRows(gc);
                break;
            case 3:
                drawDifference(gc);
                break;
            case 4:
                drawHop(gc);
                break;
            case 5:
                drawSum(gc);
                break;
            default:
                drawMean(gc);
                break;
        }
    }

    private void centerText(GraphicsContext gc, String text, double x, double y, double size, Color color) {
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, size));
        gc.setFill(color);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, x, y);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawBox(GraphicsContext gc, Box b, Color fill, Color text, double size) {
        gc.setFill(fill);
        gc.fillRoundRect(b.x, b.y, b.w, b.h, 16, 16);
        centerText(gc, b.label, b.x + b.w / 2, b.y + b.h / 2 + size / 3, size, text);
    }

    private void drawProgress(GraphicsContext gc) {
        double startX = 135, step = 105, cy = 125;
        gc.setStroke(Color.web("#475569"));
        gc.setLineWidth(3);
        gc.strokeLine(startX, cy, startX + (STATIONS - 1) * step, cy);

        for (int i = 0; i < STATIONS; i++) {
            double cx = startX + i * step;
            boolean done = i < station || (i == station && state == State.ROUND_WON);
            Color fill = done ? Color.LIMEGREEN : (i == station ? Color.GOLD : Color.web("#475569"));
            gc.setFill(fill);
            gc.fillOval(cx - 14, cy - 14, 28, 28);
            centerText(gc, String.valueOf(i + 1), cx, cy + 5, 14, Color.web("#0f172a"));
            centerText(gc, STATION_LABELS[i], cx, cy + 32, 11,
                    i == station ? Color.WHITE : Color.web("#94a3b8"));
        }
    }

    private void drawTrain(GraphicsContext gc, String[] cells, double y, Color body) {
        double w = 90, h = 56, gap = 14;
        double total = cells.length * w + (cells.length - 1) * gap;
        double x = (WIDTH - total) / 2;
        for (int i = 0; i < cells.length; i++) {
            String cell = cells[i];
            boolean unknown = cell.equals("?");
            if (i < cells.length - 1) {
                gc.setFill(Color.GRAY);
                gc.fillRect(x + w, y + h / 2 - 2, gap, 4);
            }
            gc.setFill(unknown ? Color.web("#f59e0b") : body);
            gc.fillRoundRect(x, y, w, h, 14, 14);
            gc.setFill(Color.web("#0b1220"));
            gc.fillOval(x + 14, y + h - 6, 16, 16);
            gc.fillOval(x + w - 30, y + h - 6, 16, 16);
            centerText(gc, cell, x + w / 2, y + h / 2 + 8, 24, Color.WHITE);
            x += w + gap;
        }
    }

    private void drawChips(GraphicsContext gc) {
        boolean reveal = state != State.PLAYING;
        for (Box b : options) {
            boolean gold = reveal && b.value == correctValue;
            drawBox(gc, b,
                    gold ? Color.GOLD : Color.web("#334155"),
                    gold ? Color.web("#0f172a") : Color.WHITE, 26);
        }
    }

    private void drawApRows(GraphicsContext gc) {
        centerText(gc, "A", 100, 238, 28, Color.WHITE);
        centerText(gc, "B", 100, 358, 28, Color.WHITE);
        drawTrain(gc, toCells(apRowA), 200, Color.web("#2563eb"));
        drawTrain(gc, toCells(apRowB), 320, Color.web("#7c3aed"));

        for (Box b : options) {
            boolean gold = state != State.PLAYING && b.value == correctValue;
            gc.setStroke(gold ? Color.GOLD : Color.rgb(255, 255, 255, 0.35));
            gc.setLineWidth(gold ? 4 : 1.5);
            gc.strokeRoundRect(b.x, b.y, b.w, b.h, 16, 16);
        }
    }

    private void drawDifference(GraphicsContext gc) {
        centerText(gc, "TARGET TRAIN", WIDTH / 2, 190, 14, Color.web("#94a3b8"));
        drawTrain(gc, apCells(firstA, targetD, 4), 200, Color.web("#2563eb"));

        centerText(gc, "YOUR TRAIN", WIDTH / 2, 300, 14, Color.web("#94a3b8"));
        drawTrain(gc, apCells(firstA, dialGuess, 4), 310, Color.web("#7c3aed"));

        drawBox(gc, minusBtn, Color.web("#0ea5e9"), Color.WHITE, 34);
        drawBox(gc, plusBtn, Color.web("#0ea5e9"), Color.WHITE, 34);
        drawBox(gc, fireBtn, Color.web("#16a34a"), Color.WHITE, 22);

        gc.setFill(Color.web("#1e293b"));
        gc.fillRoundRect(385, 430, 140, 60, 16, 16);
        centerText(gc, "d = " + dialGuess, 455, 468, 26, Color.GOLD);
    }

    private void drawHop(GraphicsContext gc) {
        int ticks = hopN + 2;
        double left = 110, right = 790, y = 270;
        double spacing = (right - left) / (ticks - 1);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeLine(left, y, right, y);

        for (int i = 0; i < ticks; i++) {
            double px = left + i * spacing;
            gc.setStroke(Color.LIGHTGRAY);
            gc.setLineWidth(2);
            gc.strokeLine(px, y - 10, px, y + 10);
            if (i <= hops) {
                centerText(gc, String.valueOf(hopA + i * hopD), px, y + 34, 16, Color.web("#93c5fd"));
            }
        }

        double fx = left + hops * spacing;
        gc.setFill(Color.LIMEGREEN);
        gc.fillOval(fx - 18, y - 46, 36, 30);
        gc.setFill(Color.WHITE);
        gc.fillOval(fx - 11, y - 52, 9, 9);
        gc.fillOval(fx + 2, y - 52, 9, 9);
        gc.setFill(Color.BLACK);
        gc.fillOval(fx - 8, y - 49, 4, 4);
        gc.fillOval(fx + 5, y - 49, 4, 4);
        centerText(gc, String.valueOf(hopA + hops * hopD), fx, y - 62, 20, Color.GOLD);

        centerText(gc, "Hops: " + hops, WIDTH / 2, 360, 22, Color.WHITE);

        drawBox(gc, hopBtn, Color.web("#2563eb"), Color.WHITE, 24);
        drawBox(gc, landBtn, Color.web("#16a34a"), Color.WHITE, 22);
    }

    private void drawSum(GraphicsContext gc) {
        int n = sumTerms.length;
        double barW = 50, gap = 20;
        double total = n * barW + (n - 1) * gap;
        double startX = (WIDTH - total) / 2;
        double baseY = 335;
        double unit = 90.0 / sumTerms[n - 1];

        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);
        gc.strokeLine(startX - 15, baseY, startX + total + 15, baseY);

        for (int i = 0; i < n; i++) {
            double h = sumTerms[i] * unit;
            double bx = startX + i * (barW + gap);
            Color c = paired ? Color.hsb(Math.min(i, n - 1 - i) * 70, 0.65, 0.95) : Color.web("#2563eb");
            gc.setFill(c);
            gc.fillRect(bx, baseY - h, barW, h);
            centerText(gc, String.valueOf(sumTerms[i]), bx + barW / 2, baseY - h - 6, 14, Color.WHITE);
        }

        if (paired) {
            int pairSum = sumTerms[0] + sumTerms[n - 1];
            for (int i = 0; i < n / 2; i++) {
                int j = n - 1 - i;
                double xi = startX + i * (barW + gap) + barW / 2;
                double xj = startX + j * (barW + gap) + barW / 2;
                double by = 185 + i * 14;
                Color c = Color.hsb(i * 70, 0.65, 0.95);
                gc.setStroke(c);
                gc.setLineWidth(2.5);
                gc.strokeLine(xi, by, xj, by);
                gc.strokeLine(xi, by, xi, by + 8);
                gc.strokeLine(xj, by, xj, by + 8);
                gc.setFill(c);
                gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 13));
                gc.fillText("= " + pairSum, xj + 24, by + 5);
            }
        } else {
            drawBox(gc, pairBtn, Color.web("#d97706"), Color.WHITE, 18);
        }

        drawChips(gc);
    }

    private void drawMean(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeLine(MEAN_LEFT, MEAN_Y, MEAN_RIGHT, MEAN_Y);

        for (int v = meanMin; v <= meanMax; v++) {
            double px = meanPx(v);
            gc.setStroke(Color.LIGHTGRAY);
            gc.setLineWidth(2);
            gc.strokeLine(px, MEAN_Y - 9, px, MEAN_Y + 9);
        }

        double ax = meanPx(meanX);
        double bx = meanPx(meanY);
        gc.setFill(Color.CYAN);
        gc.fillOval(ax - 11, MEAN_Y - 11, 22, 22);
        centerText(gc, "A = " + meanX, ax, MEAN_Y - 26, 16, Color.CYAN);
        gc.setFill(Color.MAGENTA);
        gc.fillOval(bx - 11, MEAN_Y - 11, 22, 22);
        centerText(gc, "B = " + meanY, bx, MEAN_Y - 26, 16, Color.MAGENTA);

        if (state == State.PLAYING && meanHover != null) {
            double hx = meanPx(meanHover);
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(hx - 14, MEAN_Y - 14, 28, 28);
            centerText(gc, String.valueOf(meanHover), hx, MEAN_Y + 40, 18, Color.YELLOW);
        }

        if (state != State.PLAYING) {
            double mx = meanPx(meanAnswer);
            gc.setFill(Color.GOLD);
            gc.fillOval(mx - 9, MEAN_Y - 9, 18, 18);
            double ring = 14 + 4 * Math.sin(pulse);
            gc.setStroke(state == State.ROUND_WON ? Color.LIMEGREEN : Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(mx - ring, MEAN_Y - ring, 2 * ring, 2 * ring);

            gc.setStroke(Color.GOLD);
            gc.setLineWidth(3);
            gc.strokeLine(ax, MEAN_Y + 60, mx, MEAN_Y + 60);
            gc.strokeLine(mx, MEAN_Y + 60, bx, MEAN_Y + 60);
            int gapValue = (meanY - meanX) / 2;
            centerText(gc, "+" + gapValue, (ax + mx) / 2, MEAN_Y + 52, 16, Color.GOLD);
            centerText(gc, "+" + gapValue, (mx + bx) / 2, MEAN_Y + 52, 16, Color.GOLD);
        }
    }
}