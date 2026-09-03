package class10;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import common.LessonContent;

public class QuadraticEquations extends Topic {

    // Concepts specific to this topic
    private static final Concept STANDARD_FORM = new Concept("QE1", "Standard Form of a Quadratic Equation");
    private static final Concept ROOTS = new Concept("QE2", "Roots of a Quadratic Equation");
    private static final Concept FACTORIZATION_METHOD = new Concept("QE3", "Solving by Factorization");
    private static final Concept QUADRATIC_FORMULA = new Concept("QE4", "The Quadratic Formula");
    private static final Concept DISCRIMINANT = new Concept("QE5", "Discriminant and Nature of Roots");
    private static final Concept BASIC_ALGEBRA = new Concept("QE6", "Basic Algebra");
    private static final Concept BASIC_MULTIPLICATION = new Concept("QE7", "Basic Multiplication");
    private static final Concept SQUARE_ROOTS = new Concept("QE8", "Square Roots");

    public QuadraticEquations() {
        super("Class 10", "Quadratic Equations");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "If x = 4, what is x squared?",
                        Arrays.asList("8", "12", "16", "20"),
                        2,
                        BASIC_MULTIPLICATION
                ),
                new Question(
                        "Solve for x: 2x = 10",
                        Arrays.asList("3", "4", "5", "6"),
                        2,
                        BASIC_ALGEBRA
                ),
                new Question(
                        "Simplify: 3x + 2x",
                        Arrays.asList("5x", "6x", "5x^2", "9x"),
                        0,
                        BASIC_ALGEBRA
                ),
                new Question(
                        "What is the square root of 49?",
                        Arrays.asList("6", "7", "8", "9"),
                        1,
                        SQUARE_ROOTS
                ),
                new Question(
                        "What is the square root of 81?",
                        Arrays.asList("7", "8", "9", "10"),
                        2,
                        SQUARE_ROOTS
                ),
                new Question(
                        "What is 6 x 7?",
                        Arrays.asList("36", "42", "48", "49"),
                        1,
                        BASIC_MULTIPLICATION
                ),
                new Question(
                        "Solve for x: x - 5 = 9",
                        Arrays.asList("12", "13", "14", "15"),
                        2,
                        BASIC_ALGEBRA
                ),
                new Question(
                        "What is 5 x 5?",
                        Arrays.asList("10", "20", "25", "30"),
                        2,
                        BASIC_MULTIPLICATION
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
                        "Which of these is the standard form of a quadratic equation?",
                        Arrays.asList("ax + b = 0", "ax^2 + bx + c = 0", "ax^3 + b = 0", "a/x + b = 0"),
                        1,
                        STANDARD_FORM
                ),
                new Question(
                        "In the equation 2x^2 + 5x + 3 = 0, what is the value of 'a'?",
                        Arrays.asList("2", "5", "3", "0"),
                        0,
                        STANDARD_FORM
                ),
                new Question(
                        "What is a 'root' of a quadratic equation?",
                        Arrays.asList("Any number you can substitute for x", "A value of x that makes the equation true", "The largest term in the equation", "The coefficient of x squared"),
                        1,
                        ROOTS
                ),
                new Question(
                        "A quadratic equation can have at most how many roots?",
                        Arrays.asList("1", "2", "3", "Unlimited"),
                        1,
                        ROOTS
                ),
                new Question(
                        "Factorize and solve: x^2 - 5x + 6 = 0",
                        Arrays.asList("x = 1, 6", "x = 2, 3", "x = -2, -3", "x = 5, 6"),
                        1,
                        FACTORIZATION_METHOD
                ),
                new Question(
                        "Factorize and solve: x^2 - 9 = 0",
                        Arrays.asList("x = 3, -3", "x = 9, -9", "x = 3 only", "x = 0, 9"),
                        0,
                        FACTORIZATION_METHOD
                ),
                new Question(
                        "What is the quadratic formula for ax^2 + bx + c = 0?",
                        Arrays.asList("x = -b ± √(b²-4ac) / 2a", "x = b ± √(b²+4ac) / 2a", "x = -b ± √(b²-4ac) / a", "x = -c ± √(b²-4ac) / 2a"),
                        0,
                        QUADRATIC_FORMULA
                ),
                new Question(
                        "Using the quadratic formula, solve x^2 + 5x + 6 = 0",
                        Arrays.asList("x = -2, -3", "x = 2, 3", "x = -1, -6", "x = 1, 6"),
                        0,
                        QUADRATIC_FORMULA
                ),
                new Question(
                        "What does the discriminant (b² - 4ac) tell us?",
                        Arrays.asList("The sum of the roots", "The nature (type) of the roots", "The value of x", "The product of a and c"),
                        1,
                        DISCRIMINANT
                ),
                new Question(
                        "If the discriminant is negative, the roots are:",
                        Arrays.asList("Real and equal", "Real and distinct", "Not real (no real roots)", "Always zero"),
                        2,
                        DISCRIMINANT
                ),
                new Question(
                        "If the discriminant equals zero, the roots are:",
                        Arrays.asList("Real and equal", "Real and distinct", "Not real", "Undefined"),
                        0,
                        DISCRIMINANT
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
                        STANDARD_FORM,
                        "A quadratic equation is an equation where the highest power of the variable is 2. "
                                + "Every quadratic equation can be written in a standard form with three terms.",
                        "ax^2 + bx + c = 0, where a ≠ 0",
                        Arrays.asList(
                                "2x^2 + 5x + 3 = 0 is in standard form, with a=2, b=5, c=3",
                                "x^2 - 4 = 0 is also quadratic, with a=1, b=0, c=-4"
                        )
                ),
                new LessonContent(
                        ROOTS,
                        "A root (or solution) of a quadratic equation is a value of x that makes the equation true. "
                                + "A quadratic equation has at most two roots, since its highest power is 2.",
                        null,
                        Arrays.asList(
                                "In x^2 - 5x + 6 = 0, the roots are x = 2 and x = 3, since substituting either makes the equation true"
                        )
                ),
                new LessonContent(
                        FACTORIZATION_METHOD,
                        "Factorization means rewriting the quadratic as a product of two simpler expressions (factors), "
                                + "then setting each factor to zero to find the roots.",
                        null,
                        Arrays.asList(
                                "x^2 - 5x + 6 = 0 factors into (x-2)(x-3) = 0, so x = 2 or x = 3",
                                "x^2 - 9 = 0 factors into (x-3)(x+3) = 0, so x = 3 or x = -3"
                        )
                ),
                new LessonContent(
                        QUADRATIC_FORMULA,
                        "When factorization is difficult, the quadratic formula solves any quadratic equation directly, "
                                + "using its a, b, and c values.",
                        "x = (-b ± √(b² - 4ac)) / 2a",
                        Arrays.asList(
                                "For x^2 + 5x + 6 = 0: a=1, b=5, c=6 -> x = (-5 ± √(25-24)) / 2 = (-5 ± 1) / 2 -> x = -2 or -3"
                        )
                ),
                new LessonContent(
                        DISCRIMINANT,
                        "The discriminant is the part of the quadratic formula under the square root. Its sign tells you "
                                + "how many real roots the equation has, without solving the whole equation.",
                        "Discriminant D = b² - 4ac",
                        Arrays.asList(
                                "If D > 0, there are two distinct real roots",
                                "If D = 0, there is exactly one real root (a repeated root)",
                                "If D < 0, there are no real roots"
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