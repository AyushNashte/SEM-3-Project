import javafx.application.Application;
import javafx.stage.Stage;
import common.gui.AppLauncher;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        new AppLauncher(primaryStage).launch();
    }

    public static void main(String[] args) {
        launch(args);
    }
}