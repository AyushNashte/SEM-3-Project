package class9.games;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Class 9 - Polynomials game, Alice in Borderland theme.
 *
 * The Borderland deals four playing cards, one holds the right answer.
 * Each round is a different suit game:
 *   Spades = Zero Hunt, Diamonds = Value Check, Hearts = Remainder Raid, Clubs = Degree Decode.
 * Pick a player from the squad, then flip a card. A wrong card flips into a Joker, a big
 * "YOU LOST!" message appears, the lasers sweep and you lose a life.
 * Your visa runs out when the time bar does.
 *
 * 3 correct games in a row open a door of light: RETURN TO THE REAL WORLD (you win and
 * the game ends with a sunrise) or STAY in the Borderland and keep playing.
 */
public class BorderlandGamesGame extends LivesGame {

    private static final String[] SUITS = {"♠", "♦", "♥", "♣"};
    private static final String[] SUIT_NAMES = {"SPADES", "DIAMONDS", "HEARTS", "CLUBS"};
    private static final String[] SCARED = {"Lasers!", "Not good!", "Run!", "Wrong card!"};

    private static final double CARD_W = 150;
    private static final double CARD_H = 185;
    private static final double CARD_Y = 200;
    private static final double HERO_BASE = 506;

    private final Hero[] heroes = {
            new Hero("Arisu", "#f1c9a5", "#1a1410", "#334155", "#38bdf8", 0, "Calculated!"),
            new Hero("Usagi", "#f5d0b5", "#1b1320", "#e5e7eb", "#f472b6", 1, "Nice climb!"),
            new Hero("Karube", "#e8b992", "#c9a24a", "#c2410c", "#f97316", 0, "Let's go!"),
            new Hero("Chishiya", "#f1c9a5", "#9ca3af", "#6b7280", "#a3e635", 0, "Obvious."),
            new Hero("Niragi", "#f1c9a5", "#e5e7eb", "#111827", "#ef4444", 2, "Hehe, easy."),
            new Hero("Kuina", "#f5d0b5", "#15101a", "#0ea5e9", "#22d3ee", 1, "Got it!"),
            new Hero("Aguni", "#e8b992", "#0f0f0f", "#374151", "#facc15", 4, "Disciplined."),
            new Hero("Mira", "#f5d0b5", "#3a2418", "#db2777", "#fb7185", 1, "Bingo!"),
            new Hero("Tatta", "#f1c9a5", "#2a1a10", "#16a34a", "#4ade80", 0, "Whoa, cool!"),
            new Hero("Hatter", "#e8b992", "#2b1d12", "#7c2d12", "#c084fc", 3, "Game on.")
    };

    private int suit;                     // 0..3 for this round
    private String roundTitle;
    private String polyText;
    private String question;
    private String hint;
    private String solution;
    private final String[] optionLabels = new String[4];
    private final boolean[] tried = new boolean[4];
    private final double[] dealT = new double[4];      // <0 waiting, 0..1 dealing back->face
    private final double[] jokerT = new double[4];     // 0 = face, 0..1 flipping to joker
    private final double[] lift = new double[4];       // hover raise
    private int correct;

    private static final double BTN_W = 270;
    private static final double BTN_H = 70;
    private static final double BTN_Y = 305;
    private static final double BTN_X0 = WIDTH / 2 - 285;     // "real world"
    private static final double BTN_X1 = WIDTH / 2 + 15;      // "stay here"
    private static final double POPUP_TIME = 2.0;

    private int streak;
    private boolean choosing;          // the real world / stay choice is on screen
    private boolean escaped;           // player chose the real world
    private double frozenTime = -1;    // visa timer is frozen at this value while a popup/choice is up
    private double lostPopupT;
    private double escapeT;
    private double cheerTimer;
    private int choiceHover = -1;      // 0 = real world, 1 = stay

    private int selected = 0;
    private boolean picked;
    private int hover = -1;
    private boolean hintShown;
    private double pulse;

    private final List<double[]> lasers = new ArrayList<>();   // y, life, sweep
    private final int[] bx = new int[16];
    private final int[] bw = new int[16];
    private final int[] bh = new int[16];
    private final double[] fallX = new double[14];
    private final double[] fallY = new double[14];
    private final double[] fallV = new double[14];
    private final double[] fallRot = new double[14];
    private final double[] fallSpin = new double[14];

    public BorderlandGamesGame() {
        roundTime = 40;
        livesLabel = "LIVES";
        tagText = "BORDERLAND";
        tagColor = Color.web("#f472b6");
        int x = 0;
        for (int i = 0; i < bx.length; i++) {
            bx[i] = x;
            bw[i] = 40 + random.nextInt(40);
            bh[i] = 60 + random.nextInt(110);
            x += bw[i] + 4;
        }
        for (int i = 0; i < fallX.length; i++) {
            fallX[i] = random.nextDouble() * WIDTH;
            fallY[i] = HUD_TOP + random.nextDouble() * 450;
            fallV[i] = 18 + random.nextDouble() * 30;
            fallRot[i] = random.nextDouble() * 360;
            fallSpin[i] = (random.nextDouble() - 0.5) * 90;
        }
    }

    @Override
    protected String title() {
        return "BORDERLAND GAMES";
    }

    @Override
    protected String wonMessage() {
        return "GAME CLEARED! CLICK TO CONTINUE";
    }

    @Override
    protected String lostMessage() {
        if (escaped) return "CLICK TO PLAY AGAIN";
        return lives <= 0 ? "YOU LOST! CLICK TO RETRY" : "VISA EXPIRED! CLICK TO RETRY";
    }

    // ------------------------------------------------------------ round setup

    @Override
    protected void startRound() {
        if (resetLivesIfNeeded()) {
            streak = 0;
        }
        choosing = false;
        escaped = false;
        lostPopupT = 0;
        frozenTime = -1;
        hintShown = false;
        hover = -1;
        lasers.clear();
        for (int i = 0; i < 4; i++) {
            tried[i] = false;
            dealT[i] = -i * 0.18;
            jokerT[i] = 0;
            lift[i] = 0;
        }
        for (Hero h : heroes) {
            h.scaredTime = 0;
            h.bubbleTime = 0;
        }

        switch (random.nextInt(4)) {
            case 0:
                suit = 0;
                makeZeroRound();
                break;
            case 1:
                suit = 1;
                makeValueRound();
                break;
            case 2:
                suit = 2;
                makeRemainderRound();
                break;
            default:
                suit = 3;
                makeDegreeRound();
                break;
        }
    }

    private void makeZeroRound() {
        int a = random.nextInt(11) - 5;
        int b;
        do {
            b = random.nextInt(11) - 5;
        } while (b == a);
        int[] c = {a * b, -(a + b), 1};
        int root = random.nextBoolean() ? a : b;

        List<Integer> pool = new ArrayList<>();
        for (int v = -6; v <= 6; v++) {
            if (v != a && v != b) pool.add(v);
        }
        Collections.shuffle(pool, random);

        roundTitle = "ZERO HUNT";
        polyText = "p(x) = " + polyString(c);
        question = "Which card is a ZERO of p(x)?";
        hint = "Hint: x = k is a zero when p(k) = 0. Try substituting.";
        solution = String.format("p(%d) = 0, so x = %d is a zero. Factors: %s%s",
                root, root, factor(a), factor(b));
        fillOptions(root, pool.subList(0, 3), "x = ");
    }

    private void makeValueRound() {
        int[] c = {random.nextInt(19) - 9, random.nextInt(11) - 5, 1 + random.nextInt(3)};
        int k = nonZero(3);
        int ans = eval(c, k);

        roundTitle = "VALUE CHECK";
        polyText = "p(x) = " + polyString(c);
        question = "Find p(" + k + ") - flip the right card";
        hint = "Hint: replace every x by " + signed(k) + " and simplify.";
        solution = String.format("Put x = %d: p(%d) = %d", k, k, ans);
        fillOptions(ans, nearbyWrongValues(ans), "");
    }

    private void makeRemainderRound() {
        int[] c = {random.nextInt(19) - 9, random.nextInt(11) - 5,
                random.nextInt(9) - 4, 1 + random.nextInt(2)};
        int k = nonZero(3);
        int ans = eval(c, k);

        roundTitle = "REMAINDER RAID";
        polyText = "p(x) = " + polyString(c);
        question = "Remainder when p(x) is divided by " + factor(k) + " ?";
        hint = "Hint: Remainder Theorem - the remainder is p(k) when dividing by (x - k).";
        solution = String.format("x = %d makes the divisor 0, so remainder = p(%d) = %d", k, k, ans);
        fillOptions(ans, nearbyWrongValues(ans), "");
    }

    private void makeDegreeRound() {
        int d = 2 + random.nextInt(4);   // degree 2..5
        int[] c = new int[d + 1];
        c[d] = (random.nextBoolean() ? 1 : -1) * (1 + random.nextInt(5));
        for (int i = 0; i < d; i++) {
            c[i] = random.nextInt(100) < 35 ? 0 : random.nextInt(13) - 6;
        }

        List<Integer> wrongs = new ArrayList<>();
        wrongs.add(d - 1);
        wrongs.add(d + 1);
        wrongs.add(d + 2);

        roundTitle = "DEGREE DECODE";
        polyText = "p(x) = " + polyString(c);
        question = "What is the DEGREE of p(x)?";
        hint = "Hint: degree = the highest power of x in the polynomial.";
        solution = "The highest power of x is " + d + ", so the degree is " + d;
        fillOptions(d, wrongs, "");
    }

    private void fillOptions(int answer, List<Integer> wrongs, String prefix) {
        List<Integer> all = new ArrayList<>();
        all.add(answer);
        all.addAll(wrongs);
        Collections.shuffle(all, random);
        for (int i = 0; i < 4; i++) {
            optionLabels[i] = prefix + all.get(i);
            if (all.get(i) == answer) correct = i;
        }
    }

    private List<Integer> nearbyWrongValues(int ans) {
        List<Integer> pool = new ArrayList<>();
        int[] offsets = {1, -1, 2, -2, 3, -3};
        for (int o : offsets) pool.add(ans + o);
        if (ans != 0) pool.add(-ans);
        Collections.shuffle(pool, random);
        List<Integer> out = new ArrayList<>();
        for (int v : pool) {
            if (v != ans && !out.contains(v)) out.add(v);
            if (out.size() == 3) break;
        }
        return out;
    }

    private int nonZero(int range) {
        int v;
        do {
            v = random.nextInt(2 * range + 1) - range;
        } while (v == 0);
        return v;
    }

    // ------------------------------------------------------------ maths helpers

    /** c[i] is the coefficient of x^i. */
    private static int eval(int[] c, int x) {
        int result = 0;
        for (int i = c.length - 1; i >= 0; i--) {
            result = result * x + c[i];
        }
        return result;
    }

    private static String polyString(int[] c) {
        StringBuilder sb = new StringBuilder();
        for (int i = c.length - 1; i >= 0; i--) {
            int v = c[i];
            if (v == 0) continue;
            if (sb.length() == 0) {
                if (v < 0) sb.append("-");
            } else {
                sb.append(v < 0 ? " - " : " + ");
            }
            int a = Math.abs(v);
            if (i == 0) {
                sb.append(a);
            } else {
                if (a != 1) sb.append(a);
                sb.append("x").append(power(i));
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    private static String power(int n) {
        switch (n) {
            case 1:
                return "";
            case 2:
                return "²";
            case 3:
                return "³";
            default:
                return "^" + n;
        }
    }

    private static String factor(int r) {
        if (r == 0) return "x";
        return r > 0 ? "(x - " + r + ")" : "(x + " + (-r) + ")";
    }

    private static String signed(int v) {
        return v < 0 ? "(" + v + ")" : String.valueOf(v);
    }

    // ------------------------------------------------------------ input

    @Override
    protected String prompt() {
        if (escaped) {
            return "Final score: " + score + " - you survived the Borderland!\nThanks for playing";
        }
        if (choosing) {
            return "3 games cleared in a row! A door of light has opened.\nGo back to the real world, or stay and keep playing?";
        }
        if (state != State.PLAYING) {
            return solution;
        }
        String who = heroes[selected].name;
        String line2 = hintShown ? hint : "Click a card to flip it   (click a player below to switch)";
        return who + ": " + question + "\n" + line2;
    }

    private double cardCx(int i) {
        return WIDTH / 4 * (i + 0.5);
    }

    private int cardAt(double x, double y) {
        for (int i = 0; i < 4; i++) {
            if (dealT[i] < 1) continue;
            if (Math.abs(x - cardCx(i)) <= CARD_W / 2 && y >= CARD_Y - 10 && y <= CARD_Y + CARD_H) return i;
        }
        return -1;
    }

    private int buttonAt(double x, double y) {
        if (y < BTN_Y || y > BTN_Y + BTN_H) return -1;
        if (x >= BTN_X0 && x <= BTN_X0 + BTN_W) return 0;
        if (x >= BTN_X1 && x <= BTN_X1 + BTN_W) return 1;
        return -1;
    }

    @Override
    protected void onMouseMove(double x, double y) {
        if (state != State.PLAYING) {
            hover = -1;
            choiceHover = -1;
            return;
        }
        if (choosing) {
            choiceHover = buttonAt(x, y);
            hover = -1;
            return;
        }
        hover = cardAt(x, y);
    }

    @Override
    protected void onPlayClick(double x, double y) {
        if (choosing) {
            int b = buttonAt(x, y);
            if (b == 0) goToRealWorld();
            else if (b == 1) stayHere();
            return;
        }
        if (lostPopupT > 0) return;      // the "YOU LOST" message is showing

        int h = heroAt(heroes, x, y, HERO_BASE);
        if (h >= 0) {
            selected = h;
            picked = true;
            heroes[h].jump();
            heroes[h].say(heroes[h].cheer, 1.2);
            burst(heroX(h, heroes.length), HERO_BASE - 40, heroes[h].power, 14, 160, 200);
            return;
        }

        int i = cardAt(x, y);
        if (i < 0 || tried[i]) return;

        double cx = cardCx(i);
        double cy = CARD_Y + CARD_H / 2;
        Hero player = heroes[selected];

        if (i == correct) {
            int points = 100 + timeBonus();
            streak++;
            floatingText("+" + points, WIDTH / 2, 100, Color.LIMEGREEN);
            triggerFlash(Color.web("#22d3ee"), 0.5);
            burst(cx, cy, Color.GOLD, 30, 300, 280);
            burst(cx, cy, Color.web("#f472b6"), 20, 240, 240);
            burst(cx, cy, Color.WHITE, 12, 200, 160);
            for (int k = 0; k < heroes.length; k++) {
                heroes[k].cheerAfter(k * 0.06);
                burst(heroX(k, heroes.length), HERO_BASE - 50, heroes[k].power, 10, 200, 260);
            }
            player.say(player.cheer, 1.4);

            if (streak >= 3) {
                // door of light: let the player choose. The timer stays frozen meanwhile.
                score += points;
                choosing = true;
                choiceHover = -1;
                frozenTime = Math.max(timeLeft, 1);
                timeLeft = frozenTime;
                player.say("A door of light...", 1.6);
            } else {
                roundWon(points);
            }
        } else {
            tried[i] = true;
            jokerT[i] = 0.001;
            hintShown = true;
            streak = 0;
            lostPopupT = POPUP_TIME;
            triggerFlash(Color.web("#dc2626"), 1.0);
            burst(cx, cy, Color.web("#ef4444"), 26, 260, 200);
            for (int k = 0; k < 3; k++) {
                lasers.add(new double[]{HUD_TOP + 60 + random.nextDouble() * 380, 1.0, 0});
            }
            player.scare(SCARED[random.nextInt(SCARED.length)]);
            loseLife(5, "JOKER!", cx, CARD_Y - 12);
            frozenTime = timeLeft > 0.5 ? timeLeft : -1;
            if (lives <= 0) {
                for (Hero h2 : heroes) h2.scare("...");
            }
        }
    }

    private void goToRealWorld() {
        choosing = false;
        escaped = true;
        escapeT = 0;
        cheerTimer = 0;
        state = State.GAME_OVER;
        triggerFlash(Color.WHITE, 1.0);
        for (int k = 0; k < heroes.length; k++) {
            heroes[k].cheerAfter(k * 0.06);
            burst(heroX(k, heroes.length), HERO_BASE - 50, heroes[k].power, 14, 240, 260);
        }
    }

    private void stayHere() {
        choosing = false;
        streak = 0;
        timeLeft = frozenTime;
        frozenTime = -1;
        heroes[selected].say("We stay.", 1.3);
        floatingText("STAY IN BORDERLAND", WIDTH / 2, 300, Color.web("#f472b6"));
        roundWon(0);
    }

    // ------------------------------------------------------------ animation

    @Override
    protected void onUpdate(double dt) {
        super.onUpdate(dt);
        pulse += 4 * dt;
        updateHeroes(heroes, dt);

        // freeze the visa timer while a popup or the choice is on screen
        if (choosing) {
            timeLeft = frozenTime;
        } else if (lostPopupT > 0 && frozenTime >= 0 && state == State.PLAYING) {
            timeLeft = frozenTime;
        }
        if (lostPopupT > 0) {
            lostPopupT -= dt;
            if (lostPopupT <= 0) {
                lostPopupT = 0;
                frozenTime = -1;
            }
        }
        if (escaped) {
            escapeT += dt;
            cheerTimer -= dt;
            if (cheerTimer <= 0) {
                cheerTimer = 1.4;
                for (int k = 0; k < heroes.length; k++) heroes[k].cheerAfter(k * 0.05);
                double bxp = 100 + random.nextDouble() * (WIDTH - 200);
                burst(bxp, 200, Color.GOLD, 24, 260, 300);
                burst(bxp, 200, Color.web("#f472b6"), 18, 220, 300);
            }
        }

        for (int i = 0; i < 4; i++) {
            dealT[i] = Math.min(1, dealT[i] + 1.6 * dt);
            if (jokerT[i] > 0) jokerT[i] = Math.min(1, jokerT[i] + 2.4 * dt);
            double target = (state == State.PLAYING && i == hover && !tried[i]) ? 1 : 0;
            lift[i] += (target - lift[i]) * Math.min(1, 12 * dt);
        }
        for (Iterator<double[]> it = lasers.iterator(); it.hasNext(); ) {
            double[] l = it.next();
            l[1] -= 1.5 * dt;
            l[2] = Math.min(1, l[2] + 5 * dt);
            if (l[1] <= 0) it.remove();
        }
        for (int i = 0; i < fallX.length; i++) {
            fallY[i] += fallV[i] * dt;
            fallRot[i] += fallSpin[i] * dt;
            if (fallY[i] > HEIGHT - HUD_BOTTOM) {
                fallY[i] = HUD_TOP;
                fallX[i] = random.nextDouble() * WIDTH;
            }
        }
    }

    // ------------------------------------------------------------ drawing

    @Override
    protected void drawLifeIcon(GraphicsContext gc, double x, double y, boolean alive) {
        // a tiny heart playing card
        gc.setFill(alive ? Color.WHITE : Color.web("#374151"));
        gc.fillRoundRect(x + 3, y, 18, 24, 4, 4);
        gc.setFill(alive ? Color.web("#dc2626") : Color.web("#1f2937"));
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("♥", x + 12, y + 18);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private boolean redSuit(int s) {
        return s == 1 || s == 2;
    }

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        double top = HUD_TOP;
        double bottom = HEIGHT - HUD_BOTTOM;

        // neon night sky + empty Tokyo skyline
        gc.setFill(Color.web("#070a18"));
        gc.fillRect(0, top, WIDTH, bottom - top);
        gc.setGlobalAlpha(0.35);
        gc.setFill(Color.web("#3b0764"));
        gc.fillRect(0, top + 150, WIDTH, bottom - top - 150);
        gc.setGlobalAlpha(1.0);
        for (int i = 0; i < bx.length; i++) {
            double by = bottom - bh[i];
            gc.setFill(Color.web("#0b1226"));
            gc.fillRect(bx[i], by, bw[i], bh[i]);
            for (int wy = 0; wy < bh[i] - 12; wy += 14) {
                for (int wx = 5; wx < bw[i] - 8; wx += 12) {
                    double tw = Math.sin(time * 0.8 + i * 3.1 + wx + wy * 0.7);
                    if (tw > 0.55) {
                        gc.setFill(tw > 0.9 ? Color.web("#f472b6") : Color.web("#22d3ee"));
                        gc.setGlobalAlpha(0.7);
                        gc.fillRect(bx[i] + wx, by + 8 + wy, 4, 6);
                    }
                }
            }
            gc.setGlobalAlpha(1.0);
        }
        // neon horizon line
        gc.setGlobalAlpha(0.5 + 0.2 * Math.sin(time * 2));
        gc.setStroke(Color.web("#f472b6"));
        gc.setLineWidth(2);
        gc.strokeLine(0, bottom - 1, WIDTH, bottom - 1);
        gc.setGlobalAlpha(1.0);

        // drifting playing cards
        for (int i = 0; i < fallX.length; i++) {
            gc.save();
            gc.translate(fallX[i], fallY[i]);
            gc.rotate(fallRot[i]);
            gc.setGlobalAlpha(0.35);
            gc.setFill(Color.WHITE);
            gc.fillRoundRect(-7, -10, 14, 20, 3, 3);
            gc.setGlobalAlpha(0.7);
            gc.setFill(redSuit(i % 4) ? Color.web("#dc2626") : Color.web("#111827"));
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 11));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(SUITS[i % 4], 0, 4);
            gc.restore();
        }
        gc.setGlobalAlpha(1.0);

        drawLives(gc);

        // visa countdown
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
        gc.setFill(timeLeft > 10 ? Color.web("#fda4af") : Color.RED);
        gc.fillText("VISA: " + (int) Math.ceil(timeLeft) + " DAYS", WIDTH - 20, top + 54);

        // round title with the suit of this round
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 22));
        gc.setFill(redSuit(suit) ? Color.web("#f87171") : Color.web("#e5e7eb"));
        gc.fillText(SUITS[suit] + " " + SUIT_NAMES[suit] + " GAME: " + roundTitle, WIDTH / 2, top + 54);

        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 30));
        gc.setFill(Color.WHITE);
        gc.fillText(polyText, WIDTH / 2, 162);

        if (!escaped) {
            for (int i = 0; i < 4; i++) drawCard(gc, i);
        }

        for (double[] l : lasers) drawLaser(gc, l);

        drawStreak(gc, top);
        if (escaped) drawEscape(gc, top, bottom);

        if (state == State.PLAYING && !picked && !choosing) {
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            gc.setFill(Color.web("#fde047"));
            gc.setGlobalAlpha(0.6 + 0.4 * Math.sin(time * 4));
            gc.fillText("Click a player to choose who plays the game!", WIDTH / 2, 420);
            gc.setGlobalAlpha(1.0);
        }

        drawHeroes(gc, heroes, HERO_BASE, 1.1, selected);
        drawParticles(gc);
        if (choosing) drawChoice(gc, top, bottom);
        drawLostPopup(gc, top, bottom);
        drawFlash(gc);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawCard(GraphicsContext gc, int i) {
        if (dealT[i] <= 0) return;
        double e = ease(dealT[i]);
        double cx = WIDTH / 2 + (cardCx(i) - WIDTH / 2) * e;
        double cy = (HUD_TOP + 60) + (CARD_Y + CARD_H / 2 - (HUD_TOP + 60)) * e - 10 * lift[i];

        // which side is showing, and how squashed it is
        int kind;     // 0 back, 1 face, 2 joker
        double sx;
        if (dealT[i] < 1) {
            kind = dealT[i] < 0.5 ? 0 : 1;
            sx = Math.abs(1 - 2 * dealT[i]);
        } else if (jokerT[i] > 0) {
            kind = jokerT[i] < 0.5 ? 1 : 2;
            sx = Math.abs(1 - 2 * jokerT[i]);
        } else {
            kind = 1;
            sx = 1;
        }
        sx = Math.max(0.02, sx);

        boolean fadeOthers = state != State.PLAYING && i != correct && kind == 1;

        gc.save();
        gc.translate(cx, cy);
        gc.scale(sx, 1);
        gc.setGlobalAlpha(fadeOthers ? 0.45 : 1.0);

        // glow
        Color glow = null;
        if (state == State.ROUND_WON && i == correct) glow = Color.LIMEGREEN;
        else if (state == State.GAME_OVER && i == correct) glow = Color.GOLD;
        else if (kind == 1 && lift[i] > 0.05) glow = Color.GOLD;
        else if (kind == 2) glow = Color.RED;
        if (glow != null) {
            double pad = 10 + 4 * Math.sin(pulse) * (state == State.PLAYING ? 0 : 1);
            gc.setGlobalAlpha(0.3 + 0.2 * lift[i]);
            gc.setFill(glow);
            gc.fillRoundRect(-CARD_W / 2 - pad, -CARD_H / 2 - pad, CARD_W + 2 * pad, CARD_H + 2 * pad, 24, 24);
            gc.setGlobalAlpha(fadeOthers ? 0.45 : 1.0);
        }

        if (kind == 0) {
            gc.setFill(Color.web("#7f1d1d"));
            gc.fillRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(3);
            gc.strokeRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
            gc.setStroke(Color.web("#fda4af"));
            gc.setLineWidth(1.5);
            gc.strokeRoundRect(-CARD_W / 2 + 12, -CARD_H / 2 + 12, CARD_W - 24, CARD_H - 24, 8, 8);
            gc.strokeLine(-CARD_W / 2 + 12, -CARD_H / 2 + 12, CARD_W / 2 - 12, CARD_H / 2 - 12);
            gc.strokeLine(CARD_W / 2 - 12, -CARD_H / 2 + 12, -CARD_W / 2 + 12, CARD_H / 2 - 12);
        } else if (kind == 1) {
            int s = i;                       // each slot always shows its own suit
            Color suitColor = redSuit(s) ? Color.web("#dc2626") : Color.web("#111827");
            gc.setFill(Color.web("#f8fafc"));
            gc.fillRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
            gc.setStroke(Color.web("#94a3b8"));
            gc.setLineWidth(2);
            gc.strokeRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
            gc.setFill(suitColor);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 26));
            gc.fillText(SUITS[s], -CARD_W / 2 + 20, -CARD_H / 2 + 34);
            gc.fillText(SUITS[s], CARD_W / 2 - 20, CARD_H / 2 - 12);
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, optionLabels[i].length() > 6 ? 30 : 42));
            gc.fillText(optionLabels[i], 0, 14);
        } else {
            gc.setFill(Color.web("#111827"));
            gc.fillRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
            gc.setStroke(Color.web("#ef4444"));
            gc.setLineWidth(4);
            gc.strokeRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
            gc.strokeLine(-35, -45, 35, 25);
            gc.strokeLine(35, -45, -35, 25);
            gc.setFill(Color.web("#ef4444"));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 24));
            gc.fillText("JOKER", 0, 62);
        }
        gc.setGlobalAlpha(1.0);
        gc.restore();
    }

    private void drawLaser(GraphicsContext gc, double[] l) {
        double life = Math.max(0, l[1]);
        double len = WIDTH * l[2];
        gc.setGlobalAlpha(Math.min(1, life * 1.5));
        gc.setStroke(Color.web("#ef4444"));
        gc.setLineWidth(12);
        gc.strokeLine(0, l[0], len, l[0]);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeLine(0, l[0], len, l[0]);
        gc.setGlobalAlpha(1.0);
    }

    // ------------------------------------------------------------ new overlays

    private void drawStreak(GraphicsContext gc, double top) {
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
        gc.setFill(Color.web("#fda4af"));
        gc.fillText("STREAK", 20, top + 62);
        for (int i = 0; i < 3; i++) {
            gc.setFill(i < streak ? Color.GOLD : Color.web("#374151"));
            gc.fillOval(92 + i * 20, top + 50, 14, 14);
        }
    }

    private void drawBackFace(GraphicsContext gc) {
        gc.setFill(Color.web("#7f1d1d"));
        gc.fillRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
        gc.setStroke(Color.web("#fda4af"));
        gc.setLineWidth(1.5);
        gc.strokeRoundRect(-CARD_W / 2 + 12, -CARD_H / 2 + 12, CARD_W - 24, CARD_H - 24, 8, 8);
    }

    private void drawJokerFace(GraphicsContext gc) {
        gc.setFill(Color.web("#111827"));
        gc.fillRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
        gc.setStroke(Color.web("#ef4444"));
        gc.setLineWidth(4);
        gc.strokeRoundRect(-CARD_W / 2, -CARD_H / 2, CARD_W, CARD_H, 14, 14);
        gc.strokeLine(-35, -45, 35, 25);
        gc.strokeLine(35, -45, -35, 25);
        gc.setFill(Color.web("#ef4444"));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 24));
        gc.fillText("JOKER", 0, 62);
    }

    /** "YOU LOST!" with a big Joker card that flips in. */
    private void drawLostPopup(GraphicsContext gc, double top, double bottom) {
        if (lostPopupT <= 0) return;
        double age = POPUP_TIME - lostPopupT;
        double fade = Math.min(1, Math.min(age * 6, lostPopupT * 3));

        gc.setGlobalAlpha(0.65 * fade);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, top, WIDTH, bottom - top);

        double u = Math.min(1, age / 0.45);
        double sx = Math.max(0.02, Math.abs(1 - 2 * u));
        double grow = 1.0 + 0.5 * ease(age / 0.45);
        gc.setGlobalAlpha(fade);
        gc.save();
        gc.translate(WIDTH / 2, 235);
        gc.scale(sx * grow, grow);
        if (u < 0.5) drawBackFace(gc);
        else drawJokerFace(gc);
        gc.restore();

        double shake = age < 0.8 ? Math.sin(age * 80) * 5 : 0;
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 58));
        gc.setFill(Color.BLACK);
        gc.fillText("YOU LOST!", WIDTH / 2 + shake + 3, 425 + 3);
        gc.setFill(Color.web("#ef4444"));
        gc.fillText("YOU LOST!", WIDTH / 2 + shake, 425);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.WHITE);
        gc.fillText(lives > 0 ? "The Joker took a life - " + lives + " left" : "No lives left - the game is over", WIDTH / 2, 462);
        gc.setGlobalAlpha(1.0);
    }

    /** The door of light after 3 correct games in a row. */
    private void drawChoice(GraphicsContext gc, double top, double bottom) {
        gc.setGlobalAlpha(0.72);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, top, WIDTH, bottom - top);
        gc.setGlobalAlpha(1.0);

        gc.setFill(Color.web("#111827"));
        gc.fillRoundRect(130, 140, 640, 280, 24, 24);
        gc.setStroke(Color.web("#f472b6"));
        gc.setLineWidth(3);
        gc.strokeRoundRect(130, 140, 640, 280, 24, 24);

        // glowing door
        double glow = 0.5 + 0.3 * Math.sin(time * 3);
        gc.setGlobalAlpha(glow * 0.5);
        gc.setFill(Color.web("#fde68a"));
        gc.fillRoundRect(WIDTH / 2 - 52, 166, 104, 118, 16, 16);
        gc.setGlobalAlpha(0.9);
        gc.fillRoundRect(WIDTH / 2 - 30, 180, 60, 94, 10, 10);
        gc.setGlobalAlpha(1.0);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.web("#fde68a"));
        gc.fillText("3 GAMES CLEARED IN A ROW!  What will you do?", WIDTH / 2, 160 + 0);

        drawChoiceButton(gc, BTN_X0, "RETURN TO THE REAL WORLD", "#16a34a", "#22c55e", choiceHover == 0);
        drawChoiceButton(gc, BTN_X1, "STAY IN BORDERLAND", "#be185d", "#ec4899", choiceHover == 1);
    }

    private void drawChoiceButton(GraphicsContext gc, double x, String text, String base, String hot, boolean hovered) {
        double lift = hovered ? 4 : 0;
        gc.setFill(Color.web(hovered ? hot : base));
        gc.fillRoundRect(x - lift, BTN_Y - lift, BTN_W + 2 * lift, BTN_H + 2 * lift, 16, 16);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(hovered ? 4 : 2);
        gc.strokeRoundRect(x - lift, BTN_Y - lift, BTN_W + 2 * lift, BTN_H + 2 * lift, 16, 16);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 15));
        gc.fillText(text, x + BTN_W / 2, BTN_Y + BTN_H / 2 + 5);
    }

    /** Sunrise over the real world after choosing to leave. */
    private void drawEscape(GraphicsContext gc, double top, double bottom) {
        double a = ease(escapeT / 1.5);
        gc.setGlobalAlpha(a);
        gc.setFill(Color.web("#fde68a"));
        gc.fillRect(0, top, WIDTH, bottom - top);
        gc.setGlobalAlpha(a * 0.8);
        gc.setFill(Color.web("#fb7185"));
        gc.fillRect(0, top + (bottom - top) * 0.62, WIDTH, (bottom - top) * 0.38);
        gc.setGlobalAlpha(a);
        gc.setFill(Color.web("#f97316"));
        double sunY = bottom - 70 - 200 * a;
        gc.fillOval(WIDTH / 2 - 85, sunY - 85, 170, 170);
        if (a > 0.5) {
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 34));
            gc.setFill(Color.web("#14532d"));
            gc.fillText("YOU RETURNED TO THE REAL WORLD!", WIDTH / 2, top + 100);
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 22));
            gc.fillText("Final score: " + score, WIDTH / 2, top + 140);
        }
        gc.setGlobalAlpha(1.0);
    }
}
