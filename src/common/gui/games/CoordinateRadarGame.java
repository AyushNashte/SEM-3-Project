package common.gui.games;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CoordinateRadarGame extends ArcadeGame {

    private static final int SCALE = 26;
    private static final int RANGE = 9;
    private static final double ORIGIN_X = WIDTH / 2;
    private static final double ORIGIN_Y = HUD_TOP + (HEIGHT - HUD_TOP - HUD_BOTTOM) / 2;
    private static final double RADAR_RADIUS = (HEIGHT - HUD_TOP - HUD_BOTTOM) / 2;

    private boolean sectionMode;
    private int ax, ay, bx, by;
    private int ansX, ansY;
    private int ratioM, ratioN;

    private int hoverX, hoverY;
    private boolean hoverValid;
    private boolean hintShown;

    private double radarAngle;
    private double pulse;
    private double reveal;

    public CoordinateRadarGame() {
        roundTime = 45;
    }

    @Override
    protected String title() {
        return "COORDINATE RADAR";
    }

    @Override
    protected String wonMessage() {
        return "TARGET LOCKED! CLICK TO CONTINUE";
    }

    @Override
    protected String lostMessage() {
        return "TIME'S UP! CLICK TO RETRY";
    }

    @Override
    protected void startRound() {
        hintShown = false;
        reveal = 0;
        sectionMode = random.nextBoolean();
        if (sectionMode) {
            generateSection();
        } else {
            generateMidpoint();
        }
    }

    private void generateMidpoint() {
        int mx = random.nextInt(11) - 5;   // -5..5
        int my = random.nextInt(11) - 5;
        int dx = randomNonZero(4);
        int dy = randomNonZero(4);
        ax = mx - dx;
        ay = my - dy;
        bx = mx + dx;
        by = my + dy;
        ansX = mx;
        ansY = my;
    }

    private void generateSection() {
        int[][] ratios = {{1, 2}, {2, 1}, {1, 3}, {3, 1}, {2, 3}, {3, 2}};
        while (true) {
            int[] ratio = ratios[random.nextInt(ratios.length)];
            ratioM = ratio[0];
            ratioN = ratio[1];
            int total = ratioM + ratioN;
            int sx = random.nextInt(5) - 2;   // -2..2
            int sy = random.nextInt(5) - 2;
            if (sx == 0 && sy == 0) continue;

            ax = random.nextInt(19) - 9;
            ay = random.nextInt(19) - 9;
            bx = ax + total * sx;
            by = ay + total * sy;
            if (Math.abs(bx) > RANGE || Math.abs(by) > RANGE) continue;

            ansX = ax + ratioM * sx;
            ansY = ay + ratioM * sy;
            return;
        }
    }

    private int randomNonZero(int max) {
        int v = random.nextInt(max) + 1;
        return random.nextBoolean() ? v : -v;
    }

    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            return solutionText();
        }
        String mission = sectionMode
                ? String.format("Click P on AB where AP : PB = %d : %d   [A(%d,%d)  B(%d,%d)]",
                ratioM, ratioN, ax, ay, bx, by)
                : String.format("Click the MIDPOINT of A(%d,%d) and B(%d,%d)", ax, ay, bx, by);
        if (hintShown) {
            String formula = sectionMode
                    ? "P = ((m*x2 + n*x1)/(m+n), (m*y2 + n*y1)/(m+n))"
                    : "M = ((x1 + x2)/2, (y1 + y2)/2)";
            return mission + "\n" + formula;
        }
        return mission;
    }

    private String solutionText() {
        if (sectionMode) {
            int t = ratioM + ratioN;
            return String.format("P = ((%d*%s + %d*%s)/%d, (%d*%s + %d*%s)/%d) = (%d, %d)",
                    ratioM, p(bx), ratioN, p(ax), t,
                    ratioM, p(by), ratioN, p(ay), t,
                    ansX, ansY);
        }
        return String.format("M = ((%s + %s)/2, (%s + %s)/2) = (%d, %d)",
                p(ax), p(bx), p(ay), p(by), ansX, ansY);
    }

    private String p(int v) {
        return v < 0 ? "(" + v + ")" : String.valueOf(v);
    }

    private double px(int gx) {
        return ORIGIN_X + gx * SCALE;
    }

    private double py(int gy) {
        return ORIGIN_Y - gy * SCALE;
    }

    private int toGridX(double x) {
        return (int) Math.round((x - ORIGIN_X) / SCALE);
    }

    private int toGridY(double y) {
        return (int) (-Math.round((y - ORIGIN_Y) / SCALE));
    }

    @Override
    protected void onMouseMove(double x, double y) {
        if (state != State.PLAYING) return;
        hoverX = toGridX(x);
        hoverY = toGridY(y);
        hoverValid = Math.abs(hoverX) <= RANGE && Math.abs(hoverY) <= RANGE;
    }

    @Override
    protected void onPlayClick(double x, double y) {
        int gx = toGridX(x);
        int gy = toGridY(y);
        if (Math.abs(gx) > RANGE || Math.abs(gy) > RANGE) return;

        if (gx == ansX && gy == ansY) {
            int points = 100 + timeBonus();
            roundWon(points);
            floatingText("+" + points, px(ansX), py(ansY) - 20, Color.LIMEGREEN);
        } else {
            hintShown = true;
            wrongAnswer(5, "(" + gx + "," + gy + ") WRONG!", x, y);
        }
    }

    @Override
    protected void onUpdate(double dt) {
        radarAngle += 1.8 * dt;
        pulse += 4 * dt;
        if (state != State.PLAYING && reveal < 1) {
            reveal = Math.min(1, reveal + 1.2 * dt);
        }
    }

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        gc.setFill(Color.rgb(0, 50, 0));
        gc.fillOval(ORIGIN_X - RADAR_RADIUS, ORIGIN_Y - RADAR_RADIUS, 2 * RADAR_RADIUS, 2 * RADAR_RADIUS);

        gc.setFill(Color.rgb(0, 255, 0, 0.16));
        gc.fillArc(ORIGIN_X - RADAR_RADIUS, ORIGIN_Y - RADAR_RADIUS, 2 * RADAR_RADIUS, 2 * RADAR_RADIUS,
                Math.toDegrees(radarAngle), 45, ArcType.ROUND);

        drawGrid(gc);

        // faint dashed segment between the two stations
        gc.setStroke(Color.rgb(255, 255, 255, 0.35));
        gc.setLineWidth(1.5);
        gc.setLineDashes(6, 6);
        gc.strokeLine(px(ax), py(ay), px(bx), py(by));
        gc.setLineDashes((double[]) null);

        drawStation(gc, ax, ay, "A", Color.CYAN);
        drawStation(gc, bx, by, "B", Color.MAGENTA);

        if (state == State.PLAYING && hoverValid) {
            double sx = px(hoverX);
            double sy = py(hoverY);
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(sx - 13, sy - 13, 26, 26);
            gc.strokeLine(sx - 22, sy, sx + 22, sy);
            gc.strokeLine(sx, sy - 22, sx, sy + 22);
            gc.setFill(Color.YELLOW);
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
            gc.fillText("(" + hoverX + ", " + hoverY + ")", sx + 15, sy - 15);
        }

        if (state != State.PLAYING) {
            double endX = px(ax) + (px(bx) - px(ax)) * reveal;
            double endY = py(ay) + (py(by) - py(ay)) * reveal;
            gc.setStroke(Color.GOLD);
            gc.setLineWidth(4);
            gc.strokeLine(px(ax), py(ay), endX, endY);

            double ring = 12 + 4 * Math.sin(pulse);
            gc.setFill(Color.GOLD);
            gc.fillOval(px(ansX) - 8, py(ansY) - 8, 16, 16);
            gc.setStroke(state == State.ROUND_WON ? Color.LIMEGREEN : Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(px(ansX) - ring, py(ansY) - ring, 2 * ring, 2 * ring);
        }
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setFont(Font.font("Arial", 10));
        for (int i = -RANGE; i <= RANGE; i++) {
            boolean axis = (i == 0);
            gc.setStroke(axis ? Color.WHITE : Color.rgb(30, 90, 50));
            gc.setLineWidth(axis ? 2 : 1);

            double vx = ORIGIN_X + i * SCALE;
            double hy = ORIGIN_Y - i * SCALE;
            gc.strokeLine(vx, ORIGIN_Y - RANGE * SCALE, vx, ORIGIN_Y + RANGE * SCALE);
            gc.strokeLine(ORIGIN_X - RANGE * SCALE, hy, ORIGIN_X + RANGE * SCALE, hy);

            if (i != 0 && i % 2 == 0) {
                gc.setFill(Color.rgb(150, 255, 150));
                gc.fillText(String.valueOf(i), vx - 5, ORIGIN_Y + 14);
                gc.fillText(String.valueOf(i), ORIGIN_X - 22, hy + 4);
            }
        }
    }

    private void drawStation(GraphicsContext gc, int gx, int gy, String label, Color color) {
        gc.setFill(color);
        gc.fillOval(px(gx) - 7, py(gy) - 7, 14, 14);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 13));
        gc.fillText(label + "(" + gx + "," + gy + ")", px(gx) + 10, py(gy) - 10);
    }
}