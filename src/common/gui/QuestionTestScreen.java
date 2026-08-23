package common.gui;

import common.Question;
import common.Test;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class QuestionTestScreen {
    private Test test;
    private String submitButtonLabel;
    private Consumer<List<Integer>> onSubmit;

    private List<Integer> answers;
    private int currentIndex;
    private VBox root;
    private ToggleGroup optionGroup;

    public QuestionTestScreen(Test test, String submitButtonLabel, Consumer<List<Integer>> onSubmit) {
        this.test = test;
        this.submitButtonLabel = submitButtonLabel;
        this.onSubmit = onSubmit;
        this.answers = new ArrayList<>();
        this.currentIndex = 0;
    }

    public Scene buildScene(String headerPrefix) {
        root = new VBox(15);
        root.setPadding(new Insets(20));
        showQuestion(headerPrefix);
        return new Scene(root, 500, 400);
    }

    private void showQuestion(String headerPrefix) {
        root.getChildren().clear();

        Label header = new Label(headerPrefix + " — Question "
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

        boolean isLast = currentIndex == test.getQuestions().size() - 1;
        Button nextButton = new Button(isLast ? submitButtonLabel : "Next");
        nextButton.setOnAction(e -> handleNext(headerPrefix));

        root.getChildren().addAll(header, questionLabel, optionsBox, nextButton);
    }

    private void handleNext(String headerPrefix) {
        Toggle selected = optionGroup.getSelectedToggle();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an answer.").showAndWait();
            return;
        }

        RadioButton selectedButton = (RadioButton) selected;
        int index = ((VBox) selectedButton.getParent()).getChildren().indexOf(selectedButton);
        answers.add(index);

        if (currentIndex < test.getQuestions().size() - 1) {
            currentIndex++;
            showQuestion(headerPrefix);
        } else {
            onSubmit.accept(answers);
        }
    }
}