package class9;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import common.LessonContent;

public class Polynomials extends Topic {

    // Concepts specific to this topic
    private static final Concept WHAT_IS_POLYNOMIAL = new Concept("PN1", "What is a Polynomial");
    private static final Concept DEGREE_OF_POLYNOMIAL = new Concept("PN2", "Degree of a Polynomial");
    private static final Concept TYPES_BY_TERMS = new Concept("PN3", "Types by Number of Terms");
    private static final Concept TYPES_BY_DEGREE = new Concept("PN4", "Types by Degree");
    private static final Concept VALUE_OF_POLYNOMIAL = new Concept("PN5", "Value of a Polynomial");
    private static final Concept ZERO_OF_POLYNOMIAL = new Concept("PN6", "Zero of a Polynomial");
    private static final Concept ADD_SUB_POLYNOMIALS = new Concept("PN7", "Addition / Subtraction of Polynomials");
    private static final Concept MULTIPLY_POLYNOMIALS = new Concept("PN8", "Multiplication of Polynomials");
    private static final Concept REMAINDER_THEOREM = new Concept("PN9", "Remainder Theorem");
    private static final Concept FACTOR_THEOREM = new Concept("PN10", "Factor Theorem");
    private static final Concept EXPONENTS = new Concept("PN11", "Basic Exponents");
    private static final Concept LIKE_TERMS = new Concept("PN12", "Like and Unlike Terms");
    private static final Concept SUBSTITUTION = new Concept("PN13", "Basic Substitution");

    public Polynomials() {
        super("Class 9", "Polynomials");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What is 2 raised to the power 3 (2³)?",
                        Arrays.asList("6", "8", "9", "12"),
                        1,
                        EXPONENTS
                ),
                new Question(
                        "What is x² x x³ equal to?",
                        Arrays.asList("x⁵", "x⁶", "2x⁵", "x"),
                        0,
                        EXPONENTS
                ),
                new Question(
                        "Which of these are like terms?",
                        Arrays.asList("3x and 3y", "5x² and 5x", "7xy and 2xy", "4x and 4x²"),
                        2,
                        LIKE_TERMS
                ),
                new Question(
                        "In the expression 4x + 3y - 2x, which terms are like terms?",
                        Arrays.asList("4x and 3y", "4x and -2x", "3y and -2x", "None of them"),
                        1,
                        LIKE_TERMS
                ),
                new Question(
                        "If x = 2, what is the value of 3x + 1?",
                        Arrays.asList("5", "6", "7", "8"),
                        2,
                        SUBSTITUTION
                ),
                new Question(
                        "If x = 3, what is the value of x²?",
                        Arrays.asList("6", "9", "12", "3"),
                        1,
                        SUBSTITUTION
                ),
                new Question(
                        "If x = -1, what is the value of x + 5?",
                        Arrays.asList("4", "5", "6", "-6"),
                        0,
                        SUBSTITUTION
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
                        "A polynomial is an expression made up of variables and constants, connected by:",
                        Arrays.asList("Addition, subtraction, and multiplication only, with whole number exponents", "Division by a variable", "Square roots of variables", "Negative exponents"),
                        0,
                        WHAT_IS_POLYNOMIAL
                ),
                new Question(
                        "Which of the following is a polynomial?",
                        Arrays.asList("3x⁻¹ + 2", "2/x + 5", "3x² + 2x - 1", "√x + 4"),
                        2,
                        WHAT_IS_POLYNOMIAL
                ),
                new Question(
                        "The degree of a polynomial is:",
                        Arrays.asList("The number of terms in it", "The highest power of the variable in it", "The value of its constant term", "The number of variables in it"),
                        1,
                        DEGREE_OF_POLYNOMIAL
                ),
                new Question(
                        "What is the degree of the polynomial 5x³ + 2x² - x + 7?",
                        Arrays.asList("1", "2", "3", "7"),
                        2,
                        DEGREE_OF_POLYNOMIAL
                ),
                new Question(
                        "A polynomial with exactly one term is called a:",
                        Arrays.asList("Binomial", "Trinomial", "Monomial", "Polynomial of degree 1"),
                        2,
                        TYPES_BY_TERMS
                ),
                new Question(
                        "3x² + 5x - 2 is an example of a:",
                        Arrays.asList("Monomial", "Binomial", "Trinomial", "Constant"),
                        2,
                        TYPES_BY_TERMS
                ),
                new Question(
                        "A polynomial of degree 2 is called a:",
                        Arrays.asList("Linear polynomial", "Quadratic polynomial", "Cubic polynomial", "Constant polynomial"),
                        1,
                        TYPES_BY_DEGREE
                ),
                new Question(
                        "5x + 3 is an example of a:",
                        Arrays.asList("Constant polynomial", "Linear polynomial", "Quadratic polynomial", "Cubic polynomial"),
                        1,
                        TYPES_BY_DEGREE
                ),
                new Question(
                        "If p(x) = 2x² + 3x - 1, what is p(2)?",
                        Arrays.asList("9", "13", "5", "11"),
                        1,
                        VALUE_OF_POLYNOMIAL
                ),
                new Question(
                        "If p(x) = x² - 4, what is p(0)?",
                        Arrays.asList("-4", "0", "4", "-2"),
                        0,
                        VALUE_OF_POLYNOMIAL
                ),
                new Question(
                        "A zero of a polynomial p(x) is a value of x for which:",
                        Arrays.asList("p(x) is at its maximum", "p(x) = 1", "p(x) = 0", "p(x) is undefined"),
                        2,
                        ZERO_OF_POLYNOMIAL
                ),
                new Question(
                        "What is the zero of the polynomial p(x) = x - 5?",
                        Arrays.asList("0", "-5", "5", "1"),
                        2,
                        ZERO_OF_POLYNOMIAL
                ),
                new Question(
                        "Add: (3x² + 2x) + (x² - 5x)",
                        Arrays.asList("4x² - 3x", "4x² + 7x", "2x² - 3x", "4x² + 3x"),
                        0,
                        ADD_SUB_POLYNOMIALS
                ),
                new Question(
                        "Subtract: (5x + 4) - (2x + 1)",
                        Arrays.asList("3x + 3", "3x + 5", "7x + 3", "3x + 4"),
                        0,
                        ADD_SUB_POLYNOMIALS
                ),
                new Question(
                        "Multiply: (x + 2)(x + 3)",
                        Arrays.asList("x² + 5x + 6", "x² + 6x + 5", "x² + 5x + 5", "x² + 6"),
                        0,
                        MULTIPLY_POLYNOMIALS
                ),
                new Question(
                        "Multiply: 2x x (3x + 4)",
                        Arrays.asList("6x + 8x", "6x² + 8x", "5x² + 4x", "6x² + 4"),
                        1,
                        MULTIPLY_POLYNOMIALS
                ),
                new Question(
                        "The Remainder Theorem says that when p(x) is divided by (x - a), the remainder is:",
                        Arrays.asList("p(0)", "p(a)", "p(1)", "a"),
                        1,
                        REMAINDER_THEOREM
                ),
                new Question(
                        "If p(x) = x² + 3x + 2, what is the remainder when p(x) is divided by (x - 1)?",
                        Arrays.asList("2", "4", "6", "0"),
                        2,
                        REMAINDER_THEOREM
                ),
                new Question(
                        "According to the Factor Theorem, (x - a) is a factor of p(x) if and only if:",
                        Arrays.asList("p(a) = 1", "p(0) = a", "p(a) = 0", "p(-a) = 0"),
                        2,
                        FACTOR_THEOREM
                ),
                new Question(
                        "Is (x - 2) a factor of p(x) = x² - 4?",
                        Arrays.asList("Yes, because p(2) = 0", "No, because p(2) = 4", "Yes, because p(0) = -4", "Cannot be determined"),
                        0,
                        FACTOR_THEOREM
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
                        WHAT_IS_POLYNOMIAL,
                        "A polynomial is an algebraic expression made up of variables and constants, combined "
                                + "using addition, subtraction, and multiplication, where every variable has a whole number "
                                + "exponent (0, 1, 2, 3, ...).",
                        "p(x) = aₙxⁿ + aₙ₋₁xⁿ⁻¹ + ... + a₁x + a₀",
                        Arrays.asList(
                                "3x² + 2x - 1 is a polynomial",
                                "2/x + 5 and √x + 4 are NOT polynomials, since they don't have whole number exponents"
                        )
                ),
                new LessonContent(
                        DEGREE_OF_POLYNOMIAL,
                        "The degree of a polynomial is the highest power of the variable that appears in it, "
                                + "once the expression is fully simplified.",
                        "Degree = highest exponent of the variable",
                        Arrays.asList(
                                "5x³ + 2x² - x + 7 has degree 3",
                                "7 (a constant) has degree 0"
                        )
                ),
                new LessonContent(
                        TYPES_BY_TERMS,
                        "Polynomials are classified by how many terms they have: a monomial has one term, a "
                                + "binomial has two terms, and a trinomial has three terms.",
                        null,
                        Arrays.asList(
                                "5x is a monomial (1 term)",
                                "3x + 2 is a binomial (2 terms)",
                                "x² + 2x - 1 is a trinomial (3 terms)"
                        )
                ),
                new LessonContent(
                        TYPES_BY_DEGREE,
                        "Polynomials are also classified by their degree: degree 0 is a constant polynomial, "
                                + "degree 1 is linear, degree 2 is quadratic, and degree 3 is cubic.",
                        null,
                        Arrays.asList(
                                "5x + 3 is linear (degree 1)",
                                "2x² - 5 is quadratic (degree 2)",
                                "x³ + x is cubic (degree 3)"
                        )
                ),
                new LessonContent(
                        VALUE_OF_POLYNOMIAL,
                        "The value of a polynomial p(x) at a given number 'a' is found by substituting x = a "
                                + "into the expression and simplifying.",
                        "p(a) = value of p(x) when x = a",
                        Arrays.asList(
                                "If p(x) = 2x² + 3x - 1, then p(2) = 2(4) + 3(2) - 1 = 13",
                                "If p(x) = x² - 4, then p(0) = 0 - 4 = -4"
                        )
                ),
                new LessonContent(
                        ZERO_OF_POLYNOMIAL,
                        "A zero (or root) of a polynomial p(x) is a value of x for which p(x) equals zero.",
                        "p(x) = 0 → x is a zero of p(x)",
                        Arrays.asList(
                                "For p(x) = x - 5, since p(5) = 0, x = 5 is a zero",
                                "A polynomial can have more than one zero"
                        )
                ),
                new LessonContent(
                        ADD_SUB_POLYNOMIALS,
                        "To add or subtract polynomials, combine the like terms (terms with the same variable "
                                + "raised to the same power).",
                        null,
                        Arrays.asList(
                                "(3x² + 2x) + (x² - 5x) = 4x² - 3x",
                                "(5x + 4) - (2x + 1) = 3x + 3"
                        )
                ),
                new LessonContent(
                        MULTIPLY_POLYNOMIALS,
                        "To multiply polynomials, multiply every term in the first polynomial by every term in "
                                + "the second, then combine like terms.",
                        null,
                        Arrays.asList(
                                "(x + 2)(x + 3) = x² + 3x + 2x + 6 = x² + 5x + 6",
                                "2x(3x + 4) = 6x² + 8x"
                        )
                ),
                new LessonContent(
                        REMAINDER_THEOREM,
                        "The Remainder Theorem states that if a polynomial p(x) is divided by (x - a), the "
                                + "remainder of that division is equal to p(a), so you don't have to actually perform long division "
                                + "to find the remainder.",
                        "Remainder = p(a), when p(x) is divided by (x - a)",
                        Arrays.asList(
                                "For p(x) = x² + 3x + 2 divided by (x - 1), the remainder is p(1) = 1 + 3 + 2 = 6",
                                "This works for division by (x + a) too, using p(-a)"
                        )
                ),
                new LessonContent(
                        FACTOR_THEOREM,
                        "The Factor Theorem states that (x - a) is a factor of a polynomial p(x) if and only if "
                                + "p(a) = 0. It follows directly from the Remainder Theorem: a remainder of zero means the division "
                                + "is exact.",
                        "(x - a) is a factor of p(x) ⇔ p(a) = 0",
                        Arrays.asList(
                                "For p(x) = x² - 4, since p(2) = 4 - 4 = 0, (x - 2) is a factor",
                                "Since p(-2) = 4 - 4 = 0 as well, (x + 2) is also a factor"
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
