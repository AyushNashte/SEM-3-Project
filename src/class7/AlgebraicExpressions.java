package class7;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AlgebraicExpressions extends Topic {

    // ---- Lesson concepts ----
    private static final Concept TERMS        = new Concept("AE1", "Terms, Coefficients and Types");
    private static final Concept LIKE_TERMS   = new Concept("AE2", "Like and Unlike Terms");
    private static final Concept ADD_SUB      = new Concept("AE3", "Adding and Subtracting Expressions");
    private static final Concept EVALUATE     = new Concept("AE4", "Finding the Value of an Expression");
    private static final Concept FORMING      = new Concept("AE5", "Forming Expressions from Words");

    // ---- Prerequisite concepts (Class 6 knowledge) ----
    private static final Concept LETTERS      = new Concept("AE6", "Letters Stand for Numbers");
    private static final Concept BODMAS       = new Concept("AE7", "Order of Operations");
    private static final Concept INTEGER_OPS  = new Concept("AE8", "Integer Operations");
    private static final Concept REPEATED_ADD = new Concept("AE9", "Repeated Addition as Multiplication");

    public AlgebraicExpressions() {
        super("Class 7", "Algebraic Expressions");
    }

    // ==========================================================
    // 1. PREREQUISITE TEST
    // ==========================================================
    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question("If n = 6, what is the value of n + 4?",
                        Arrays.asList("2", "10", "64", "24"), 1, LETTERS),
                new Question("Which expression means 'a number y increased by 7'?",
                        Arrays.asList("7y", "y - 7", "y + 7", "7 - y"), 2, LETTERS),

                new Question("What is 2 + 3 × 4?",
                        Arrays.asList("20", "14", "24", "9"), 1, BODMAS),
                new Question("What is (8 - 2) × 3?",
                        Arrays.asList("18", "14", "22", "6"), 0, BODMAS),

                new Question("What is (-3) + 5?",
                        Arrays.asList("-8", "8", "2", "-2"), 2, INTEGER_OPS),
                new Question("What is (-4) × 3?",
                        Arrays.asList("12", "-7", "7", "-12"), 3, INTEGER_OPS),

                new Question("What is 12 + 12 + 12?",
                        Arrays.asList("24", "15", "36", "48"), 2, REPEATED_ADD),
                new Question("Which is the same as 5 + 5 + 5 + 5?",
                        Arrays.asList("5 × 5", "5 + 4", "20 + 5", "4 × 5"), 3, REPEATED_ADD)
        );
        return new Test(questions);
    }

    // ==========================================================
    // 2. LESSON TEST (3 per concept)
    // ==========================================================
    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                // ---- AE1: Terms and coefficients ----
                new Question("How many terms does 3x + 5 have?",
                        Arrays.asList("1", "2", "3", "5"), 1, TERMS),
                // Trap: the sign belongs to the coefficient, so it is -5, not 5
                new Question("What is the coefficient of y in 3x - 5y?",
                        Arrays.asList("5", "-5", "y", "3"), 1, TERMS),
                new Question("4a + 3b + 2c has three terms, so it is called a:",
                        Arrays.asList("Monomial", "Binomial", "Trinomial", "Constant"), 2, TERMS),

                // ---- AE2: Like and unlike terms ----
                new Question("Which pair are like terms?",
                        Arrays.asList("3x and 3y", "4x and 4xy", "5a and 2a", "6 and 6b"), 2, LIKE_TERMS),
                // Trap: the number in front (and its sign) does not matter, only the letters
                new Question("Which term is like 7xy?",
                        Arrays.asList("7x", "7y", "7xyz", "-3xy"), 3, LIKE_TERMS),
                new Question("Which terms in the list 3y, 5x, 4, -x are like 2x?",
                        Arrays.asList("3y and 4", "5x and -x", "only 5x", "4 and -x"), 1, LIKE_TERMS),

                // ---- AE3: Add and subtract ----
                new Question("3x + 5x = ?",
                        Arrays.asList("15x", "8x", "8x²", "35x"), 1, ADD_SUB),
                new Question("(2a + 3b) + (4a - b) = ?",
                        Arrays.asList("8ab", "6a + 4b", "6a - 2b", "6a + 2b"), 3, ADD_SUB),
                // Trap: subtracting changes the sign of EVERY term, so -(3x - 2) = -3x + 2
                new Question("Subtract (3x - 2) from (5x + 4).",
                        Arrays.asList("2x + 2", "2x - 6", "2x + 6", "8x + 2"), 2, ADD_SUB),

                // ---- AE4: Evaluate ----
                new Question("Find the value of 3x + 2 when x = 4.",
                        Arrays.asList("14", "9", "12", "24"), 0, EVALUATE),
                new Question("Find the value of 5a - 3 when a = 2.",
                        Arrays.asList("13", "2", "19", "7"), 3, EVALUATE),
                // Trap: 2(3) - (-2) = 6 + 2 = 8
                new Question("Find the value of 2x - y when x = 3 and y = -2.",
                        Arrays.asList("4", "10", "-4", "8"), 3, EVALUATE),

                // ---- AE5: Forming expressions ----
                new Question("A pencil costs Rs x. What is the cost of 6 pencils?",
                        Arrays.asList("x + 6", "x / 6", "6x", "6 - x"), 2, FORMING),
                new Question("Riya is y years old. Her brother is 4 years older. His age is:",
                        Arrays.asList("y - 4", "4y", "y / 4", "y + 4"), 3, FORMING),
                // Trap: 3(n - 5) would mean the 5 is taken away BEFORE multiplying
                new Question("'Three times a number n, decreased by 5' is written as:",
                        Arrays.asList("3 + n - 5", "3n - 5", "3(n - 5)", "n/3 - 5"), 1, FORMING)
        );
    }

    // ==========================================================
    // 3. RETEST
    // ==========================================================
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

    // ==========================================================
    // 4. LESSON CONTENT
    // ==========================================================
    @Override
    protected void teachLesson() {
        getLessonContentBank().forEach(LessonContent::display);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(
                        TERMS,
                        "An algebraic expression is made of numbers and letters (variables) joined by + and -. "
                                + "The parts separated by + or - are called terms. The number multiplying a variable is its "
                                + "coefficient, and it includes the sign in front of it. A term with no variable is a constant. "
                                + "An expression with 1 term is a monomial, with 2 terms a binomial, with 3 terms a trinomial.",
                        "Expression = term + term + ...",
                        Arrays.asList(
                                "4x - 3y + 7 has terms 4x, -3y and 7",
                                "In 4x - 3y + 7, the coefficient of x is 4 and the coefficient of y is -3",
                                "5a is a monomial, 4a + 3b is a binomial, 4a + 3b + 2c is a trinomial"
                        )
                ),
                new LessonContent(
                        LIKE_TERMS,
                        "Like terms have exactly the same variable part (the same letters). Only their coefficients "
                                + "may differ, and the signs do not matter. Terms with different letters are unlike terms. "
                                + "Only like terms can be added or subtracted into a single term.",
                        "Like terms: same letters.   Unlike terms: different letters.",
                        Arrays.asList(
                                "5a and -2a are like terms (both have a)",
                                "7xy and -3xy are like terms (both have xy)",
                                "3x and 3y are unlike terms (different letters)",
                                "4x and 4xy are unlike terms (xy is not the same as x)"
                        )
                ),
                new LessonContent(
                        ADD_SUB,
                        "To add expressions, group the like terms and add their coefficients. To subtract an "
                                + "expression, change the sign of every term in it and then add. The letter part stays the "
                                + "same when you add or subtract like terms.",
                        "ax + bx = (a + b)x",
                        Arrays.asList(
                                "3x + 5x = (3 + 5)x = 8x",
                                "(2a + 3b) + (4a - b) = (2a + 4a) + (3b - b) = 6a + 2b",
                                "7x - 2x = 5x",
                                "Subtract (3x - 2) from (5x + 4): (5x + 4) - (3x - 2) = 5x + 4 - 3x + 2 = 2x + 6"
                        )
                ),
                new LessonContent(
                        EVALUATE,
                        "To find the value of an expression, replace each variable with the given number and then "
                                + "follow the order of operations. When you replace a variable with a negative number, "
                                + "put it in brackets so the signs stay correct.",
                        "Substitute, then calculate (BODMAS)",
                        Arrays.asList(
                                "3x + 2 when x = 4: 3(4) + 2 = 12 + 2 = 14",
                                "5a - 3 when a = 2: 5(2) - 3 = 10 - 3 = 7",
                                "2x - y when x = 3 and y = -2: 2(3) - (-2) = 6 + 2 = 8"
                        )
                ),
                new LessonContent(
                        FORMING,
                        "To form an expression from words, pick a letter for the unknown number and translate the "
                                + "words: 'more than', 'increased by' and 'sum' mean +; 'less than', 'decreased by' and "
                                + "'difference' mean -; 'times' and 'product' mean multiplication; 'shared equally' means division. "
                                + "Be careful with the order, because 'decreased by' is applied last.",
                        "n more -> n + a,   n less -> n - a,   a times n -> an",
                        Arrays.asList(
                                "6 pencils at Rs x each cost 6x",
                                "A brother 4 years older than y is y + 4 years old",
                                "Three times n, decreased by 5, is 3n - 5",
                                "5 less than twice m is 2m - 5"
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
    public java.util.Optional<String> getGameId() {
        return java.util.Optional.of("term-catcher");
    }
}