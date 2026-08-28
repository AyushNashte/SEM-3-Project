package class9;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import common.LessonContent;

public class RealNumbers extends Topic {

    // Concepts specific to this topic
    private static final Concept RATIONAL_NUMBERS = new Concept("RN1", "Rational Numbers");
    private static final Concept IRRATIONAL_NUMBERS = new Concept("RN2", "Irrational Numbers");
    private static final Concept REAL_NUMBERS = new Concept("RN3", "Real Numbers");
    private static final Concept DECIMAL_EXPANSION = new Concept("RN4", "Decimal Expansion (Terminating / Non-terminating)");
    private static final Concept SURDS = new Concept("RN5", "What is a Surd");
    private static final Concept ORDER_OF_SURD = new Concept("RN6", "Order of a Surd");
    private static final Concept SIMPLEST_FORM_SURD = new Concept("RN7", "Simplest Form of a Surd");
    private static final Concept COMPARING_SURDS = new Concept("RN8", "Comparing / Operations on Surds");
    private static final Concept RATIONALIZATION = new Concept("RN9", "Rationalization of Surds");
    private static final Concept FRACTIONS = new Concept("RN10", "Basic Fractions");
    private static final Concept SQUARE_ROOTS = new Concept("RN11", "Basic Square Roots");
    private static final Concept DIVISION = new Concept("RN12", "Basic Division");

    public RealNumbers() {
        super("Class 9", "Real Numbers");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What is 3/4 written as a decimal?",
                        Arrays.asList("0.25", "0.5", "0.75", "1.25"),
                        2,
                        FRACTIONS
                ),
                new Question(
                        "Which fraction is equivalent to 2/6?",
                        Arrays.asList("1/2", "1/3", "2/3", "3/6"),
                        1,
                        FRACTIONS
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
                        "What is 144 divided by 12?",
                        Arrays.asList("10", "11", "12", "14"),
                        2,
                        DIVISION
                ),
                new Question(
                        "What is 1 divided by 3, rounded to 2 decimal places?",
                        Arrays.asList("0.30", "0.33", "0.35", "0.66"),
                        1,
                        DIVISION
                ),
                new Question(
                        "Which of these is a whole number?",
                        Arrays.asList("-3", "0.5", "5", "2/3"),
                        2,
                        FRACTIONS
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
                        "A rational number is any number that can be written as:",
                        Arrays.asList("A never-ending, non-repeating decimal", "p/q where p and q are integers and q ≠ 0", "The square root of a negative number", "None of these"),
                        1,
                        RATIONAL_NUMBERS
                ),
                new Question(
                        "Which of the following is a rational number?",
                        Arrays.asList("√2", "π", "0.75", "√5"),
                        2,
                        RATIONAL_NUMBERS
                ),
                new Question(
                        "An irrational number is a number that:",
                        Arrays.asList("Can always be written as p/q", "Cannot be written as p/q, with a non-terminating, non-repeating decimal expansion", "Is always negative", "Is always a whole number"),
                        1,
                        IRRATIONAL_NUMBERS
                ),
                new Question(
                        "Which of the following is an irrational number?",
                        Arrays.asList("4/5", "√7", "0.333...", "6"),
                        1,
                        IRRATIONAL_NUMBERS
                ),
                new Question(
                        "The set of Real Numbers is made up of:",
                        Arrays.asList("Only rational numbers", "Only irrational numbers", "Rational numbers together with irrational numbers", "Only whole numbers"),
                        2,
                        REAL_NUMBERS
                ),
                new Question(
                        "Every point on the number line represents:",
                        Arrays.asList("A rational number only", "A real number", "An irrational number only", "A whole number only"),
                        1,
                        REAL_NUMBERS
                ),
                new Question(
                        "The decimal expansion of a rational number is always:",
                        Arrays.asList("Non-terminating and non-repeating", "Terminating or non-terminating repeating", "Never terminating", "Always a whole number"),
                        1,
                        DECIMAL_EXPANSION
                ),
                new Question(
                        "Which of these has a terminating decimal expansion?",
                        Arrays.asList("1/3", "1/7", "1/4", "2/11"),
                        2,
                        DECIMAL_EXPANSION
                ),
                new Question(
                        "In the surd ∛5, what is the order of the surd?",
                        Arrays.asList("2", "3", "5", "1"),
                        1,
                        ORDER_OF_SURD
                ),
                new Question(
                        "√12 in its simplest form is:",
                        Arrays.asList("2√3", "3√2", "4√3", "√12"),
                        0,
                        SIMPLEST_FORM_SURD
                ),
                new Question(
                        "Which surd is in its simplest form?",
                        Arrays.asList("√8", "√18", "√7", "√50"),
                        2,
                        SIMPLEST_FORM_SURD
                ),
                new Question(
                        "Which of these surds has the greatest value?",
                        Arrays.asList("√10", "√15", "√7", "√12"),
                        1,
                        COMPARING_SURDS
                ),
                new Question(
                        "What is 3√2 + 5√2?",
                        Arrays.asList("8√2", "8√4", "15√2", "2√8"),
                        0,
                        COMPARING_SURDS
                ),
                new Question(
                        "Rationalizing the denominator means:",
                        Arrays.asList("Making the numerator a whole number", "Removing the surd from the denominator", "Making the surd bigger", "Squaring the whole fraction"),
                        1,
                        RATIONALIZATION
                ),
                new Question(
                        "The rationalizing factor of √3 is:",
                        Arrays.asList("3", "√3", "-√3", "1/3"),
                        1,
                        RATIONALIZATION
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
                        RATIONAL_NUMBERS,
                        "A rational number is any number that can be expressed in the form p/q, where p and q "
                                + "are integers and q is not equal to zero. This includes whole numbers, fractions, "
                                + "and terminating or repeating decimals.",
                        "Rational number = p/q, where p, q are integers and q ≠ 0",
                        Arrays.asList(
                                "3/4, -5/2, 6, and 0.75 are all rational numbers",
                                "0.333... (repeating) = 1/3 is also rational"
                        )
                ),
                new LessonContent(
                        IRRATIONAL_NUMBERS,
                        "An irrational number cannot be written as p/q. Its decimal expansion goes on forever "
                                + "without ever repeating a pattern.",
                        null,
                        Arrays.asList(
                                "√2 = 1.41421356... never terminates or repeats",
                                "π = 3.14159265... is irrational"
                        )
                ),
                new LessonContent(
                        REAL_NUMBERS,
                        "The Real Numbers are the collection of all rational numbers and all irrational "
                                + "numbers put together. Every real number corresponds to a unique point on the number line, "
                                + "and every point on the number line corresponds to a real number.",
                        "Real Numbers = Rational Numbers ∪ Irrational Numbers",
                        Arrays.asList(
                                "-3, 0, 1/2, √2, and π are all real numbers",
                                "There is no number that is 'not real' on the number line"
                        )
                ),
                new LessonContent(
                        DECIMAL_EXPANSION,
                        "The decimal expansion of a rational number either terminates (ends) or repeats a "
                                + "pattern forever. The decimal expansion of an irrational number never terminates and never repeats.",
                        null,
                        Arrays.asList(
                                "1/4 = 0.25 terminates, so it is rational",
                                "1/3 = 0.333... repeats, so it is rational",
                                "√2 = 1.41421356... never repeats, so it is irrational"
                        )
                ),
                new LessonContent(
                        SURDS,
                        "A surd is an irrational root of a rational number that cannot be simplified to remove "
                                + "the root sign, written as the nth root of a number.",
                        "ⁿ√a is a surd if it is irrational",
                        Arrays.asList(
                                "√5 is a surd because 5 has no exact square root",
                                "√4 is NOT a surd because √4 = 2, a rational number"
                        )
                ),
                new LessonContent(
                        ORDER_OF_SURD,
                        "The order of a surd is the value of n in the nth root, ⁿ√a. It tells you which root "
                                + "(square root, cube root, etc.) is being taken.",
                        "Order of ⁿ√a is n",
                        Arrays.asList(
                                "In √7 (i.e. ²√7), the order is 2",
                                "In ∛10 (i.e. ³√10), the order is 3"
                        )
                ),
                new LessonContent(
                        SIMPLEST_FORM_SURD,
                        "A surd is in its simplest form when the number under the root has no perfect square "
                                + "(or perfect nth power) factor left inside it.",
                        null,
                        Arrays.asList(
                                "√12 = √(4 x 3) = 2√3, since 4 is a perfect square factor of 12",
                                "√18 = √(9 x 2) = 3√2, since 9 is a perfect square factor of 18"
                        )
                ),
                new LessonContent(
                        COMPARING_SURDS,
                        "Surds of the same order can be added or subtracted like ordinary terms by combining "
                                + "their coefficients, as long as the number under the root is the same. To compare surds, compare "
                                + "the numbers under the root sign.",
                        "a√x + b√x = (a + b)√x",
                        Arrays.asList(
                                "3√2 + 5√2 = 8√2",
                                "Since 15 > 10, √15 > √10"
                        )
                ),
                new LessonContent(
                        RATIONALIZATION,
                        "Rationalizing the denominator means removing the surd from the denominator of a "
                                + "fraction by multiplying the numerator and denominator by a suitable rationalizing factor, "
                                + "so the denominator becomes a rational number.",
                        "1/√a x √a/√a = √a/a",
                        Arrays.asList(
                                "1/√3 x √3/√3 = √3/3",
                                "The rationalizing factor of √5 is √5, since √5 x √5 = 5"
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
