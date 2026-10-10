package class10.games;

import common.gui.games.ArcadeGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.HashSet;
import java.util.Set;

public class RootRadarGame extends ArcadeGame {

    private static final int X_RANGE = 10;
    private static final int X_SCALE = 42;
    private static final int Y_SCALE = 8;
    private static final double ORIGIN_X = WIDTH / 2;
    private static final double ORIGIN_Y = HUD_TOP + (HEIGHT - HUD_TOP - HUD_BOTTOM) / 2;

    private static final double BTN_X = 30;
    private static final double BTN_Y = 495;
    private static final double BTN_W = 210;
    private static final double BTN_H = 36;

    private int b, c;                       // equation is x^2 + bx + c = 0
    private boolean noRealRoots;
    private int rootA, rootB;
    private final Set<Integer> targets = new HashSet<>();
    private final Set<Integer> found = new HashSet<>();

    private int hoverX;
    private boolean hoverValid;
    private boolean hintShown;
    private double pulse;
    private double reveal;

    public RootRadarGame() {
        roundTime = 50;
    }

    @Override
    protected String title() {
        return "ROOT RADAR";
    }

    @Override
    protected String wonMessage() {
        return "ROOTS LOCATED! CLICK TO CONTINUE";
    }

    @Override
    protected String lostMessage() {
        return "TIME'S UP! CLICK TO RETRY";
    }

    @Override
    protected void startRound() {
        targets.clear();
        found.clear();
        hintShown = false;
        reveal = 0;

        int type = random.nextInt(10);
        if (type < 2) {
            generateNoRoots();
        } else if (type == 2) {
            rootA = random.nextInt(11) - 5;
            rootB = rootA;
            setFromRoots();
        } else {
            do {
                rootA = random.nextInt(13) - 6;
                rootB = random.nextInt(13) - 6;
            } while (rootA == rootB);
            if (rootA > rootB) {
                int tmp = rootA;
                rootA = rootB;
                rootB = tmp;
            }
            setFromRoots();
        }
    }

    private void setFromRoots() {
        noRealRoots = false;
        b = -(rootA + rootB);
        c = rootA * rootB;
        targets.add(rootA);
        targets.add(rootB);
    }

    private void generateNoRoots() {
        noRealRoots = true;
        b = random.nextInt(9) - 4;                       // -4..4
        c = (b * b) / 4 + 2 + random.nextInt(4);         // guarantees b^2 - 4c < 0
    }

    private int discriminant() {
        return b * b - 4 * c;
    }

    private String equation() {
        StringBuilder sb = new StringBuilder("x²");
        if (b != 0) {
            sb.append(b < 0 ? " - " : " + ")
                    .append(Math.abs(b) == 1 ? "" : String.valueOf(Math.abs(b)))
                    .append("x");
        }
        if (c != 0) {
            sb.append(c < 0 ? " - " : " + ").append(Math.abs(c));
        }
        return sb.append(" = 0").toString();
    }

    private String factor(int r) {
        if (r == 0) return "x";
        return r > 0 ? "(x - " + r + ")" : "(x + " + (-r) + ")";
    }

    private String p(int v) {
        return v < 0 ? "(" + v + ")" : String.valueOf(v);
    }

    private String hintText() {
        return noRealRoots
                ? "Hint: find D = b² - 4ac. If D < 0 there are no real roots."
                : "Hint: the two roots add up to " + (-b) + " and multiply to " + c;
    }

    private String solutionText() {
        if (noRealRoots) {
            return String.format("D = b² - 4ac = %s² - 4(1)(%s) = %d, which is below 0: NO real roots",
                    p(b), p(c), discriminant());
        }
        if (rootA == rootB) {
            return String.format("%s² = 0, so x = %d (repeated root, D = 0)", factor(rootA), rootA);
        }
        return String.format("%s%s = 0, so x = %d or x = %d", factor(rootA), factor(rootB), rootA, rootB);
    }

    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            return solutionText();
        }
        String line2 = hintShown
                ? hintText()
                : "Roots found: " + found.size() + "   (no roots? press NO REAL ROOTS)";
        return "Solve  " + equation() + "  - click each root on the x-axis\n" + line2;
    }

    private int toGridX(double x) {
        return (int) Math.round((x - ORIGIN_X) / X_SCALE);
    }

    private boolean insideButton(double x, double y) {
        return x >= BTN_X && x <= BTN_X + BTN_W && y >= BTN_Y && y <= BTN_Y + BTN_H;
    }

    private boolean insidePlayArea(double y) {
        return y >= HUD_TOP && y <= HEIGHT - HUD_BOTTOM;
    }

    @Override
    protected void onMouseMove(double x, double y) {
        if (state != State.PLAYING) return;
        hoverX = toGridX(x);
        hoverValid = Math.abs(hoverX) <= X_RANGE && insidePlayArea(y);
    }

    @Override
    protected void onPlayClick(double x, double y) {
        if (!insidePlayArea(y)) return;

        if (insideButton(x, y)) {
            if (noRealRoots) {
                win();
            } else {
                hintShown = true;
                wrongAnswer(5, "REAL ROOTS EXIST!", x, y);
            }
            return;
        }

        int gx = toGridX(x);
        if (Math.abs(gx) > X_RANGE || found.contains(gx)) return;

        if (targets.contains(gx)) {
            found.add(gx);
            floatingText("x = " + gx + " FOUND", px(gx), ORIGIN_Y - 25, Color.GOLD);
            if (found.size() == targets.size()) {
                win();
            }
        } else {
            hintShown = true;
            wrongAnswer(5, "x = " + gx + " WRONG", x, y);
        }
    }

    private void win() {
        int points = 100 + timeBonus();
        roundWon(points);
        floatingText("+" + points, WIDTH / 2, 150, Color.LIMEGREEN);
    }

    private double px(int gx) {
        return ORIGIN_X + gx * X_SCALE;
    }

    @Override
    protected void onUpdate(double dt) {
        pulse += 4 * dt;
        if (state != State.PLAYING && reveal < 1) {
            reveal = Math.min(1, reveal + 0.8 * dt);
        }
    }

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        double top = HUD_TOP;
        double bottom = HEIGHT - HUD_BOTTOM;

        gc.setFill(Color.web("#0f1b2d"));
        gc.fillRect(0, top, WIDTH, bottom - top);

        // vertical grid lines + x labels
        gc.setFont(Font.font("Arial", 11));
        gc.setTextAlign(TextAlignment.CENTER);
        for (int i = -X_RANGE; i <= X_RANGE; i++) {
            double vx = px(i);
            gc.setStroke(i == 0 ? Color.WHITE : Color.rgb(40, 70, 110));
            gc.setLineWidth(i == 0 ? 2 : 1);
            gc.strokeLine(vx, top, vx, bottom);
            gc.setFill(Color.rgb(150, 190, 255));
            gc.fillText(String.valueOf(i), vx, ORIGIN_Y + 18);
        }
        gc.setTextAlign(TextAlignment.LEFT);

        // x-axis
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeLine(0, ORIGIN_Y, WIDTH, ORIGIN_Y);

        // roots the player has found so far
        for (int r : found) {
            gc.setFill(Color.GOLD);
            gc.fillOval(px(r) - 8, ORIGIN_Y - 8, 16, 16);
        }

        // hover marker
        if (state == State.PLAYING && hoverValid) {
            double vx = px(hoverX);
            gc.setStroke(Color.RED);
            gc.setLineWidth(1.5);
            gc.setLineDashes(6, 6);
            gc.strokeLine(vx, top, vx, bottom);
            gc.setLineDashes((double[]) null);
            gc.strokeOval(vx - 10, ORIGIN_Y - 10, 20, 20);
            gc.setFill(Color.YELLOW);
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
            gc.fillText("x = " + hoverX, vx + 14, ORIGIN_Y - 16);
        }

        // NO REAL ROOTS button
        if (state == State.PLAYING) {
            gc.setFill(Color.web("#dc2626"));
            gc.fillRoundRect(BTN_X, BTN_Y, BTN_W, BTN_H, 12, 12);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText("NO REAL ROOTS", BTN_X + BTN_W / 2, BTN_Y + 23);
            gc.setTextAlign(TextAlignment.LEFT);
        }

        // reveal the parabola once the round ends
        if (state != State.PLAYING) {
            gc.setStroke(Color.CYAN);
            gc.setLineWidth(3);
            double limit = -X_RANGE + 2.0 * X_RANGE * reveal;
            double prevX = Double.NaN;
            double prevY = Double.NaN;
            for (double xv = -X_RANGE; xv <= limit; xv += 0.1) {
                double yv = xv * xv + b * xv + c;
                double sx = ORIGIN_X + xv * X_SCALE;
                double sy = ORIGIN_Y - yv * Y_SCALE;
                boolean visible = sy >= top && sy <= bottom;
                if (visible && !Double.isNaN(prevX)) {
                    gc.strokeLine(prevX, prevY, sx, sy);
                }
                if (visible) {
                    prevX = sx;
                    prevY = sy;
                } else {
                    prevX = Double.NaN;
                }
            }

            double ring = 12 + 4 * Math.sin(pulse);
            gc.setStroke(state == State.ROUND_WON ? Color.LIMEGREEN : Color.RED);
            gc.setLineWidth(2);
            for (int r : targets) {
                gc.setFill(Color.GOLD);
                gc.fillOval(px(r) - 8, ORIGIN_Y - 8, 16, 16);
                gc.strokeOval(px(r) - ring, ORIGIN_Y - ring, 2 * ring, 2 * ring);
            }
        }
    }
}