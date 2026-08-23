package common.gui;

import common.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PrerequisiteTestScreen {
    private AppLauncher launcher;
    private Topic topic;
    private Test test;
    private List<Integer> answers;
    private int currentIndex;
    private VBox root;
    private ToggleGroup optionGroup;

    public PrerequisiteTestScreen(AppLauncher launcher, Topic topic) {
        this.launcher = launcher;
        this.topic = topic;
        this.test = topic.getPrerequisiteTestForDisplay();
        this.answers = new ArrayList<>();
        this.currentIndex = 0;
    }

    public Scene buildScene() {
        root = new VBox(15);
        root.setPadding(new Insets(20));
        showQuestion();
        return new Scene(root, 500, 400);
    }

    private void showQuestion() {
        root.getChildren().clear();

        Label header = new Label("Pre-requisite Test — Question "
                + (currentIndex + 1) + " of " + test.getQuestions().size());
        Question q = test.getQuestions().get(currentIndex);
        Label questionLabel = new Label(q.getQuestionText());
        questionLabel.setWrapText(true);

        optionGroup = new ToggleGroup();
        VBox optionsBox = new VBox(8);
        for (String option : q.getOptions()) {
            RadioButton rb = new RadioButton(option);
            rb.setToggleGroup(optionGroup);
            optionsBox.getChildren().add(rb);
        }

        Button nextButton = new Button(
                currentIndex == test.getQuestions().size() - 1 ? "Submit" : "Next"
        );
        nextButton.setOnAction(e -> handleNext());

        root.getChildren().addAll(header, questionLabel, optionsBox, nextButton);
    }

    private void handleNext() {
        Toggle selected = optionGroup.getSelectedToggle();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an answer.");
            alert.showAndWait();
            return;
        }

        RadioButton selectedButton = (RadioButton) selected;
        int index = ((VBox) selectedButton.getParent()).getChildren().indexOf(selectedButton);
        answers.add(index);

        if (currentIndex < test.getQuestions().size() - 1) {
            currentIndex++;
            showQuestion();
        } else {
            submitTest();
        }
    }

    private void submitTest() {
        TestResult result = topic.submitPrerequisiteTest(answers);
        if (result.isPerfectScore()) {
            launcher.showReport();
        }
        // else branch (go to LessonScreen) added once LessonScreen exists
    }
}