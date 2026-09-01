package class10;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import common.LessonContent;

public class ArithmeticProgression extends Topic {

    // Concepts specific to this topic
    private static final Concept SEQUENCES = new Concept("AP1", "What is a Sequence");
    private static final Concept AP_DEFINITION = new Concept("AP2", "What is an Arithmetic Progression");
    private static final Concept FIRST_TERM = new Concept("AP3", "First Term (a)");
    private static final Concept COMMON_DIFFERENCE = new Concept("AP4", "Common Difference (d)");
    private static final Concept NTH_TERM = new Concept("AP5", "General / nth Term");
    private static final Concept SUM_N_TERMS = new Concept("AP6", "Sum of First n Terms");
    private static final Concept ARITHMETIC_MEAN = new Concept("AP7", "Arithmetic Mean");
    private static final Concept NEGATIVE_NUMBERS = new Concept("AP8", "Positive and Negative Numbers");
    private static final Concept BASIC_MULTIPLICATION = new Concept("AP9", "Basic Multiplication");
    private static final Concept BASIC_ALGEBRA = new Concept("AP10", "Basic Algebra");

    public ArithmeticProgression() {
        super("Class 10", "Arithmetic Progression");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What comes next in the pattern 2, 4, 6, 8, ?",
                        Arrays.asList("9", "10", "12", "16"),
                        1,
                        SEQUENCES
                ),
                new Question(
                        "What comes next in the pattern 5, 10, 15, 20, ?",
                        Arrays.asList("22", "24", "25", "30"),
                        2,
                        SEQUENCES
                ),
                new Question(
                        "What is 15 - 22?",
                        Arrays.asList("-7", "7", "-8", "8"),
                        0,
                        NEGATIVE_NUMBERS
                ),
                new Question(
                        "What is -4 + 9?",
                        Arrays.asList("-13", "5", "-5", "13"),
                        1,
                        NEGATIVE_NUMBERS
                ),
                new Question(
                        "What is 7 x 6?",
                        Arrays.asList("36", "42", "48", "13"),
                        1,
                        BASIC_MULTIPLICATION
                ),
                new Question(
                        "If x = 3, what is the value of 2x + 5?",
                        Arrays.asList("8", "10", "11", "13"),
                        2,
                        BASIC_ALGEBRA
                ),
                new Question(
                        "Solve for x: x + 7 = 12",
                        Arrays.asList("3", "4", "5", "19"),
                        2,
                        BASIC_ALGEBRA
                ),
                new Question(
                        "What comes next in the pattern 1, 4, 7, 10, ?",
                        Arrays.asList("11", "12", "13", "14"),
                        2,
                        SEQUENCES
                )
        );
        return new Test(questions);
    }

    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    // Full bank kept separate so the retest can filter it by concept
    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question(
                        "Which of these is a sequence?",
                        Arrays.asList("A single number", "An ordered list of numbers following a rule", "A random set of numbers", "None of these"),
                        1,
                        SEQUENCES
                ),
                new Question(
                        "Which of the following is an Arithmetic Progression?",
                        Arrays.asList("2, 4, 8, 16", "1, 4, 9, 16", "3, 6, 9, 12", "1, 1, 2, 3, 5"),
                        2,
                        AP_DEFINITION
                ),
                new Question(
                        "In an AP, what stays the same between every pair of consecutive terms?",
                        Arrays.asList("Their product", "Their difference", "Their sum", "Their ratio"),
                        1,
                        AP_DEFINITION
                ),
                new Question(
                        "In the AP 5, 8, 11, 14, ... what is the first term (a)?",
                        Arrays.asList("3", "5", "8", "14"),
                        1,
                        FIRST_TERM
                ),
                new Question(
                        "In the AP 3, 7, 11, 15, ... what is the common difference (d)?",
                        Arrays.asList("3", "4", "7", "11"),
                        1,
                        COMMON_DIFFERENCE
                ),
                new Question(
                        "In the AP 20, 15, 10, 5, ... what is the common difference (d)?",
                        Arrays.asList("5", "-5", "15", "-15"),
                        1,
                        COMMON_DIFFERENCE
                ),
                new Question(
                        "The nth term of an AP is given by which formula?",
                        Arrays.asList("a + nd", "a + (n-1)d", "a x n x d", "n(a+d)"),
                        1,
                        NTH_TERM
                ),
                new Question(
                        "For the AP 2, 5, 8, 11, ... what is the 6th term?",
                        Arrays.asList("14", "17", "20", "23"),
                        1,
                        NTH_TERM
                ),
                new Question(
                        "Which formula gives the sum of the first n terms of an AP?",
                        Arrays.asList("n/2 x (2a + (n-1)d)", "a x n x d", "n(a+d)/2 x d", "a + (n-1)d"),
                        0,
                        SUM_N_TERMS
                ),
                new Question(
                        "Find the sum of the first 5 terms of the AP 2, 4, 6, 8, 10.",
                        Arrays.asList("20", "25", "30", "36"),
                        2,
                        SUM_N_TERMS
                ),
                new Question(
                        "What is the arithmetic mean of 8 and 14?",
                        Arrays.asList("10", "11", "12", "22"),
                        1,
                        ARITHMETIC_MEAN
                ),
                new Question(
                        "If 6 and x form an AP with 12 as the arithmetic mean between them, what is x?",
                        Arrays.asList("15", "16", "18", "20"),
                        2,
                        ARITHMETIC_MEAN
                )
        );
    }

    @Override
    protected Test getRetest(List<Concept> weakConcepts) {
        List<Question> filtered = getLessonQuestionBank().stream()
                .filter(q -> weakConcepts.contains(q.getConcept()))
                .collect(Collectors.toList());

        // Safety net: if for some reason no questions matched
        // (e.g. weakConcepts was empty), fall back to the full bank
        if (filtered.isEmpty()) {
            return new Test(getLessonQuestionBank());
        }
        return new Test(filtered);
    }

    @Override
    protected void teachLesson() {
        getLessonContentBank().forEach(LessonContent::display);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(
                        SEQUENCES,
                        "A sequence is an ordered list of numbers that follows a specific rule or pattern, "
                                + "where each number is called a term.",
                        null,
                        Arrays.asList(
                                "2, 4, 6, 8, 10, ... follows the rule 'add 2 to the previous term'",
                                "1, 3, 9, 27, ... follows the rule 'multiply the previous term by 3'"
                        )
                ),
                new LessonContent(
                        AP_DEFINITION,
                        "An Arithmetic Progression (AP) is a sequence in which the difference between "
                                + "any two consecutive terms is always the same. This constant difference is called "
                                + "the common difference.",
                        null,
                        Arrays.asList(
                                "3, 6, 9, 12, 15 is an AP because each term increases by 3",
                                "20, 15, 10, 5 is an AP because each term decreases by 5"
                        )
                ),
                new LessonContent(
                        FIRST_TERM,
                        "The first term of an AP, written as 'a', is simply the very first number in the sequence.",
                        "a = first term of the sequence",
                        Arrays.asList(
                                "In the AP 7, 11, 15, 19, ... the first term a = 7"
                        )
                ),
                new LessonContent(
                        COMMON_DIFFERENCE,
                        "The common difference, written as 'd', is the fixed amount added (or subtracted) "
                                + "to get from one term to the next. Find it by subtracting any term from the term after it.",
                        "d = (any term) - (previous term)",
                        Arrays.asList(
                                "In 3, 7, 11, 15, ... d = 7 - 3 = 4",
                                "In 20, 15, 10, 5, ... d = 15 - 20 = -5"
                        )
                ),
                new LessonContent(
                        NTH_TERM,
                        "The nth term (also called the general term) lets you find any term in the AP "
                                + "directly, without listing every term before it.",
                        "an = a + (n - 1)d",
                        Arrays.asList(
                                "For the AP 2, 5, 8, 11, ... find the 6th term: a=2, d=3, n=6 -> a6 = 2 + (6-1)(3) = 17"
                        )
                ),
                new LessonContent(
                        SUM_N_TERMS,
                        "The sum of the first n terms adds up all terms from the first term up to the nth term, "
                                + "using a shortcut formula instead of adding them one by one.",
                        "Sn = n/2 x (2a + (n - 1)d)",
                        Arrays.asList(
                                "Sum of first 5 terms of 2, 4, 6, 8, 10: a=2, d=2, n=5 -> S5 = 5/2 x (2x2 + (5-1)x2) = 30"
                        )
                ),
                new LessonContent(
                        ARITHMETIC_MEAN,
                        "The arithmetic mean between two numbers is the middle value that would make the "
                                + "three numbers form an AP.",
                        "Arithmetic Mean = (first number + second number) / 2",
                        Arrays.asList(
                                "Arithmetic mean of 8 and 14 = (8 + 14) / 2 = 11",
                                "If 6, x, 18 form an AP, x = (6 + 18) / 2 = 12"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }

    @Override
    public java.util.Optional<String> getVisualComponentId(Concept concept) {
        if (concept == COMMON_DIFFERENCE || concept == NTH_TERM) {
            return java.util.Optional.of("ap-common-difference-explorer");
        }
        return java.util.Optional.empty();
    }
}