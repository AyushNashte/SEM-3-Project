package class7;
import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class 7 - Algebraic Expressions
 * Framed as "mystery number" puzzles and pattern-building so 'x' feels like
 * a placeholder for something real, not an abstract symbol.
 */
public class AlgebraicExpressions extends Topic {

    private static final Concept VARIABLES_TERMS = new Concept("AE1", "Variables, Constants & Terms");
    private static final Concept LIKE_UNLIKE_TERMS = new Concept("AE2", "Like & Unlike Terms");
    private static final Concept FORMING_EXPRESSIONS = new Concept("AE3", "Forming Expressions");
    private static final Concept ADD_SUB_EXPRESSIONS = new Concept("AE4", "Adding & Subtracting Expressions");
    private static final Concept EVALUATING = new Concept("AE5", "Evaluating Expressions");

    public AlgebraicExpressions() {
        super("Class 7", "Algebraic Expressions");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "If x = 4, what is x + 3?",
                        Arrays.asList("6", "7", "8", "12"),
                        1,
                        VARIABLES_TERMS
                ),
                new Question(
                        "What is 5 x 6?",
                        Arrays.asList("11", "25", "30", "35"),
                        2,
                        EVALUATING
                ),
                new Question(
                        "What is 12 + 7 - 4?",
                        Arrays.asList("13", "15", "16", "19"),
                        1,
                        EVALUATING
                ),
                new Question(
                        "What comes next: 5, 10, 15, 20, ?",
                        Arrays.asList("22", "24", "25", "30"),
                        2,
                        FORMING_EXPRESSIONS
                ),
                new Question(
                        "If y = 2, what is 3y?",
                        Arrays.asList("3", "5", "6", "23"),
                        2,
                        VARIABLES_TERMS
                ),
                new Question(
                        "What is 9 - 5 + 2?",
                        Arrays.asList("2", "4", "6", "12"),
                        2,
                        EVALUATING
                )
        );
        return new Test(questions);
    }

    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question(
                        "In the expression 5x + 3, what is 'x' called?",
                        Arrays.asList("A constant", "A variable", "A coefficient", "An operator"),
                        1,
                        VARIABLES_TERMS
                ),
                new Question(
                        "In the term 7y, what is 7 called?",
                        Arrays.asList("Variable", "Constant", "Coefficient", "Exponent"),
                        2,
                        VARIABLES_TERMS
                ),
                new Question(
                        "How many terms are in the expression 3x + 2y - 5?",
                        Arrays.asList("1", "2", "3", "4"),
                        2,
                        VARIABLES_TERMS
                ),
                new Question(
                        "Which pair are LIKE terms?",
                        Arrays.asList("3x and 3y", "4x and 7x", "2x and 2x²", "5 and 5x"),
                        1,
                        LIKE_UNLIKE_TERMS
                ),
                new Question(
                        "Which of these is an UNLIKE term to 6ab?",
                        Arrays.asList("2ab", "-9ab", "7ba... (same as ab)", "3a"),
                        3,
                        LIKE_UNLIKE_TERMS
                ),
                new Question(
                        "Rahul is 'x' years old. His sister is 3 years younger. Which expression shows her age?",
                        Arrays.asList("x + 3", "3 - x", "x - 3", "3x"),
                        2,
                        FORMING_EXPRESSIONS
                ),
                new Question(
                        "A notebook costs ₹n. What is the cost of 5 notebooks?",
                        Arrays.asList("n + 5", "5n", "n - 5", "n/5"),
                        1,
                        FORMING_EXPRESSIONS
                ),
                new Question(
                        "Add: (3x + 5) + (2x + 1)",
                        Arrays.asList("5x + 6", "6x + 5", "5x + 5", "6x + 6"),
                        0,
                        ADD_SUB_EXPRESSIONS
                ),
                new Question(
                        "Subtract: (7y + 4) - (2y + 1)",
                        Arrays.asList("5y + 3", "5y + 5", "9y + 3", "5y - 3"),
                        0,
                        ADD_SUB_EXPRESSIONS
                ),
                new Question(
                        "Simplify: 4a + 3b + 2a - b",
                        Arrays.asList("6a + 2b", "6a + 4b", "2a + 2b", "6a - 2b"),
                        0,
                        ADD_SUB_EXPRESSIONS
                ),
                new Question(
                        "If x = 3, what is the value of 2x + 7?",
                        Arrays.asList("9", "10", "12", "13"),
                        3,
                        EVALUATING
                ),
                new Question(
                        "If a = 2 and b = 5, what is the value of 3a + 2b?",
                        Arrays.asList("13", "16", "19", "21"),
                        1,
                        EVALUATING
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
                        VARIABLES_TERMS,
                        "A variable is a letter (like x, y, a, b) that stands for a number we don't know yet "
                                + "-- think of it as a mystery box. A constant is a fixed number. A term is a "
                                + "single number, variable, or a product of numbers and variables, separated by "
                                + "+ or - signs.",
                        null,
                        Arrays.asList(
                                "In 4x + 7, '4x' and '7' are terms; x is the variable, 4 is the coefficient, 7 is the constant",
                                "'Your age in x years' uses x as a mystery box for an unknown number of years"
                        )
                ),
                new LessonContent(
                        LIKE_UNLIKE_TERMS,
                        "Like terms have the exact same variable(s) raised to the exact same power -- only "
                                + "their coefficients can differ. Unlike terms have different variables or "
                                + "different powers. Only like terms can be combined by adding or subtracting.",
                        null,
                        Arrays.asList(
                                "3x and -5x are like terms (both just 'x')",
                                "3x and 3x² are UNLIKE terms (different powers of x)",
                                "4xy and 9xy are like terms; 4xy and 9x are not"
                        )
                ),
                new LessonContent(
                        FORMING_EXPRESSIONS,
                        "To turn a word problem into an expression, pick a letter for the unknown quantity, "
                                + "then translate the words into math: 'more than' means +, 'less than' means -, "
                                + "'times'/'of' means multiply, and 'shared equally' means divide.",
                        null,
                        Arrays.asList(
                                "'5 more than a number n' -> n + 5",
                                "'A number x reduced by 8' -> x - 8",
                                "'Twice a number y, plus 3' -> 2y + 3"
                        )
                ),
                new LessonContent(
                        ADD_SUB_EXPRESSIONS,
                        "To add or subtract algebraic expressions, group and combine only the like terms. "
                                + "Keep unlike terms as they are -- they cannot be merged.",
                        null,
                        Arrays.asList(
                                "(2x + 3y) + (5x + y) = 7x + 4y",
                                "(6a + 4) - (2a + 1) = 4a + 3"
                        )
                ),
                new LessonContent(
                        EVALUATING,
                        "Evaluating an expression means replacing each variable with its given number value, "
                                + "then working out the arithmetic using the correct order of operations.",
                        null,
                        Arrays.asList(
                                "If x = 5: 3x + 2 = 3(5) + 2 = 17",
                                "If a = 2, b = 3: a² + 2b = 4 + 6 = 10"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts in Algebraic Expressions:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }
}