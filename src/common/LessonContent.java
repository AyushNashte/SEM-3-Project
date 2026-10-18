package common;

import java.util.List;

public class LessonContent {
    private Concept concept;
    private String explanation;
    private String formula;
    private List<String> workedExamples;

    public LessonContent(Concept concept, String explanation, String formula, List<String> workedExamples) {
        this.concept = concept;
        this.explanation = explanation;
        this.formula = formula;
        this.workedExamples = workedExamples;
    }

    public Concept getConcept() {
        return concept;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getFormula() {
        return formula;
    }

    public List<String> getWorkedExamples() {
        return workedExamples;
    }

    public void display() {
        System.out.println("\n--- " + concept.getName() + " ---");
        System.out.println(explanation);
        if (formula != null) {
            System.out.println("Formula: " + formula);
        }
        if (workedExamples != null && !workedExamples.isEmpty()) {
            System.out.println("Worked Examples:");
            for (String example : workedExamples) {
                System.out.println("  - " + example);
            }
        }
    }
}