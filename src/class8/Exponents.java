package class8;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Exponents extends Topic {

    // Lesson concepts
    private static final Concept EXPONENT_MEANING = new Concept("EX1", "Meaning of Base and Exponent");
    private static final Concept PRODUCT_QUOTIENT_LAW = new Concept("EX2", "Laws: Multiplying and Dividing Powers");
    private static final Concept POWER_LAWS = new Concept("EX3", "Laws: Power of a Power and Power of a Product");
    private static final Concept ZERO_NEGATIVE = new Concept("EX4", "Zero and Negative Exponents");
    private static final Concept STANDARD_FORM = new Concept("EX5", "Standard (Scientific) Form");

    // Prerequisite concepts
    private static final Concept MULTIPLICATION_BASICS = new Concept("EX6", "Repeated Multiplication");
    private static final Concept INTEGER_OPERATIONS = new Concept("EX7", "Operations on Integers");
    private static final Concept RECIPROCALS = new Concept("EX8", "Reciprocals");
    private static final Concept PLACE_VALUE = new Concept("EX9", "Powers of 10 and Place Value");

    public Exponents() {
        super("Class 8", "Exponents");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What is 2 x 2 x 2 x 2?",
                        Arrays.asList("8", "16", "24", "32"),
                        1,
                        MULTIPLICATION_BASICS
                ),
                new Question(
                        "What is 5 x 5 x 5?",
                        Arrays.asList("15", "75", "125", "25"),
                        2,
                        MULTIPLICATION_BASICS
                ),
                new Question(
                        "What is (-3) x (-3)?",
                        Arrays.asList("-9", "9", "-6", "6"),
                        1,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is (-2) x (-2) x (-2)?",
                        Arrays.asList("-8", "8", "-6", "6"),
                        0,
                        INTEGER_OPERATIONS
                ),
                new Question(
                        "What is the reciprocal of 5?",
                        Arrays.asList("5", "-5", "1/5", "0"),
                        2,
                        RECIPROCALS
                ),
                new Question(
                        "What is the reciprocal of 2/3?",
                        Arrays.asList("2/3", "3/2", "-3/2", "1/6"),
                        1,
                        RECIPROCALS
                ),
                new Question(
                        "What is 10 x 10 x 10?",
                        Arrays.asList("30", "100", "1000", "10000"),
                        2,
                        PLACE_VALUE
                ),
                new Question(
                        "How many zeros are there in 10,000?",
                        Arrays.asList("3", "4", "5", "10"),
                        1,
                        PLACE_VALUE
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
                        "In 3^4, the number 3 is called the ___ and 4 is called the ___.",
                        Arrays.asList("base, exponent", "exponent, base", "product, base", "base, product"),
                        0,
                        EXPONENT_MEANING
                ),
                new Question(
                        "What is the value of 2^5?",
                        Arrays.asList("10", "25", "32", "16"),
                        2,
                        EXPONENT_MEANING
                ),
                new Question(
                        "How is 7 x 7 x 7 x 7 written using exponents?",
                        Arrays.asList("7^3", "7^4", "4^7", "28"),
                        1,
                        EXPONENT_MEANING
                ),
                new Question(
                        "Simplify: 2^3 x 2^4",
                        Arrays.asList("2^12", "2^7", "4^7", "2^1"),
                        1,
                        PRODUCT_QUOTIENT_LAW
                ),
                new Question(
                        "Simplify: 5^6 ÷ 5^2",
                        Arrays.asList("5^3", "5^4", "5^8", "1^4"),
                        1,
                        PRODUCT_QUOTIENT_LAW
                ),
                new Question(
                        "Which law of exponents is correct?",
                        Arrays.asList("a^m x a^n = a^(m+n)", "a^m x a^n = a^(m x n)", "a^m x a^n = (2a)^(m+n)", "a^m x a^n = a^(m-n)"),
                        0,
                        PRODUCT_QUOTIENT_LAW
                ),
                new Question(
                        "Simplify: (3^2)^3",
                        Arrays.asList("3^5", "3^6", "3^8", "9^5"),
                        1,
                        POWER_LAWS
                ),
                new Question(
                        "Which expression is equal to (2 x 3)^2?",
                        Arrays.asList("2^2 x 3^2", "2 x 3^2", "2^2 + 3^2", "2^3 x 3^3"),
                        0,
                        POWER_LAWS
                ),
                new Question(
                        "(a^m)^n is equal to:",
                        Arrays.asList("a^(m+n)", "a^(m x n)", "a^(m-n)", "(a+n)^m"),
                        1,
                        POWER_LAWS
                ),
                new Question(
                        "What is the value of 7^0?",
                        Arrays.asList("0", "7", "1", "undefined"),
                        2,
                        ZERO_NEGATIVE
                ),
                new Question(
                        "a^(-n) is equal to:",
                        Arrays.asList("-a^n", "1/a^n", "a^n", "n/a"),
                        1,
                        ZERO_NEGATIVE
                ),
                new Question(
                        "What is the value of 2^(-3)?",
                        Arrays.asList("-8", "1/8", "-6", "1/6"),
                        1,
                        ZERO_NEGATIVE
                ),
                new Question(
                        "Write 4,500 in standard form.",
                        Arrays.asList("4.5 x 10^3", "45 x 10^2", "0.45 x 10^4", "4.5 x 10^2"),
                        0,
                        STANDARD_FORM
                ),
                new Question(
                        "Which is the standard form of 0.0072?",
                        Arrays.asList("7.2 x 10^-3", "72 x 10^-4", "7.2 x 10^3", "0.72 x 10^-2"),
                        0,
                        STANDARD_FORM
                ),
                new Question(
                        "Which number is written in standard form?",
                        Arrays.asList("12 x 10^4", "3.2 x 10^5", "0.5 x 10^3", "25.1 x 10^2"),
                        1,
                        STANDARD_FORM
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
                        EXPONENT_MEANING,
                        "An exponent tells you how many times to multiply a number by itself. In a^n, the number "
                                + "a is the base and n is the exponent (or power).",
                        "a^n = a x a x a ... (n times)",
                        Arrays.asList(
                                "2^5 = 2 x 2 x 2 x 2 x 2 = 32",
                                "7 x 7 x 7 x 7 is written as 7^4"
                        )
                ),
                new LessonContent(
                        PRODUCT_QUOTIENT_LAW,
                        "When the bases are the same, add the exponents to multiply powers and subtract the "
                                + "exponents to divide powers.",
                        "a^m x a^n = a^(m+n)     a^m ÷ a^n = a^(m-n)",
                        Arrays.asList(
                                "2^3 x 2^4 = 2^7 = 128",
                                "5^6 ÷ 5^2 = 5^4 = 625"
                        )
                ),
                new LessonContent(
                        POWER_LAWS,
                        "A power raised to another power multiplies the exponents. A power of a product gives "
                                + "each factor the same exponent.",
                        "(a^m)^n = a^(m x n)     (a x b)^m = a^m x b^m",
                        Arrays.asList(
                                "(3^2)^3 = 3^6 = 729",
                                "(2 x 3)^2 = 2^2 x 3^2 = 4 x 9 = 36"
                        )
                ),
                new LessonContent(
                        ZERO_NEGATIVE,
                        "Any non-zero number raised to the power 0 equals 1. A negative exponent means take the "
                                + "reciprocal of the base raised to the positive exponent.",
                        "a^0 = 1 (a ≠ 0)     a^(-n) = 1 / a^n",
                        Arrays.asList(
                                "7^0 = 1",
                                "2^(-3) = 1 / 2^3 = 1/8"
                        )
                ),
                new LessonContent(
                        STANDARD_FORM,
                        "Very large or very small numbers are written in standard (scientific) form as a number "
                                + "between 1 and 10 multiplied by a power of 10.",
                        "N = a x 10^n, where 1 ≤ a < 10",
                        Arrays.asList(
                                "4,500 = 4.5 x 10^3",
                                "0.0072 = 7.2 x 10^-3"
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