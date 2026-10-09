package class8;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Factorisation extends Topic {

    // Lesson concepts
    private static final Concept WHAT_IS_FACTORISATION = new Concept("FA1", "What is Factorisation");
    private static final Concept COMMON_FACTOR = new Concept("FA2", "Taking Out the Common Factor");
    private static final Concept GROUPING = new Concept("FA3", "Factorising by Grouping");
    private static final Concept IDENTITIES = new Concept("FA4", "Factorising Using Identities");
    private static final Concept QUADRATIC_TRINOMIAL = new Concept("FA5", "Factorising x² + bx + c");

    // Prerequisite concepts
    private static final Concept HCF_BASICS = new Concept("FA6", "HCF of Numbers");
    private static final Concept EXPANDING_BRACKETS = new Concept("FA7", "Expanding Brackets");
    private static final Concept ALGEBRA_TERMS = new Concept("FA8", "Terms and Like Terms");
    private static final Concept SQUARES = new Concept("FA9", "Perfect Squares");

    public Factorisation() {
        super("Class 8", "Factorisation");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What is the HCF of 24 and 36?",
                        Arrays.asList("6", "12", "24", "72"),
                        1,
                        HCF_BASICS
                ),
                new Question(
                        "What is the HCF of 15 and 25?",
                        Arrays.asList("5", "3", "15", "75"),
                        0,
                        HCF_BASICS
                ),
                new Question(
                        "Expand: 3(x + 4)",
                        Arrays.asList("3x + 4", "x + 12", "3x + 12", "7x"),
                        2,
                        EXPANDING_BRACKETS
                ),
                new Question(
                        "Expand: 2x(x - 5)",
                        Arrays.asList("2x² - 10x", "2x² - 5", "2x - 10x", "x² - 10x"),
                        0,
                        EXPANDING_BRACKETS
                ),
                new Question(
                        "Expand: (x + 2)(x + 3)",
                        Arrays.asList("x² + 5", "x² + 6", "x² + 5x + 6", "2x + 5"),
                        2,
                        EXPANDING_BRACKETS
                ),
                new Question(
                        "Which pair are like terms?",
                        Arrays.asList("3x and 5x²", "2x and 2y", "3x and 5x", "x and x²"),
                        2,
                        ALGEBRA_TERMS
                ),
                new Question(
                        "What is x multiplied by x?",
                        Arrays.asList("2x", "x²", "x + x", "x³"),
                        1,
                        ALGEBRA_TERMS
                ),
                new Question(
                        "Which of these is a perfect square?",
                        Arrays.asList("40", "36", "50", "60"),
                        1,
                        SQUARES
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
                        "Writing an expression as a product of its factors is called:",
                        Arrays.asList("Expansion", "Factorisation", "Simplification", "Substitution"),
                        1,
                        WHAT_IS_FACTORISATION
                ),
                new Question(
                        "Which of the following is a factor of 12x?",
                        Arrays.asList("5", "3x", "x + 12", "12 + x"),
                        1,
                        WHAT_IS_FACTORISATION
                ),
                new Question(
                        "The factors of 5xy are:",
                        Arrays.asList("5 + x + y", "5x + y", "5, x and y", "only xy"),
                        2,
                        WHAT_IS_FACTORISATION
                ),
                new Question(
                        "Factorise: 4x + 8",
                        Arrays.asList("4(x + 8)", "2(x + 4)", "4(x + 2)", "8(x + 4)"),
                        2,
                        COMMON_FACTOR
                ),
                new Question(
                        "Factorise completely: 6x² + 9x",
                        Arrays.asList("3x(2x + 3)", "3(2x² + 3x)", "x(6x + 9)", "9x(x + 1)"),
                        0,
                        COMMON_FACTOR
                ),
                new Question(
                        "What is the greatest common factor of 12x²y and 18xy²?",
                        Arrays.asList("3xy", "6x²y²", "12xy", "6xy"),
                        3,
                        COMMON_FACTOR
                ),
                new Question(
                        "Factorise: ax + ay + bx + by",
                        Arrays.asList("(a + x)(b + y)", "ab(x + y)", "(a + b)(x + y)", "(a + y)(b + x)"),
                        2,
                        GROUPING
                ),
                new Question(
                        "Factorise: x² + xy + 3x + 3y",
                        Arrays.asList("(x + 3)(x - y)", "(x - 3)(x + y)", "x(x + y + 3)", "(x + 3)(x + y)"),
                        3,
                        GROUPING
                ),
                new Question(
                        "Grouping works when, after taking out a common factor from each group, the groups share:",
                        Arrays.asList("the same coefficient", "a common bracket (binomial factor)", "the same sign", "no terms at all"),
                        1,
                        GROUPING
                ),
                new Question(
                        "Factorise: x² - 49",
                        Arrays.asList("(x - 7)²", "(x + 7)²", "(x + 7)(x - 7)", "(x - 49)(x + 1)"),
                        2,
                        IDENTITIES
                ),
                new Question(
                        "Factorise: x² + 6x + 9",
                        Arrays.asList("(x + 9)(x + 1)", "(x + 3)²", "(x - 3)²", "(x + 6)(x + 3)"),
                        1,
                        IDENTITIES
                ),
                new Question(
                        "Factorise: 25x² - 16",
                        Arrays.asList("(5x + 4)(5x - 4)", "(5x - 4)²", "(25x + 16)(x - 1)", "5(5x - 16)"),
                        0,
                        IDENTITIES
                ),
                new Question(
                        "To factorise x² + bx + c, find two numbers whose product is c and whose sum is:",
                        Arrays.asList("c", "b", "b + c", "b x c"),
                        1,
                        QUADRATIC_TRINOMIAL
                ),
                new Question(
                        "Factorise: x² - 7x + 12",
                        Arrays.asList("(x - 3)(x - 4)", "(x + 3)(x + 4)", "(x - 2)(x - 6)", "(x - 1)(x - 12)"),
                        0,
                        QUADRATIC_TRINOMIAL
                ),
                new Question(
                        "Factorise: x² + 2x - 15",
                        Arrays.asList("(x + 3)(x - 5)", "(x - 3)(x - 5)", "(x + 5)(x - 3)", "(x + 15)(x - 1)"),
                        2,
                        QUADRATIC_TRINOMIAL
                )
        );
    }

    @Override
    protected Test getRetest(List<Concept> weakConcepts) {
        List<Question> filtered = getLessonQuestionBank().stream()
                .filter(q -> weakConcepts.contains(q.getConcept()))
                .collect(Collectors.toList());

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
                        WHAT_IS_FACTORISATION,
                        "Factorisation means writing an expression as a product of its factors. It is the reverse "
                                + "of expanding, where you multiply out brackets.",
                        "Expanding: a(b + c) = ab + ac     Factorising: ab + ac = a(b + c)",
                        Arrays.asList(
                                "12x = 3 x 4x, so 3 and 4x are factors of 12x",
                                "The factors of 5xy are 5, x and y"
                        )
                ),
                new LessonContent(
                        COMMON_FACTOR,
                        "Find the greatest common factor (GCF) of all the terms, then take it outside a bracket. "
                                + "Check your answer by expanding it again.",
                        "ab + ac = a(b + c)",
                        Arrays.asList(
                                "4x + 8 = 4(x + 2)",
                                "6x² + 9x = 3x(2x + 3)",
                                "The GCF of 12x²y and 18xy² is 6xy"
                        )
                ),
                new LessonContent(
                        GROUPING,
                        "When no factor is common to every term, group the terms in pairs, take a common factor "
                                + "out of each pair, and then look for a common bracket.",
                        null,
                        Arrays.asList(
                                "ax + ay + bx + by = a(x + y) + b(x + y) = (a + b)(x + y)",
                                "x² + xy + 3x + 3y = x(x + y) + 3(x + y) = (x + 3)(x + y)"
                        )
                ),
                new LessonContent(
                        IDENTITIES,
                        "Some expressions match a standard identity. Spotting the pattern lets you factorise "
                                + "straight away: a difference of two squares, or a perfect square.",
                        "a² - b² = (a + b)(a - b)     a² + 2ab + b² = (a + b)²     a² - 2ab + b² = (a - b)²",
                        Arrays.asList(
                                "x² - 49 = x² - 7² = (x + 7)(x - 7)",
                                "x² + 6x + 9 = (x + 3)²",
                                "25x² - 16 = (5x)² - 4² = (5x + 4)(5x - 4)"
                        )
                ),
                new LessonContent(
                        QUADRATIC_TRINOMIAL,
                        "To factorise x² + bx + c, find two numbers p and q whose product is c and whose sum is b. "
                                + "Then write the expression as (x + p)(x + q).",
                        "x² + bx + c = (x + p)(x + q), where p x q = c and p + q = b",
                        Arrays.asList(
                                "x² - 7x + 12: the numbers are -3 and -4 (product 12, sum -7), so (x - 3)(x - 4)",
                                "x² + 2x - 15: the numbers are 5 and -3 (product -15, sum 2), so (x + 5)(x - 3)"
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
}