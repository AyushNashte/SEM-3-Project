package class7.games;

import common.gui.games.ArcadeGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TermCatcherGame extends ArcadeGame {

    private static final double TILE_W = 200;
    private static final double TILE_H = 100;
    private static final double GAP_X = 30;
    private static final double GAP_Y = 24;
    private static final int COLS = 3;

    private static class Tile {
        final String text;
        final boolean correct;
        boolean done;
        double flash;
        double x, y;

        Tile(String text, boolean correct) {
            this.text = text;
            this.correct = correct;
        }

        boolean contains(double px, double py) {
            return px >= x && px <= x + TILE_W && py >= y && py <= y + TILE_H;
        }
    }

    private final List<Tile> tiles = new ArrayList<>();
    private boolean likeMode;
    private String targetVar = "";
    private String targetText = "";
    private String expressionText = "";
    private String workingText = "";
    private int likeTotal;
    private int likeFound;
    private boolean hintShown;
    private int hoverIndex = -1;

    public TermCatcherGame() {
        roundTime = 45;
    }

    @Override
    protected String title() {
        return "TERM CATCHER";
    }

    @Override
    protected String wonMessage() {
        return "NICE CATCH! CLICK TO CONTINUE";
    }

    @Override
    protected String lostMessage() {
        return "TIME'S UP! CLICK TO RETRY";
    }

    // ------------------------------------------------------------------
    // Round setup
    // ------------------------------------------------------------------
    @Override
    protected void startRound() {
        tiles.clear();
        hintShown = false;
        likeFound = 0;
        hoverIndex = -1;
        likeMode = random.nextBoolean();

        if (likeMode) {
            buildLikeRound();
        } else {
            buildSimplifyRound();
        }
        layoutTiles();
    }

    private void buildLikeRound() {
        String[] vars = {"x", "y", "a", "b", "xy", "ab", "pq"};
        targetVar = vars[random.nextInt(vars.length)];
        targetText = fmt(randomCoef(), targetVar);
        likeTotal = 3 + random.nextInt(2);   // 3 or 4 like terms

        for (int i = 0; i < likeTotal; i++) {
            tiles.add(new Tile(fmt(randomCoef(), targetVar), true));
        }

        List<String> unlikePool = new ArrayList<>();
        for (String v : vars) {
            if (!v.equals(targetVar)) unlikePool.add(v);
        }
        unlikePool.add("");   // plain numbers (constants)

        for (int i = tiles.size(); i < 9; i++) {
            String v = unlikePool.get(random.nextInt(unlikePool.size()));
            tiles.add(new Tile(fmt(randomCoef(), v), false));
        }
        Collections.shuffle(tiles, random);

        workingText = "Like terms of " + targetText + " all contain exactly '" + targetVar + "'";
    }

    private void buildSimplifyRound() {
        String[] vars = {"x", "y", "a", "b", "p", "q"};
        String v = vars[random.nextInt(vars.length)];
        int c1 = random.nextInt(9) + 1;
        int c2 = random.nextInt(9) + 1;
        boolean minus = random.nextBoolean();
        if (minus && c1 == c2) {
            c2 = (c1 == 9) ? 8 : c1 + 1;   // avoid an answer of 0
        }

        int ans = minus ? c1 - c2 : c1 + c2;
        String op = minus ? " - " : " + ";
        String answerText = fmt(ans, v);
        expressionText = fmt(c1, v) + op + fmt(c2, v);
        workingText = expressionText + " = (" + c1 + op + c2 + ")" + v + " = " + answerText;

        List<String> wrong = new ArrayList<>();
        addWrong(wrong, fmt(ans, v + "\u00B2"), answerText);            // letter squared by mistake
        addWrong(wrong, fmt(c1 * c2, v), answerText);                   // multiplied the numbers
        int other = minus ? c1 + c2 : c1 - c2;                          // used the other operation
        addWrong(wrong, fmt(other, v), answerText);
        addWrong(wrong, String.valueOf(ans), answerText);               // dropped the letter
        addWrong(wrong, fmt(ans + 1, v), answerText);
        addWrong(wrong, fmt(ans - 1, v), answerText);
        addWrong(wrong, fmt(ans + 2, v), answerText);
        addWrong(wrong, fmt(ans + 3, v), answerText);
        Collections.shuffle(wrong, random);

        tiles.add(new Tile(answerText, true));
        for (int i = 0; i < Math.min(5, wrong.size()); i++) {
            tiles.add(new Tile(wrong.get(i), false));
        }
        Collections.shuffle(tiles, random);
    }

    private void addWrong(List<String> list, String candidate, String answerText) {
        if (!candidate.equals(answerText) && !list.contains(candidate)) {
            list.add(candidate);
        }
    }

    private int randomCoef() {
        int v = random.nextInt(9) + 1;
        return random.nextBoolean() ? v : -v;
    }

    // Builds a term like 5xy, -x, x, or a plain number when v is empty
    private String fmt(int coef, String v) {
        if (coef == 0) return "0";
        if (v.isEmpty()) return String.valueOf(coef);
        if (coef == 1) return v;
        if (coef == -1) return "-" + v;
        return coef + v;
    }

    private void layoutTiles() {
        int n = tiles.size();
        int rows = (n + COLS - 1) / COLS;
        double gridW = COLS * TILE_W + (COLS - 1) * GAP_X;
        double gridH = rows * TILE_H + (rows - 1) * GAP_Y;
        double areaTop = HUD_TOP + 70;
        double areaH = HEIGHT - HUD_BOTTOM - areaTop;
        double left = (WIDTH - gridW) / 2;
        double top = areaTop + (areaH - gridH) / 2;

        for (int i = 0; i < n; i++) {
            Tile t = tiles.get(i);
            t.x = left + (i % COLS) * (TILE_W + GAP_X);
            t.y = top + (i / COLS) * (TILE_H + GAP_Y);
        }
    }

    // ------------------------------------------------------------------
    // Prompt
    // ------------------------------------------------------------------
    @Override
    protected String prompt() {
        if (state != State.PLAYING) {
            return workingText;
        }
        if (likeMode) {
            String mission = "Click ALL terms like  " + targetText
                    + "   (" + (likeTotal - likeFound) + " left)";
            if (hintShown) {
                return mission + "\nLike terms have the SAME letters. Only the number in front can differ.";
            }
            return mission;
        }
        String mission = "Simplify:  " + expressionText + "   Click the answer";
        if (hintShown) {
            return mission + "\nAdd or subtract the numbers in front. The letter part stays the same.";
        }
        return mission;
    }

    // ------------------------------------------------------------------
    // Input
    // ------------------------------------------------------------------
    @Override
    protected void onMouseMove(double x, double y) {
        hoverIndex = -1;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).contains(x, y)) {
                hoverIndex = i;
                break;
            }
        }
    }

    @Override
    protected void onPlayClick(double x, double y) {
        Tile hit = null;
        for (Tile t : tiles) {
            if (t.contains(x, y)) {
                hit = t;
                break;
            }
        }
        if (hit == null || hit.done) return;

        if (likeMode) {
            if (hit.correct) {
                hit.done = true;
                likeFound++;
                score += 20;
                floatingText("+20", hit.x + TILE_W / 2, hit.y, Color.LIMEGREEN);
                if (likeFound == likeTotal) {
                    int points = 60 + timeBonus();
                    roundWon(points);
                    floatingText("+" + points, WIDTH / 2, HUD_TOP + 100, Color.LIMEGREEN);
                }
            } else {
                hintShown = true;
                hit.flash = 0.5;
                wrongAnswer(4, "NOT LIKE!", x, y);
            }
        } else {
            if (hit.correct) {
                hit.done = true;
                int points = 100 + timeBonus();
                roundWon(points);
                floatingText("+" + points, hit.x + TILE_W / 2, hit.y, Color.LIMEGREEN);
            } else {
                hintShown = true;
                hit.flash = 0.5;
                wrongAnswer(5, "WRONG!", x, y);
            }
        }
    }

    @Override
    protected void onUpdate(double dt) {
        for (Tile t : tiles) {
            if (t.flash > 0) t.flash = Math.max(0, t.flash - dt);
        }
    }

    // ------------------------------------------------------------------
    // Drawing
    // ------------------------------------------------------------------
    @Override
    protected void drawWorld(GraphicsContext gc, double width, double height) {
        gc.setFill(Color.web("#0f172a"));
        gc.fillRect(0, HUD_TOP, WIDTH, HEIGHT - HUD_TOP - HUD_BOTTOM);

        gc.setTextAlign(TextAlignment.CENTER);

        if (state == State.PLAYING) {
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 40));
            gc.setFill(Color.GOLD);
            String header = likeMode ? "TARGET:  " + targetText : expressionText + "  =  ?";
            gc.fillText(header, WIDTH / 2, HUD_TOP + 55);
        }

        for (int i = 0; i < tiles.size(); i++) {
            Tile t = tiles.get(i);

            Color fill = Color.web("#1e293b");
            if (t.done) {
                fill = Color.web("#166534");
            } else if (t.flash > 0) {
                fill = Color.web("#991b1b");
            } else if (state == State.PLAYING && i == hoverIndex) {
                fill = Color.web("#1e3a8a");
            }
            gc.setFill(fill);
            gc.fillRoundRect(t.x, t.y, TILE_W, TILE_H, 18, 18);

            boolean reveal = state != State.PLAYING && t.correct;
            gc.setStroke(reveal ? Color.GOLD : Color.web("#38bdf8"));
            gc.setLineWidth(reveal ? 4 : 2);
            gc.strokeRoundRect(t.x, t.y, TILE_W, TILE_H, 18, 18);

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 34));
            gc.fillText(t.text, t.x + TILE_W / 2, t.y + TILE_H / 2 + 12);
        }

        gc.setTextAlign(TextAlignment.LEFT);
    }
}
