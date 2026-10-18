package class9.games;

import common.gui.games.ArcadeGame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Shared base for the Class 9 themed games.
 * Adds on top of the common ArcadeGame (nothing in common/ is changed):
 *  - 3 lives (a wrong answer costs a life; game over when lives or time run out)
 *  - particle effects, screen flash, animation clock
 *  - a cartoon "Hero" character that can be drawn, animated (jump / shake / speech bubble)
 */
public abstract class LivesGame extends ArcadeGame {

    protected static final int MAX_LIVES = 3;

    protected int lives = MAX_LIVES;
    protected double time;                       // seconds, drives all idle animations
    protected String livesLabel = "LIVES";
    protected String tagText = "";
    protected Color tagColor = Color.WHITE;

    private boolean needsReset = false;
    private double flash = 0;
    private Color flashColor = Color.RED;
    private final List<Particle> particles = new ArrayList<>();

    // ------------------------------------------------------------ lives

    /** Call at the very start of startRound(): refills lives after a game over. Returns true if the game was reset. */
    protected boolean resetLivesIfNeeded() {
        if (needsReset || lives <= 0) {
            lives = MAX_LIVES;
            needsReset = false;
            return true;
        }
        return false;
    }

    /** Wrong answer: lose one life (plus the usual time penalty + shake). */
    protected void loseLife(double timePenalty, String message, double x, double y) {
        lives--;
        wrongAnswer(timePenalty, message, x, y);
        if (lives <= 0) {
            lives = 0;
            state = State.GAME_OVER;
        }
    }

    @Override
    protected String lostMessage() {
        return lives <= 0 ? "OUT OF LIVES! CLICK TO RETRY" : "TIME'S UP! CLICK TO RETRY";
    }

    @Override
    protected void onUpdate(double dt) {
        time += dt;
        if (state == State.GAME_OVER) needsReset = true;
        if (flash > 0) flash = Math.max(0, flash - 2.2 * dt);

        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.vy += p.gravity * dt;
            p.x += p.vx * dt;
            p.y += p.vy * dt;
            p.life -= dt;
            if (p.life <= 0) it.remove();
        }
    }

    /** Subclasses draw their own life icon (waffle, heart card, ...). x,y = top-left of a 24x24 box. */
    protected void drawLifeIcon(GraphicsContext gc, double x, double y, boolean alive) {
        gc.setFill(alive ? Color.web("#ef4444") : Color.web("#374151"));
        gc.fillOval(x, y, 24, 24);
    }

    protected void drawLives(GraphicsContext gc) {
        double y = HUD_TOP + 12;
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        gc.setFill(Color.WHITE);
        gc.fillText(livesLabel, 20, y + 17);
        double startX = 20 + livesLabel.length() * 10 + 12;
        for (int i = 0; i < MAX_LIVES; i++) {
            drawLifeIcon(gc, startX + i * 34, y, i < lives);
        }
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.setFill(tagColor);
        gc.fillText(tagText, WIDTH - 20, y + 17);
        gc.setTextAlign(TextAlignment.LEFT);
    }

    // ------------------------------------------------------------ effects

    protected void triggerFlash(Color color, double strength) {
        flashColor = color;
        flash = strength;
    }

    protected void drawFlash(GraphicsContext gc) {
        if (flash <= 0) return;
        gc.setGlobalAlpha(Math.min(0.55, flash * 0.55));
        gc.setFill(flashColor);
        gc.fillRect(0, HUD_TOP, WIDTH, HEIGHT - HUD_TOP - HUD_BOTTOM);
        gc.setGlobalAlpha(1.0);
    }

    protected void burst(double x, double y, Color color, int count, double speed, double gravity) {
        for (int i = 0; i < count; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double v = speed * (0.3 + random.nextDouble() * 0.7);
            Particle p = new Particle();
            p.x = x;
            p.y = y;
            p.vx = Math.cos(angle) * v;
            p.vy = Math.sin(angle) * v - speed * 0.2;
            p.gravity = gravity;
            p.max = 0.6 + random.nextDouble() * 0.7;
            p.life = p.max;
            p.size = 3 + random.nextDouble() * 5;
            p.color = color;
            particles.add(p);
        }
    }

    protected void drawParticles(GraphicsContext gc) {
        for (Particle p : particles) {
            gc.setGlobalAlpha(Math.max(0, Math.min(1, p.life / p.max)));
            gc.setFill(p.color);
            gc.fillOval(p.x - p.size / 2, p.y - p.size / 2, p.size, p.size);
        }
        gc.setGlobalAlpha(1.0);
    }

    protected static double ease(double t) {
        double c = Math.max(0, Math.min(1, t));
        return 1 - Math.pow(1 - c, 3);
    }

    private static final class Particle {
        double x, y, vx, vy, gravity, life, max, size;
        Color color;
    }

    // ------------------------------------------------------------ heroes

    /** A cartoon character: drawn from simple shapes, animated by jump / shake / speech bubble. */
    protected static final class Hero {
        final String name;
        final String cheer;
        final Color skin, hair, shirt, power;
        final int style;     // 0 short hair, 1 long hair, 2 big/curly hair, 3 cap/hat, 4 headband

        double jumpH, jumpV, shake, bubbleTime, scaredTime, delay;
        boolean cheerPending;
        String bubble = "";

        Hero(String name, String skin, String hair, String shirt, String power, int style, String cheer) {
            this.name = name;
            this.skin = Color.web(skin);
            this.hair = Color.web(hair);
            this.shirt = Color.web(shirt);
            this.power = Color.web(power);
            this.style = style;
            this.cheer = cheer;
        }

        void jump() {
            jumpV = 330;
        }

        void cheerAfter(double seconds) {
            delay = seconds;
            cheerPending = true;
        }

        void say(String text, double seconds) {
            bubble = text;
            bubbleTime = seconds;
        }

        void scare(String text) {
            scaredTime = 1.1;
            shake = 1;
            say(text, 1.1);
        }

        void update(double dt) {
            if (cheerPending) {
                delay -= dt;
                if (delay <= 0) {
                    cheerPending = false;
                    jump();
                }
            }
            if (jumpV != 0 || jumpH > 0) {
                jumpH += jumpV * dt;
                jumpV -= 900 * dt;
                if (jumpH <= 0) {
                    jumpH = 0;
                    jumpV = 0;
                }
            }
            if (shake > 0) shake = Math.max(0, shake - 1.6 * dt);
            if (bubbleTime > 0) bubbleTime -= dt;
            if (scaredTime > 0) scaredTime -= dt;
        }
    }

    protected void updateHeroes(Hero[] heroes, double dt) {
        for (Hero h : heroes) h.update(dt);
    }

    protected double heroX(int i, int n) {
        return WIDTH / n * (i + 0.5);
    }

    /** Returns the index of the hero under (x,y), or -1. Heroes stand on baseY. */
    protected int heroAt(Hero[] heroes, double x, double y, double baseY) {
        int n = heroes.length;
        double half = WIDTH / n / 2;
        for (int i = 0; i < n; i++) {
            if (Math.abs(x - heroX(i, n)) <= half && y >= baseY - 95 && y <= baseY + 18) return i;
        }
        return -1;
    }

    protected void drawHeroes(GraphicsContext gc, Hero[] heroes, double baseY, double scale, int selected) {
        int n = heroes.length;
        for (int i = 0; i < n; i++) {
            drawHero(gc, heroes[i], heroX(i, n), baseY, scale, i == selected);
        }
    }

    protected void drawHero(GraphicsContext gc, Hero h, double cx, double baseY, double s, boolean selected) {
        double shakeX = h.shake > 0 ? Math.sin(time * 70) * 5 * h.shake : 0;
        double x = cx + shakeX;
        double bob = selected ? Math.sin(time * 4) * 2 : 0;
        double by = baseY - h.jumpH + bob;
        boolean jumping = h.jumpH > 0;
        boolean scared = h.scaredTime > 0;

        // floor shadow / selection glow
        gc.setGlobalAlpha(selected ? 0.5 : 0.25);
        gc.setFill(selected ? Color.GOLD : Color.BLACK);
        gc.fillOval(cx - 22 * s, baseY - 4 * s, 44 * s, 9 * s);
        gc.setGlobalAlpha(1.0);

        double headCy = by - 34 * s - 13 * s;

        // hair that sits behind the head
        gc.setFill(h.hair);
        if (h.style == 2) gc.fillOval(x - 19 * s, headCy - 21 * s, 38 * s, 33 * s);
        if (h.style == 1) gc.fillRoundRect(x - 16 * s, headCy - 13 * s, 32 * s, 36 * s, 12 * s, 12 * s);

        // arms + body
        gc.setFill(h.shirt);
        double armLift = jumping ? 12 * s : 0;
        gc.fillOval(x - 22 * s, by - 32 * s - armLift, 8 * s, 18 * s);
        gc.fillOval(x + 14 * s, by - 32 * s - armLift, 8 * s, 18 * s);
        gc.fillRoundRect(x - 15 * s, by - 34 * s, 30 * s, 34 * s, 8 * s, 8 * s);

        // head
        gc.setFill(h.skin);
        gc.fillOval(x - 14 * s, headCy - 14 * s, 28 * s, 28 * s);

        // fringe / hat / band
        gc.setFill(h.hair);
        gc.fillOval(x - 15 * s, headCy - 17 * s, 30 * s, 13 * s);
        if (h.style == 3) {
            gc.setFill(h.shirt);
            gc.fillRoundRect(x - 15 * s, headCy - 19 * s, 30 * s, 11 * s, 8 * s, 8 * s);
            gc.fillRoundRect(x + 2 * s, headCy - 10 * s, 17 * s, 4 * s, 3 * s, 3 * s);
        }
        if (h.style == 4) {
            gc.setFill(h.shirt);
            gc.fillRect(x - 14 * s, headCy - 10 * s, 28 * s, 5 * s);
        }

        // eyes
        if (scared) {
            gc.setFill(Color.WHITE);
            gc.fillOval(x - 10 * s, headCy - 6 * s, 8 * s, 8 * s);
            gc.fillOval(x + 2 * s, headCy - 6 * s, 8 * s, 8 * s);
            gc.setFill(Color.BLACK);
            gc.fillOval(x - 7.5 * s, headCy - 3.5 * s, 3 * s, 3 * s);
            gc.fillOval(x + 4.5 * s, headCy - 3.5 * s, 3 * s, 3 * s);
        } else {
            gc.setFill(Color.BLACK);
            gc.fillOval(x - 8 * s, headCy - 3 * s, 4 * s, 5 * s);
            gc.fillOval(x + 4 * s, headCy - 3 * s, 4 * s, 5 * s);
        }

        // mouth
        gc.setFill(Color.web("#3b0d0d"));
        if (scared) {
            gc.fillOval(x - 3 * s, headCy + 5 * s, 6 * s, 8 * s);
        } else if (jumping || h.bubbleTime > 0) {
            gc.fillOval(x - 4 * s, headCy + 5 * s, 8 * s, 6 * s);
        } else {
            gc.setStroke(Color.web("#3b0d0d"));
            gc.setLineWidth(1.5);
            gc.strokeLine(x - 3 * s, headCy + 8 * s, x + 3 * s, headCy + 8 * s);
        }

        // name
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        gc.setFill(selected ? Color.GOLD : Color.web("#cbd5e1"));
        gc.fillText(h.name, cx, baseY + 15);

        // selection marker
        if (selected) {
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            gc.setFill(Color.GOLD);
            gc.fillText("▼", cx, headCy - 24 * s + Math.sin(time * 5) * 3);
        }

        // speech bubble
        if (h.bubbleTime > 0 && !h.bubble.isEmpty()) {
            double bw = h.bubble.length() * 7.2 + 18;
            double bx = Math.max(bw / 2 + 4, Math.min(WIDTH - bw / 2 - 4, cx));
            double byTop = headCy - 62 * s;
            gc.setGlobalAlpha(Math.min(1, h.bubbleTime * 3));
            gc.setFill(Color.WHITE);
            gc.fillRoundRect(bx - bw / 2, byTop, bw, 24, 12, 12);
            gc.setFill(Color.web("#0f172a"));
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            gc.fillText(h.bubble, bx, byTop + 16);
            gc.setGlobalAlpha(1.0);
        }
        gc.setTextAlign(TextAlignment.LEFT);
    }
}
