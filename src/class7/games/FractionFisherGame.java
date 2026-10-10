package class7.games;

import common.gui.games.ArcadeGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class FractionFisherGame extends ArcadeGame {

    private static final double LEFT = 50;
    private static final double RIGHT = WIDTH - 50;
    private static final double LINE_Y = HUD_TOP + (HEIGHT - HUD_TOP - HUD_BOTTOM) / 2 + 20;

    private int parts;          // number of equal parts on the ruler (12 or 20)
    private int answerTick;     // 0..parts
    private String targetText;
    private String workingText;

    private int hoverTick;
    private boolean hoverValid;
    private boolean hintShown;
    private double pulse;

    public FractionFisherGame() {
        roundTime = 40;
    }

    @Override
    protected String title() {
        return "FRACTION FISHER";
    }

    @Override
    protected String wonMessage() {
        return "FISH HOOKED! CLICK TO CONTINUE";
    }

    @Override
    protected String lostMessage() {
        return "TIME'S UP! CLICK TO RETRY";
    }

    @Override
    protected void startRound() {
        hintShown = false;
        int mode = random.nextInt(3);

        if (mode == 0) {
            // fraction on a 12-part ruler
            parts = 12;
            int[] dens = {2, 3, 4, 6, 12};
            int d = dens[random.nextInt(dens.length)];
            int n = random.nextInt(d - 1) + 1;
            answerTick = n * (12 / d);
            targetText = n + "/" + d;
            workingText = targetText + " = " + answerTick + "/12  ->  tick " + answerTick;
        } else if (mode == 1) {
            // decimal on a 20-part ruler
            parts = 20;
            answerTick = random.nextInt(19) + 1;
            int hundredths = answerTick * 5;
            targetText = "0." + String.format("%02d", hundredths);
            workingText = targetText + " = " + hundredths + "/100 = " + answerTick + "/20  ->  tick " + answerTick;
        } else {
            // fraction on a 20-part ruler
            parts = 20;
            int[] dens = {2, 4, 5, 10, 20};
            int d = dens[random.nextInt(dens.length)];
            int n = random.nextInt(d - 1) + 1;
            answerTick = n * (20 / d);
            targetText = n + "/" + d;
            workingText = targetText + " = " + answerTick + "/20  ->  tick " + answerTick;
        }
    }

    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            return workingText;
        }
        String mission = "Click " + targetText + " on the ruler (0 to 1, " + parts + " equal parts)";
        if (hintShown) {
            return mission + "\nHint: rewrite it as a fraction with denominator " + parts;
        }
        return mission;
    }

    private double tickX(int tick) {
        return LEFT + (RIGHT - LEFT) * tick / parts;
    }

    private int toTick(double x) {
        return (int) Math.round((x - LEFT) / (RIGHT - LEFT) * parts);
    }

    private boolean inPlayArea(double y) {
        return y > HUD_TOP && y < HEIGHT - HUD_BOTTOM;
    }

    @Override
    protected void onMouseMove(double x, double y) {
        if (state != State.PLAYING) return;
        hoverTick = toTick(x);
        hoverValid = hoverTick >= 0 && hoverTick <= parts && inPlayArea(y);
    }

    @Override
    protected void onPlayClick(double x, double y) {
        if (!inPlayArea(y)) return;
        int tick = toTick(x);
        if (tick < 0 || tick > parts) return;

        if (tick == answerTick) {
            int points = 100 + timeBonus();
            roundWon(points);
            floatingText("+" + points, tickX(answerTick), LINE_Y - 90, Color.LIMEGREEN);
        } else {
            hintShown = true;
            wrongAnswer(5, "WRONG SPOT!", x, y);
        }
    }

    @Override
    protected void onUpdate(double dt) {
        pulse += 4 * dt;
    }

    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        // water
        gc.setFill(Color.web("#082f49"));
        gc.fillRect(0, HUD_TOP, WIDTH, HEIGHT - HUD_TOP - HUD_BOTTOM);
        gc.setFill(Color.rgb(56, 189, 248, 0.10));
        gc.fillRect(0, LINE_Y + 20, WIDTH, HEIGHT - HUD_BOTTOM - LINE_Y - 20);

        // ruler plank
        gc.setFill(Color.web("#b45309"));
        gc.fillRoundRect(LEFT - 15, LINE_Y - 8, (RIGHT - LEFT) + 30, 16, 8, 8);

        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        gc.setTextAlign(TextAlignment.CENTER);

        for (int i = 0; i <= parts; i++) {
            double x = tickX(i);
            boolean major = (i == 0 || i == parts || i * 2 == parts);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(major ? 3 : 2);
            gc.strokeLine(x, LINE_Y - (major ? 22 : 14), x, LINE_Y + (major ? 22 : 14));

            if (major) {
                String label = (i == 0) ? "0" : (i == parts ? "1" : "1/2");
                gc.setFill(Color.WHITE);
                gc.fillText(label, x, LINE_Y + 46);
            }
        }

        // fishing line while playing
        if (state == State.PLAYING && hoverValid) {
            double hx = tickX(hoverTick);
            gc.setStroke(Color.rgb(255, 255, 255, 0.7));
            gc.setLineWidth(1.5);
            gc.strokeLine(hx, HUD_TOP + 4, hx, LINE_Y - 24);
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(hx - 12, LINE_Y - 36, 24, 24);
            gc.setFill(Color.YELLOW);
            gc.fillText(hoverTick + "/" + parts, hx, LINE_Y - 56);
        }

        // reveal the fish
        if (state != State.PLAYING) {
            double fx = tickX(answerTick);
            drawFish(gc, fx, LINE_Y - 60);

            double ring = 14 + 4 * Math.sin(pulse);
            gc.setStroke(state == State.ROUND_WON ? Color.LIMEGREEN : Color.RED);
            gc.setLineWidth(2);
            gc.strokeOval(fx - ring, LINE_Y - ring, 2 * ring, 2 * ring);
        }

        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawFish(GraphicsContext gc, double cx, double cy) {
        gc.setFill(Color.ORANGE);
        gc.fillOval(cx - 22, cy - 12, 44, 24);
        gc.fillPolygon(new double[]{cx + 20, cx + 36, cx + 36},
                new double[]{cy, cy - 12, cy + 12}, 3);
        gc.setFill(Color.WHITE);
        gc.fillOval(cx - 15, cy - 6, 7, 7);
        gc.setFill(Color.BLACK);
        gc.fillOval(cx - 13, cy - 4, 3, 3);
    }
}