package common.gui.book;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/** Colours, fonts and sizes for the Balbharati-style lesson screen. */
public final class BookTheme {

    private BookTheme() { }

    public static final String ACCENT = "#08A8E0";
    public static final String PALE = "#CBEFFB";
    public static final String PAGE = "#FFFFFF";
    public static final String TEXT = "#111111";
    public static final String ORANGE = "#F26522";
    public static final String DESK = "#EAF6FB";
    public static final String SERIF = "Times New Roman";

    public static final double BODY_SIZE = 17;
    public static final double MAX_PAGE_WIDTH = 940;

    public static double pageWidth(double sceneWidth) {
        return Math.max(560, Math.min(MAX_PAGE_WIDTH, sceneWidth - 80));
    }

    public static double pageWidth() {
        return MAX_PAGE_WIDTH;
    }

    public static void applyDesk(Region region) {
        region.setStyle("-fx-background-color: " + DESK + ";");
    }

    public static DropShadow pageShadow() {
        return new DropShadow(18, 0, 4, Color.rgb(0, 60, 90, 0.30));
    }

    public static String font(double size, boolean bold) {
        return "-fx-font-family: '" + SERIF + "', serif; -fx-font-size: " + size + "px;"
                + (bold ? " -fx-font-weight: bold;" : "");
    }
}