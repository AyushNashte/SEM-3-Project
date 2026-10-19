package class10.games;

import common.gui.games.ArcadeGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * SIMILARITY DEFENCE - enemy shapes walk toward your base. Fire the shape that is
 * similar to them (or the missing length) before they arrive.
 * 7 waves, one per Similarity concept, 3 enemies per wave, 3 base health.
 */
public class SimilarityDefenceGame extends ArcadeGame {

    private static final String[] WAVE_LABELS = {
            "SIMILAR?", "AA", "SSS", "SAS", "THALES", "SIDES", "AREAS"
    };
    private static final int WAVES = WAVE_LABELS.length;
    private static final int FOES_PER_WAVE = 3;
    private static final int START_HEALTH = 3;

    private static final double LANE_TOP = 185;
    private static final double LANE_H = 150;
    private static final double BODY_TOP = 195;
    private static final double BODY_W = 210;
    private static final double BODY_H = 130;
    private static final double BODY_CY = BODY_TOP + BODY_H / 2;
    private static final double START_X = 120;
    private static final double BASE_HIT_X = 675;     // enemy centre x when it touches the base

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

    private static class Foe {
        double x;
        int[] ref = new int[0];
        List<int[]> cands = new ArrayList<>();
        List<String> chips = new ArrayList<>();
        int correctIdx;
        String mission = "";
        String hint = "";
        String solution = "";
        boolean dying;
        double dyingTime;
    }

    // ---- state ----
    private int station = -1;
    private boolean failed;
    private boolean hintShown;
    private double pulse;
    private int health = START_HEALTH;
    private int lap;
    private int resolved;

    private Foe current;
    private Foe ammoFoe;
    private final List<Box> options = new ArrayList<>();

    private String lastSolution = "";
    private double solutionFlash;
    private double baseFlash;

    private double beamTime;
    private double beamFromX, beamFromY, beamToX, beamToY;
    private boolean beamOk;

    public SimilarityDefenceGame() {
        roundTime = 120;
    }

    @Override
    protected String title() {
        return "SIMILARITY DEFENCE";
    }

    @Override
    protected String wonMessage() {
        return station == WAVES - 1
                ? "ALL 7 WAVES CLEARED! CLICK FOR A NEW LAP"
                : "WAVE CLEARED! CLICK FOR THE NEXT WAVE";
    }

    @Override
    protected String lostMessage() {
        return health <= 0
                ? "BASE DESTROYED! CLICK TO RESTART FROM WAVE 1"
                : "TIME'S UP! CLICK TO RESTART FROM WAVE 1";
    }

    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            if (current != null && !current.dying) return current.solution;
            return lastSolution;
        }
        String mission = current != null ? current.mission : "";
        if (current != null && hintShown) return mission + "\n" + current.hint;
        if (solutionFlash > 0) return mission + "\nOK: " + lastSolution;
        return mission;
    }

    private double speed() {
        return 26 * (1 + 0.15 * lap);
    }

    // =========================================================
    // Round / wave setup
    // =========================================================

    @Override
    protected void startRound() {
        if (failed || station < 0) {
            station = 0;
            health = START_HEALTH;
            lap = 0;
            failed = false;
        } else {
            station = (station + 1) % WAVES;
            if (station == 0) lap++;
        }
        resolved = 0;
        current = null;
        beamTime = 0;
        solutionFlash = 0;
        baseFlash = 0;
        lastSolution = "";
        spawnNext();
    }

    private void spawnNext() {
        current = makeFoe();
        current.x = START_X;
        ammoFoe = current;
        hintShown = false;
        if (station <= 3) {
            buildCards();
        } else {
            setChips(430, current.chips, current.correctIdx);
        }
    }

    private Foe makeFoe() {
        switch (station) {
            case 0: return genShapes();
            case 1: return genAngles();
            case 2: return genSss();
            case 3: return genSas();
            case 4: return genThales();
            case 5: return genSides();
            default: return genAreas();
        }
    }

    private Foe genShapes() {
        Foe f = new Foe();
        int w = 2 + random.nextInt(3);
        int h = w + 1 + random.nextInt(2);
        int k = 2 + random.nextInt(2);
        f.ref = new int[]{w, h};
        f.cands.add(new int[]{k * w, k * h});
        f.cands.add(new int[]{k * w + 2, k * h});
        f.cands.add(new int[]{k * w, k * h + 2});
        f.cands.add(new int[]{w + 2, h + 2});
        f.correctIdx = shuffleTrackingFirst(f.cands);
        f.mission = "WAVE 1 - SIMILAR?: fire the rectangle SIMILAR to the enemy";
        f.hint = "Similar = same shape: compare width : height for each one";
        f.solution = String.format("Same shape = same ratio: %d:%d = %d:%d, so %d x %d is similar",
                w, h, k * w, k * h, k * w, k * h);
        return f;
    }

    private Foe genAngles() {
        Foe f = new Foe();
        int a, b, c;
        do {
            a = 35 + 5 * random.nextInt(10);
            b = 35 + 5 * random.nextInt(10);
            c = 180 - a - b;
        } while (a == b || c < 30 || c > 100 || c == a || c == b);
        f.ref = new int[]{a, b, c};

        int[][] patterns = {{a, c}, {c, a}, {b, c}, {c, b}};
        int[] correct = patterns[random.nextInt(4)].clone();
        f.cands.add(correct);

        int[] pool = {a, b, c};
        int guard = 0;
        while (f.cands.size() < 4 && guard++ < 500) {
            int x = pool[random.nextInt(3)] + (random.nextInt(3) - 1) * 10;
            int y = pool[random.nextInt(3)] + (random.nextInt(3) - 1) * 10;
            if (x < 20 || y < 20 || x + y > 150) continue;
            if (sameAngleSet(x, y, a, b) || containsPair(f.cands, x, y)) continue;
            f.cands.add(new int[]{x, y});
        }
        int bump = 5;
        while (f.cands.size() < 4 && bump < 100) {
            int x = a + bump;
            if (x + b < 170 && !sameAngleSet(x, b, a, b) && !containsPair(f.cands, x, b)) {
                f.cands.add(new int[]{x, b});
            }
            int x2 = a - bump;
            if (f.cands.size() < 4 && x2 >= 20 && !sameAngleSet(x2, b, a, b) && !containsPair(f.cands, x2, b)) {
                f.cands.add(new int[]{x2, b});
            }
            bump += 5;
        }
        f.correctIdx = shuffleTrackingFirst(f.cands);
        f.mission = "WAVE 2 - AA: fire the triangle SIMILAR to the enemy";
        f.hint = "Find each third angle (180 - the other two). Same angles = similar";
        f.solution = String.format("Enemy: %d, %d, %d. Similar one: %d, %d, %d - same angles (AA)",
                a, b, c, correct[0], correct[1], 180 - correct[0] - correct[1]);
        return f;
    }

    private Foe genSss() {
        Foe f = new Foe();
        int[][] bases = {{3, 4, 5}, {4, 5, 6}, {5, 6, 7}, {6, 7, 8}, {5, 7, 9}, {4, 6, 7}};
        int[] base = bases[random.nextInt(bases.length)];
        int k = 2 + random.nextInt(2);
        f.ref = base.clone();

        int[] correct = {base[0] * k, base[1] * k, base[2] * k};
        f.cands.add(correct);
        int guard = 0;
        while (f.cands.size() < 4 && guard++ < 200) {
            int[] t = correct.clone();
            int idx = random.nextInt(3);
            int delta = (random.nextBoolean() ? 1 : -1) * (1 + random.nextInt(2));
            t[idx] += delta;
            if (!validTriangle(t) || containsTriple(f.cands, t)) continue;
            f.cands.add(t);
        }
        int bump = 3;
        while (f.cands.size() < 4 && bump < 60) {
            int[] t = correct.clone();
            t[bump % 3] += bump;
            if (validTriangle(t) && !containsTriple(f.cands, t)) f.cands.add(t);
            bump++;
        }
        f.correctIdx = shuffleTrackingFirst(f.cands);
        f.mission = "WAVE 3 - SSS: fire the triangle SIMILAR to the enemy";
        f.hint = "Divide each side by the enemy's side - all 3 answers must match";
        f.solution = String.format("%d/%d = %d/%d = %d/%d = %d: same ratio on all sides (SSS)",
                correct[0], base[0], correct[1], base[1], correct[2], base[2], k);
        return f;
    }

    private Foe genSas() {
        Foe f = new Foe();
        int a = 3 + random.nextInt(4);
        int b = a + 1 + random.nextInt(3);
        int theta = 40 + 10 * random.nextInt(5);
        int k = 2 + random.nextInt(2);
        f.ref = new int[]{a, b, theta};
        f.cands.add(new int[]{k * a, k * b, theta});
        f.cands.add(new int[]{k * a, k * b, theta + 10});
        f.cands.add(new int[]{k * a, k * b + 2, theta});
        f.cands.add(new int[]{k * a + 2, k * b, theta});
        f.correctIdx = shuffleTrackingFirst(f.cands);
        f.mission = "WAVE 4 - SAS: fire the triangle SIMILAR to the enemy";
        f.hint = "Two sides in the SAME ratio AND the angle between them equal";
        f.solution = String.format("%d/%d = %d/%d = %d and the angle between is %d°: similar (SAS)",
                k * a, a, k * b, b, k, theta);
        return f;
    }

    private Foe genThales() {
        Foe f = new Foe();
        int[][] splits = {{1, 2}, {2, 1}, {2, 3}, {3, 2}, {1, 3}, {3, 1}};
        int[] s = splits[random.nextInt(splits.length)];
        int u = 1 + random.nextInt(2);
        int v = 2 + random.nextInt(2);
        int ad = s[0] * u;
        int db = s[1] * u;
        int ae = s[0] * v;
        int ec = s[1] * v;
        f.ref = new int[]{ad, db, ae, s[0], s[1]};
        buildNumeric(f, ec, ae, db, ec + 1);
        f.mission = "WAVE 5 - THALES: DE is parallel to BC - fire the length of EC";
        f.hint = "AD/DB = AE/EC, so EC = DB x AE / AD";
        f.solution = String.format("AD/DB = AE/EC, so EC = %d x %d / %d = %d", db, ae, ad, ec);
        return f;
    }

    private Foe genSides() {
        Foe f = new Foe();
        int[][] bases = {{3, 4, 5}, {4, 5, 6}, {5, 6, 7}, {6, 7, 8}, {5, 7, 9}, {4, 6, 7}};
        int[] small = bases[random.nextInt(bases.length)];
        int k = 2 + random.nextInt(2);
        int key = random.nextInt(3);
        int ask;
        do {
            ask = random.nextInt(3);
        } while (ask == key);
        f.ref = new int[]{small[0], small[1], small[2], k, key, ask};
        int answer = small[ask] * k;
        buildNumeric(f, answer, small[ask] + (k - 1) * small[key], small[ask] * (k + 1), answer + k);
        f.mission = "WAVE 6 - SIDES: the triangles are similar - fire the side marked ?";
        f.hint = "Scale factor = big side / small side (use the pair you know)";
        f.solution = String.format("Scale = %d/%d = %d, so ? = %d x %d = %d",
                small[key] * k, small[key], k, small[ask], k, answer);
        return f;
    }

    private Foe genAreas() {
        Foe f = new Foe();
        int[][] ratios = {{1, 2}, {2, 3}, {1, 3}, {3, 4}, {2, 5}, {3, 5}};
        int[] r = ratios[random.nextInt(ratios.length)];
        int p = r[0];
        int q = r[1];
        int t = 1 + random.nextInt(3);
        int s = p * p * t;
        int big = q * q * t;
        boolean modeBig = random.nextBoolean();
        f.ref = new int[]{p, q, s, modeBig ? 1 : 0};

        if (modeBig) {
            buildNumeric(f, big, p * q * t, s * q, big + p);
            f.mission = String.format("WAVE 7 - AREAS: side ratio %d:%d, small area %d - fire the BIG area", p, q, s);
            f.solution = String.format("Areas scale by (%d/%d)^2 = %d/%d, so %d x %d/%d = %d",
                    q, p, q * q, p * p, s, q * q, p * p, big);
        } else {
            List<String> labels = new ArrayList<>(Arrays.asList(
                    p + ":" + q,
                    (p * p) + ":" + (q * q),
                    q + ":" + p,
                    (p * p * p) + ":" + (q * q * q)));
            String correctLabel = labels.get(0);
            Collections.shuffle(labels, random);
            f.chips = labels;
            f.correctIdx = labels.indexOf(correctLabel);
            f.mission = String.format("WAVE 7 - AREAS: area ratio %d:%d - fire the SIDE ratio", p * p, q * q);
            f.solution = String.format("Side ratio = sqrt(%d):sqrt(%d) = %d:%d", p * p, q * q, p, q);
        }
        f.hint = "Area ratio = (side ratio) squared, so sides = square roots";
        return f;
    }

    // =========================================================
    // Helpers
    // =========================================================

    private int shuffleTrackingFirst(List<int[]> list) {
        int[] marker = list.get(0);
        Collections.shuffle(list, random);
        return list.indexOf(marker);
    }

    private boolean sameAngleSet(int x, int y, int a, int b) {
        int[] s1 = {x, y, 180 - x - y};
        int[] s2 = {a, b, 180 - a - b};
        Arrays.sort(s1);
        Arrays.sort(s2);
        return Arrays.equals(s1, s2);
    }

    private boolean containsPair(List<int[]> list, int x, int y) {
        for (int[] p : list) {
            if ((p[0] == x && p[1] == y) || (p[0] == y && p[1] == x)) return true;
        }
        return false;
    }

    private boolean containsTriple(List<int[]> list, int[] t) {
        for (int[] p : list) {
            if (Arrays.equals(p, t)) return true;
        }
        return false;
    }

    private boolean validTriangle(int[] t) {
        return t[0] > 0 && t[1] > 0 && t[2] > 0
                && t[0] + t[1] > t[2] && t[0] + t[2] > t[1] && t[1] + t[2] > t[0];
    }

    private void buildNumeric(Foe f, int correct, int... extras) {
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

        f.chips = new ArrayList<>();
        for (int v : four) f.chips.add(String.valueOf(v));
        f.correctIdx = four.indexOf(correct);
    }

    private void buildCards() {
        options.clear();
        double w = 190, gap = 20;
        double x0 = (WIDTH - (4 * w + 3 * gap)) / 2;
        for (int i = 0; i < 4; i++) {
            options.add(new Box(x0 + i * (w + gap), 372, w, 150, "", i));
        }
    }

    private void setChips(double y, List<String> labels, int correctIdx) {
        double w = 150, h = 64, gap = 24;
        double startX = (WIDTH - (4 * w + 3 * gap)) / 2;
        options.clear();
        for (int i = 0; i < 4; i++) {
            options.add(new Box(startX + i * (w + gap), y, w, h, labels.get(i), i));
        }
    }

    // =========================================================
    // Input and game logic
    // =========================================================

    @Override
    protected void onPlayClick(double x, double y) {
        if (current == null || current.dying) return;
        for (Box b : options) {
            if (b.contains(x, y)) {
                fire(b, x, y);
                return;
            }
        }
    }

    private void fire(Box b, double x, double y) {
        beamFromX = b.x + b.w / 2;
        beamFromY = b.y;
        beamToX = current.x;
        beamToY = BODY_CY;
        beamTime = 0.25;

        if (b.value == current.correctIdx) {
            beamOk = true;
            int pts = 50 + (int) ((BASE_HIT_X - current.x) / 10);
            score += pts;
            floatingText("+" + pts, current.x, BODY_TOP - 6, Color.LIMEGREEN);
            current.dying = true;
            current.dyingTime = 0;
            lastSolution = current.solution;
            solutionFlash = 3.0;
        } else {
            beamOk = false;
            hintShown = true;
            current.x += 70;
            wrongAnswer(0, "WRONG SHOT!", x, y);
        }
    }

    @Override
    protected void onUpdate(double dt) {
        pulse += 4 * dt;
        if (state == State.GAME_OVER) {
            failed = true;
        }
        if (solutionFlash > 0) solutionFlash -= dt;
        if (beamTime > 0) beamTime -= dt;
        if (baseFlash > 0) baseFlash -= dt;
        if (state != State.PLAYING || current == null) return;

        if (current.dying) {
            current.dyingTime += dt;
            if (current.dyingTime >= 0.5) {
                current = null;
                resolved++;
                if (resolved >= FOES_PER_WAVE) {
                    roundWon(100);
                    floatingText("+100", WIDTH / 2, 160, Color.LIMEGREEN);
                } else {
                    spawnNext();
                }
            }
            return;
        }

        current.x += speed() * dt;
        if (current.x >= BASE_HIT_X) {
            health--;
            baseFlash = 0.6;
            current.dying = true;
            current.dyingTime = 0;
            lastSolution = current.solution;
            solutionFlash = 3.0;
            wrongAnswer(0, "BASE HIT!", 800, 190);
            if (health <= 0) {
                state = State.GAME_OVER;
            }
        }
    }

    // =========================================================
    // Geometry helpers
    // =========================================================

    private double[][] anglePoints(double alpha, double beta) {
        double a = Math.toRadians(alpha);
        double g = Math.toRadians(180 - alpha - beta);
        double pr = Math.sin(Math.toRadians(beta)) / Math.sin(g);
        return new double[][]{{0, 1, pr * Math.cos(a)}, {0, 0, -pr * Math.sin(a)}};
    }

    private double[][] sidePoints(int s0, int s1, int s2) {
        double rx = (s0 * (double) s0 + s2 * (double) s2 - s1 * (double) s1) / (2.0 * s0);
        double ry = Math.sqrt(Math.max(0.0001, s2 * (double) s2 - rx * rx));
        return new double[][]{{0, s0, rx}, {0, 0, -ry}};
    }

    private double[][] sasPoints(int s1, int s2, double thetaDeg) {
        double t = Math.toRadians(thetaDeg);
        return new double[][]{{0, s1, s2 * Math.cos(t)}, {0, 0, -s2 * Math.sin(t)}};
    }

    private double[][] fit(double[] xs, double[] ys, double ax, double ay, double aw, double ah) {
        double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;
        for (int i = 0; i < xs.length; i++) {
            minX = Math.min(minX, xs[i]);
            maxX = Math.max(maxX, xs[i]);
            minY = Math.min(minY, ys[i]);
            maxY = Math.max(maxY, ys[i]);
        }
        double bw = Math.max(maxX - minX, 0.0001);
        double bh = Math.max(maxY - minY, 0.0001);
        double s = Math.min(aw / bw, ah / bh);
        double offX = ax + (aw - bw * s) / 2;
        double offY = ay + (ah - bh * s) / 2;
        double[] tx = new double[xs.length];
        double[] ty = new double[ys.length];
        for (int i = 0; i < xs.length; i++) {
            tx[i] = offX + (xs[i] - minX) * s;
            ty[i] = offY + (ys[i] - minY) * s;
        }
        return new double[][]{tx, ty};
    }

    private double[][] fit(double[][] pts, double ax, double ay, double aw, double ah) {
        return fit(pts[0], pts[1], ax, ay, aw, ah);
    }

    private double[][] scaledShape(double[][] shape, double x0, double baseY, double scale) {
        double[] xs = new double[3];
        double[] ys = new double[3];
        for (int i = 0; i < 3; i++) {
            xs[i] = x0 + shape[0][i] * scale;
            ys[i] = baseY + shape[1][i] * scale;
        }
        return new double[][]{xs, ys};
    }

    // =========================================================
    // Drawing
    // =========================================================

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        gc.setFill(Color.web("#0f172a"));
        gc.fillRect(0, HUD_TOP, WIDTH, HEIGHT - HUD_TOP - HUD_BOTTOM);

        drawProgress(gc);
        drawStatus(gc);
        drawLane(gc);
        drawBase(gc);
        if (current != null) drawFoe(gc, current);
        drawAmmo(gc);

        if (beamTime > 0) {
            gc.setStroke(beamOk ? Color.LIMEGREEN : Color.RED);
            gc.setLineWidth(4);
            gc.strokeLine(beamFromX, beamFromY, beamToX, beamToY);
        }
    }

    private void centerText(GraphicsContext gc, String text, double x, double y, double size, Color color) {
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, size));
        gc.setFill(color);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(text, x, y);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawProgress(GraphicsContext gc) {
        double startX = 135, step = 105, cy = 125;
        gc.setStroke(Color.web("#475569"));
        gc.setLineWidth(3);
        gc.strokeLine(startX, cy, startX + (WAVES - 1) * step, cy);

        for (int i = 0; i < WAVES; i++) {
            double cx = startX + i * step;
            boolean done = i < station || (i == station && state == State.ROUND_WON);
            Color fill = done ? Color.LIMEGREEN : (i == station ? Color.GOLD : Color.web("#475569"));
            gc.setFill(fill);
            gc.fillOval(cx - 14, cy - 14, 28, 28);
            centerText(gc, String.valueOf(i + 1), cx, cy + 5, 14, Color.web("#0f172a"));
            centerText(gc, WAVE_LABELS[i], cx, cy + 32, 11,
                    i == station ? Color.WHITE : Color.web("#94a3b8"));
        }
    }

    private void drawStatus(GraphicsContext gc) {
        centerText(gc, "BASE", 50, 80, 14, Color.WHITE);
        for (int i = 0; i < START_HEALTH; i++) {
            gc.setFill(i < health ? Color.LIMEGREEN : Color.web("#475569"));
            gc.fillRoundRect(86 + i * 28, 64, 22, 22, 6, 6);
        }
        centerText(gc, "ENEMIES " + Math.min(resolved, FOES_PER_WAVE) + "/" + FOES_PER_WAVE,
                WIDTH - 90, 80, 14, Color.WHITE);
    }

    private void drawLane(GraphicsContext gc) {
        gc.setFill(Color.web("#1e293b"));
        gc.fillRect(0, LANE_TOP, 780, LANE_H);
        gc.setStroke(Color.web("#334155"));
        gc.setLineWidth(2);
        gc.setLineDashes(14, 12);
        gc.strokeLine(0, BODY_CY, 780, BODY_CY);
        gc.setLineDashes((double[]) null);
    }

    private void drawBase(GraphicsContext gc) {
        Color body = baseFlash > 0 ? Color.web("#dc2626") : Color.web("#64748b");
        gc.setFill(body);
        gc.fillRect(795, 245, 80, 90);
        for (int i = 0; i < 4; i++) {
            gc.fillRect(795 + i * 22, 225, 14, 20);
        }
        gc.setFill(Color.web("#0f172a"));
        gc.fillRect(823, 295, 24, 40);
        centerText(gc, "BASE", 835, 215, 14, Color.WHITE);
    }

    private void drawFoe(GraphicsContext gc, Foe f) {
        double left = f.x - BODY_W / 2;
        double alpha = f.dying ? Math.max(0, 1 - f.dyingTime / 0.25) : 1;
        gc.setGlobalAlpha(alpha);
        gc.setFill(Color.web("#3b0d0d"));
        gc.fillRoundRect(left, BODY_TOP, BODY_W, BODY_H, 18, 18);
        gc.setStroke(Color.web("#ef4444"));
        gc.setLineWidth(2.5);
        gc.strokeRoundRect(left, BODY_TOP, BODY_W, BODY_H, 18, 18);
        drawFoeContent(gc, f, left);
        gc.setGlobalAlpha(1.0);

        if (f.dying) {
            double t = f.dyingTime / 0.5;
            double r = 20 + t * 150;
            gc.setGlobalAlpha(Math.max(0, 1 - t));
            gc.setStroke(Color.ORANGE);
            gc.setLineWidth(6);
            gc.strokeOval(f.x - r, BODY_CY - r, 2 * r, 2 * r);
            gc.setGlobalAlpha(1.0);
        }
    }

    private void drawTriangleShape(GraphicsContext gc, double[][] p, Color fill, Color stroke) {
        gc.setFill(fill);
        gc.fillPolygon(p[0], p[1], 3);
        gc.setStroke(stroke);
        gc.setLineWidth(2.5);
        gc.strokePolygon(p[0], p[1], 3);
    }

    private void vertexLabel(GraphicsContext gc, double[][] p, int idx, String text, Color color, double size) {
        double cx = (p[0][0] + p[0][1] + p[0][2]) / 3.0;
        double cy = (p[1][0] + p[1][1] + p[1][2]) / 3.0;
        double dx = p[0][idx] - cx;
        double dy = p[1][idx] - cy;
        double len = Math.max(1, Math.hypot(dx, dy));
        centerText(gc, text, p[0][idx] + dx / len * 18, p[1][idx] + dy / len * 18 + 5, size, color);
    }

    private void sideLabel(GraphicsContext gc, double[][] p, int i, int j, String text, Color color, double size) {
        double cx = (p[0][0] + p[0][1] + p[0][2]) / 3.0;
        double cy = (p[1][0] + p[1][1] + p[1][2]) / 3.0;
        double mx = (p[0][i] + p[0][j]) / 2;
        double my = (p[1][i] + p[1][j]) / 2;
        double dx = mx - cx;
        double dy = my - cy;
        double len = Math.max(1, Math.hypot(dx, dy));
        centerText(gc, text, mx + dx / len * 14, my + dy / len * 14 + 4, size, color);
    }

    private void drawRect(GraphicsContext gc, double ax, double ay, double aw, double ah, int w, int h) {
        double s = Math.min(aw / w, ah / h);
        double rw = w * s;
        double rh = h * s;
        double rx = ax + (aw - rw) / 2;
        double ry = ay + (ah - rh) / 2;
        gc.setFill(Color.web("#2563eb", 0.45));
        gc.fillRect(rx, ry, rw, rh);
        gc.setStroke(Color.web("#60a5fa"));
        gc.setLineWidth(2.5);
        gc.strokeRect(rx, ry, rw, rh);
    }

    private void drawFoeContent(GraphicsContext gc, Foe f, double left) {
        Color fill = Color.web("#ef4444", 0.35);
        Color stroke = Color.web("#fca5a5");
        double ax = left + 30, ay = BODY_TOP + 22, aw = BODY_W - 60, ah = BODY_H - 44;

        switch (station) {
            case 0: {
                drawRect(gc, left + 25, BODY_TOP + 12, BODY_W - 50, BODY_H - 24, f.ref[0], f.ref[1]);
                centerText(gc, f.ref[0] + " x " + f.ref[1], f.x, BODY_CY + 6, 18, Color.WHITE);
                break;
            }
            case 1: {
                double[][] p = fit(anglePoints(f.ref[0], f.ref[1]), ax, ay, aw, ah);
                drawTriangleShape(gc, p, fill, stroke);
                vertexLabel(gc, p, 0, f.ref[0] + "°", Color.GOLD, 13);
                vertexLabel(gc, p, 1, f.ref[1] + "°", Color.GOLD, 13);
                vertexLabel(gc, p, 2, "?", Color.web("#f59e0b"), 13);
                break;
            }
            case 2: {
                double[][] p = fit(sidePoints(f.ref[0], f.ref[1], f.ref[2]), ax, ay, aw, ah);
                drawTriangleShape(gc, p, fill, stroke);
                sideLabel(gc, p, 0, 1, String.valueOf(f.ref[0]), Color.GOLD, 14);
                sideLabel(gc, p, 1, 2, String.valueOf(f.ref[1]), Color.GOLD, 14);
                sideLabel(gc, p, 2, 0, String.valueOf(f.ref[2]), Color.GOLD, 14);
                break;
            }
            case 3: {
                double[][] p = fit(sasPoints(f.ref[0], f.ref[1], f.ref[2]), ax, ay, aw, ah);
                drawTriangleShape(gc, p, fill, stroke);
                sideLabel(gc, p, 0, 1, String.valueOf(f.ref[0]), Color.GOLD, 14);
                sideLabel(gc, p, 0, 2, String.valueOf(f.ref[1]), Color.GOLD, 14);
                vertexLabel(gc, p, 0, f.ref[2] + "°", Color.CYAN, 13);
                break;
            }
            case 4: {
                double[][] tri = fit(new double[]{0.6, 0, 1.3}, new double[]{0, 1, 1},
                        left + 45, BODY_TOP + 16, BODY_W - 90, BODY_H - 32);
                drawTriangleShape(gc, tri, fill, stroke);
                double t = (double) f.ref[3] / (f.ref[3] + f.ref[4]);
                double dx = tri[0][0] + t * (tri[0][1] - tri[0][0]);
                double dy = tri[1][0] + t * (tri[1][1] - tri[1][0]);
                double ex = tri[0][0] + t * (tri[0][2] - tri[0][0]);
                double ey = tri[1][0] + t * (tri[1][2] - tri[1][0]);
                gc.setStroke(Color.GOLD);
                gc.setLineWidth(2.5);
                gc.strokeLine(dx, dy, ex, ey);
                centerText(gc, "AD=" + f.ref[0], (tri[0][0] + dx) / 2 - 26, (tri[1][0] + dy) / 2 + 4, 11, Color.GOLD);
                centerText(gc, "DB=" + f.ref[1], (dx + tri[0][1]) / 2 - 26, (dy + tri[1][1]) / 2 + 4, 11, Color.GOLD);
                centerText(gc, "AE=" + f.ref[2], (tri[0][0] + ex) / 2 + 26, (tri[1][0] + ey) / 2 + 4, 11, Color.GOLD);
                centerText(gc, "EC=?", (ex + tri[0][2]) / 2 + 24, (ey + tri[1][2]) / 2 + 4, 12, Color.CYAN);
                break;
            }
            case 5: {
                int[] sides = {f.ref[0], f.ref[1], f.ref[2]};
                int k = f.ref[3];
                int key = f.ref[4];
                int ask = f.ref[5];
                double[][] local = sidePoints(sides[0], sides[1], sides[2]);
                double[] bxs = new double[3];
                double[] bys = new double[3];
                for (int i = 0; i < 3; i++) {
                    bxs[i] = local[0][i] * k;
                    bys[i] = local[1][i] * k;
                }
                double[][] big = fit(bxs, bys, left + 100, BODY_TOP + 20, 98, BODY_H - 44);
                double minX = Math.min(big[0][0], Math.min(big[0][1], big[0][2]));
                double maxY = Math.max(big[1][0], Math.max(big[1][1], big[1][2]));
                double[] sx = new double[3];
                double[] sy = new double[3];
                for (int i = 0; i < 3; i++) {
                    sx[i] = left + 16 + (big[0][i] - minX) / k;
                    sy[i] = maxY - (maxY - big[1][i]) / k;
                }
                double[][] small = {sx, sy};
                drawTriangleShape(gc, small, Color.web("#2563eb", 0.45), Color.web("#60a5fa"));
                drawTriangleShape(gc, big, fill, stroke);
                for (int i = 0; i < 3; i++) {
                    int j = (i + 1) % 3;
                    sideLabel(gc, small, i, j, String.valueOf(sides[i]), Color.web("#93c5fd"), 11);
                    if (i == key) {
                        sideLabel(gc, big, i, j, String.valueOf(sides[i] * k), Color.GOLD, 13);
                    } else if (i == ask) {
                        sideLabel(gc, big, i, j, "?", Color.CYAN, 18);
                    }
                }
                break;
            }
            default: {
                int p = f.ref[0];
                int q = f.ref[1];
                int s = f.ref[2];
                boolean modeBig = f.ref[3] == 1;
                double[][] shape = sidePoints(3, 4, 5);
                double u = Math.min(80.0 / (3 * q), 80.0 / (4 * q));
                double baseY = BODY_TOP + 98;
                double[][] small = scaledShape(shape, left + 16, baseY, p * u);
                double[][] big = scaledShape(shape, left + 106, baseY, q * u);
                drawTriangleShape(gc, small, Color.web("#2563eb", 0.45), Color.web("#60a5fa"));
                drawTriangleShape(gc, big, fill, stroke);
                centerText(gc, modeBig ? "area " + s : "area " + (p * p),
                        left + 16 + 1.5 * p * u, BODY_TOP + BODY_H - 10, 12, Color.web("#93c5fd"));
                centerText(gc, modeBig ? "area ?" : "area " + (q * q),
                        left + 106 + 1.5 * q * u, BODY_TOP + BODY_H - 10, 12, modeBig ? Color.CYAN : Color.GOLD);
                break;
            }
        }
    }

    private void drawAmmo(GraphicsContext gc) {
        if (ammoFoe == null) return;
        if (station <= 3) {
            for (int i = 0; i < options.size(); i++) {
                Box b = options.get(i);
                gc.setFill(Color.web("#1e293b"));
                gc.fillRoundRect(b.x, b.y, b.w, b.h, 16, 16);
                gc.setStroke(Color.web("#475569"));
                gc.setLineWidth(1.5);
                gc.strokeRoundRect(b.x, b.y, b.w, b.h, 16, 16);
                centerText(gc, String.valueOf((char) ('A' + i)), b.x + 16, b.y + 22, 16, Color.web("#94a3b8"));
                drawCandidate(gc, ammoFoe.cands.get(i), b);
            }
        } else {
            for (Box b : options) {
                gc.setFill(Color.web("#334155"));
                gc.fillRoundRect(b.x, b.y, b.w, b.h, 16, 16);
                centerText(gc, b.label, b.x + b.w / 2, b.y + b.h / 2 + 9, 26, Color.WHITE);
            }
        }
    }

    private void drawCandidate(GraphicsContext gc, int[] v, Box b) {
        double ax = b.x + 30, ay = b.y + 30, aw = b.w - 60, ah = b.h - 62;
        Color fill = Color.web("#7c3aed", 0.45);
        Color stroke = Color.web("#a78bfa");
        switch (station) {
            case 0: {
                drawRect(gc, b.x + 20, b.y + 28, b.w - 40, b.h - 58, v[0], v[1]);
                centerText(gc, v[0] + " x " + v[1], b.x + b.w / 2, b.y + b.h - 12, 15, Color.WHITE);
                break;
            }
            case 1: {
                double[][] p = fit(anglePoints(v[0], v[1]), ax, ay, aw, ah);
                drawTriangleShape(gc, p, fill, stroke);
                vertexLabel(gc, p, 0, v[0] + "°", Color.GOLD, 13);
                vertexLabel(gc, p, 1, v[1] + "°", Color.GOLD, 13);
                break;
            }
            case 2: {
                double[][] p = fit(sidePoints(v[0], v[1], v[2]), ax, ay, aw, ah);
                drawTriangleShape(gc, p, fill, stroke);
                centerText(gc, v[0] + ", " + v[1] + ", " + v[2], b.x + b.w / 2, b.y + b.h - 12, 15, Color.WHITE);
                break;
            }
            default: {
                double[][] p = fit(sasPoints(v[0], v[1], v[2]), ax, ay, aw, ah);
                drawTriangleShape(gc, p, fill, stroke);
                sideLabel(gc, p, 0, 1, String.valueOf(v[0]), Color.GOLD, 12);
                sideLabel(gc, p, 0, 2, String.valueOf(v[1]), Color.GOLD, 12);
                vertexLabel(gc, p, 0, v[2] + "°", Color.CYAN, 12);
                break;
            }
        }
    }
}