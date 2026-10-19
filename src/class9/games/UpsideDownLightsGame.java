package class9.games;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Class 9 - Real Numbers game, Stranger Things theme.
 *
 * Joyce's wall of lights is glowing: eight number-bulbs hang on a wire.
 * Pick a hero from the squad, then blast every RATIONAL (or every IRRATIONAL)
 * bulb with their power. A wrong bulb summons Upside Down lightning, costs an Eggo (life)
 * and gives Vecna 25 points.
 *
 * Streak power-up: 3 correct blasts in a row charge up Eleven. Click Eleven and she uses
 * her mind to find one correct bulb for you (once per charge).
 */
public class UpsideDownLightsGame extends LivesGame {

    private static final List<String> RATIONAL = Arrays.asList(
            "3/4", "-5/2", "0.75", "6", "0.5", "0.333…", "√49", "√64",
            "1/7", "22/7", "-8", "0.125", "√(9/16)", "2.5", "√81", "-0.6");
    private static final List<String> IRRATIONAL = Arrays.asList(
            "√2", "√3", "√5", "√7", "π", "√11", "√13", "√17", "√19",
            "0.1010010001…", "3√5", "1 + √2", "2π", "√6", "√10");

    private static final Color[] BULBS = {
            Color.web("#ef4444"), Color.web("#22c55e"), Color.web("#3b82f6"),
            Color.web("#facc15"), Color.web("#ec4899"), Color.web("#f97316")
    };
    private static final String[] SCARED = {"Oh no!", "Run!", "It's coming!", "Not good!"};

    private static final int CARDS = 8;
    private static final int COLS = 4;
    private static final double CARD_W = 190;
    private static final double CARD_H = 108;
    private static final double GAP_X = 20;
    private static final double GAP_Y = 20;
    private static final double START_X = (WIDTH - (COLS * CARD_W + (COLS - 1) * GAP_X)) / 2;
    private static final double START_Y = 152;
    private static final double WIRE_Y = 130;
    private static final double HERO_BASE = 506;

    private final Hero[] heroes = {
            new Hero("Eleven", "#f5d0b5", "#7c4a2d", "#f472b6", "#f43f5e", 0, "Mind blast!"),
            new Hero("Mike", "#f1c9a5", "#1f1308", "#2563eb", "#38bdf8", 0, "Got 'em!"),
            new Hero("Dustin", "#f1c9a5", "#5a3a1a", "#dc2626", "#facc15", 3, "Math is cool!"),
            new Hero("Lucas", "#8d5a3c", "#120a05", "#4d7c0f", "#22c55e", 4, "Good call!"),
            new Hero("Will", "#f5d0b5", "#6b4423", "#65a30d", "#a78bfa", 0, "I sensed it."),
            new Hero("Max", "#f5d0b5", "#d9541e", "#0d9488", "#fb923c", 1, "Too easy!"),
            new Hero("Steve", "#f1c9a5", "#3b2415", "#1e3a8a", "#60a5fa", 2, "Nice one, kids!"),
            new Hero("Nancy", "#f5d0b5", "#4a2c17", "#a855f7", "#f472b6", 1, "Logic wins!"),
            new Hero("Hopper", "#e8b992", "#2b1a0e", "#ea580c", "#f59e0b", 0, "Good work."),
            new Hero("Joyce", "#f1c9a5", "#6e4a2a", "#9f1239", "#fde047", 2, "Lights are on!"),
            new Hero("Robin", "#f5d0b5", "#e5c76b", "#0891b2", "#2dd4bf", 0, "Brainy!"),
            new Hero("Eddie", "#f1c9a5", "#1c0f08", "#18181b", "#c084fc", 1, "Rock on!")
    };

    private final String[] labels = new String[CARDS];
    private final boolean[] isTarget = new boolean[CARDS];
    private final boolean[] smashed = new boolean[CARDS];
    private final boolean[] wrong = new boolean[CARDS];
    private final double[] appear = new double[CARDS];
    private final double[] hoverGlow = new double[CARDS];

    private final List<double[]> beams = new ArrayList<>();   // x1,y1,x2,y2,life,heroIndex
    private final List<double[]> bolts = new ArrayList<>();   // life, x0,y0, x1,y1 ... (jagged points)

    private final double[] sporeX = new double[45];
    private final double[] sporeY = new double[45];
    private final double[] sporeV = new double[45];
    private final double[] sporeR = new double[45];

    private static final int VECNA_POINTS = 25;

    private final Hero vecna = new Hero("Vecna", "#9f1239", "#9f1239", "#1c1917", "#ef4444", 0, "Mine!");
    private int vecnaScore;
    private int streak;
    private boolean powerReady;

    private int selected = 0;
    private boolean picked;
    private boolean targetIrrational;
    private int targetCount;
    private int foundCount;
    private int hover = -1;
    private boolean hintShown;
    private double pulse;

    public UpsideDownLightsGame() {
        roundTime = 45;
        livesLabel = "EGGOS";
        for (int i = 0; i < sporeX.length; i++) {
            sporeX[i] = random.nextDouble() * WIDTH;
            sporeY[i] = HUD_TOP + random.nextDouble() * (HEIGHT - HUD_TOP - HUD_BOTTOM);
            sporeV[i] = 10 + random.nextDouble() * 25;
            sporeR[i] = 1.5 + random.nextDouble() * 2.5;
        }
    }

    @Override
    protected String title() {
        return "WALL OF LIGHTS";
    }

    @Override
    protected String wonMessage() {
        return "LIGHTS ON! CLICK TO CONTINUE";
    }

    @Override
    protected String lostMessage() {
        return lives <= 0 ? "VECNA CLAIMED YOU! CLICK TO RETRY" : "TIME'S UP! CLICK TO RETRY";
    }

    // ------------------------------------------------------------ round setup

    @Override
    protected void startRound() {
        if (resetLivesIfNeeded()) {
            vecnaScore = 0;
            streak = 0;
            powerReady = false;
        }
        hintShown = false;
        hover = -1;
        foundCount = 0;
        beams.clear();
        bolts.clear();
        targetIrrational = random.nextBoolean();
        targetCount = 3 + random.nextInt(2);   // 3 or 4 targets

        List<String> targets = new ArrayList<>(targetIrrational ? IRRATIONAL : RATIONAL);
        List<String> others = new ArrayList<>(targetIrrational ? RATIONAL : IRRATIONAL);
        Collections.shuffle(targets, random);
        Collections.shuffle(others, random);

        List<String[]> deck = new ArrayList<>();   // {label, "1" if target}
        for (int i = 0; i < targetCount; i++) deck.add(new String[]{targets.get(i), "1"});
        for (int i = 0; i < CARDS - targetCount; i++) deck.add(new String[]{others.get(i), "0"});
        Collections.shuffle(deck, random);

        for (int i = 0; i < CARDS; i++) {
            labels[i] = deck.get(i)[0];
            isTarget[i] = deck.get(i)[1].equals("1");
            smashed[i] = false;
            wrong[i] = false;
            appear[i] = -i * 0.09;        // negative = waiting to drop in
            hoverGlow[i] = 0;
        }
        for (Hero h : heroes) {
            h.scaredTime = 0;
            h.bubbleTime = 0;
        }
    }

    @Override
    protected String prompt() {
        String who = heroes[selected].name;
        if (state != State.PLAYING) {
            StringBuilder sb = new StringBuilder("Answer: ");
            boolean first = true;
            for (int i = 0; i < CARDS; i++) {
                if (isTarget[i]) {
                    if (!first) sb.append(", ");
                    sb.append(labels[i]);
                    first = false;
                }
            }
            sb.append("\n").append(targetIrrational
                    ? "Irrational = never ends, never repeats (√ of non-squares, π)"
                    : "Rational = p/q, or a decimal that ends / repeats (√49 = 7)");
            return sb.toString();
        }
        String line2 = powerReady
                ? "Eleven's power is READY - click Eleven and she will find an answer!"
                : hintShown
                ? (targetIrrational
                ? "Hint: irrational = cannot be written as p/q (√ of non-squares, π)"
                : "Hint: rational = p/q, or ends / repeats (√49 = 7 is rational)")
                : "Blasted: " + foundCount + " / " + targetCount + "   (click a hero below to switch)";
        return who + ", blast every " + (targetIrrational ? "IRRATIONAL" : "RATIONAL") + " number!\n" + line2;
    }

    // ------------------------------------------------------------ input

    private double cardX(int i) {
        return START_X + (i % COLS) * (CARD_W + GAP_X);
    }

    private double cardY(int i) {
        return START_Y + (i / COLS) * (CARD_H + GAP_Y);
    }

    private int cardAt(double x, double y) {
        for (int i = 0; i < CARDS; i++) {
            if (appear[i] < 0.6) continue;     // still dropping in
            if (x >= cardX(i) && x <= cardX(i) + CARD_W && y >= cardY(i) && y <= cardY(i) + CARD_H) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onMouseMove(double x, double y) {
        hover = state == State.PLAYING ? cardAt(x, y) : -1;
    }

    @Override
    protected void onPlayClick(double x, double y) {
        int h = heroAt(heroes, x, y, HERO_BASE);
        if (h >= 0) {
            if (h == 0 && powerReady) {
                useElevenPower();
                return;
            }
            selected = h;
            picked = true;
            heroes[h].jump();
            heroes[h].say(heroes[h].cheer, 1.2);
            burst(heroX(h, heroes.length), HERO_BASE - 40, heroes[h].power, 14, 160, 200);
            return;
        }

        int i = cardAt(x, y);
        if (i < 0 || smashed[i] || wrong[i]) return;

        double cx = cardX(i) + CARD_W / 2;
        double cy = cardY(i) + CARD_H / 2;

        if (isTarget[i]) {
            blast(i, selected, false);
            vecna.scare("Curses!");
            streak++;
            if (streak >= 3) {
                if (!powerReady) {
                    powerReady = true;
                    streak = 0;
                    heroes[0].jump();
                    triggerFlash(Color.web("#f472b6"), 0.6);
                    burst(heroX(0, heroes.length), HERO_BASE - 50, Color.web("#f472b6"), 30, 260, 120);
                    floatingText("ELEVEN'S POWER READY!", WIDTH / 2, 410, Color.web("#f472b6"));
                } else {
                    streak = 3;
                }
            }
        } else {
            wrong[i] = true;
            hintShown = true;
            streak = 0;
            vecnaScore += VECNA_POINTS;
            vecna.jump();
            vecna.say("Mine!", 1.3);
            floatingText("VECNA +" + VECNA_POINTS, 790, 160, Color.web("#ef4444"));
            addBolt(cx, cy);
            triggerFlash(Color.web("#dc2626"), 1.0);
            burst(cx, cy, Color.web("#7f1d1d"), 26, 240, 220);
            burst(790, 90, Color.web("#ef4444"), 14, 160, 100);
            heroes[selected].scare(SCARED[random.nextInt(SCARED.length)]);
            loseLife(4, "NOT " + (targetIrrational ? "IRRATIONAL" : "RATIONAL") + "!", cx, cy - 30);
            if (lives <= 0) {
                for (Hero h2 : heroes) h2.scare("...");
            }
        }
    }

    /** A correct bulb is blasted by the hero at index casterIdx. */
    private void blast(int i, int casterIdx, boolean viaPower) {
        double cx = cardX(i) + CARD_W / 2;
        double cy = cardY(i) + CARD_H / 2;
        Hero caster = heroes[casterIdx];

        smashed[i] = true;
        foundCount++;
        beams.add(new double[]{heroX(casterIdx, heroes.length), HERO_BASE - 60, cx, cy, 1.0, casterIdx});
        burst(cx, cy, caster.power, 28, 260, 260);
        burst(cx, cy, Color.WHITE, 10, 180, 100);
        caster.jump();
        if (!viaPower) caster.say(caster.cheer, 1.1);
        floatingText(viaPower ? "ELEVEN GUESSED IT!" : "BLAST!", cx, cy - 30, Color.GOLD);

        if (foundCount == targetCount) {
            int points = 100 + timeBonus();
            roundWon(points);
            floatingText("+" + points, WIDTH / 2, 100, Color.LIMEGREEN);
            triggerFlash(Color.WHITE, 0.6);
            for (int k = 0; k < heroes.length; k++) {
                heroes[k].cheerAfter(k * 0.06);
                burst(heroX(k, heroes.length), HERO_BASE - 50, heroes[k].power, 12, 200, 260);
            }
        }
    }

    /** Eleven's mind power: she finds one correct bulb for the player. */
    private void useElevenPower() {
        List<Integer> open = new ArrayList<>();
        for (int i = 0; i < CARDS; i++) {
            if (isTarget[i] && !smashed[i]) open.add(i);
        }
        if (open.isEmpty()) return;
        int i = open.get(random.nextInt(open.size()));

        powerReady = false;
        selected = 0;
        picked = true;
        triggerFlash(Color.web("#f472b6"), 0.5);
        heroes[0].say("I see it...", 1.2);
        burst(heroX(0, heroes.length), HERO_BASE - 52, Color.web("#dc2626"), 8, 90, 300);   // nosebleed
        blast(i, 0, true);
    }

    private void addBolt(double tx, double ty) {
        double[] b = new double[2 + 2 * 9];
        b[0] = 1.0;
        double x = tx + (random.nextDouble() - 0.5) * 80;
        double y = HUD_TOP;
        for (int k = 0; k < 9; k++) {
            double t = k / 8.0;
            b[1 + 2 * k] = x + (tx - x) * t + (k == 0 || k == 8 ? 0 : (random.nextDouble() - 0.5) * 50);
            b[2 + 2 * k] = y + (ty - y) * t;
        }
        bolts.add(b);
    }

    // ------------------------------------------------------------ animation

    @Override
    protected void onUpdate(double dt) {
        super.onUpdate(dt);
        pulse += 5 * dt;
        updateHeroes(heroes, dt);
        vecna.update(dt);

        if (powerReady && state == State.PLAYING) {
            heroes[0].say("Click me!", 0.3);
            if (random.nextDouble() < 0.2) {
                burst(heroX(0, heroes.length), HERO_BASE - 50, Color.web("#f472b6"), 2, 90, -40);
            }
        }

        for (int i = 0; i < CARDS; i++) {
            appear[i] = Math.min(1, appear[i] + 2.0 * dt);
            double target = (state == State.PLAYING && i == hover) ? 1 : 0;
            hoverGlow[i] += (target - hoverGlow[i]) * Math.min(1, 12 * dt);
        }
        for (Iterator<double[]> it = beams.iterator(); it.hasNext(); ) {
            double[] b = it.next();
            b[4] -= 2.6 * dt;
            if (b[4] <= 0) it.remove();
        }
        for (Iterator<double[]> it = bolts.iterator(); it.hasNext(); ) {
            double[] b = it.next();
            b[0] -= 3.2 * dt;
            if (b[0] <= 0) it.remove();
        }
        for (int i = 0; i < sporeX.length; i++) {
            sporeY[i] -= sporeV[i] * dt;
            sporeX[i] += Math.sin(time + i) * 12 * dt;
            if (sporeY[i] < HUD_TOP) {
                sporeY[i] = HEIGHT - HUD_BOTTOM;
                sporeX[i] = random.nextDouble() * WIDTH;
            }
        }
    }

    // ------------------------------------------------------------ drawing

    @Override
    protected void drawLifeIcon(GraphicsContext gc, double x, double y, boolean alive) {
        // a little waffle
        gc.setFill(alive ? Color.web("#f59e0b") : Color.web("#374151"));
        gc.fillRoundRect(x, y, 24, 24, 6, 6);
        gc.setStroke(alive ? Color.web("#b45309") : Color.web("#1f2937"));
        gc.setLineWidth(1.5);
        gc.strokeLine(x + 8, y + 2, x + 8, y + 22);
        gc.strokeLine(x + 16, y + 2, x + 16, y + 22);
        gc.strokeLine(x + 2, y + 8, x + 22, y + 8);
        gc.strokeLine(x + 2, y + 16, x + 22, y + 16);
    }

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        double top = HUD_TOP;
        double bottom = HEIGHT - HUD_BOTTOM;

        // Upside Down sky
        gc.setFill(Color.web("#070b14"));
        gc.fillRect(0, top, WIDTH, bottom - top);
        gc.setGlobalAlpha(0.35 + 0.15 * Math.sin(time * 1.3));
        gc.setFill(Color.web("#3b0a16"));
        gc.fillRect(0, top, WIDTH, (bottom - top) * 0.55);
        gc.setGlobalAlpha(0.18 + 0.1 * Math.sin(time * 0.9 + 2));
        gc.setFill(Color.web("#0b2a4a"));
        gc.fillRect(0, top + (bottom - top) * 0.45, WIDTH, (bottom - top) * 0.55);
        gc.setGlobalAlpha(1.0);

        // floating spores
        gc.setFill(Color.web("#fca5a5"));
        for (int i = 0; i < sporeX.length; i++) {
            gc.setGlobalAlpha(0.25 + 0.25 * Math.sin(time * 2 + i));
            gc.fillOval(sporeX[i], sporeY[i], sporeR[i], sporeR[i]);
        }
        gc.setGlobalAlpha(1.0);

        drawLives(gc);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 22));
        gc.setFill(Color.web("#fca5a5"));
        gc.fillText("BLAST THE " + (targetIrrational ? "IRRATIONAL" : "RATIONAL") + " BULBS", WIDTH / 2, top + 54);

        drawWire(gc);
        for (int i = 0; i < CARDS; i++) drawCard(gc, i);

        for (double[] b : beams) drawBeam(gc, b);
        for (double[] b : bolts) drawBolt(gc, b);

        if (state == State.PLAYING && !picked) {
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            gc.setFill(Color.web("#fde047"));
            gc.setGlobalAlpha(0.6 + 0.4 * Math.sin(time * 4));
            gc.fillText("Click a hero to choose who blasts the bulbs!", WIDTH / 2, 420);
            gc.setGlobalAlpha(1.0);
        }

        if (powerReady) {
            double ex = heroX(0, heroes.length);
            double r = 38 + 7 * Math.sin(time * 6);
            gc.setGlobalAlpha(0.28 + 0.12 * Math.sin(time * 6));
            gc.setFill(Color.web("#f472b6"));
            gc.fillOval(ex - r, HERO_BASE - 45 - r, 2 * r, 2 * r);
            gc.setGlobalAlpha(1.0);
        }
        drawHeroes(gc, heroes, HERO_BASE, 1.0, selected);
        drawVecnaAndStreak(gc);
        drawParticles(gc);
        drawFlash(gc);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawWire(GraphicsContext gc) {
        gc.setStroke(Color.web("#44403c"));
        gc.setLineWidth(3);
        int segs = 30;
        double px = 0;
        double py = WIRE_Y;
        for (int i = 1; i <= segs; i++) {
            double x = WIDTH * i / segs;
            double y = WIRE_Y + 7 * Math.sin(i * 0.6);
            gc.strokeLine(px, py, x, y);
            px = x;
            py = y;
        }
        for (int i = 0; i < 15; i++) {
            double x = 30 + i * 60;
            double y = WIRE_Y + 7 * Math.sin((x / WIDTH * segs) * 0.6) + 8;
            double flicker = 0.35 + 0.65 * Math.abs(Math.sin(time * 2.7 + i * 1.9));
            Color c = BULBS[i % BULBS.length];
            gc.setGlobalAlpha(flicker * 0.35);
            gc.setFill(c);
            gc.fillOval(x - 11, y - 8, 22, 22);
            gc.setGlobalAlpha(flicker);
            gc.fillOval(x - 5, y - 2, 10, 13);
        }
        gc.setGlobalAlpha(1.0);
    }

    private void drawCard(GraphicsContext gc, int i) {
        double t = ease(appear[i]);
        if (appear[i] <= 0) return;
        double dropOffset = -(1 - t) * 170;
        double x = cardX(i);
        double y = cardY(i) + dropOffset - 7 * hoverGlow[i];
        Color bulb = BULBS[i % BULBS.length];
        double flicker = 0.6 + 0.4 * Math.sin(time * 3.1 + i * 2.3);

        gc.setGlobalAlpha(Math.min(1, t * 1.5));

        // hanging string
        gc.setStroke(Color.web("#57534e"));
        gc.setLineWidth(2);
        gc.strokeLine(x + CARD_W / 2, WIRE_Y + 10, x + CARD_W / 2, y);

        Color fill = Color.web("#111827");
        Color border = bulb;
        double glow = 0.25 * flicker + 0.35 * hoverGlow[i];
        double borderWidth = 2 + 2 * hoverGlow[i];

        if (smashed[i]) {
            fill = Color.web("#14532d");
            border = Color.LIMEGREEN;
            glow = 0.5;
            borderWidth = 4;
        } else if (wrong[i]) {
            fill = Color.web("#450a0a");
            border = Color.RED;
            glow = random.nextDouble() < 0.5 ? 0.5 : 0.05;     // dying flicker
            borderWidth = 4;
        } else if (state != State.PLAYING && isTarget[i]) {
            border = Color.GOLD;
            borderWidth = 3 + Math.sin(pulse);
            glow = 0.45;
        }

        // glow halo
        gc.setGlobalAlpha(Math.min(1, t * 1.5) * glow);
        gc.setFill(border);
        gc.fillRoundRect(x - 10, y - 10, CARD_W + 20, CARD_H + 20, 28, 28);
        gc.setGlobalAlpha(Math.min(1, t * 1.5));

        gc.setFill(fill);
        gc.fillRoundRect(x, y, CARD_W, CARD_H, 18, 18);
        gc.setStroke(border);
        gc.setLineWidth(borderWidth);
        gc.strokeRoundRect(x, y, CARD_W, CARD_H, 18, 18);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, labels[i].length() > 8 ? 26 : 38));
        gc.fillText(labels[i], x + CARD_W / 2, y + CARD_H / 2 + 12);
        gc.setGlobalAlpha(1.0);
    }

    private void drawBeam(GraphicsContext gc, double[] b) {
        double life = Math.max(0, b[4]);
        Color c = heroes[(int) b[5]].power;
        gc.setGlobalAlpha(life);
        gc.setStroke(c);
        gc.setLineWidth(14 * life);
        gc.strokeLine(b[0], b[1], b[2], b[3]);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(5 * life);
        gc.strokeLine(b[0], b[1], b[2], b[3]);
        gc.setGlobalAlpha(1.0);
    }

    private void drawBolt(GraphicsContext gc, double[] b) {
        double life = Math.max(0, b[0]);
        gc.setGlobalAlpha(life);
        for (int pass = 0; pass < 2; pass++) {
            gc.setStroke(pass == 0 ? Color.web("#ef4444") : Color.WHITE);
            gc.setLineWidth(pass == 0 ? 8 : 3);
            for (int k = 0; k < 8; k++) {
                gc.strokeLine(b[1 + 2 * k], b[2 + 2 * k], b[3 + 2 * k], b[4 + 2 * k]);
            }
        }
        gc.setGlobalAlpha(1.0);
    }

    private void drawVecnaAndStreak(GraphicsContext gc) {
        // Vecna scoreboard (top right)
        double pulseGlow = 0.2 + 0.1 * Math.sin(time * 3) + (vecna.jumpH > 0 ? 0.25 : 0);
        gc.setGlobalAlpha(pulseGlow);
        gc.setFill(Color.web("#dc2626"));
        gc.fillOval(755, 62, 120, 62);
        gc.setGlobalAlpha(1.0);
        drawHero(gc, vecna, 815, 108, 0.75, false);
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 13));
        gc.setFill(Color.web("#fca5a5"));
        gc.fillText("VECNA", 760, 86);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 26 + (vecna.jumpH > 0 ? 4 : 0)));
        gc.setFill(Color.web("#ef4444"));
        gc.fillText(String.valueOf(vecnaScore), 760, 114);

        // streak pips (top left, under the Eggos)
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
        if (powerReady) {
            gc.setFill(Color.web("#f472b6"));
            gc.setGlobalAlpha(0.65 + 0.35 * Math.sin(time * 6));
            gc.fillText("ELEVEN READY!", 20, 112);
            gc.setGlobalAlpha(1.0);
        } else {
            gc.setFill(Color.web("#fca5a5"));
            gc.fillText("STREAK", 20, 112);
            for (int i = 0; i < 3; i++) {
                gc.setFill(i < streak ? Color.GOLD : Color.web("#374151"));
                gc.fillOval(92 + i * 20, 100, 14, 14);
            }
        }
        gc.setTextAlign(TextAlignment.LEFT);
    }
}
