package common.gui.games;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class ArcadeGame {

    protected enum State { PLAYING, ROUND_WON, GAME_OVER }

    protected static final double WIDTH = 900;
    protected static final double HEIGHT = 620;
    protected static final double HUD_TOP = 50;
    protected static final double HUD_BOTTOM = 80;

    protected final Random random = new Random();
    protected State state = State.PLAYING;
    protected int score = 0;
    protected double roundTime = 60;
    protected double timeLeft = 60;

    private double shake = 0;
    private final List<FloatingText> floatingTexts = new ArrayList<>();
    private GraphicsContext gc;
    private AnimationTimer loop;
    private Runnable onExit;

    // ---- Each game must provide these ----
    protected abstract String title();
    protected abstract String prompt();                 // text in the bottom bar (use \n for a 2nd line)
    protected abstract void startRound();               // set up a fresh round
    protected abstract void drawWorld(GraphicsContext gc, double width, double height);
    protected abstract void onPlayClick(double x, double y);

    // ---- Optional hooks ----
    protected void onMouseMove(double x, double y) { }
    protected void onUpdate(double dt) { }
    protected String wonMessage() { return "CORRECT! CLICK TO CONTINUE"; }
    protected String lostMessage() { return "TIME UP! CLICK TO RETRY"; }

    // ---- Helpers for games ----
    protected void roundWon(int points) {
        state = State.ROUND_WON;
        score += points;
    }

    protected int timeBonus() {
        return (int) (timeLeft * 2);
    }

    protected void wrongAnswer(double timePenalty, String message, double x, double y) {
        shake = 15;
        timeLeft = Math.max(0, timeLeft - timePenalty);
        floatingText(message, x, y, Color.RED);
    }

    protected void floatingText(String text, double x, double y, Color color) {
        floatingTexts.add(new FloatingText(text, x, y, color));
    }

    // ---- Scene + loop ----
    public Scene buildScene(Runnable onExit) {
        this.onExit = onExit;

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseMoved(e -> onMouseMove(e.getX(), e.getY()));
        canvas.setOnMouseClicked(e -> handleClick(e.getX(), e.getY()));

        Button exitButton = new Button("Exit (Esc)");
        exitButton.setOnAction(e -> exit());
        StackPane.setAlignment(exitButton, Pos.TOP_RIGHT);
        StackPane.setMargin(exitButton, new Insets(10));

        StackPane root = new StackPane(canvas, exitButton);
        root.setStyle("-fx-background-color: #0a0f14;");

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) exit();
        });

        score = 0;
        floatingTexts.clear();
        beginNewRound();
        startLoop();
        return scene;
    }

    private void beginNewRound() {
        state = State.PLAYING;
        startRound();
        timeLeft = roundTime;
    }

    private void startLoop() {
        loop = new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) {
                    last = now;
                    return;
                }
                double dt = Math.min((now - last) / 1_000_000_000.0, 0.1);
                last = now;
                update(dt);
                render();
            }
        };
        loop.start();
    }

    private void exit() {
        if (loop != null) loop.stop();
        if (onExit != null) onExit.run();
    }

    private void handleClick(double x, double y) {
        switch (state) {
            case PLAYING:
                onPlayClick(x, y);
                break;
            case ROUND_WON:
                beginNewRound();
                break;
            case GAME_OVER:
                score = 0;
                beginNewRound();
                break;
        }
    }

    private void update(double dt) {
        if (shake > 0) shake = Math.max(0, shake - 40 * dt);

        Iterator<FloatingText> it = floatingTexts.iterator();
        while (it.hasNext()) {
            FloatingText t = it.next();
            t.y -= 50 * dt;
            t.life -= dt;
            if (t.life <= 0) it.remove();
        }

        if (state == State.PLAYING) {
            timeLeft -= dt;
            if (timeLeft <= 0) {
                timeLeft = 0;
                state = State.GAME_OVER;
            }
        }
        onUpdate(dt);
    }

    private void render() {
        gc.setFill(Color.web("#0a0f14"));
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.save();
        if (shake > 0) {
            gc.translate((random.nextDouble() - 0.5) * shake, (random.nextDouble() - 0.5) * shake);
        }
        drawWorld(gc, WIDTH, HEIGHT);
        gc.restore();

        drawHud();
        drawFloatingTexts();
        drawBanner();
    }

    private void drawHud() {
        gc.setFill(Color.rgb(0, 0, 0, 0.8));
        gc.fillRect(0, 0, WIDTH, HUD_TOP);
        gc.fillRect(0, HEIGHT - HUD_BOTTOM, WIDTH, HUD_BOTTOM);

        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.WHITE);
        gc.fillText("SCORE: " + score, 20, 32);

        double barWidth = 300;
        double fraction = Math.max(0, timeLeft / roundTime);
        gc.setFill(timeLeft > 10 ? Color.LIMEGREEN : Color.RED);
        gc.fillRect(200, 18, barWidth * fraction, 15);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(200, 18, barWidth, 15);
        gc.setFill(Color.WHITE);
        gc.fillText(String.format("%.1f s", timeLeft), 515, 32);

        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
        gc.setFill(Color.web("#38bdf8"));
        gc.fillText(title(), 620, 32);

        String[] lines = prompt().split("\n");
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 20));
        gc.setFill(Color.CYAN);
        gc.setTextAlign(TextAlignment.CENTER);
        double y = HEIGHT - HUD_BOTTOM / 2 - (lines.length - 1) * 14 + 7;
        for (String line : lines) {
            gc.fillText(line, WIDTH / 2, y);
            y += 28;
        }
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawFloatingTexts() {
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 22));
        gc.setTextAlign(TextAlignment.CENTER);
        for (FloatingText t : floatingTexts) {
            gc.setGlobalAlpha(Math.max(0, Math.min(1, t.life)));
            gc.setFill(t.color);
            gc.fillText(t.text, t.x, t.y);
        }
        gc.setGlobalAlpha(1.0);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private void drawBanner() {
        if (state == State.PLAYING) return;
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 26));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(state == State.ROUND_WON ? Color.LIMEGREEN : Color.RED);
        gc.fillText(state == State.ROUND_WON ? wonMessage() : lostMessage(), WIDTH / 2, 100);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    private static class FloatingText {
        final String text;
        final double x;
        double y;
        final Color color;
        double life = 1.2;

        FloatingText(String text, double x, double y, Color color) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }
}