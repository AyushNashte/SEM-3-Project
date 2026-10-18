package class7.games;

import common.gui.games.ArcadeGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class IntegerHopperGame extends ArcadeGame {

    private static final int RANGE = 10;
    private static final int SCALE = 40;
    private static final double ORIGIN_X = WIDTH / 2;
    private static final double LINE_Y = HUD_TOP + (HEIGHT - HUD_TOP - HUD_BOTTOM) / 2;

    private int start, operand, answer;
    private boolean subtract;

    private int hoverN;
    private boolean hoverValid;
    private boolean hintShown;

    private double pulse;
    private double reveal;

    public IntegerHopperGame() {
        roundTime = 40;
    }

    @Override
    protected String title() {
        return "INTEGER HOPPER";
    }

    @Override
    protected String wonMessage() {
        return "NICE HOP! CLICK TO CONTINUE";
    }

    @Override
    protected String lostMessage() {
        return "TIME'S UP! CLICK TO RETRY";
    }

    @Override
    protected void startRound() {
        hintShown = false;
        reveal = 0;
        while (true) {
            start = random.nextInt(19) - 9;      // -9..9
            operand = random.nextInt(19) - 9;    // -9..9
            if (operand == 0) continue;
            subtract = random.nextBoolean();
            answer = subtract ? start - operand : start + operand;
            if (Math.abs(answer) > RANGE) continue;
            return;
        }
    }

    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            return solutionText();
        }
        String expr = p(start) + (subtract ? " - " : " + ") + p(operand);
        String mission = "Frog hops:  " + expr + "   Click where it lands";
        if (hintShown) {
            String rule = subtract
                    ? "Subtracting = adding the opposite:  a - b = a + (-b)"
                    : "Add a positive: hop RIGHT.  Add a negative: hop LEFT.";
            return mission + "\n" + rule;
        }
        return mission;
    }

    private String solutionText() {
        if (subtract) {
            return String.format("%s - %s = %s + %s = %d",
                    p(start), p(operand), p(start), p(-operand), answer);
        }
        return String.format("%s + %s = %d", p(start), p(operand), answer);
    }

    private String p(int v) {
        return v < 0 ? "(" + v + ")" : String.valueOf(v);
    }

    private double px(int n) {
        return ORIGIN_X + n * SCALE;
    }

    private int toN(double x) {
        return (int) Math.round((x - ORIGIN_X) / SCALE);
    }

    private boolean inPlayArea(double y) {
        return y > HUD_TOP && y < HEIGHT - HUD_BOTTOM;
    }

    @Override
    protected void onMouseMove(double x, double y) {
        if (state != State.PLAYING) return;
        hoverN = toN(x);
        hoverValid = Math.abs(hoverN) <= RANGE && inPlayArea(y);
    }

    @Override
    protected void onPlayClick(double x, double y) {
        if (!inPlayArea(y)) return;
        int n = toN(x);
        if (Math.abs(n) > RANGE) return;

        if (n == answer) {
            int points = 100 + timeBonus();
            roundWon(points);
            floatingText("+" + points, px(answer), LINE_Y - 90, Color.LIMEGREEN);
        } else {
            hintShown = true;
            wrongAnswer(5, n + " is WRONG!", x, y);
        }
    }

    @Override
    protected void onUpdate(double dt) {
        pulse += 4 * dt;
        if (state != State.PLAYING && reveal < 1) {
            reveal = Math.min(1, reveal + 1.2 * dt);
        }
    }

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        double top = LINE_Y - 110;
        double zoneHeight = 220;
        double zoneWidth = (RANGE + 0.5) * SCALE;

        // negative side (left) and positive side (right)
        gc.setFill(Color.rgb(239, 68, 68, 0.10));
        gc.fillRect(ORIGIN_X - zoneWidth, top, zoneWidth, zoneHeight);
        gc.setFill(Color.rgb(16, 185, 129, 0.10));
        gc.fillRect(ORIGIN_X, top, zoneWidth, zoneHeight);

        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.web("#f87171"));
        gc.fillText("NEGATIVE (left = smaller)", px(-5), LINE_Y + 85);
        gc.setFill(Color.web("#34d399"));
        gc.fillText("POSITIVE (right = greater)", px(5), LINE_Y + 85);

        // number line
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeLine(px(-RANGE) - 20, LINE_Y, px(RANGE) + 20, LINE_Y);

        for (int i = -RANGE; i <= RANGE; i++) {
            double tick = (i == 0) ? 12 : 7;
            gc.setLineWidth(i == 0 ? 3 : 2);
            gc.strokeLine(px(i), LINE_Y - tick, px(i), LINE_Y + tick);

            gc.setFill(i == 0 ? Color.WHITE : (i < 0 ? Color.web("#fca5a5") : Color.web("#86efac")));
            gc.fillText(String.valueOf(i), px(i), LINE_Y + 36);
        }

        // hover marker
        if (state == State.PLAYING && hoverValid) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(px(hoverN) - 13, LINE_Y - 13, 26, 26);
            gc.setFill(Color.YELLOW);
            gc.fillText(String.valueOf(hoverN), px(hoverN), LINE_Y - 24);
        }

        // start dot + frog
        gc.setFill(Color.MAGENTA);
        gc.fillOval(px(start) - 7, LINE_Y - 7, 14, 14);
        drawFrog(gc, px(start), LINE_Y - 50);
        gc.setFill(Color.MAGENTA);
        gc.fillText("START " + start, px(start), LINE_Y - 78);

        // reveal hop + answer
        if (state != State.PLAYING) {
            double x0 = px(start);
            double x1 = px(answer);
            int steps = 40;
            gc.setStroke(Color.GOLD);
            gc.setLineWidth(4);
            gc.beginPath();
            for (int i = 0; i <= steps * reveal; i++) {
                double t = (double) i / steps;
                double x = x0 + (x1 - x0) * t;
                double y = LINE_Y - 14 - 240 * t * (1 - t);
                if (i == 0) gc.moveTo(x, y); else gc.lineTo(x, y);
            }
            gc.stroke();

            double ring = 12 + 4 * Math.sin(pulse);
            gc.setFill(Color.GOLD);
            gc.fillOval(px(answer) - 8, LINE_Y - 8, 16, 16);
            gc.setStroke(state == State.ROUND_WON ? Color.LIMEGREEN : Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(px(answer) - ring, LINE_Y - ring, 2 * ring, 2 * ring);
        }

        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawFrog(GraphicsContext gc, double cx, double cy) {
        gc.setFill(Color.LIMEGREEN);
        gc.fillOval(cx - 18, cy - 14, 36, 28);
        gc.setFill(Color.WHITE);
        gc.fillOval(cx - 13, cy - 20, 11, 11);
        gc.fillOval(cx + 2, cy - 20, 11, 11);
        gc.setFill(Color.BLACK);
        gc.fillOval(cx - 9, cy - 17, 4, 4);
        gc.fillOval(cx + 6, cy - 17, 4, 4);
    }
}